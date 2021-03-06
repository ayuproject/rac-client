package org.app.ayu.ruteangkotcianjur.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import org.app.ayu.ruteangkotcianjur.R;
import org.app.ayu.ruteangkotcianjur.data.DataInfowindow;
import org.app.ayu.ruteangkotcianjur.data.DataLocation;
import org.app.ayu.ruteangkotcianjur.data.DataLocationStreet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by anon999 on 4/14/2018.
 */

public class MapUtil {

    public static class GoogleDomain {
        public static final String ROOT_DIRECTION = "https://maps.googleapis.com/maps/api/directions";
        public static final String ROOT_INFO = "https://maps.googleapis.com/maps/api/place/details";
        public static final String API_KEY = "AIzaSyDYrNGfgY2cwCAuI96qFYQmlTxQHFwHTJs";
        public static String getMapsApiDirectionURL(LatLng[] points, boolean satuJalur) {
            String key = "key=" + API_KEY;
            String origin = "origin=" + points[0].latitude + "," + points[0].longitude;
            String waypoints = "waypoints=optimize:true|";
            String destination = "destination=";
            destination += satuJalur ? points[points.length - 1].latitude + "," + points[points.length - 1].longitude : points[0].latitude + "," + points[0].longitude;
            int isatuJalur  = satuJalur ? 1 : 0;
            for (int i = 1; i < points.length - isatuJalur; ++i)
                waypoints += points[i].latitude + "," + points[i].longitude + "|";

            String sensor = "sensor=false";
            String params = origin + "&" + key + "&" + waypoints + "&"  + destination + "&" + sensor;
            String output = "json";
            String url = ROOT_DIRECTION + "/" + output + "?" + params;
            return url;
        }

        public static String getMapsApiDetaiInformationURL(String placeID) {
            String key = "key=" + API_KEY;
            String params = "placeid=" + placeID + "&" + key;
            String output = "json";
            String url = ROOT_INFO + "/" + output + "?" + params;
            return url;
        }
    }

    public LatLngBounds getBounds(LatLng[] latLng) {
        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        for (int i = 0; i < latLng.length; ++i) {
            bounds.include(latLng[i]);
        }
        return bounds.build();
    }

    public LatLngBounds getBoundStreet(LatLng northEast, LatLng southWest) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(northEast);
        builder.include(southWest);

        return builder.build();
    }

    public PolygonOptions drawBoundarieStreet(LatLng northEast, LatLng southWest) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(northEast);
        builder.include(southWest);

        LatLngBounds initialBounds = builder.build();

        return new PolygonOptions()
                .add(new LatLng(initialBounds.northeast.latitude, initialBounds.northeast.longitude))
                .add(new LatLng(initialBounds.southwest.latitude, initialBounds.northeast.longitude))
                .add(new LatLng(initialBounds.southwest.latitude, initialBounds.southwest.longitude))
                .add(new LatLng(initialBounds.northeast.latitude, initialBounds.southwest.longitude))
                .strokeColor(Color.RED)
                .strokeWidth(2);
    }

    public MarkerOptions getMarkForStreet(DataLocation data) {
        return new MarkerOptions()
                .position(data.location)
                .title(data.nama)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
    }

    public MarkerOptions getMarkForPlace(DataLocation data) {
        return new MarkerOptions()
                .position(data.location)
                .title(data.nama)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    }

    public DataInfowindow parseDetailLocationFromJSON(JSONObject jObj, Activity context) throws JSONException {
        JSONObject result = jObj.getJSONObject("result");
        JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
        String name = result.getString("name");
        String adderes = result.getString("formatted_address");
        String phone = result.getString("international_phone_number").replace(" ", "").trim();
        Bitmap icon = null;
        try {
            icon = new HttpConnection().readBitmapUrl(result.getString("icon"));
        } catch (Exception ex) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.police_71);
        }
        return new DataInfowindow(
                name,
                adderes,
                phone,
                new LatLng(
                        location.getDouble("lat"),
                        location.getDouble("lng")
                ),
                icon
        );
    }

    /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
    public List<List<HashMap<String, String>>> parseRouteFormJSON(JSONObject jObject) {
        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;
        try {
            jRoutes = jObject.getJSONArray("routes");
            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

                /** Traversing all legs */
                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline = "";
                        polyline = (String) ((JSONObject) ((JSONObject) jSteps
                                .get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        /** Traversing all points */
                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat",
                                    Double.toString(((LatLng) list.get(l)).latitude));
                            hm.put("lng",
                                    Double.toString(((LatLng) list.get(l)).longitude));
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return routes;
    }

    /**
     * Method Courtesy :
     * jeffreysambells.com/2010/05/27
     * /decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
}
