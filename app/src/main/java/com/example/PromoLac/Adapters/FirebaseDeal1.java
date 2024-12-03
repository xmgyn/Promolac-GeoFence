package com.example.PromoLac.Adapters;

public class FirebaseDeal1 {
    String Name,Price,Location,Link;

    public FirebaseDeal1(String name, String price, String location, String link) {
        Name = name;
        Price = price;
        Location = location;
        Link = link;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
