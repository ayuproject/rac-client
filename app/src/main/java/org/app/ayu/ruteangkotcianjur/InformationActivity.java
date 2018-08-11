package org.app.ayu.ruteangkotcianjur;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import com.github.barteksc.pdfviewer.PDFView;

/**
 * Created by Asus i5 on 7/15/2018.
 */

public class InformationActivity extends Activity {
    WebView web;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getIntent().getStringExtra("filetype").equals("pdf")) {
            setContentView(R.layout.activity_information_pdf);
            PDFView pdfView = (PDFView)this.findViewById(R.id.pdfView);
            pdfView.fromAsset(this.getIntent().getStringExtra("filename")).swipeHorizontal(false).load();
        } else {
            setContentView(R.layout.activity_information);
            web = (WebView) findViewById(R.id.webview);
            web.loadUrl("file:///android_asset/" + this.getIntent().getStringExtra("filename"));
            web.getSettings().setLoadWithOverviewMode(true);
            web.getSettings().setUseWideViewPort(true);
            web.getSettings().setBuiltInZoomControls(true);
        }
    }
}
