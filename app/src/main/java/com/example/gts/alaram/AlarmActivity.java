package com.example.gts.alaram;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.gts.alaram.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.analytics.FirebaseAnalytics;

import com.example.gts.alaram.models.LocationModel;
import com.example.gts.alaram.models.TaskModel;
import com.example.gts.alaram.utils.DistanceUtils;
import com.example.gts.alaram.utils.TaskActionUtils;
import com.example.gts.alaram.utils.alarm.AlarmRinger;
import com.example.gts.alaram.utils.alarm.AlarmVibrator;
import com.example.gts.alaram.utils.alarm.VoiceAlarmRinger;
import com.example.gts.alaram.utils.firebase.AnalyticsConstants;

public class AlarmActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = AlarmActivity.class.getSimpleName();
    private static final String EXTRA_TASK_ID = "taskIdForAlarm";
    private AlarmVibrator mAlarmVibrator;
    private AlarmRinger mAlarmRinger;
    private VoiceAlarmRinger mVoiceAlarmRinger;
    private TaskRepository mTaskRepository;
    private TaskModel mTask;
    private LocationModel mTaskLocation;
    private FirebaseAnalytics mFirebaseAnalytics;
    private boolean isVoiceReminderEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setWindowFlags();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        long taskId = getIntent().getLongExtra(EXTRA_TASK_ID, -1);
        if (taskId == -1) {
            Log.w(TAG, "No task id has been passed.");
            return;
        }

        // Fetch data.
        mTaskRepository = new TaskRepository(getApplicationContext());
        mTask = mTaskRepository.getTaskWithId(taskId);
        mTaskLocation = mTaskRepository.getLocationById(mTask.getLocationId());

        // Check if voice reminders are enabled.
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(this);
        isVoiceReminderEnabled = defaultPref.getBoolean(getString(R.string
                .pref_voice_alarm_key), false);
        if (isVoiceReminderEnabled) {
            mVoiceAlarmRinger = new VoiceAlarmRinger(this, mTask, mTaskLocation);
        } else {
            // Initialize the ringer and vibrator.
            mAlarmVibrator = new AlarmVibrator(this);
            mAlarmRinger = new AlarmRinger(this);
        }


        setClickListeners();

        // Analytics.
        mFirebaseAnalytics.logEvent(AnalyticsConstants.ANALYTICS_ALARM_RING, new Bundle());
    }

    /**
     * This will be used to generate the intent containing taskId when starting AlarmActivity.
     *
     * @param context context of the calling activity.
     * @param taskId  taskId for which alarm will ring.
     * @return the intent that can be passed to startActivity() method.
     */
    public static Intent getStartingIntent(@NonNull Context context, long taskId) {
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }

    /**
     * To show this activity even when the screen is locked.
     */
    private void setWindowFlags() {
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= 27) {
            // In API level 27 setting these via window flags is deprecated.
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    private void setDataToUi() {
        // Find Views by id.
        TextView taskNameView = findViewById(R.id.text_task_name);
        TextView locationNameView = findViewById(R.id.text_location_name);
        TextView lastDistanceView = findViewById(R.id.text_last_distance);

     //   ImageView coverImageView = findViewById(R.id.imageViewCover);

        // Set taskDetails.
        taskNameView.setText(mTask.getTaskName());
        locationNameView.setText(mTaskLocation.getPlaceName());
        lastDistanceView.setText(DistanceUtils.getFormattedDistanceString(this, mTask
                .getLastDistance()));


    }



    private void setClickListeners() {
        findViewById(R.id.button_mark_done).setOnClickListener(v -> {
            TaskActionUtils.onTaskMarkedDone(getApplicationContext(), mTask);
            mFirebaseAnalytics.logEvent(AnalyticsConstants.ANALYTICS_ALARM_MARK_DONE, new Bundle());
            finish();
        });

    }



    /**
     * Centers the map to the current location and adds a marker to it.
     */
    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null) {
            Log.w(TAG, "onMapReady: null map returned");
            return;
        }
        googleMap.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(mTaskLocation.getLatitude(), mTaskLocation.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        // Remove any pre-existing markers.
        googleMap.clear();
        // Set marker.
        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_red_a400_36dp))
                .anchor(0.5f, 1.0f) // bottom middle corner.
                .position(latLng));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isVoiceReminderEnabled) {
            mVoiceAlarmRinger.startVoiceAlarms();
        } else {
            mAlarmVibrator.startVibrating();
            mAlarmRinger.startRinging();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isVoiceReminderEnabled) {
            mVoiceAlarmRinger.stopVoiceAlarms();
        } else {
            mAlarmVibrator.stopVibrationg();
            mAlarmRinger.stopRinging();
        }
    }
}
