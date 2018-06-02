package org.app.ayu.ruteangkotcianjur.data;

import java.util.ArrayList;

/**
 * Created by anon999 on 4/18/2018.
 */

public class DataResult {
    public DataResult(int result, String msg, ArrayList<DataAngkot> data) {
        this.result = result;
        this.message = msg;
        this.dataAngkot = data;
    }
    public DataResult(int result, String msg, String data) {
        this.result = result;
        this.message = msg;
        this.dataString = data;
    }
    public int result;
    public String message;
    public ArrayList<DataAngkot> dataAngkot;
    public String dataString;
}
