package org.app.ayu.ruteangkotcianjur.data.listview;

import org.app.ayu.ruteangkotcianjur.data.DataAngkot;
import org.app.ayu.ruteangkotcianjur.data.DataLocationStreet;
import org.app.ayu.ruteangkotcianjur.data.DataLocation;

/**
 * Created by anon999 on 4/13/2018.
 */

public class DLVAngkotStreetPlaceDirection extends DataAngkot {

    public DLVAngkotStreetPlaceDirection(
            String id,
            String nama,
            DataLocation dataLocation1,
            DataLocation dataLocation2) {
        super(id, nama);
        this.data_location1 = dataLocation1;
        this.data_location2 = dataLocation2;
    }

    public DataLocation data_location1;
    public DataLocation data_location2;
}
