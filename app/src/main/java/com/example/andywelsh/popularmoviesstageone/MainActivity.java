package com.example.andywelsh.popularmoviesstageone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;


//This app connects to a movie database api and returns the results in a recyclerview.
//Needs API Key input in UTILITIES class.

public class MainActivity extends AppCompatActivity {

    //Constants
    private static final String DEFAULT_SORT = "popularity.desc";
    private static final String DEFAULT_SORT_MESSAGE = "Sorted by Popularity";
    private static final String ERROR_MESSAGE = "ERROR: No network connection";
    private static final String POPULAR_SORT_MESSAGE = "Sorted by Popularity";
    private static final String TOP_RATED_MESSAGE = "Sorted by Top Rated";
    private static final int COLUMNS = 2;
    private TextView mHeaderBox;

    private MoviesAdapter adapter;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //IDs
        mHeaderBox = findViewById(R.id.tv_header_text);

        //get shared preferences
        prefs = this.getSharedPreferences(getString(R.string.pref_sort), Context.MODE_PRIVATE);
        String sortPref = prefs.getString(getString(R.string.pref_sort), DEFAULT_SORT);
        String sortText = prefs.getString(getString(R.string.pref_text), DEFAULT_SORT_MESSAGE);

        //Is the device connected to a network?
        if (Utilities.isOnline(this)) {

            //build the URL with the shared preference, default is popularity (2nd arg)
            URL url = Utilities.buildUrl(sortPref);

            //Set default sort message
            mHeaderBox.setText(sortText);

            //Start an async task to make request to http.
            //keeps the request off of the main thread.
            //Modified from Udacity T02.05 solution
            new MoviesAsyncTask().execute(url);
        } else {
            //Should probably be a broadcast receiver to listen/resume when connection occurs
            //As-is:  Simple message in the header textview
            mHeaderBox.setText(ERROR_MESSAGE);

        }
    }

    //create and inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        SharedPreferences.Editor editor = prefs.edit();
        switch (itemThatWasClickedId) {
            case (R.id.dropdown_highest):
                //change sort references so main screen loads this preference next time
                editor.putString(getString(R.string.pref_sort), Utilities.SORT_RATED);
                editor.putString(getString(R.string.pref_text), TOP_RATED_MESSAGE);
                editor.apply();

                //Set header text to sort message
                mHeaderBox.setText(TOP_RATED_MESSAGE);

                //run a new request to sort by top rated
                new MoviesAsyncTask().execute(Utilities.buildUrl(Utilities.SORT_RATED));
                return true;
            case (R.id.dropdown_popular):
                //change sort references so main screen loads this preference next time
                editor.putString(getString(R.string.pref_sort), Utilities.SORT_POPULAR);
                editor.putString(getString(R.string.pref_text), POPULAR_SORT_MESSAGE);
                editor.apply();

                //Set header text to sort message
                mHeaderBox.setText(POPULAR_SORT_MESSAGE);

                //run a new request to sort by top rated
                new MoviesAsyncTask().execute(Utilities.buildUrl(Utilities.SORT_POPULAR));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //AsyncTask that executes the request from the movie API and populates a recyclerview
    class MoviesAsyncTask extends AsyncTask<URL, Void, Movie[]> {

        final ProgressBar mProgBar = findViewById(R.id.pb_progress);

       @Override
        protected void onPreExecute() {
           //get the spinner going
           mProgBar.setVisibility(View.VISIBLE);
       }

        @Override
        protected Movie[] doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieResults;

            try {
                //try to use the getResponse utility to connect with movie API.
                movieResults = Utilities.getResponseFromHttpUrl(searchUrl);
                return Utilities.parseAndBuildMovie(movieResults);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Movie[] moviesToAdapter) {

            //Proceed only if the array is not null or empty
            if (moviesToAdapter != null && moviesToAdapter.length != 0) {
                //turn the spinner off
                mProgBar.setVisibility(View.INVISIBLE);

                // set up the RecyclerView
                RecyclerView recyclerView = findViewById(R.id.rv_Movies);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, COLUMNS));
                adapter = new MoviesAdapter(getApplicationContext(), moviesToAdapter);
                recyclerView.setAdapter(adapter);
            }
        }


    }

}
