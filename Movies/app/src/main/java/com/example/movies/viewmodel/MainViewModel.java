package com.example.movies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.movies.model.Movie;
import com.example.movies.model.TvShow;
import com.example.movies.repositories.ListMovieRepository;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private static final String API_KEY = "68eff651539ae197e48884a6d31d2059";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> listTvShows = new MutableLiveData<>();

    public void setListMovies(){
        if (listMovies != null){
            return;
        }else {
            ListMovieRepository movieRepository = ListMovieRepository.getInstance();
            listMovies = movieRepository.getMovieData();
        }
    }

    public LiveData<ArrayList<Movie>> getListMovies() {
        return listMovies;
    }

    void setListTvShows(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+ API_KEY +"&language=en-US";
    }
    public LiveData<ArrayList<TvShow>> getListTvShows() {
        return listTvShows;
    }
}
