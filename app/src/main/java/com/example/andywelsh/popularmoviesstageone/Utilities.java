package com.example.andywelsh.popularmoviesstageone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

class Utilities {

    //API key must be added in order for app to run
    private final static String API_KEY = "";

    //Constants for JSON parsing
    private final static String MOVIE_RESULTS = "results";
    private final static String MOVIE_TITLE = "title";
    private final static String MOVIE_POSTER = "poster_path";
    private final static String MOVIE_OVERVIEW = "overview";
    private final static String MOVIE_VOTE_AVERAGE = "vote_average";
    private final static String MOVIE_RELEASE_DATE = "release_date";


    //Constants for URL
    private final static String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie";
    final static String SORT_POPULAR = "popular";
    final static String SORT_RATED = "top_rated";
    private final static String API_TEXT = "api_key";



    //Builds proper URL from constants
    //Modified from Udacity T 02.05 Solution

    public static URL buildUrl(String mHowSort) {
        //mHowSort comes in from shared preferences during initial creation, and from
        //user clicks otherwise

        Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(mHowSort)
                .appendQueryParameter(API_TEXT, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            //check the url
            Log.d("main", builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    //Makes a request to database using Scanner
    //from Udacity T02.04 Solution
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

    //parses JSON response, builds Movie object, and returns Array of Movie objects
    public static Movie[] parseAndBuildMovie(String inJsonMdbData) throws JSONException {

        //Initialize Json movie object
        JSONObject movieObj = new JSONObject(inJsonMdbData);

        //Get the results into an array
        JSONArray jsonArrayResult = movieObj.getJSONArray(MOVIE_RESULTS);

        //initialize an array based on the number of results (generic arraylist didn't work, throws error 'expecting array')
        Movie[] movieCollection = new Movie[jsonArrayResult.length()];

        //walk the JSON response using constants

        for (int i = 0; i < jsonArrayResult.length(); i++) {

            //pulls a single JsonObject at position i out of the full results array
            JSONObject thisMovie = jsonArrayResult.getJSONObject(i);

            //using opt string so it can return an empty string if it needs to
            String movieTitle = thisMovie.optString(MOVIE_TITLE);
            String movieOverview = thisMovie.optString(MOVIE_OVERVIEW);
            String moviePoster = thisMovie.optString(MOVIE_POSTER);
            String movieVote = thisMovie.optString(MOVIE_VOTE_AVERAGE);
            String movieRelease = thisMovie.optString(MOVIE_RELEASE_DATE);

            //create new movie object and add it to the collection at position i
            movieCollection[i] = new Movie(movieTitle, moviePoster, movieOverview, movieVote, movieRelease);

        }

        //Debug message
        Log.d("utilities", "number of movies in collection: " + String.valueOf(movieCollection.length));

        return movieCollection;
    }

    //checks network connection, does not specifically see if there is active internet (no ping)
    //Simplest answer from https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    //Slightly modified

    public static Boolean isOnline(Context mContext) {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


}
