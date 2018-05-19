package org.app.ayu.ruteangkotcianjur.util;

import com.google.android.gms.maps.model.LatLng;

import org.app.ayu.ruteangkotcianjur.data.DataAngkot;
import org.app.ayu.ruteangkotcianjur.data.DataLocation;
import org.app.ayu.ruteangkotcianjur.data.DataResult;
import org.app.ayu.ruteangkotcianjur.data.DataLocationStreet;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkot;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkotPlace;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkotStreet;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkotStreetPlaceDirection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by anon999 on 4/14/2018.
 */

public class ParserJSON {
    public ParserJSON() {

    }

    public DataResult getListAngkot(JSONObject datas) throws JSONException {
        ArrayList<DataAngkot> result = new ArrayList<>();
        int resultCount = datas.getInt("result");
        String resultMsg = datas.getString("msg");
        JSONArray listAngkot = datas.getJSONArray("angkot");

        for (int i = 0; i < listAngkot.length(); ++i) {
            result.add(
                    new DLVAngkot(
                            listAngkot.getJSONObject(i).getString("id"),
                            listAngkot.getJSONObject(i).getString("nama"),
                            listAngkot.getJSONObject(i).getString("alljalan")
                    )
            );

        }

        return new DataResult(resultCount, resultMsg, result);
    }

