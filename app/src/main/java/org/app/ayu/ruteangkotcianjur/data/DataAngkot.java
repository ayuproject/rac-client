package org.app.ayu.ruteangkotcianjur.data;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by anon999 on 4/13/2018.
 */

public class DataAngkot {
    public DataAngkot(String id, String nama, String harga, boolean satuJalur, ArrayList<LatLng> rute, ArrayList<DataLocationStreet> street, ArrayList<DataLocation> place) {
        this.id_angkot = id;
        this.nama_angkot = nama;
        this.harga_angkot = harga;
        this.satu_jalur = satuJalur;
        this.rute_angkot = rute;
        this.street_angkot = street;
        this.place_angkot = place;
    }

    public DataAngkot(String id, String nama, String harga, boolean satuJalur, LatLng[] rute, DataLocationStreet[] street, DataLocation[] place) {
        this.id_angkot = id;
        this.nama_angkot = nama;
        this.harga_angkot = harga;
        this.satu_jalur = satuJalur;
        this.rute_angkot = new ArrayList<>(Arrays.asList(rute));
        this.street_angkot = new ArrayList<>(Arrays.asList(street));
        this.place_angkot = new ArrayList<>(Arrays.asList(place));
    }

    //for ListviewAngkotJalan
    public DataAngkot(String id, String nama) {
        this.id_angkot = id;
        this.nama_angkot = nama;
        this.harga_angkot = "";
        this.satu_jalur = false;
        this.rute_angkot = null;
        this.street_angkot = null;
        this.place_angkot = null;
    }

    public void copyData(DataAngkot data) {
        if (data == null)
            return;
        this.id_angkot = data.id_angkot;
        this.nama_angkot = data.nama_angkot;
        this.harga_angkot = data.harga_angkot;
        this.satu_jalur = data.satu_jalur;
        this.rute_angkot = data.rute_angkot;
        this.street_angkot = data.street_angkot;
        this.place_angkot = data.place_angkot;
    }

    public String id_angkot;
    public String nama_angkot;
    public String harga_angkot;
    public boolean satu_jalur;
    public ArrayList<LatLng> rute_angkot;
    public ArrayList<DataLocationStreet> street_angkot;
    public ArrayList<DataLocation> place_angkot;
    public String getAppenedStreet() {
        if (street_angkot == null)
            return "";
        String res = "";
        for (int i = 0; i < street_angkot.size(); ++i)
            res += i == 0 ? street_angkot.get(i).nama : " -- " + street_angkot.get(i).nama;
        return res;
    }

    public String getAppendedPlace() {
        if (place_angkot == null)
            return "";
        String res = "";
        for (int i = 0; i < place_angkot.size(); ++i)
            res += i == 0 ? place_angkot.get(i).nama : " -- " + place_angkot.get(i).nama;
        return res;
    }
}
