package org.app.ayu.ruteangkotcianjur.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.AlteredCharSequence;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.app.ayu.ruteangkotcianjur.R;
import org.app.ayu.ruteangkotcianjur.data.DataAngkot;

/**
 * Created by anon999 on 4/14/2018.
 */

public class DialogAngkot {
    public DialogAngkot(Context context) {
        this.context = context;

        LayoutInflater li = LayoutInflater.from(context);
        final View dialogInput = li.inflate(R.layout.dialog_angkot, null);

        txtNama = (TextView)dialogInput.findViewById(R.id.nama_angkot);
        txtStreet = (TextView)dialogInput.findViewById(R.id.txt_street);
        txtPlace = (TextView)dialogInput.findViewById(R.id.txt_place);
        txtHarga = (TextView)dialogInput.findViewById(R.id.txt_harga);

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(context);
        aBuilder.setView(dialogInput);
        aBuilder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }
        );
        dialog = aBuilder.create();
        this.context = context;
    }

    private TextView txtNama;
    private TextView txtStreet;
    private TextView txtPlace;
    private TextView txtHarga;
    private AlertDialog dialog;

    private Context context;

    public void show(DataAngkot dataAngkot) {
        if (dataAngkot == null) {
            Toast.makeText(context, "Data is null", Toast.LENGTH_LONG).show();
            return;
        }
        txtNama.setText(dataAngkot.nama_angkot);
        txtStreet.setText(dataAngkot.getAppenedStreet());
        txtPlace.setText(dataAngkot.getAppendedPlace());
        txtHarga.setText(dataAngkot.harga_angkot);
        dialog.show();
    }
}
