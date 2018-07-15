package org.app.ayu.ruteangkotcianjur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

/**
 * Created by Asus i5 on 7/15/2018.
 */

public class InformationActivity extends Activity {
    WebView web;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        web = (WebView)findViewById(R.id.webview);
        web.loadUrl("file:///android_asset/" + this.getIntent().getStringExtra("filename"));
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setBuiltInZoomControls(true);
    }
}
