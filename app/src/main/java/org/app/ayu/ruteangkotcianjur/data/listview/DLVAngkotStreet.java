package org.app.ayu.ruteangkotcianjur.data.listview;

import org.app.ayu.ruteangkotcianjur.data.DataAngkot;
import org.app.ayu.ruteangkotcianjur.data.DataLocationStreet;

/**
 * Created by anon999 on 4/13/2018.
 */

public class DLVAngkotStreet extends DataAngkot {

    public DLVAngkotStreet(
            String idAngkot,
            String namaAngkot,
            DataLocationStreet street) {
        super(idAngkot, namaAngkot);
        this.street = street;
    }

    public DataLocationStreet street;
}
