package com.example.android.safey;

public class Report {
    private String comment;
    private String longitude;
    private String latitude;
    private String date;

    public Report(String comment, String longitude, String latitude, String date) {
        this.setComment(comment);
        this.setLongitude(longitude);
        this.setLatitude(latitude);
        this.setDate(date);
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
