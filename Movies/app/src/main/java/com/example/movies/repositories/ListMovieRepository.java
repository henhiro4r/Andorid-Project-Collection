package com.example.movies.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;
import android.util.Log;

import com.example.movies.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListMovieRepository {

    private static ListMovieRepository listMovieRepository;
    private static final String API_KEY = "68eff651539ae197e48884a6d31d2059";
    private ArrayList<Movie> listItems = new ArrayList<>();
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    public static ListMovieRepository getInstance(){
        if (listMovieRepository == null){
            listMovieRepository = new ListMovieRepository();
        }
        return listMovieRepository;
    }

    public MutableLiveData<ArrayList<Movie>> getMovieData(){
        setListMovie();
        listMovies.setValue(listItems);
        return listMovies;
    }

    private void setListMovie() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+ API_KEY +"&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        String id = String.valueOf(movie.getInt("id"));
                        String url = "https://api.themoviedb.org/3/movie/"+ id +"?api_key=" + API_KEY + "&language=en-US";
                        String genre = getGenre(url);
                        Movie m = new Movie();
                        m.setId_movie(id);
                        m.setTitle(movie.getString("title"));
                        m.setDirector("");
                        m.setDescription(movie.getString("overview"));
                        m.setPoster("https://image.tmdb.org/t/p/original" + movie.getString("poster_path") );
                        m.setCover("https://image.tmdb.org/t/p/original" + movie.getString("backdrop_path") );
                        m.setGenres(genre);
                        m.setReleaseDate(movie.getString("release_date"));
                        listItems.add(m);
                    }
                }catch (Exception e){
                    Log.d("exceptionMovie", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    private String getGenre(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<String> movieGenres = new ArrayList<>();
        final String[] temp = {""};
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("genres");
                    if (list.length() > 1){
                        for (int i = 0; i < list.length(); i++){
                            JSONObject g = list.getJSONObject(i);
                            movieGenres.add(g.getString("name"));
                        }
                    }else {
                        temp[0] = list.getJSONObject(0).getString("name");
                    }
                }catch (Exception e){
                    Log.d("exceptionGenre", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
        String temps = TextUtils.join(" | ", movieGenres);

        if (temp[0].equals("")){
            return temps;
        }else {
            return temp[0];
        }
    }
}
