package org.app.ayu.ruteangkotcianjur.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.app.ayu.ruteangkotcianjur.AppConfig;
import org.app.ayu.ruteangkotcianjur.R;

/**
 * Created by anon999 on 4/8/2018.
 */

public class SpinnerAdapterPlaceStreet extends BaseAdapter {
    private static int[] img = {
            R.drawable.ic_up,
            R.drawable.ic_down
    };
    private static String[] titile = {
            "Tempat",
            "Jalan"
    };
    private int mode;
    private Context context;

    public SpinnerAdapterPlaceStreet(Context context) {
        mode = AppConfig.SearchMode.PLACE;
        this.context = context;
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int i) {
        return new Object[] {
                img[i],
                titile[i]
        };
    }

    @Override
    public long getItemId(int i) {
        return img[i];
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewParent) {
        View v = converView;

        if (v == null) {
            v = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.sp_row_place_street,
                            viewParent,
                            false
                    );

        }
        v.setPadding(0, v.getPaddingTop(), v.getPaddingRight(), v.getPaddingBottom());

        this.mode = position == 0 ? AppConfig.SearchMode.PLACE : AppConfig.SearchMode.STREET;

        ImageView imgView = (ImageView)v.findViewById(R.id.img);
        TextView name = (TextView)v.findViewById(R.id.txt_title);

        name.setText(titile[position]);
        imgView.setImageResource(img[position]);

        v.setTag(new Object[] {
                this.mode,
                img[position],
                titile[position]
        });

        return v;
    }
}
