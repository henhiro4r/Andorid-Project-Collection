package com.example.favorite;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.favorite.adapter.CastAdapter;
import com.example.favorite.model.Cast;
import com.example.favorite.model.Movie;
import com.example.favorite.model.TvShow;
import com.example.favorite.viewmodel.MainViewModel;

import java.util.ArrayList;

import static com.example.favorite.db.DatabaseContract.CONTENT_MOVIE_URI;
import static com.example.favorite.db.DatabaseContract.CONTENT_SHOW_URI;
import static com.example.favorite.utils.ContentValueHelper.getContentValueMovie;
import static com.example.favorite.utils.ContentValueHelper.getContentValueShow;

public class DetailActivity extends AppCompatActivity {

    private ImageView iv_Poster, iv_Cover, layerHide, addFav;
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
        iv_Poster = findViewById(R.id.detail_poster);
        iv_Cover = findViewById(R.id.detail_cover);
        addFav = findViewById(R.id.img_fav);
        tv_title = findViewById(R.id.detail_title);
        tv_description = findViewById(R.id.detail_description);
        tv_popular = findViewById(R.id.detail_popular);
        tv_genre = findViewById(R.id.detail_genre);
        tv_addFav = findViewById(R.id.tv_addFav);

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
            if (isMovie) {
                favorite = true;
                ContentValues values = getContentValueMovie(movie);
                getContentResolver().insert(CONTENT_MOVIE_URI, values);
                Toast.makeText(this, movie.getTitle() + " " + getString(R.string.added_favorite), Toast.LENGTH_SHORT).show();
                favChanger();
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
        public void onChanged(@Nullable ArrayList<String> strings) {
            if (strings != null) {
                String temps = TextUtils.join(" | ", strings);
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
        Glide.with(this).load(tvShow.getCover()).into(iv_Cover);
        Glide.with(this).load(tvShow.getPoster()).into(iv_Poster);
        tv_title.setText(tvShow.getTitle());
        tv_popular.setText(tvShow.getPopularity());
        tv_description.setText(tvShow.getDescription());
    }

    private void setDetails() {
        Glide.with(this).load(movie.getCover()).into(iv_Cover);
        Glide.with(this).load(movie.getPoster()).into(iv_Poster);
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
            iv_Cover.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
            iv_Poster.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_transition));
            toolbar.show();
        }
    }
}
