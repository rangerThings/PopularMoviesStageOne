package com.example.andywelsh.popularmoviesstageone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    public static final String POSTER_URL = "http://image.tmdb.org/t/p/w500";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //get ids
        TextView titleTv = findViewById(R.id.tv_title);
        TextView overviewTv = findViewById(R.id.tv_overview);
        TextView voteTv = findViewById(R.id.tv_vote_average);
        ImageView posterIv = findViewById(R.id.iv_poster_detail);
        TextView dateTv = findViewById(R.id.tv_date);

        //get the intent
        Intent inMovie = getIntent();
        Movie movie = inMovie.getParcelableExtra("clickedMovie");

        //Set the UI
        titleTv.setText(movie.getmTitle());
        String posterUrl = POSTER_URL + movie.getmPoster();
        Log.d("detail", posterUrl);
        Picasso.with(this).load(posterUrl).into(posterIv);
        overviewTv.setText(movie.getmOverview());
        voteTv.setText(movie.getmVoteAverage());
        dateTv.setText(movie.getmReleaseDate());








    }
}
