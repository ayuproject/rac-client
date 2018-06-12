package org.app.ayu.ruteangkotcianjur;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.app.ayu.ruteangkotcianjur.adapter.InfowindowAdapterDetailPlace;
import org.app.ayu.ruteangkotcianjur.adapter.ListViewAdapterAngkot;
import org.app.ayu.ruteangkotcianjur.adapter.SpinnerAdapterPlaceStreet;
import org.app.ayu.ruteangkotcianjur.data.DataAngkot;
import org.app.ayu.ruteangkotcianjur.data.DataInfowindow;
import org.app.ayu.ruteangkotcianjur.data.DataLocation;
import org.app.ayu.ruteangkotcianjur.data.DataLocationStreet;
import org.app.ayu.ruteangkotcianjur.data.DataPolice;
import org.app.ayu.ruteangkotcianjur.data.DataResult;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkot;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkotPlace;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkotStreet;
import org.app.ayu.ruteangkotcianjur.data.listview.DLVAngkotStreetPlaceDirection;
import org.app.ayu.ruteangkotcianjur.util.AsyncTaskResult;
import org.app.ayu.ruteangkotcianjur.util.DialogAngkot;
import org.app.ayu.ruteangkotcianjur.util.HttpConnection;
import org.app.ayu.ruteangkotcianjur.util.MapUtil;
import org.app.ayu.ruteangkotcianjur.util.ParserJSON;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private SlidingUpPanelLayout sLayout;
    private Spinner spSrch1, spSrch2;
    private EditText txtSrch1, txtSrch2;
    private LinearLayout laySrch2, laySrch, laySrchParent;
    private RelativeLayout layAngkot;
    private NavigationView navigationView;
    private ListView lvApp;
    private ImageView btnSearch, btnHideShowSearch;
    private ProgressBar progressBar;
    private TextView txtAngkot;
    private DialogAngkot dialogAngkot;
    private Button btnDetailAngkot;
    private DataAngkot currentDataAngkot;
    private int searchMode;
    private LatLngBounds cianjurBounds;
    private DataPolice dataPolice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //initialize variable and class

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //initialize controls
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        txtSrch1 = (EditText) findViewById(R.id.txt_serc1);
        txtSrch2 = (EditText) findViewById(R.id.txt_serc2);
        txtAngkot = (TextView) findViewById(R.id.txt_d_angkot);
        spSrch1 = (Spinner) findViewById(R.id.sp_mode_serc1);
        spSrch2 = (Spinner) findViewById(R.id.sp_mode_serc2);
        laySrch = (LinearLayout) findViewById(R.id.lay_search);
        laySrch2 = (LinearLayout) findViewById(R.id.lay_srch2);
        laySrchParent = (LinearLayout) findViewById(R.id.lay_search_parent);
        layAngkot = (RelativeLayout) findViewById(R.id.lay_angkot);
        lvApp = (ListView) findViewById(R.id.lv_app);
        sLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        btnHideShowSearch = (ImageView) findViewById(R.id.btn_hide_show_search);
        btnSearch = (ImageView) findViewById(R.id.btn_search);
        btnDetailAngkot = (Button) findViewById(R.id.btn_detail_angkot);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ImageView btnMainMenu = (ImageView) findViewById(R.id.btn_main_menu);
        dialogAngkot = new DialogAngkot(this);
        AppConfig.TOKEN = "";
        dataPolice = null;

        //set controls

        loadModeOn();

        spSrch1.setAdapter(new SpinnerAdapterPlaceStreet(this));
        spSrch2.setAdapter(new SpinnerAdapterPlaceStreet(this));

        btnHideShowSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (laySrch.getVisibility() == View.GONE) {
                    laySrch.setVisibility(View.VISIBLE);
                    btnSearch.setVisibility(View.VISIBLE);
                    btnHideShowSearch.setImageResource(R.drawable.ic_up);
                } else {
                    laySrch.setVisibility(View.GONE);
                    btnSearch.setVisibility(View.GONE);
                    btnHideShowSearch.setImageResource(R.drawable.ic_down);
                }
            }
        });

        lvApp.setAdapter(null);
        lvApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new GetAngkotTask().execute((DataAngkot) view.getTag());
                loadModeOn();
            }
        });

        spSrch1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtSrch1.setHint(
                        (int) ((Object[]) view.getTag())[0] == AppConfig.SearchMode.PLACE ?
                                "Cari Tempat" : "Cari Jalan"
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spSrch2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtSrch2.setHint(
                        (int) ((Object[]) view.getTag())[0] == AppConfig.SearchMode.PLACE ?
                                "Cari Tempat" : "Cari Jalan"
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        txtSrch1.setHint("Cari Tempat");
        txtSrch2.setHint("Cari Tempat");

        layAngkot.setVisibility(View.GONE);

        sLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        sLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mode1 = spSrch1.getSelectedItemPosition() == 0 ? AppConfig.SearchMode.PLACE : AppConfig.SearchMode.STREET;
                int mode2 = spSrch2.getSelectedItemPosition() == 0 ? AppConfig.SearchMode.PLACE : AppConfig.SearchMode.STREET;
                if (searchMode == AppConfig.SearchMode.ANGKOT) {
                    if (txtSrch1.getText().toString().equals("")) {
                        Toast.makeText(
                                MapsActivity.this,
                                "Isi pencarian",
                                Toast.LENGTH_LONG
                        ).show();
                        return;
                    }
                    new GetListAngkotTask(MapsActivity.this).execute(
                            AppConfig.HttpDomain.getUrlAngkotByName(),
                            AppConfig.HttpDomain.getParamAngkotByName(txtSrch1.getText().toString()),
                            "" + AppConfig.SearchMode.ANGKOT
                    );
                }
                if (searchMode == AppConfig.SearchMode.PLACE ||
                        searchMode == AppConfig.SearchMode.STREET) {
                    if (txtSrch1.getText().toString().equals("")) {
                        Toast.makeText(
                                MapsActivity.this,
                                "Isi pencarian",
                                Toast.LENGTH_LONG
                        ).show();
                        return;
                    }
                    if (mode1 == AppConfig.SearchMode.STREET)
                        new GetListAngkotTask(MapsActivity.this).execute(
                                AppConfig.HttpDomain.getUrlAngkotByStreet(),
                                AppConfig.HttpDomain.getParamAngkotByStreet(txtSrch1.getText().toString()),
                                "" + AppConfig.SearchMode.STREET
                        );
                    else
                        new GetListAngkotTask(MapsActivity.this).execute(
                                AppConfig.HttpDomain.getUrlAngkotByPlace(),
                                AppConfig.HttpDomain.getParamAngkotByPlace(txtSrch1.getText().toString()),
                                "" + AppConfig.SearchMode.PLACE
                        );
                }
                if (searchMode == AppConfig.SearchMode.PLACE_STREET_DEST) {
                    if (txtSrch1.getText().toString().equals("") ||
                            txtSrch2.getText().toString().equals("")) {
                        Toast.makeText(
                                MapsActivity.this,
                                "Isi Pencarian",
                                Toast.LENGTH_LONG
                        ).show();
                        return;
                    }
                    if (mode1 == mode2)
                        new GetListAngkotTask(
                                MapsActivity.this
                        ).execute(
                                mode1 == AppConfig.SearchMode.STREET ?
                                        AppConfig.HttpDomain.getUrlAngkotByStreetStreet() :
                                        AppConfig.HttpDomain.getUrlAngkotByPlacePlace(),
                                mode1 == AppConfig.SearchMode.STREET ?
                                        AppConfig.HttpDomain.getParamAngkotByStreetStreet(
                                                txtSrch1.getText().toString(),
                                                txtSrch2.getText().toString()
                                        ) :
                                        AppConfig.HttpDomain.getParamAngkotByPlacePlace(
                                                txtSrch1.getText().toString(),
                                                txtSrch2.getText().toString()
                                        ),
                                mode1 == AppConfig.SearchMode.STREET ?
                                        "" + (AppConfig.SearchMode.STREET | AppConfig.SearchMode.PLACE_STREET_DEST) :
                                        "" + (AppConfig.SearchMode.PLACE | AppConfig.SearchMode.PLACE_STREET_DEST)
                        );
                    else
                        new GetListAngkotTask(
                                MapsActivity.this
                        ).execute(
                                AppConfig.HttpDomain.getUrlAngkotByStreetPlace(),
                                AppConfig.HttpDomain.getParamAngkotByStreetPlace(
                                        mode1 == AppConfig.SearchMode.STREET ?
                                                txtSrch1.getText().toString() :
                                                txtSrch2.getText().toString(),
                                        mode1 == AppConfig.SearchMode.PLACE ?
                                                txtSrch1.getText().toString() :
                                                txtSrch2.getText().toString(),
                                        mode1 == AppConfig.SearchMode.STREET ?
                                                "street" : "place"
                                ),
                                "" + AppConfig.SearchMode.PLACE_STREET_DEST
                        );
                }
                View viewKey = MapsActivity.this.getCurrentFocus();
                if (viewKey != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(MapsActivity.this.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                loadModeOn();
            }
        });

        btnDetailAngkot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentDataAngkot != null)
                    dialogAngkot.show(currentDataAngkot);
            }
        });

        //set app search
        setSearchMode(AppConfig.SearchMode.NONE);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_loc_cianjur :
                if (cianjurBounds != null)
                    setCameraToCianjur();
                return true;
            case R.id.nav_loc_police :
                if (dataPolice != null) {
                    if (mMap != null) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(dataPolice.dataInfowindow.location));
                        dataPolice.isInfoWindown = true;
                        Marker m = mMap.addMarker(
                                new MarkerOptions()
                                        .position(dataPolice.dataInfowindow.location)
                                        .title(dataPolice.dataInfowindow.name)
                                        .icon(BitmapDescriptorFactory.fromBitmap(dataPolice.dataInfowindow.icon))
                        );
                        m.setTag(dataPolice.dataInfowindow);
                        m.showInfoWindow();
                    }
                }

                return true;
            case R.id.nav_about :
                LayoutInflater li = LayoutInflater.from(this);
                final View dialogInput = li.inflate(R.layout.dialog_about, null);

                AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
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
                aBuilder.create().show();
                return true;
        }
        clearAnckot();
        switch (id) {
            case R.id.nav_all_angkot :
                item.setChecked(true);
                setSearchMode(AppConfig.SearchMode.NONE);
                loadModeOn();
                new GetListAngkotTask(this).execute(AppConfig.HttpDomain.getUrlAllAngkot(), "", "" + AppConfig.SearchMode.ANGKOT);
                break;
            case R.id.nav_search_angkot :
                item.setChecked(true);
                setSearchMode(AppConfig.SearchMode.ANGKOT);
                break;
            case R.id.nav_search_place_street :
                item.setChecked(true);
                setSearchMode(AppConfig.SearchMode.PLACE); //or street
                break;
            case R.id.nav_search_place_street_dest :
                item.setChecked(true);
                setSearchMode(AppConfig.SearchMode.PLACE_STREET_DEST);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (sLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
            sLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return;
        }
        if (sLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){
            int size = navigationView.getMenu().size();
            for (int i = 0; i < size; i++)
                navigationView.getMenu().getItem(i).setChecked(false);
            clearAnckot();
            return;
        }

        if (dataPolice.isInfoWindown) {
            if (mMap != null)
                mMap.clear();
            dataPolice.isInfoWindown = false;
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Anda yakin ingin keluar ?")
                .setPositiveButton(
                        "Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MapsActivity.super.onBackPressed();
                            }
                        }
                )
                .setNegativeButton(
                        "Tidak",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }
                )
                .create()
                .show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setPadding(
                0,
                100,
                0,
                0
        );

        mMap.setInfoWindowAdapter(new InfowindowAdapterDetailPlace(this));
        mMap.setOnInfoWindowClickListener(this);

        //set camera to cianjur bounds
        //cianjur bounds
        List<LatLng> positions = new ArrayList<>();
        positions.add(new LatLng( -6.803476, 107.15325));
        positions.add(new LatLng(-6.84117, 107.122121));

        //calculate cianjur boundaries
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng position : positions) {
            builder.include(position);
        }

        //build cianjur boundaries and set it when map loaded
        cianjurBounds = builder.build();
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

                setCameraToCianjur();
                new ValidateApp(MapsActivity.this).execute(AppConfig.HttpDomain.getUrlAuth(),AppConfig.HttpDomain.getParamAuth());
                new GetPlaceById(MapsActivity.this).execute(MapUtil.GoogleDomain.getMapsApiDetaiInformationURL("ChIJM_MZAvlSaC4RXcoLcNnBSaM"));
            }
        });
    }

    private void setCameraToCianjur() {
        if (mMap != null && cianjurBounds != null)
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(cianjurBounds, 50));
    }

    private void clearAnckot() {
        sLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        lvApp.setAdapter(null);
        mMap.clear();
        layAngkot.setVisibility(View.GONE);
    }

    private void setSearchMode(int mode) {
        searchMode = mode;
        txtSrch1.setText("");
        txtSrch2.setText("");
        switch (mode) {
            case AppConfig.SearchMode.ANGKOT :
                laySrchParent.setVisibility(View.VISIBLE);
                txtSrch1.setHint("Cari Angkot");
                spSrch1.setVisibility(View.GONE);
                laySrch2.setVisibility(View.GONE);
                return;
            case AppConfig.SearchMode.PLACE :
            case AppConfig.SearchMode.STREET :
                laySrchParent.setVisibility(View.VISIBLE);
                spSrch1.setVisibility(View.VISIBLE);
                laySrch2.setVisibility(View.GONE);
                break;
            case AppConfig.SearchMode.PLACE_STREET_DEST :
                laySrchParent.setVisibility(View.VISIBLE);
                spSrch1.setVisibility(View.VISIBLE);
                laySrch2.setVisibility(View.VISIBLE);
                break;
            case AppConfig.SearchMode.NONE :
                laySrchParent.setVisibility(View.GONE);
                break;
        }
        txtSrch1.setHint(
                spSrch1.getSelectedItemPosition() == 0 ?
                        "Cari Tempat" :
                        "Cari Jalan"
        );
        txtSrch2.setHint(
                spSrch2.getSelectedItemPosition() == 0 ?
                        "Cari Tempat" :
                        "Cari Jalan"
        );
    }

    private void loadModeOn() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void loadModeOff() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void setMaps(DataAngkot dataAngkot) {
        mMap.clear();
        if (sLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)
            sLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        String urlMap = MapUtil.GoogleDomain.getMapsApiDirectionURL(
                dataAngkot.rute_angkot.toArray(
                        new LatLng[dataAngkot.rute_angkot.size()]
                )
        );
        Log.e("maps", urlMap);
        currentDataAngkot = dataAngkot;
        new DrawAngkotRouteTask(dataAngkot).execute(urlMap);
        if (layAngkot.getVisibility() == View.GONE)
            layAngkot.setVisibility(View.VISIBLE);
        txtAngkot.setText(
                String.format(
                        "Angkot : %s\nJalan : %s",
                        dataAngkot.nama_angkot,
                        dataAngkot.getAppenedStreet()
                )
        );

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.getTag() instanceof DataInfowindow) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + ((DataInfowindow)marker.getTag()).phone_number));
            startActivity(intent);
        }
    }

    private class GetPlaceById extends AsyncTask<String, Void, AsyncTaskResult<DataInfowindow>> {
        public GetPlaceById(Activity context) {
            this.context = context;
        }

        private Activity context;

        protected AsyncTaskResult<DataInfowindow> doInBackground(String... params) {
            String urlS = params[0];
            DataInfowindow res = null;
            try {
                String rawJSON = new HttpConnection().readUrl(urlS);
                res = new MapUtil().parseDetailLocationFromJSON(new JSONObject(rawJSON), context);
            } catch (Exception ex) {
                return new AsyncTaskResult<>(new Exception("Terjadi Kesalahan : " + ex.getMessage()));
            }

            return new AsyncTaskResult<>(res);
        }

        protected void onPostExecute(AsyncTaskResult<DataInfowindow> result) {
            super.onPostExecute(result);
            if (result.getError() != null){
                Toast.makeText(
                        context,
                        result.getError().getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                return;
            }
            //create marker with info window
            DataInfowindow data = result.getResult();
            dataPolice = new DataPolice(data);
        }
    }

    private class ValidateApp extends AsyncTask<String, Void, AsyncTaskResult<DataResult>> {
        public ValidateApp(Activity context) {
            this.context = context;
        }

        private Activity context;

        protected AsyncTaskResult<DataResult> doInBackground(String... params) {
            String urlS = params[0];
            String param = params[1];
            DataResult res = null;
            try {
                String rawJSON = new HttpConnection().readUrlPost(urlS, param);
                res = new ParserJSON().getToken(new JSONObject(rawJSON));
            } catch (Exception ex) {
                return new AsyncTaskResult<>(new Exception("Terjadi Kesalahan : " + ex.getMessage()));
            }

            return new AsyncTaskResult<>(res);
        }

        protected void onPostExecute(AsyncTaskResult<DataResult> result) {
            super.onPostExecute(result);
            loadModeOff();
            if (result.getError() != null){
                Toast.makeText(
                        context,
                        result.getError().getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                return;
            }
            AppConfig.TOKEN = result.getResult().dataString;
        }
    }

    private class GetListAngkotTask extends AsyncTask<String, Void, AsyncTaskResult<ArrayList<DataAngkot>>> {
        public GetListAngkotTask(Activity context) {
            this.context = context;
        }
        private Activity context;
        @Override
        protected AsyncTaskResult<ArrayList<DataAngkot>> doInBackground(String... params) {
            String url = params[0];
            String param = params[1];
            int mode = Integer.parseInt(params[2]);
            try {
                String rawJSON = "";
                HttpConnection http = new HttpConnection();
                rawJSON = http.readAPIUrlPost(url, param);
                DataResult dataResult = null;
                switch (mode) {
                    case AppConfig.SearchMode.PLACE :
                        dataResult = new ParserJSON().getListAngkotPalace(new JSONObject(rawJSON));
                        break;
                    case AppConfig.SearchMode.STREET :
                        dataResult = new ParserJSON().getListAngkotStreet(new JSONObject(rawJSON));
                        break;
                    case AppConfig.SearchMode.STREET | AppConfig.SearchMode.PLACE_STREET_DEST : //street to street
                        dataResult = new ParserJSON().getListAngkotStreetToStreet(new JSONObject(rawJSON));
                        break;
                    case AppConfig.SearchMode.PLACE | AppConfig.SearchMode.PLACE_STREET_DEST : //place to place
                        dataResult = new ParserJSON().getListAngkotPlaceToPlace(new JSONObject(rawJSON));
                        break;
                    case AppConfig.SearchMode.PLACE_STREET_DEST :
                        dataResult = new ParserJSON().getListAngkotStreetOrPlace(new JSONObject(rawJSON));
                        break;
                    case  AppConfig.SearchMode.ANGKOT :
                    case AppConfig.SearchMode.NONE :
                    default:
                        dataResult = new ParserJSON().getListAngkot(new JSONObject(rawJSON));
                        break;
                }
                if (dataResult.result <= 0)
                    throw new Exception("Angkot Tidak Ditemukan");
                return new AsyncTaskResult<>(dataResult.dataAngkot);
            } catch (Exception e) {
                Exception ex = new Exception("Terjadi Kesalahan : " + e.getMessage());
                return new AsyncTaskResult<>(ex);
            }
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<ArrayList<DataAngkot>> result) {
            super.onPostExecute(result);
            loadModeOff();
            if (result.getError() != null){
                Toast.makeText(
                        context,
                        result.getError().getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                return;
            }
            lvApp.setAdapter(new ListViewAdapterAngkot(result.getResult(), context, result.getResult().get(0) instanceof DLVAngkotStreetPlaceDirection ? R.layout.list_view_direction : R.layout.list_view_non_direction));
            sLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
    }

    private class GetAngkotTask extends AsyncTask<DataAngkot, Void, AsyncTaskResult<DataAngkot>> {
        @Override
        protected AsyncTaskResult<DataAngkot> doInBackground(DataAngkot... dataAngkots) {
            DataAngkot result = dataAngkots[0];
            String url = AppConfig.HttpDomain.getUrlAngkotById();
            String param = AppConfig.HttpDomain.getParamAngkotById(result.id_angkot);
            try {
                String rawJSON = "";
                HttpConnection http = new HttpConnection();
                rawJSON = http.readAPIUrlPost(url, param);
                result.copyData(new ParserJSON().getAngkot(new JSONObject(rawJSON)));
                return new AsyncTaskResult<>(result);
            } catch (Exception e) {
                Log.e("GetAngkotTask", e.getMessage());
                return new AsyncTaskResult<>(e);
            }
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<DataAngkot> result) {
            super.onPostExecute(result);
            loadModeOff();
            if (result.getError() != null)
                Toast.makeText(
                        MapsActivity.this,
                        "Terjadi Kesalahan : " + result.getError().getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            else
                setMaps(result.getResult());
        }
    }

    private class DrawAngkotRouteTask extends AsyncTask<String, Integer, AsyncTaskResult<List<List<HashMap<String, String>>>>> {
        private DrawAngkotRouteTask(DataAngkot dataAngkot) {
            this.dataAngkot = dataAngkot;
        }

        private DataAngkot dataAngkot;

        @Override
        protected void onPreExecute(){
            if (dataAngkot instanceof DLVAngkot) {
                mMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                                new MapUtil().getBounds(
                                        dataAngkot.rute_angkot.toArray(new LatLng[dataAngkot.rute_angkot.size()])
                                ),
                                100
                        )
                );
            } else if (dataAngkot instanceof DLVAngkotStreet) {
                DLVAngkotStreet street = (DLVAngkotStreet)dataAngkot;
                PolygonOptions bounds = new MapUtil().drawBoundarieStreet(street.street.northeast, street.street.southwest);
                mMap.addPolygon(bounds);
                Marker m = mMap.addMarker(
                        new MarkerOptions()
                                .position(street.street.location)
                                .title(street.street.nama)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                );
                m.setTag(dataAngkot);
                m.showInfoWindow();
                LatLngBounds latLngBounds = new MapUtil().getBoundStreet(street.street.northeast, street.street.southwest);
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 40));
            } else if (dataAngkot instanceof DLVAngkotPlace) {
                DLVAngkotPlace place = (DLVAngkotPlace)dataAngkot;
                Marker m = mMap.addMarker(
                        new MarkerOptions()
                                .position(place.place.location)
                                .title(place.place.nama)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                );
                m.setTag(dataAngkot);
                m.showInfoWindow();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.place.location, 15));
            } else if (dataAngkot instanceof DLVAngkotStreetPlaceDirection) {
                DLVAngkotStreetPlaceDirection streetPlace = (DLVAngkotStreetPlaceDirection)dataAngkot;
                DataLocation location1 = streetPlace.data_location1;
                DataLocation location2 = streetPlace.data_location2;
                if (location1 instanceof DataLocationStreet){
                    PolygonOptions bounds = new MapUtil().drawBoundarieStreet(((DataLocationStreet)location1).northeast, ((DataLocationStreet)location1).southwest);
                    Marker m = mMap.addMarker(
                            new MapUtil().getMarkForStreet(location1)
                    );
                    m.setTag(dataAngkot);
                    m.showInfoWindow();
                    mMap.addPolygon(bounds);
                } else {
                    Marker m = mMap.addMarker(
                            new MapUtil().getMarkForPlace(location1)
                    );
                    m.setTag(dataAngkot);
                    m.showInfoWindow();
                }
                if (location2 instanceof DataLocationStreet){
                    PolygonOptions bounds = new MapUtil().drawBoundarieStreet(((DataLocationStreet)location2).northeast, ((DataLocationStreet)location2).southwest);
                    Marker m = mMap.addMarker(
                            new MapUtil().getMarkForStreet(location2)
                    );
                    m.setTag(dataAngkot);
                    m.showInfoWindow();
                    mMap.addPolygon(bounds);
                } else {
                    Marker m = mMap.addMarker(
                            new MapUtil().getMarkForPlace(location2)
                    );
                    m.setTag(dataAngkot);
                    m.showInfoWindow();
                }
                LatLngBounds zoomCamera = new MapUtil().getBounds(
                        new LatLng[] {
                                location1.location,
                                location2.location
                        }
                );
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(zoomCamera, 50));
            }
        }

        @Override
        protected AsyncTaskResult<List<List<HashMap<String, String>>>> doInBackground(
                String... urlMap) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            String rawJSON = "";
            try {
                HttpConnection http = new HttpConnection();
                rawJSON = http.readUrl(urlMap[0]);
            } catch (Exception e) {
                return new AsyncTaskResult<>(e);
            }

            try {
                jObject = new JSONObject(rawJSON);
                MapUtil parser = new MapUtil();
                routes = parser.parseRouteFormJSON(jObject);
                return new AsyncTaskResult<>(routes);
            } catch (Exception e) {
                Log.e("DrawAngkotRouteTask", e.getMessage());
                return new AsyncTaskResult<>(e);
            }
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<List<List<HashMap<String, String>>>> result) {
            if (result.getError() != null){
                Toast.makeText(
                        MapsActivity.this,
                        "Terjadi Keslahan : " + result.getError().getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                return;
            }
            List<List<HashMap<String, String>>> routes = result.getResult();
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(2);
                polyLineOptions.color(Color.BLUE);
            }

            if (polyLineOptions != null)
                mMap.addPolyline(polyLineOptions);
            else
                Toast.makeText(MapsActivity.this, "Terjadi Kesalahan : route is null", Toast.LENGTH_LONG).show();
        }
    }
}
