package org.app.ayu.ruteangkotcianjur;

/**
 * Created by anon999 on 4/7/2018.
 */

public class AppConfig {
    public static class SearchMode {
        public static final int ANGKOT = 3;
        public static final int PLACE = 5;
        public static final int STREET = 7;
        public static final int PLACE_STREET_DEST = 9;
        public static final int NONE = 11;
    }

    public static final String APP_ID = "72AB0449F8679251A4CA1D8365F67E94";
    public static String TOKEN;

    public static class HttpDomain {
        public static final String ROOT = "http://192.168.43.135/ruteangkotcianjur/app";
        public static final String ANGKOT = "angkot-result.php";
        public static final String ANGKOT_ALL = "all-angkot-result.php";
        public static final String ANGKOT_STREET = "street-result.php";
        public static final String ANGKOT_PLACE = "place-result.php";
        public static final String ANGKOT_STREET_STREET = "street-street-result.php";
        public static final String ANGKOT_PLACE_PLACE = "place-place-result.php";
        public static final String ANGKOT_STREET_PLACE = "street-or-place-result.php";
        public static final String AUTH = "auth.php";

        public static String getUrlAllAngkot() {
            return ROOT + "/" + ANGKOT_ALL;
        }

        public static String getUrlAngkotByName() { return ROOT + "/" + ANGKOT_ALL; }
        public static String getParamAngkotByName(String name) { return "q=" + name; }

        public static String getUrlAngkotById() { return ROOT + "/" + ANGKOT; }
        public static String getParamAngkotById(String id) { return "id=" + id; }

        public static String getUrlAngkotByStreet() { return ROOT + "/" + ANGKOT_STREET; }
        public static String getParamAngkotByStreet(String q) { return "q=" + q; }

        public static String getUrlAngkotByPlace() { return ROOT + "/" + ANGKOT_PLACE; }
        public static String getParamAngkotByPlace(String q) { return "q=" + q; }

        public static String getUrlAngkotByStreetStreet() { return ROOT + "/" + ANGKOT_STREET_STREET; }
        public static String getParamAngkotByStreetStreet(String street1, String street2) { return "street1=" + street1 + "&street2=" + street2; }

        public static String getUrlAngkotByPlacePlace() { return ROOT + "/" + ANGKOT_PLACE_PLACE; }
        public static String getParamAngkotByPlacePlace(String place1, String place2) { return "place1=" + place1 + "&place2=" + place2; }

        public static String getUrlAngkotByStreetPlace() { return ROOT + "/" + ANGKOT_STREET_PLACE; }
        public static String getParamAngkotByStreetPlace(String street, String place, String start) { return "street=" + street + "&place=" + place + "&start="+ start; }

        public static String getUrlAuth() { return ROOT + "/" + AUTH; }
        public static String getParamAuth() { return "app_id=" + APP_ID; }
    }
}
