package com.example.movies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.example.movies.model.Cast;
import com.example.movies.model.Movie;
import com.example.movies.model.TvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private static final String API_KEY = "68eff651539ae197e48884a6d31d2059";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> resultMovie = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> listTvShows = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> resultTvShows = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Cast>> listCast = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> movieGenres = new MutableLiveData<>();

    public void setListMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
        final ArrayList<Movie> listItems = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie m = new Movie();
                        String id = String.valueOf(movie.getInt("id"));
                        m.setId_movie(id);
                        m.setTitle(movie.getString("title"));
                        m.setPopularity(String.valueOf(movie.getDouble("popularity")));
                        m.setDescription(movie.getString("overview"));
                        m.setPoster("https://image.tmdb.org/t/p/original" + movie.getString("poster_path"));
                        m.setCover("https://image.tmdb.org/t/p/original" + movie.getString("backdrop_path"));
                        m.setReleaseDate(movie.getString("release_date").substring(0, 4));
                        listItems.add(m);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("exceptionMovie", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureMovie", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getListMovies() {
        return listMovies;
    }

    public void setListTvShows(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+ API_KEY +"&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvshow = list.getJSONObject(i);
                        TvShow tv = new TvShow();
                        String id = String.valueOf(tvshow.getInt("id"));
                        tv.setId_show(id);
                        tv.setTitle(tvshow.getString("name"));
                        tv.setPopularity(String.valueOf(tvshow.getDouble("popularity")));
                        tv.setDescription(tvshow.getString("overview"));
                        tv.setPoster("https://image.tmdb.org/t/p/original" + tvshow.getString("poster_path"));
                        tv.setCover("https://image.tmdb.org/t/p/original" + tvshow.getString("backdrop_path"));
                        tv.setReleaseDate(tvshow.getString("first_air_date").substring(0,4));
                        listItems.add(tv);
                    }
                    listTvShows.postValue(listItems);
                }catch (Exception e){
                    Log.d("exceptionTv", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureTv", error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<TvShow>> getListTvShows() {
        return listTvShows;
    }

    public void setGenre(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<String> listItems = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("genres");
                    for (int i = 0; i < list.length(); i++){
                        JSONObject g = list.getJSONObject(i);
                        listItems.add(g.getString("name"));
                    }
                    movieGenres.postValue(listItems);
                }catch (Exception e){
                    Log.d("exceptionGenre", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureGenre", error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<String>> getGenre() {
        return movieGenres;
    }

    public void setCast(String url){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Cast> listItems = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("cast");
                    for (int i = 0; i < list.length(); i++){
                        JSONObject c = list.getJSONObject(i);
                        Cast cast = new Cast();
                        cast.setImg_url("https://image.tmdb.org/t/p/original" + c.getString("profile_path"));
                        cast.setRole(c.getString("character"));
                        cast.setName(c.getString("name"));
                        listItems.add(cast);
                    }
                    listCast.postValue(listItems);
                }catch (Exception e){
                    Log.d("exceptionCast", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureCast", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Cast>> getCast(){
        return listCast;
    }

    public void searchMovie(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie m = new Movie();
                        String id = String.valueOf(movie.getInt("id"));
                        m.setId_movie(id);
                        m.setTitle(movie.getString("title"));
                        m.setPopularity(String.valueOf(movie.getDouble("popularity")));
                        m.setDescription(movie.getString("overview"));
                        m.setPoster("https://image.tmdb.org/t/p/original" + movie.getString("poster_path"));
                        m.setCover("https://image.tmdb.org/t/p/original" + movie.getString("backdrop_path"));
                        m.setReleaseDate(movie.getString("release_date").substring(0, 4));
                        listItems.add(m);
                    }
                    resultMovie.postValue(listItems);
                } catch (Exception e) {
                    Log.d("onFailureSearchMovie", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureSearchMovie", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getSearchMovieResult() {
        return resultMovie;
    }

    public void searchTvShow(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listItems = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvshow = list.getJSONObject(i);
                        TvShow tv = new TvShow();
                        String id = String.valueOf(tvshow.getInt("id"));
                        tv.setId_show(id);
                        tv.setTitle(tvshow.getString("name"));
                        tv.setPopularity(String.valueOf(tvshow.getDouble("popularity")));
                        tv.setDescription(tvshow.getString("overview"));
                        tv.setPoster("https://image.tmdb.org/t/p/original" + tvshow.getString("poster_path"));
                        tv.setCover("https://image.tmdb.org/t/p/original" + tvshow.getString("backdrop_path"));
                        tv.setReleaseDate(tvshow.getString("first_air_date").substring(0,4));
                        listItems.add(tv);
                    }
                    resultTvShows.postValue(listItems);
                }catch (Exception e){
                    Log.d("onFailureSearchTv", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureSearchTv", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvShow>> getSearchShowResult() {
        return resultTvShows;
    }
}
