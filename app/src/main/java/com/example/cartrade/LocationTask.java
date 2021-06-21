package com.example.cartrade;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LocationTask extends AsyncTask<String, Integer, String> {
    private double lat;
    private double lon;
    private AddActivity addActivity;

    public LocationTask(AddActivity addActivity) {
        this.addActivity = addActivity;
    }

    @Override
    protected String doInBackground(String... strings) {
        System.out.println("do in background");
        lat = addActivity.getLat();
        lon = addActivity.getLon();
        String url = "https://eu1.locationiq.com/v1/reverse.php?key=pk.9481c0cbd2b6d81d3aef0f7077b78f4e&lat=" + lat + "&lon=" + lon + "&format=json";
        String sjson = "";

        try {

            HttpURLConnection connection =
                    (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                sjson = readResponseStream(reader);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sjson;
    }

    private String readResponseStream(BufferedReader reader) throws IOException {

        StringBuilder stringBuilder = new StringBuilder() ;
        String line = "";
        while ( ( line =reader.readLine()) != null ) {
            stringBuilder .append(line);
        }
        return stringBuilder . toString () ;
    }

}
