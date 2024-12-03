package com.example.PromoLac.NotificationLogs;

import android.graphics.Bitmap;

public class Model {
    String name;
    Bitmap image;
    String Tiltle;
    String Time;

    public Model(String name, Bitmap image, String tiltle, String time) {
        this.name = name;
        this.image = image;
        Tiltle = tiltle;
        Time = time;
    }
    public Model(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTiltle() {
        return Tiltle;
    }

    public void setTiltle(String tiltle) {
        Tiltle = tiltle;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
