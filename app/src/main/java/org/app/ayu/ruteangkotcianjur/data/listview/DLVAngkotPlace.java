package org.app.ayu.ruteangkotcianjur.data.listview;

import org.app.ayu.ruteangkotcianjur.data.DataAngkot;
import org.app.ayu.ruteangkotcianjur.data.DataLocation;

/**
 * Created by anon999 on 4/13/2018.
 */

public class DLVAngkotPlace extends DataAngkot {
    public DLVAngkotPlace(
            String idAngkot,
            String namaAngkot,
            DataLocation place) {
        super(idAngkot, namaAngkot);
        this.place = place;
    }
    public DataLocation place;
}
