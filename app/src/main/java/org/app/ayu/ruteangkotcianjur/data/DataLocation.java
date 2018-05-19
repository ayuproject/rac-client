package org.app.ayu.ruteangkotcianjur.data;
import com.google.android.gms.maps.model.LatLng;
/**
 * Created by anon999 on 4/13/2018.
 */

public class DataLocation {
    public DataLocation(String id, String nama, LatLng location) {
        this.id_ = id;
        this.nama = nama;
        this.location = location;
    }
    public String id_;
    public String nama;
    public LatLng location;
}
