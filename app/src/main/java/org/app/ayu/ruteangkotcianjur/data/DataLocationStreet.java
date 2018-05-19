package org.app.ayu.ruteangkotcianjur.data;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by anon999 on 4/13/2018.
 */

public class DataLocationStreet extends DataLocation {
    public DataLocationStreet(String id, String nama, LatLng location, LatLng northeast, LatLng southwest) {
        super(id, nama, location);
        this.northeast = northeast;
        this.southwest = southwest;
    }
    public LatLng northeast;
    public LatLng southwest;
}
