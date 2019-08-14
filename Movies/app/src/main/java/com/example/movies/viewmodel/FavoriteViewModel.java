package com.example.movies.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.movies.model.Movie;
import com.example.movies.model.TvShow;

import java.util.ArrayList;

public class FavoriteViewModel extends ViewModel {
    private static final String API_KEY = "68eff651539ae197e48884a6d31d2059";
    private MutableLiveData<ArrayList<Movie>> FavMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> FavTvShows = new MutableLiveData<>();


}
