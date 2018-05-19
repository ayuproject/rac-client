package org.app.ayu.ruteangkotcianjur.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.app.ayu.ruteangkotcianjur.R;
import org.app.ayu.ruteangkotcianjur.data.DataAngkot;
import org.app.ayu.ruteangkotcianjur.data.DataLocationStreet;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkot;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkotPlace;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkotStreet;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkotStreetPlaceDirection;

import java.util.ArrayList;

/**
 * Created by anon999 on 4/14/2018.
 */

public class ListViewAdapterAngkot extends ArrayAdapter<DataAngkot> {

    public ListViewAdapterAngkot(ArrayList<DataAngkot> datas, Activity context, int resouces) {
        super(context, resouces, datas);
        this.context = context;
        this.dataAngkots = datas;
        this.resouces = resouces;
    }

    private Activity context;
    private int resouces;
    private ArrayList<DataAngkot> dataAngkots;

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(resouces, null, true);

        if (dataAngkots.get(position) instanceof DLVAngkot) {
            ((TextView)rowView.findViewById(R.id.txt_angkot)).setText(((DLVAngkot)dataAngkots.get(position)).nama_angkot);
            ((TextView)rowView.findViewById(R.id.txt_location)).setText(((DLVAngkot)dataAngkots.get(position)).all_street);
        } else if (dataAngkots.get(position) instanceof DLVAngkotStreet) {
            ((TextView)rowView.findViewById(R.id.txt_angkot)).setText(((DLVAngkotStreet)dataAngkots.get(position)).nama_angkot);
            ((TextView)rowView.findViewById(R.id.txt_location)).setText(((DLVAngkotStreet)dataAngkots.get(position)).street.nama);
        } else if (dataAngkots.get(position) instanceof DLVAngkotPlace) {
            ((TextView)rowView.findViewById(R.id.txt_angkot)).setText(((DLVAngkotPlace)dataAngkots.get(position)).nama_angkot);
            ((TextView)rowView.findViewById(R.id.txt_location)).setText(((DLVAngkotPlace)dataAngkots.get(position)).place.nama);
        } else if(dataAngkots.get(position) instanceof DLVAngkotStreetPlaceDirection) {
            ((TextView)rowView.findViewById(R.id.txt_angkot)).setText(((DLVAngkotStreetPlaceDirection)dataAngkots.get(position)).nama_angkot);
            ((TextView)rowView.findViewById(R.id.txt_location1)).setText(
                    (((DLVAngkotStreetPlaceDirection)dataAngkots.get(position)).data_location1 instanceof DataLocationStreet ?
                            "Jalan : "  :
                            "Tempat : ")
                     + ((DLVAngkotStreetPlaceDirection)dataAngkots.get(position)).data_location1.nama
            );
            ((TextView)rowView.findViewById(R.id.txt_location2)).setText(
                    (((DLVAngkotStreetPlaceDirection)dataAngkots.get(position)).data_location2 instanceof DataLocationStreet ?
                            "Jalan : "  :
                            "Tempat : ")
                     + ((DLVAngkotStreetPlaceDirection)dataAngkots.get(position)).data_location2.nama

            );
        } else
            ((TextView)rowView.findViewById(R.id.txt_angkot)).setText(dataAngkots.get(position).nama_angkot);
        rowView.setTag(dataAngkots.get(position));

        return rowView;
    }
}