    public DataResult getListAngkotPlaceToPlace(JSONObject datas) throws JSONException {
        int resultCount = datas.getInt("result");
        String resultMsg = datas.getString("msg");
        JSONArray listAngkot = datas.getJSONArray("angkot");
        ArrayList<DataAngkot> result = new ArrayList<>();

        for (int i = 0; i < listAngkot.length(); ++i)
            result.add(
                    new DLVAngkotStreetPlaceDirection(
                            listAngkot.getJSONObject(i).getString("id"),
                            listAngkot.getJSONObject(i).getString("nama"),
                            new DataLocation(
                                    listAngkot.getJSONObject(i).getJSONObject("tempat1").getString("id"),
                                    listAngkot.getJSONObject(i).getJSONObject("tempat1").getString("nama"),
                                    new LatLng(
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("tempat1").getString("location").split(",")[0].trim()
                                            ),
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("tempat1").getString("location").split(",")[1].trim()
                                            )
                                    )
                            ),
                            new DataLocation(
                                    listAngkot.getJSONObject(i).getJSONObject("tempat2").getString("id"),
                                    listAngkot.getJSONObject(i).getJSONObject("tempat2").getString("nama"),
                                    new LatLng(
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("tempat2").getString("location").split(",")[0].trim()
                                            ),
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("tempat2").getString("location").split(",")[1].trim()
                                            )
                                    )
                            )
                    )
            );

        return new DataResult(resultCount, resultMsg, result);
    }

    public DataResult getListAngkotStreetToStreet(JSONObject datas) throws JSONException {
        int resultCount = datas.getInt("result");
        String resultMsg = datas.getString("msg");
        JSONArray listAngkot = datas.getJSONArray("angkot");
        ArrayList<DataAngkot> result = new ArrayList<>();

        for (int i = 0; i < listAngkot.length(); ++i)
            result.add(
                    new DLVAngkotStreetPlaceDirection(
                            listAngkot.getJSONObject(i).getString("id"),
                            listAngkot.getJSONObject(i).getString("nama"),
                            new DataLocationStreet(
                                    listAngkot.getJSONObject(i).getJSONObject("jalan1").getString("id"),
                                    listAngkot.getJSONObject(i).getJSONObject("jalan1").getString("nama"),
                                    new LatLng(
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("jalan1").getString("location").split(",")[0].trim()
                                            ),
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("jalan1").getString("location").split(",")[1].trim()
                                            )
                                    ),
                                    new LatLng(
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("jalan1").getString("latlng1").split(",")[0].trim()
                                            ),
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("jalan1").getString("latlng1").split(",")[1].trim()
                                            )
                                    ),
                                    new LatLng(
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("jalan1").getString("latlng2").split(",")[0].trim()
                                            ),
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("jalan1").getString("latlng2").split(",")[1].trim()
                                            )
                                    )
                            ),
                            new DataLocationStreet(
                                    listAngkot.getJSONObject(i).getJSONObject("jalan2").getString("id"),
                                    listAngkot.getJSONObject(i).getJSONObject("jalan2").getString("nama"),
                                    new LatLng(
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("jalan2").getString("location").split(",")[0].trim()
                                            ),
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("jalan2").getString("location").split(",")[1].trim()
                                            )
                                    ),
                                    new LatLng(
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("jalan2").getString("latlng1").split(",")[0].trim()
                                            ),
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("jalan2").getString("latlng1").split(",")[1].trim()
                                            )
                                    ),
                                    new LatLng(
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("jalan2").getString("latlng2").split(",")[0].trim()
                                            ),
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("jalan2").getString("latlng2").split(",")[1].trim()
                                            )
                                    )
                            )
                    )
            );

        return new DataResult(resultCount, resultMsg, result);
    }

    public DataResult getListAngkotStreetOrPlace(JSONObject datas) throws JSONException {
        ArrayList<DataAngkot> result = new ArrayList<>();
        int resultCount = datas.getInt("result");
        String resultMsg = datas.getString("msg");
        boolean isPlaceStart = datas.getString("start").equals("place");
        JSONArray listAngkot = datas.getJSONArray("angkot");

        for (int i = 0; i < listAngkot.length(); ++i) {
            DataLocation place = new DataLocation(
                    listAngkot.getJSONObject(i).getJSONObject("tempat").getString("id"),
                    listAngkot.getJSONObject(i).getJSONObject("tempat").getString("nama"),
                    new LatLng(
                            Double.parseDouble(
                                    listAngkot.getJSONObject(i).getJSONObject("tempat").getString("location").split(",")[0].trim()
                            ),
                            Double.parseDouble(
                                    listAngkot.getJSONObject(i).getJSONObject("tempat").getString("location").split(",")[1].trim()
                            )
                    )
            );
            DataLocationStreet street = new DataLocationStreet(
                    listAngkot.getJSONObject(i).getJSONObject("jalan").getString("id"),
                    listAngkot.getJSONObject(i).getJSONObject("jalan").getString("nama"),
                    new LatLng(
                            Double.parseDouble(
                                    listAngkot.getJSONObject(i).getJSONObject("jalan").getString("location").split(",")[0].trim()
                            ),
                            Double.parseDouble(
                                    listAngkot.getJSONObject(i).getJSONObject("jalan").getString("location").split(",")[1].trim()
                            )
                    ),
                    new LatLng(
                            Double.parseDouble(
                                    listAngkot.getJSONObject(i).getJSONObject("jalan").getString("latlng1").split(",")[0].trim()
                            ),
                            Double.parseDouble(
                                    listAngkot.getJSONObject(i).getJSONObject("jalan").getString("latlng1").split(",")[1].trim()
                            )
                    ),
                    new LatLng(
                            Double.parseDouble(
                                    listAngkot.getJSONObject(i).getJSONObject("jalan").getString("latlng2").split(",")[0].trim()
                            ),
                            Double.parseDouble(
                                    listAngkot.getJSONObject(i).getJSONObject("jalan").getString("latlng2").split(",")[1].trim()
                            )
                    )
            );

            result.add(
                    new DLVAngkotStreetPlaceDirection(
                            listAngkot.getJSONObject(i).getString("id"),
                            listAngkot.getJSONObject(i).getString("nama"),
                            isPlaceStart ? place : street,
                            isPlaceStart ? street : place

                    )
            );
        }

        return new DataResult(resultCount, resultMsg, result);
    }

    public DataResult getListAngkotStreet(JSONObject datas) throws JSONException {
        ArrayList<DataAngkot> result = new ArrayList<>();
        int resultCount = datas.getInt("result");
        String resultMsg = datas.getString("msg");
        JSONArray listAngkot = datas.getJSONArray("angkot");

        for (int i = 0; i < listAngkot.length(); ++i) {
            result.add(
                    new DLVAngkotStreet(
                            listAngkot.getJSONObject(i).getString("id"),
                            listAngkot.getJSONObject(i).getString("nama"),
                            new DataLocationStreet(
                                    listAngkot.getJSONObject(i).getJSONObject("jalan").getString("id"),
                                    listAngkot.getJSONObject(i).getJSONObject("jalan").getString("nama"),
                                    new LatLng(
                                            Double.parseDouble(listAngkot.getJSONObject(i).getJSONObject("jalan").getString("location").split(",")[0].trim()),
                                            Double.parseDouble(listAngkot.getJSONObject(i).getJSONObject("jalan").getString("location").split(",")[1].trim())
                                    ),
                                    new LatLng(
                                            Double.parseDouble(listAngkot.getJSONObject(i).getJSONObject("jalan").getString("latlng1").split(",")[0].trim()),
                                            Double.parseDouble(listAngkot.getJSONObject(i).getJSONObject("jalan").getString("latlng1").split(",")[1].trim())
                                    ),
                                    new LatLng(
                                            Double.parseDouble(listAngkot.getJSONObject(i).getJSONObject("jalan").getString("latlng2").split(",")[0].trim()),
                                            Double.parseDouble(listAngkot.getJSONObject(i).getJSONObject("jalan").getString("latlng2").split(",")[1].trim())
                                    )
                            )
                    )
            );

        }

        return new DataResult(resultCount, resultMsg, result);
    }

    public DataResult getListAngkotPalace(JSONObject datas) throws JSONException {
        ArrayList<DataAngkot> result = new ArrayList<>();
        int resultCount = datas.getInt("result");
        String resultMsg = datas.getString("msg");
        JSONArray listAngkot = datas.getJSONArray("angkot");

        for (int i = 0; i < listAngkot.length(); ++i) {
            result.add(
                    new DLVAngkotPlace(
                            listAngkot.getJSONObject(i).getString("id"),
                            listAngkot.getJSONObject(i).getString("nama"),
                            new DataLocation(
                                    listAngkot.getJSONObject(i).getJSONObject("tempat").getString("id"),
                                    listAngkot.getJSONObject(i).getJSONObject("tempat").getString("nama"),
                                    new LatLng(
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("tempat").getString("location").split(",")[0].trim()
                                            ),
                                            Double.parseDouble(
                                                    listAngkot.getJSONObject(i).getJSONObject("tempat").getString("location").split(",")[1].trim()
                                            )
                                    )
                            )
                    )
            );

        }

        return new DataResult(resultCount, resultMsg, result);
    }

    public DataAngkot getAngkot(JSONObject datas) throws JSONException {
        DataAngkot result = null;
        JSONObject angkotObject = datas.getJSONObject("angkot");
        int id = angkotObject.getInt("id");
        String nama = angkotObject.getString("nama");
        String harga = angkotObject.getString("harga");
        JSONArray ruteJSONArray = angkotObject.getJSONArray("rute");
        JSONArray jalanJSONArray = angkotObject.getJSONArray("jalan");
        JSONArray tempatJSONArray = angkotObject.getJSONArray("tempat");
        ArrayList<LatLng> ruteList = new ArrayList<>();
        ArrayList<DataLocationStreet> jalanList = new ArrayList<>();
        ArrayList<DataLocation> tempatList = new ArrayList<>();
        for (int i = 0; i < ruteJSONArray.length(); ++i) {
            String[] latlng = ruteJSONArray.getString(i).split(",");
            ruteList.add(
                    new LatLng(
                            Double.parseDouble(latlng[0].trim()),
                            Double.parseDouble(latlng[1].trim())
                    )
            );
        }

        for (int i = 0; i < jalanJSONArray.length(); ++i) {
            String[] location = jalanJSONArray.getJSONObject(i).getString("location").split(",");
            String[] latlng1 = jalanJSONArray.getJSONObject(i).getString("latlng1").split(",");
            String[] latlng2 = jalanJSONArray.getJSONObject(i).getString("latlng2").split(",");
            jalanList.add(
                    new DataLocationStreet(
                            jalanJSONArray.getJSONObject(i).getString("id"),
                            jalanJSONArray.getJSONObject(i).getString("nama"),
                            new LatLng(
                                    Double.parseDouble(location[0].trim()),
                                    Double.parseDouble(location[1].trim())
                            ),
                            new LatLng(
                                    Double.parseDouble(latlng1[0].trim()),
                                    Double.parseDouble(latlng1[1].trim())
                            ),
                            new LatLng(
                                    Double.parseDouble(latlng2[0].trim()),
                                    Double.parseDouble(latlng2[1].trim())
                            )
                    )
            );
        }
        for (int i = 0; i < tempatJSONArray.length(); ++i) {
            String latlng[] = tempatJSONArray.getJSONObject(i).getString("latlng").split(",");
            tempatList.add(
                    new DataLocation(
                            tempatJSONArray.getJSONObject(i).getString("id"),
                            tempatJSONArray.getJSONObject(i).getString("nama"),
                            new LatLng(
                                    Double.parseDouble(latlng[0].trim()),
                                    Double.parseDouble(latlng[1].trim())
                            )
                    )
            );
        }

        result = new DataAngkot(
                "" + id,
                nama,
                harga,
                ruteList,
                jalanList,
                tempatList

        );
        return result;
    }


}
