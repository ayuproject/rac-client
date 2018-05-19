package org.app.ayu.ruteangkotcianjur.data.listview;

import org.app.ayu.ruteangkotcianjur.data.DataAngkot;

/**
 * Created by anon999 on 4/13/2018.
 */

public class DLVAngkot extends DataAngkot {
    public DLVAngkot(String id, String nama, String allStreet) {
        super(id, nama);
        this.all_street = allStreet;
    }
    public String all_street;
}
