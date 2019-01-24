package com.example.gts.alaram;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.gts.alaram.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.touchboarder.weekdaysbuttons.WeekdaysDataSource;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;

import com.example.gts.alaram.database.DbConstants;
import com.example.gts.alaram.models.LocationModel;
import com.example.gts.alaram.models.TaskModel;
import com.example.gts.alaram.utils.AppUtils;
import com.example.gts.alaram.utils.DistanceUtils;
import com.example.gts.alaram.utils.WeekdayCodeUtils;
import com.example.gts.alaram.utils.firebase.AnalyticsConstants;


/**
 * Creates a new task and also responsible for editing an old one. For editing, we need to use
 * the getEditModeIntent() method to get the starting intent.
 *
 * @author vermayash8
 */
public class TaskCreatorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = TaskCreatorActivity.class.getSimpleName();

    /**
     * Since this activity serves both edit and add task operations, when this extra is set in
     * the calling intent, it will be started in edit mode.
     */
    private static final String EXTRA_EDIT_MODE_TASK_ID = "editTaskIdTaskCreatorActivity";

    /**
     * Request code constants.
     */
    private static final int REQUEST_CODE_PLACE_PICKER = 0;
    private static final int REQUEST_CODE_LOCATION_SELECTION = 1;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 2;
    private static final int REQUEST_CODE_GALLERY_IMAGE_PICKER = 4;

    private EditText taskNameInput;
    private TextView locationNameInput;
    private EditText reminderRangeInput;
    private EditText noteInput;
    private TextView startTimeTv, endTimeTv;
    private TextView startDateTv, endDateTv;
    private TextView unitsTv;
    private ImageView taskImageView, arrowAttachmentImage, arrowScheduleImage;
    private Switch alarmSwitch;
    private Switch anytimeSwitch;
    private Switch repeatSwitch;
    private ViewStub weekdaysStub;
    private LinearLayout selectLocationLayout, selectImageLayout, attachmentTitleLayout,
            scheduleTitleLayout, timeIntervalLayout, startTimeLayout, endTimeLayout,
            startDateLayout, endDateLayout, lockLayoutAttachment, lockLayoutSchdule;
    private FrameLayout scheduleFrameLayout, attachmentFrameLayout;
    private RecyclerView locationRecyclerView;
    private Button saveButton, upgradeAttachmentButton, upgradeScheduleButton;
    private WeekdaysDataSource wds;

    private FirebaseAnalytics mFirebaseAnalytics;

    /**
     * Tells if the task present is being edited or a new one is being created.
     */
    private TaskModel taskBeingEdited = null;

    /**
     * For keeping track of selected location.
     */
    private boolean hasSelectedLocation = false;
    private LocationModel mSelectedLocation;

    private TaskRepository mTaskRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creator);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mTaskRepository = new TaskRepository(getApplicationContext());

        setActionBar();
        // Find views and set click listeners.
        initializeViews();


        // check if activity has been started for editing a task.
        if (getIntent().hasExtra(EXTRA_EDIT_MODE_TASK_ID)) {
            long taskId = getIntent().getLongExtra(EXTRA_EDIT_MODE_TASK_ID, -1);
            taskBeingEdited = mTaskRepository.getTaskWithId(taskId);
            fillDataForEditing(taskBeingEdited);
            getSupportActionBar().setTitle(getString(R.string.title_edit_task));
        }

    }

    /**
     * This will be used to get the intent to start this activity when we need to edit the task.
     *
     * @param context context of the calling activity.
     * @param taskId  taskId of the task to be edited.
     * @return intent that can be used in startActivity.
     */
    public static Intent getEditModeIntent(Context context, long taskId) {
        Intent intent = new Intent(context, TaskCreatorActivity.class);
        intent.putExtra(EXTRA_EDIT_MODE_TASK_ID, taskId);
        return intent;
    }


    /**
     * Sets the toolbar as actionBar and also sets the up button.
     */
    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setElevation(0);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        }
    }


    /**
     * Finds views by id and sets OnClickListener to them.
     */
    private void initializeViews() {

        // initializing all views
        taskNameInput = findViewById(R.id.edit_text_task_name);
        locationNameInput = findViewById(R.id.editText_location_name);
        reminderRangeInput = findViewById(R.id.edit_text_reminder_range);
        noteInput = findViewById(R.id.edit_text_note);
        taskImageView = findViewById(R.id.image_selected_image);
        alarmSwitch = findViewById(R.id.switch_alarm);
        attachmentFrameLayout = findViewById(R.id.frame_layout_attachment);
        scheduleFrameLayout = findViewById(R.id.frame_layout_schedule);
        selectLocationLayout = findViewById(R.id.layout_select_location);
        selectImageLayout = findViewById(R.id.layout_select_image);

        unitsTv = findViewById(R.id.text_units);
        anytimeSwitch = findViewById(R.id.switch_anytime);
        timeIntervalLayout = findViewById(R.id.layout_time_interval);
        startTimeLayout = findViewById(R.id.layout_time_from);
        endTimeLayout = findViewById(R.id.layout_time_to);
        startTimeTv = findViewById(R.id.text_time_from);
        endTimeTv = findViewById(R.id.text_time_to);
        startDateLayout = findViewById(R.id.layout_date_from);
        endDateLayout = findViewById(R.id.layout_date_to);
        startDateTv = findViewById(R.id.text_date_from);
        endDateTv = findViewById(R.id.text_date_to);
        repeatSwitch = findViewById(R.id.switch_repeat);
        weekdaysStub = findViewById(R.id.viewStub_repeat);
        locationRecyclerView = findViewById(R.id.recycler_view_location);
        saveButton = findViewById(R.id.button_save);
        upgradeAttachmentButton = findViewById(R.id.button_upgrade_attachment);
        upgradeScheduleButton = findViewById(R.id.button_upgrade_schedule);
        lockLayoutAttachment = findViewById(R.id.ll_premium_overlay_lock_attachment);
        lockLayoutSchdule = findViewById(R.id.ll_premium_overlay_lock_schedule);


        selectImageLayout.setOnClickListener(this);
        startDateLayout.setOnClickListener(this);
        endDateLayout.setOnClickListener(this);
        startTimeLayout.setOnClickListener(this);
        endTimeLayout.setOnClickListener(this);
        selectLocationLayout.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        upgradeScheduleButton.setOnClickListener(this);
        upgradeAttachmentButton.setOnClickListener(this);
        lockLayoutAttachment.setOnClickListener(this);
        lockLayoutSchdule.setOnClickListener(this);

        // setting defaults and other settings

        // setting default distance range
        String defReminderRange = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.pref_distance_range_key),
                        getString(R.string.pref_distance_range_default));
        reminderRangeInput.setText(defReminderRange);

        // setting units
        setReminderRangeUnits();

        // setting anytime switch
        anytimeSwitch.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            timeIntervalLayout.setVisibility((isChecked) ? View.GONE : View.VISIBLE);
        }));


        repeatSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                weekdaysStub.setVisibility(isChecked ? View.VISIBLE : View.GONE));

        // setting weekday stub

    }

    /**
     * Specifies the action to be taken when a view is clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.layout_select_location:
                onPlacePickerRequested();
                break;
            case R.id.button_save:
                saveTask();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case REQUEST_CODE_PLACE_PICKER:
                if (resultCode == RESULT_OK) {
                    onPlacePickerSuccess(data);
                }
                break;

            default:
                Log.w(TAG, "Unknown request code in onActivityResult.");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Action to take when user selectes the image.
     */


    /**
     * sets units for reminder range.
     */
    private void setReminderRangeUnits() {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(this);
        String unitsPref = defaultPref.getString(getString(R.string.pref_unit_key), getString(R
                .string.pref_unit_default));
        if (unitsPref.equals(getString(R.string.pref_unit_metric))) {
            unitsTv.setText(getString(R.string.unit_metres));
        } else {
            unitsTv.setText(getString(R.string.unit_yards));
        }
    }




    /**
     * Called when user clicks on Date display.
     */


    /**
     * Triggered when the user clicks on the Pick Place button.
     */
    private void onPlacePickerRequested() {
        if (!isInternetConnected())
            return;
        PlacePicker.IntentBuilder placePickerIntent = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(placePickerIntent.build(this), REQUEST_CODE_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException e) {
            mFirebaseAnalytics.logEvent(AnalyticsConstants.PLACE_PICKER_EXCEPTION, new Bundle());
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            mFirebaseAnalytics.logEvent(AnalyticsConstants.PLACE_PICKER_FATAL, new Bundle());
            e.printStackTrace();
        }
    }

    /**
     * Checks for internet permission. If internet is not connected, it shows a snackbar and
     * return false.
     */
    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context
                .CONNECTIVITY_SERVICE);
        if (cm != null && cm.getActiveNetworkInfo() == null) {
            // No internet connection present. Show snackbar.
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R
                            .string.creator_no_internet_error),
                    Snackbar.LENGTH_SHORT);
            snackbar.show();
            return false;
        }
        return true;
    }

    /**
     * Initializes the location with place picker returned data. Also sets that to the UI.
     */
    private void onPlacePickerSuccess(Intent data) {
        Place place = PlacePicker.getPlace(this, data);
        // Create a new location object with use count = 1
        mSelectedLocation = new LocationModel(place.getName().toString(),
                place.getLatLng().latitude,
                place.getLatLng().longitude,
                1, 0, new LocalDate());
        hasSelectedLocation = true;
        onLocationSelected();
    }

    /**
     * Sets the selected location's name to the input textView.
     */
    private void onLocationSelected() {
        locationNameInput.setText(mSelectedLocation.getPlaceName());

    }






    /**
     * Validates the input entered by the user.
     */
    private boolean isInputValid() {
        String errorMsg = null;
        if (TextUtils.isEmpty(taskNameInput.getText())) {
            errorMsg = getString(R.string.creator_error_empty_taskname);
        } else if (TextUtils.isEmpty(locationNameInput.getText()) || !hasSelectedLocation) {
            errorMsg = getString(R.string.creator_error_empty_location);
        } else if (TextUtils.isEmpty(reminderRangeInput.getText())) {
            errorMsg = getString(R.string.creator_error_empty_range);
        } else if (repeatSwitch.isChecked() && (int) weekdaysStub.getTag() == 0) {
            errorMsg = getString(R.string.creator_error_no_weekday);
        } else if (!AppUtils.isReminderRangeValid(this, reminderRangeInput.getText().toString())) {

        } else {
            return true;
        }
        // If reminder range is not valid, no toast has to be shown. In that case, error msg will
        // be empty.
        if (errorMsg != null && !errorMsg.isEmpty()) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void saveTask() {
        if (!isInputValid()) {
            return;
        }
        String taskName = taskNameInput.getText().toString();
        String locationName = locationNameInput.getText().toString();
        int enteredReminderRange = Integer.parseInt(reminderRangeInput.getText().toString());
        int reminderRange = (int) DistanceUtils.getDistanceToSave(this, enteredReminderRange);
        boolean isAlarmEnabled = alarmSwitch.isChecked();
        long locationId;
        if (mSelectedLocation.getId() != 0
                && mSelectedLocation.getPlaceName().equals(locationName)) {
            // Location was selected from saved places and the name was not changed.
            // auto-increment numbering starts from 1.
            // We can also set place picker to return location with id = -1.
            locationId = mSelectedLocation.getId();
            // Since this location is already picked up from the database, we just need
            // to update the location use count.
            mSelectedLocation.setUseCount(mSelectedLocation.getUseCount() + 1);
            mTaskRepository.updateLocation(mSelectedLocation);
        } else {
            mSelectedLocation.setPlaceName(locationName);
            // Need to set this id because if it's a location chosen from saved places, it
            // already has an id that causes problems in inserting it again.
            mSelectedLocation.setId(0);
            // TODO: Check if place with same name already exists to improve UX.
            // Doing this when place picker gave the location. i.e. new location with use_count = 1.
            locationId = mTaskRepository.saveLocation(mSelectedLocation);
        }

        TaskModel task = new TaskModel.Builder(this, taskName, locationId)
                .setReminderRange(reminderRange)
                .setIsAlarmSet(isAlarmEnabled ? 1 : 0)
                .build();

        if (taskBeingEdited == null) {
            // add new task.
            mTaskRepository.saveTask(task);

        } else {
            // update task.
            task.setId(taskBeingEdited.getId());
            mTaskRepository.updateTask(task);
        }

        restartService();
        finish();
    }

    private void restartService() {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isAppEnabled = defaultPref.getString(getString(R.string.pref_status_key),
                getString(R.string.pref_status_default)).equals(getString(R.string
                .pref_status_enabled));
        if (isAppEnabled) {
            AppUtils.stopService(this);
            AppUtils.startService(this);
        }
    }


    private void fillDataForEditing(final TaskModel task) {
        taskNameInput.setText(task.getTaskName());
        // Set location
        mSelectedLocation = mTaskRepository.getLocationById(task.getLocationId());
        // Shows the location name and makes it visible.
        onLocationSelected();
        hasSelectedLocation = true;
        // Set reminder range
        int reminderRange = task.getReminderRange();
        String unitsPref = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.pref_unit_key), getString(R.string.pref_unit_default));
        if (!unitsPref.equals(getString(R.string.pref_unit_default))) {
            reminderRange = (int) Math.ceil(DistanceUtils.metersToYards(reminderRange));
        }
        reminderRangeInput.setText(String.valueOf(reminderRange));
        // Set note
        noteInput.setText(task.getNote());
        // Setup time.
        boolean anytime = task.getStartTime().equals(new LocalTime(0, 0))
                && task.getEndTime().equals(new LocalTime(23, 59));
        anytimeSwitch.setChecked(anytime);


        // Repeat options.

        }





    @Override
    protected void onStart() {
        super.onStart();
        // Setting in onStart so that when the upgrade activity closes, the lock layout refreshes
        // taking into account the purchase(if any).

    }


    }






