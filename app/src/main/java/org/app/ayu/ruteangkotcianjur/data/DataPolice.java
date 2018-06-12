package org.app.ayu.ruteangkotcianjur.data;

import com.google.android.gms.maps.model.MarkerOptions;

public class DataPolice {
    public DataPolice(DataInfowindow dataInfowindow) {
        this.dataInfowindow = dataInfowindow;
        this.isInfoWindown = false;
    }

    public DataInfowindow dataInfowindow;
    public boolean isInfoWindown;
}
