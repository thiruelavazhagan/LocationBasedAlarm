package com.example.gts.alaram.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.joda.time.LocalDate;

import com.example.gts.alaram.database.converters.DateConverter;


/**
 * @author shilpi
 */

@Entity(tableName = "locations")
@TypeConverters({DateConverter.class})
public class LocationModel {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "place_name")
    private String placeName;

    private double latitude;

    private double longitude;

    @ColumnInfo(name = "use_count")
    private int useCount;

    @ColumnInfo(name = "is_hidden")
    private int isHidden;

    @ColumnInfo(name = "date_added")
    private LocalDate dateAdded;

    @Ignore
    public LocationModel() {

    }

    public LocationModel(String placeName, double latitude, double longitude, int useCount, int isHidden, LocalDate dateAdded) {
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.useCount = useCount;
        this.isHidden = isHidden;
        this.dateAdded = dateAdded;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public int getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(int isHidden) {
        this.isHidden = isHidden;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }
}
