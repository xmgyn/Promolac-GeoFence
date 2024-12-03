package com.example.PromoLac.Payment;

public class PaymentDetails {
    String dealname, promocode,time;
    int count;

    public PaymentDetails(String dealname, String promocode, String time, int count) {
        this.dealname = dealname;
        this.promocode = promocode;
        this.time = time;
        this.count = count;
    }


    public PaymentDetails() { }

    public String getDealname() {
        return dealname;
    }

    public void setDealname(String dealname) {
        this.dealname = dealname;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
