package org.app.ayu.ruteangkotcianjur.util;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.app.ayu.ruteangkotcianjur.AppConfig;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by anon999 on 4/3/2018.
 */

public class HttpConnection {
    public String readUrl(String mapsApiDirectionsUrl) throws Exception {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        URL url = new URL(mapsApiDirectionsUrl);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();
        iStream = urlConnection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                iStream));
        StringBuffer sb = new StringBuffer();
        String line = "";


        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        if (urlConnection.getResponseCode() !=  HttpURLConnection.HTTP_OK) {
            br.close();
            iStream.close();
            urlConnection.disconnect();
            throw new Exception("Kode Respon " + urlConnection.getResponseCode());
        }

        urlConnection.disconnect();
        data = sb.toString();
        br.close();
        iStream.close();


        return data;
    }

    public String readAPIUrlPost(String url_s, String param) throws Exception {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        URL url = new URL(url_s);
        byte[] paramB = param.getBytes(Charset.forName("UTF-8"));

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.addRequestProperty("Authorization", "Bearer " + AppConfig.TOKEN);
        urlConnection.setRequestMethod("POST");
        if (!"".equals(param)) {
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.write(paramB);
        }
        urlConnection.connect();
        iStream = urlConnection.getInputStream();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    iStream
                )
        );
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        if (urlConnection.getResponseCode() !=  HttpURLConnection.HTTP_OK) {
            br.close();
            iStream.close();
            urlConnection.disconnect();
            throw new Exception("Kode Respon " + urlConnection.getResponseCode());
        }

        if (!"application/json".equals(urlConnection.getContentType())) {
            br.close();
            iStream.close();
            urlConnection.disconnect();
            throw new Exception(sb.toString());
        }

        data = sb.toString();
        br.close();

        iStream.close();
        urlConnection.disconnect();
        return data;
    }

    public String readUrlPost(String url_s, String param) throws Exception {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        URL url = new URL(url_s);
        byte[] paramB = param.getBytes(Charset.forName("UTF-8"));

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
        wr.write(paramB);
        urlConnection.connect();
        iStream = urlConnection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                iStream));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        if (urlConnection.getResponseCode() !=  HttpURLConnection.HTTP_OK) {
            br.close();
            iStream.close();
            urlConnection.disconnect();
            throw new Exception("Kode Respon " + urlConnection.getResponseCode());
        }

        if (!"application/json".equals(urlConnection.getContentType())) {
            br.close();
            iStream.close();
            urlConnection.disconnect();
            throw new Exception(sb.toString());
        }

        data = sb.toString();
        br.close();

        iStream.close();
        urlConnection.disconnect();
        return data;
    }
}
