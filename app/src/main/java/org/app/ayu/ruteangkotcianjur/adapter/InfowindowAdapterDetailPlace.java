package org.app.ayu.ruteangkotcianjur.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.app.ayu.ruteangkotcianjur.R;
import org.app.ayu.ruteangkotcianjur.data.DataAngkot;
import org.app.ayu.ruteangkotcianjur.data.DataInfowindow;
import org.app.ayu.ruteangkotcianjur.data.DataLocation;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkotPlace;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkotStreet;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkotStreetPlaceDirection;

public class InfowindowAdapterDetailPlace implements GoogleMap.InfoWindowAdapter {
    public InfowindowAdapterDetailPlace(Activity context) {
        this.context = context;
    }

    private Activity context;

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (marker.getTag() == null)
            return null;
        if (marker.getTag() instanceof DataInfowindow) {
            View view = context.getLayoutInflater()
                    .inflate(R.layout.infowindow_detail_place, null);
            DataInfowindow data = (DataInfowindow)marker.getTag();
            ((TextView) view.findViewById(R.id.txt_name)).setText(data.name);
            ((TextView) view.findViewById(R.id.txt_addr)).setText(data.address);
            ((TextView) view.findViewById(R.id.txt_phone)).setText(data.phone_number);

            return view;
        }

        if (marker.getTag() instanceof DataAngkot) {
            View view = context.getLayoutInflater()
                    .inflate(R.layout.infowindow_angkot, null);
            DataAngkot data = (DataAngkot)marker.getTag();
            ((TextView) view.findViewById(R.id.txt_name)).setText(marker.getTitle());
            ((TextView) view.findViewById(R.id.txt_angkot)).setText("Angkot : " + data.nama_angkot);
            ((TextView) view.findViewById(R.id.txt_ongkos)).setText("Ongkos : " + data.harga_angkot);

            return view;
        }

        return null;
    }
}
