package com.example.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String id_movie;
    private String title;
    private String popularity;
    private String description;
    private String poster;
    private String cover;
    private String releaseDate;
    private int isFav;

    public Movie() {

    }

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String director) {
        this.popularity = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getIsFav() {
        return isFav;
    }

    public void setIsFav(int isFav) {
        this.isFav = isFav;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_movie);
        dest.writeString(this.title);
        dest.writeString(this.popularity);
        dest.writeString(this.description);
        dest.writeString(this.poster);
        dest.writeString(this.cover);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.isFav);
    }

    protected Movie(Parcel in) {
        this.id_movie = in.readString();
        this.title = in.readString();
        this.popularity = in.readString();
        this.description = in.readString();
        this.poster = in.readString();
        this.cover = in.readString();
        this.releaseDate = in.readString();
        this.isFav = in.readInt();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
