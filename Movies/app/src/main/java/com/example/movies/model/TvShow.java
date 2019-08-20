package com.example.movies.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.movies.db.DatabaseContract;

import static com.example.movies.db.DatabaseContract.getColumnString;

public class TvShow implements Parcelable {

    private String id_show;
    private String title;
    private String popularity;
    private String description;
    private String poster;
    private String cover;
    private String releaseDate;

    public TvShow() {

    }

    public TvShow(String id_show, String title, String popularity, String description, String poster, String cover, String releaseDate) {
        this.id_show = id_show;
        this.title = title;
        this.popularity = popularity;
        this.description = description;
        this.poster = poster;
        this.cover = cover;
        this.releaseDate = releaseDate;
    }

    public TvShow(Cursor cursor){
        this.id_show = getColumnString(cursor, DatabaseContract.TableColumns.OBJECT_ID);
        this.title = getColumnString(cursor, DatabaseContract.TableColumns.TITLE);
        this.popularity = getColumnString(cursor, DatabaseContract.TableColumns.POPULAR);
        this.description = getColumnString(cursor, DatabaseContract.TableColumns.DESCRIPTION);
        this.poster = getColumnString(cursor, DatabaseContract.TableColumns.POSTER);
        this.cover = getColumnString(cursor, DatabaseContract.TableColumns.COVER);
        this.releaseDate = getColumnString(cursor, DatabaseContract.TableColumns.RELEASE_YEAR);
    }

    public String getId_show() {
        return id_show;
    }

    public void setId_show(String id_show) {
        this.id_show = id_show;
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

    public void setPopularity(String creator) {
        this.popularity = creator;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_show);
        dest.writeString(this.title);
        dest.writeString(this.popularity);
        dest.writeString(this.description);
        dest.writeString(this.poster);
        dest.writeString(this.cover);
        dest.writeString(this.releaseDate);
    }

    protected TvShow(Parcel in) {
        this.id_show = in.readString();
        this.title = in.readString();
        this.popularity = in.readString();
        this.description = in.readString();
        this.poster = in.readString();
        this.cover = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
