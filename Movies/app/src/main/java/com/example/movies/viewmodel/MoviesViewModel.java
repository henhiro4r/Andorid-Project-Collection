package com.example.movies.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.movies.model.Movie;

import java.util.ArrayList;

public class MoviesViewModel extends ViewModel {
    private static final String API_KEY = "68eff651539ae197e48884a6d31d2059";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

}
