package com.example.movies;

import android.appwidget.AppWidgetManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movies.adapter.CastAdapter;
import com.example.movies.model.Cast;
import com.example.movies.model.Movie;
import com.example.movies.model.TvShow;
import com.example.movies.viewmodel.MainViewModel;
import com.example.movies.widget.FavoriteMovieWidget;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.movies.db.DatabaseContract.CONTENT_MOVIE_URI;
import static com.example.movies.db.DatabaseContract.CONTENT_SHOW_URI;
import static com.example.movies.helper.ContentValueHelper.getContentValueMovie;
import static com.example.movies.helper.ContentValueHelper.getContentValueShow;

public class DetailActivity extends AppCompatActivity {

    private ImageView moviePoster, movieCover, layerHide, addFav;
    private TextView tv_title, tv_description, tv_popular, tv_genre, tv_addFav;
    private Movie movie;
    private TvShow tvShow;
    private ProgressBar pb_detail;
    private CastAdapter castAdapter;
    private ActionBar toolbar;
    private MainViewModel mainViewModel;
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_SHOW = "extra_show";
    private static final String API_KEY = "68eff651539ae197e48884a6d31d2059";
    private boolean favorite = false;
    private boolean isMovie = false;
    Uri uri;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        pb_detail = findViewById(R.id.pb_detail);
        layerHide = findViewById(R.id.layerHide);
        toolbar = getSupportActionBar();
        showLoading(true);
        moviePoster = findViewById(R.id.detail_poster);
        movieCover = findViewById(R.id.detail_cover);
        addFav = findViewById(R.id.img_fav);
        tv_title = findViewById(R.id.detail_title);
        tv_description = findViewById(R.id.detail_description);
        tv_popular = findViewById(R.id.detail_popular);
        tv_genre = findViewById(R.id.detail_genre);
        tv_addFav = findViewById(R.id.tv_addFav);

        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView rv_cast = findViewById(R.id.rv_cast);
        rv_cast.setHasFixedSize(true);
        castAdapter = new CastAdapter(this);
        castAdapter.notifyDataSetChanged();

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getGenre().observe(this, getGenre);
        mainViewModel.getCast().observe(this, getCast);

        if (getIntent().getParcelableExtra(EXTRA_MOVIE) != null){
            isMovie = true;
            uri = getIntent().getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if (cursor.moveToFirst()) {
                    movie = new Movie(cursor);
                    cursor.close();
                }
            }
            if (movie != null){
                favorite = true;
            } else {
                favorite = false;
                movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            }
            String url = "https://api.themoviedb.org/3/movie/" + movie.getId_movie() + "?api_key=" + API_KEY + "&language=en-US";
            String castUrl = "https://api.themoviedb.org/3/movie/" + movie.getId_movie() + "/credits?api_key=" + API_KEY;
            setDetails();
            setAttribute(movie.getTitle(), url, castUrl);
        }else{
            isMovie = false;
            uri = getIntent().getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if (cursor.moveToFirst()) {
                    tvShow = new TvShow(cursor);
                    cursor.close();
                }
            }
            if (tvShow != null) {
                favorite = true;
            } else {
                favorite = false;
                tvShow = getIntent().getParcelableExtra(EXTRA_SHOW);
            }
            String url = "https://api.themoviedb.org/3/tv/" + tvShow.getId_show() + "?api_key=" + API_KEY + "&language=en-US";
            String castUrl = "https://api.themoviedb.org/3/tv/" + tvShow.getId_show() + "/credits?api_key=" + API_KEY;
            setShowDetails();
            setAttribute(tvShow.getTitle(), url, castUrl);
        }
        favChanger();
        addFav.setOnClickListener(favListener);
        rv_cast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_cast.setAdapter(castAdapter);
    }

    private View.OnClickListener favListener = view -> {
        if (!favorite) {
            if (isMovie){
                favorite = true;
                ContentValues values = getContentValueMovie(movie);
                getContentResolver().insert(CONTENT_MOVIE_URI, values);
                Toast.makeText(this, movie.getTitle() + " " + getString(R.string.added_favorite), Toast.LENGTH_SHORT).show();
                favChanger();
                updateWidget();
            } else {
                favorite = true;
                ContentValues values = getContentValueShow(tvShow);
                getContentResolver().insert(CONTENT_SHOW_URI, values);
                Toast.makeText(this, tvShow.getTitle() + " " + getString(R.string.added_favorite), Toast.LENGTH_SHORT).show();
                favChanger();
            }
        } else {
            if (isMovie) {
                favorite = false;
                getContentResolver().delete(uri, null, null);
                Toast.makeText(this, movie.getTitle() + " " + getString(R.string.rmed_favorite), Toast.LENGTH_SHORT).show();
                favChanger();
                updateWidget();
            } else {
                favorite = false;
                getContentResolver().delete(uri, null, null);
                Toast.makeText(this, tvShow.getTitle() + " " + getString(R.string.rmed_favorite), Toast.LENGTH_SHORT).show();
                favChanger();
            }
        }
    };

    private void setAttribute(String title, String url, String castUrl){
        toolbar.setTitle(title);
        mainViewModel.setGenre(url);
        mainViewModel.setCast(castUrl);
    }

    private Observer<ArrayList<String>> getGenre = new Observer<ArrayList<String>>() {
        @Override
        public void onChanged(ArrayList<String> movieGenres) {
            if (movieGenres != null) {
                String temps = TextUtils.join(" | ", movieGenres);
                tv_genre.setText(temps);
            }
        }
    };

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

    private void favChanger(){
        if (favorite) {
            Glide.with(this).load("").placeholder(R.drawable.ic_favorite).into(addFav);
            tv_addFav.setText(getString(R.string.rm_favorite));
        } else {
            Glide.with(this).load("").placeholder(R.drawable.ic_outline_favorite).into(addFav);
            tv_addFav.setText(getString(R.string.add_to_favorite));
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            pb_detail.setVisibility(View.VISIBLE);
            layerHide.setVisibility(View.VISIBLE);
            toolbar.hide();
        } else {
            pb_detail.setVisibility(View.GONE);
            layerHide.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
            layerHide.setVisibility(View.GONE);
            movieCover.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
            moviePoster.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_transition));
            toolbar.show();
        }
    }

    private void updateWidget() {
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName componentName = new ComponentName(getApplicationContext(), FavoriteMovieWidget.class);
        int[] id = manager.getAppWidgetIds(componentName);
        manager.notifyAppWidgetViewDataChanged(id, R.id.sv_widget);
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
