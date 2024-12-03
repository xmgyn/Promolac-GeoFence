package com.example.PromoLac.Adapters;

public class Items {
    private String label;
    private int Image;

    public Items(String label, int image) {
        this.label = label;
        Image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
