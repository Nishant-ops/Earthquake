package com.example.earthquake;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 8/28/17.
 */

public class Query {
    /**
     * Create a private constructor because no one should ever create a {@link Query} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private Query() {
    }


    public static  List<EarthQuake> fetchEarthQuakeData(String requestUrl) {

        URL url = createUrl(requestUrl);
        List<EarthQuake> EarthQuakes = null;

        try {
            String jsonResponse = makeHttpRequest(url);
            EarthQuakes = extractFeaturesFromJson(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return EarthQuakes;
    }

    /**
     * Return a list of {@link EarthQuake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<EarthQuake> extractFeaturesFromJson(String jsonResponse) {

        if(jsonResponse == null) {
            return null;
        }
        // Create an empty ArrayList that we can start adding EarthQuakes to
        ArrayList<EarthQuake> EarthQuakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of EarthQuake objects with the corresponding data.

            JSONObject object = new JSONObject(jsonResponse);
            JSONArray features = object.getJSONArray("features");

            for(int i = 0; i < features.length(); i++) {
                JSONObject properties = features.getJSONObject(i).getJSONObject("properties");

                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                String url = properties.getString("url");
                long time = properties.getLong("time");

                EarthQuake EarthQuake = new EarthQuake(mag, place, time, url);

                EarthQuakes.add(EarthQuake);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the EarthQuake JSON results", e);
        }

        // Return the list of EarthQuakes
        return EarthQuakes;
    }

    private static URL createUrl(String stringUrl) {

        URL url = null;

        if(stringUrl == null) {
            return url;
        }
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        if(url == null) {
            return jsonResponse;
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream (InputStream inputStream) {
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();

        if(inputStream == null) {
            return "";
        }

        streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        reader = new BufferedReader(streamReader);
          try {
            String line = reader.readLine();
            while (line != null) {

                result.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

}