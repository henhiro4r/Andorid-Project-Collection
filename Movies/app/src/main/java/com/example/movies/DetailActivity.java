package com.example.movies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movies.adapter.CastAdapter;
import com.example.movies.model.Cast;
import com.example.movies.model.Movie;
import com.example.movies.model.TvShow;
import com.example.movies.viewmodel.MainViewModel;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ImageView moviePoster, movieCover;
    private TextView tv_title, tv_description, tv_popular, tv_genre;
    private RecyclerView rv_cast;
    private Movie movie;
    private TvShow tvShow;
    private ProgressBar pb_detail;
    private CastAdapter castAdapter;
    private String temps = "";
    private MainViewModel genreViewModel, castViewModel;
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_SHOW = "extra_show";
    private static final String API_KEY = "68eff651539ae197e48884a6d31d2059";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        pb_detail = findViewById(R.id.pb_detail);
        showLoading(true);
        moviePoster = findViewById(R.id.detail_poster);
        movieCover = findViewById(R.id.detail_cover);
        tv_title = findViewById(R.id.detail_title);
        tv_description = findViewById(R.id.detail_description);
        tv_popular = findViewById(R.id.detail_popular);
        tv_genre = findViewById(R.id.detail_genre);
        rv_cast = findViewById(R.id.rv_cast);
        rv_cast.setHasFixedSize(true);
        movieCover.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        moviePoster.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_transition));

        castAdapter = new CastAdapter(this);
        castAdapter.notifyDataSetChanged();

        genreViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        genreViewModel.getGenre().observe(this, getGenre);

        castViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        castViewModel.getCast().observe(this, getCast);

        if (getIntent().getParcelableExtra(EXTRA_MOVIE) != null){
            movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            getSupportActionBar().setTitle(movie.getTitle());
            setDetails();
            String url = "https://api.themoviedb.org/3/movie/" + movie.getId_movie() + "?api_key=" + API_KEY + "&language=en-US";
            String castUrl = "https://api.themoviedb.org/3/movie/" + movie.getId_movie() + "/credits?api_key=" + API_KEY;
            genreViewModel.setGenre(url);
            castViewModel.setCast(castUrl);
        }else{
            tvShow = getIntent().getParcelableExtra(EXTRA_SHOW);
            getSupportActionBar().setTitle(tvShow.getTitle());
            setShowDetails();
            String url = "https://api.themoviedb.org/3/tv/" + tvShow.getId_show() + "?api_key=" + API_KEY + "&language=en-US";
            String castUrl = "https://api.themoviedb.org/3/tv/" + tvShow.getId_show() + "/credits?api_key=" + API_KEY;
            genreViewModel.setGenre(url);
            castViewModel.setCast(castUrl);
        }

        rv_cast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_cast.setAdapter(castAdapter);
    }

    private Observer<ArrayList<String>> getGenre = new Observer<ArrayList<String>>() {
        @Override
        public void onChanged(ArrayList<String> movieGenres) {
            if (movieGenres != null) {
                temps = TextUtils.join(" | ", movieGenres);
                setGenre(temps);
            }
        }
    };

    private void setGenre(String temps) {
        tv_genre.setText(temps);
    }

    private Observer<ArrayList<Cast>> getCast = new Observer<ArrayList<Cast>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Cast> casts) {
            if (casts != null){
                castAdapter.setCasts(casts);
                showLoading(false);
            }
        }
    };

    private void setShowDetails() {
        Glide.with(this).load(tvShow.getCover()).into(movieCover);
        Glide.with(this).load(tvShow.getPoster()).into(moviePoster);
        tv_title.setText(tvShow.getTitle());
        tv_popular.setText(tvShow.getPopularity());
        tv_description.setText(tvShow.getDescription());
    }

    private void setDetails() {
        Glide.with(this).load(movie.getCover()).into(movieCover);
        Glide.with(this).load(movie.getPoster()).into(moviePoster);
        tv_title.setText(movie.getTitle());
        tv_popular.setText(movie.getPopularity());
        tv_description.setText(movie.getDescription());
    }

    private void showLoading(Boolean state) {
        if (state) {
            pb_detail.setVisibility(View.VISIBLE);
        } else {
            pb_detail.setVisibility(View.GONE);
        }
    }
}
