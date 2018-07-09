package com.example.andywelsh.popularmoviesstageone;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String mTitle;
    private String mPoster;
    private String mOverview;
    private String mVoteAverage;
    private String mReleaseDate;




    //Bulk Constructor for Movie class, may not need it
    public Movie (String inTitle, String inPoster, String inOverview, String inVoteAverage, String inReleaseDate){
        setmTitle(inTitle);
        setmPoster(inPoster);
        setmOverview(inOverview);
        setmVoteAverage(inVoteAverage);
        setmRealeaseDate(inReleaseDate);
    }


    //Auto-generated Parcelable methods, (modified)
    private Movie(Parcel in) {
        mTitle = in.readString();
        mPoster = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readString();
        mReleaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPoster);
        dest.writeString(mOverview);
        dest.writeString(mVoteAverage);
        dest.writeString(mReleaseDate);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    //Getter and Setters for Movie
    public String getmTitle() {
        return mTitle;
    }

    private void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmPoster() {
        return mPoster;
    }

    private void setmPoster(String mPoster) {
        this.mPoster = mPoster;
    }

    public String getmOverview() {
        return mOverview;
    }

    private void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getmVoteAverage() {
        return mVoteAverage;
    }

    private void setmVoteAverage(String mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    private void setmRealeaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }


}
