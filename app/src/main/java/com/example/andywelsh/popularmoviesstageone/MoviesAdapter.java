package com.example.andywelsh.popularmoviesstageone;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;



public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private final Movie[] mMovieResults;
    private final LayoutInflater mInflator;
    private final Context mAppContext;

    //constructor takes context and movie array passed in from MainActivity
    public MoviesAdapter(Context appContext, Movie[] movieResults) {
        this.mInflator = LayoutInflater.from(appContext);
        this.mMovieResults = movieResults;
        mAppContext = appContext;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mPosterView;

        ViewHolder(View view) {
            super(view);
            mPosterView = view.findViewById(R.id.iv_poster);
            view.setOnClickListener(this);
        }

        //What happens when clicked?
        @Override
        public void onClick(View v) {
            onItemClick(getAdapterPosition());

        }
    }

    //method that does something when user clicks a single item
    private void onItemClick(int position) {

        Intent detailIntent = new Intent(mAppContext, DetailActivity.class);
        detailIntent.putExtra("clickedMovie", mMovieResults[position]);
        mAppContext.startActivity(detailIntent);


        //Toast Debugging
        /*String toastText = "Item Clicked: " + String.valueOf(position);
        Toast toast = Toast.makeText(mAppContext, toastText, LENGTH_SHORT);
        toast.show();*/
    }

    //inflates the viewholder from the xml
    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflator.inflate(R.layout.movies_grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    //binds data to the viewholder
    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder viewHolder, int position) {
        String posterUrl = DetailActivity.POSTER_URL + "/" + mMovieResults[position].getmPoster();
        Picasso.with(mAppContext).load(posterUrl).into(viewHolder.mPosterView);
    }

    //counts items
    @Override
    public int getItemCount() {
        return mMovieResults.length;
    }


}
