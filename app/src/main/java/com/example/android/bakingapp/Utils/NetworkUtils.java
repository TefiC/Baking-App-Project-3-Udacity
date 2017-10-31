package com.example.android.bakingapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Helper methods to handle events related to network requests
 */

public class NetworkUtils {

    /*
     * Fields
     */

    public static final String RECIPES_SEARCH_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    /*
     * Methods
     */

    /**
     * Fetches data by performing an http network request
     *
     * @param url The url to fetch data from
     *
     * @return A JSON in String format
     * @throws IOException In case there is an error with the connection
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Builds a URL from a String Url
     *
     * @param stringUrl The String Url
     *
     * @return A URL instance
     */
    public static URL buildSearchUrl(String stringUrl) {

        URL url = null;

        try {
            Uri buildUri = buildUri(stringUrl);
            url = new URL(buildUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Build a Uri from a String Url
     *
     * @param stringUrl The String Url
     *
     * @return A Uri instance
     */
    private static Uri buildUri(String stringUrl) {
        return Uri.parse(stringUrl);
    }

    /**
     * Determine if there is an internet connection available.
     *
     * @return true if there is, false if there isn't.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
