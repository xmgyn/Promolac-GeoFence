package com.example.PromoLac.Adapters;

public class Fence_ {
    private String Latitude;
    private String Longitude;
    private String Notification_;
    private String Radius;
    private String TimeStamp;

    public Fence_() {
    }

    public Fence_(String latitude, String longitude, String notification_, String radius, String timeStamp) {
        Latitude = latitude;
        Longitude = longitude;
        Notification_ = notification_;
        Radius = radius;
        this.TimeStamp = timeStamp;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getNotification_() {
        return Notification_;
    }

    public void setNotification_(String notification_) {
        Notification_ = notification_;
    }

    public String getRadius() {
        return Radius;
    }

    public void setRadius(String radius) {
        Radius = radius;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Fence_)) {
            return false;
        }

        Fence_ other = (Fence_) obj;

        return Latitude.compareTo(other.Latitude)==0 && Longitude.compareTo(other.Longitude)==0 && Radius.compareTo(other.Radius) == 0 && Notification_.compareTo(other.Notification_)==0;
    }

}
