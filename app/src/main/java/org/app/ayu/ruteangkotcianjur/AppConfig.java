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
    public static class HttpDomain {
        public static final String ROOT = "http://192.168.43.113/ruteangkotcianjur/app";
        public static final String ANGKOT = "angkot-result.php";
        public static final String ANGKOT_ALL = "all-angkot-result.php";
        public static final String ANGKOT_STREET = "street-result.php";
        public static final String ANGKOT_PLACE = "place-result.php";
        public static final String ANGKOT_STREET_STREET = "street-street-result.php";
        public static final String ANGKOT_PLACE_PLACE = "place-place-result.php";
        public static final String ANGKOT_STREET_PLACE = "street-or-place-result.php";

        public static String getAllAngkot() {
            return ROOT + "/" + ANGKOT_ALL;
        }
        public static String getAngkotByName(String name) {
            return ROOT + "/" + ANGKOT_ALL + "?q=" + name;
        }
        public static String getAngkotById(String id) {
            return ROOT + "/" + ANGKOT + "?id=" + id;
        }
        public static String getAngkotByStreet(String q) {
            return ROOT + "/" + ANGKOT_STREET + "?q=" + q;
        }
        public static String getAngkotByPlace(String q) {
            return ROOT + "/" + ANGKOT_PLACE + "?q=" + q;
        }
        public static String getAngkotByStreetStreet(String street1, String street2) {
            return ROOT + "/" + ANGKOT_STREET_STREET + "?street1=" + street1 + "&street2=" + street2;
        }
        public static String getAngkotByPlacePlace(String place1, String place2) {
            return ROOT + "/" + ANGKOT_PLACE_PLACE + "?place1=" + place1 + "&place2=" + place2;
        }
        public static String getAngkotByStreetPlace(String street, String place, String start) {
            return ROOT + "/" + ANGKOT_STREET_PLACE + "?street=" + street + "&place=" + place + "&start="+ start;
        }
    }
}
