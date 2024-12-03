package com.example.PromoLac.Adapters;

public class Deals {
    private String dealprice;
    private String dealtitle;
    private String dealimg;
    private String longitute,latitude,rating ;



    public Deals(String dealprice, String dealtitle, String dealimg) {
        this.dealprice = dealprice;
        this.dealtitle = dealtitle;
        this.dealimg = dealimg;

    }

    public Deals(String dealprice, String dealtitle, String dealimg, String longitute, String latitude, String rating) {
        this.dealprice = dealprice;
        this.dealtitle = dealtitle;
        this.dealimg = dealimg;
        this.longitute = longitute;
        this.latitude = latitude;
        this.rating = rating;
    }

    public String getLongitute() {
        return longitute;
    }

    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Deals() {

    }



    public String getDealprice() {
        return dealprice;
    }

    public void setDealprice(String dealprice) {
        this.dealprice = dealprice;
    }

    public String getDealtitle() {
        return dealtitle;
    }

    public void setDealtitle(String dealtitle) {
        this.dealtitle = dealtitle;
    }

    public String getDealimg() {
        return dealimg;
    }

    public void setDealimg(String dealimg) {
        this.dealimg = dealimg;
    }


}

