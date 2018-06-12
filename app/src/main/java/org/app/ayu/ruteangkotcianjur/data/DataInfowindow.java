package org.app.ayu.ruteangkotcianjur.data;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class DataInfowindow {

    public DataInfowindow(String name, String address, String phoneNumber, LatLng location, Bitmap icon) {
        this.name = name;
        this.address = address;
        this.phone_number = phoneNumber;
        this.location = location;
        this.icon = icon;
    }

    public String name;
    public String address;
    public String phone_number;
    public LatLng location;
    public Bitmap icon;

}
