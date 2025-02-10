package com.example.vcartbusbooking;

import android.content.Context;

public class Item {
    String travelsname;
    int price;
    String  pickuptiming;
    String  totiming;

    public Item(String travelsname, int price, String pickuptiming, String totiming) {
        this.travelsname = travelsname;
        this.price = price;
        this.pickuptiming = pickuptiming;
        this.totiming = totiming;
    }

    public String getTravelsname() {
        return travelsname;
    }

    public void setTravelsname(String travelsname) {
        this.travelsname = travelsname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPickuptiming() {
        return pickuptiming;
    }

    public void setPickuptiming(String pickuptiming) {
        this.pickuptiming = pickuptiming;
    }

    public String getTotiming() {
        return totiming;
    }

    public void setTotiming(String totiming) {
        this.totiming = totiming;
    }
}

