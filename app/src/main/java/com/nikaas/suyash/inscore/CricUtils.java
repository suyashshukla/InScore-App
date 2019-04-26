package com.nikaas.suyash.inscore;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Suyash on 12/4/2017.
 */

public class CricUtils {

    public static StringBuilder JSON = new StringBuilder();
    public static String line = null;



    public static ArrayList<String> getJSON(String url)throws IOException {

        URL address = createURL(url);

        return makeHTTPRequest(address);
    }



    private static ArrayList<String> makeHTTPRequest(URL url)throws IOException {
        JSON.delete(0,JSON.length());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();

        Log.v("Utils","InputSTream = "+inputStream);

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,Charset.defaultCharset().forName("UTF-8")));

        line = br.readLine();
        JSON.append(line);



        return getScore(JSON.toString());

    }


    private static ArrayList<String> getScore(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.optJSONArray("data");

            ArrayList<String> matchData = new ArrayList<>();
            int i = 0;

            while(!jsonArray.isNull(i)){
                matchData.add(((JSONObject)jsonArray.get(i++)).get("description").toString());
            }

            return matchData;

        } catch (JSONException e) {
            return null;
        }
    }

    private static URL createURL(String url) {

        try {
            return new URL(url);
        } catch (Exception e) {
            return null;
        }

    }





}



