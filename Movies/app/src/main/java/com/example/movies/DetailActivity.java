package com.example.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movies.adapter.CastAdapter;
import com.example.movies.model.Cast;
import com.example.movies.model.Movie;
import com.example.movies.model.MovieData;
import com.example.movies.model.TvShow;
import com.example.movies.model.TvShowData;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ImageView moviePoster, movieCover;
    TextView tv_title, tv_description, tv_director, tv_genre;
    RecyclerView rv_cast;
    ArrayList<Cast> casts = new ArrayList<>();
    Movie movie;
    TvShow tvShow;
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_SHOW = "extra_show";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        getSupportActionBar().hide();
        moviePoster = findViewById(R.id.detail_poster);
        movieCover = findViewById(R.id.detail_cover);
        tv_title = findViewById(R.id.detail_title);
        tv_description = findViewById(R.id.detail_description);
        tv_director = findViewById(R.id.detail_director);
        tv_genre = findViewById(R.id.detail_genre);
        rv_cast = findViewById(R.id.rv_cast);
        rv_cast.setHasFixedSize(true);
        movieCover.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        moviePoster.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_transition));
        if (getIntent().getParcelableExtra(EXTRA_MOVIE) != null){
            movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            getSupportActionBar().setTitle(movie.getTitle());
            setDetails();
            setCast(movie.getId_movie());
        }else{
            tvShow = getIntent().getParcelableExtra(EXTRA_SHOW);
            getSupportActionBar().setTitle(tvShow.getTitle());
            setShowDetails();
            setCast(tvShow.getId_show());
        }
    }

    private void setShowDetails() {
        Glide.with(this).load(tvShow.getCover()).into(movieCover);
        Glide.with(this).load(tvShow.getPoster()).into(moviePoster);
        tv_title.setText(tvShow.getTitle());
        tv_director.setText(tvShow.getCreator());
        tv_description.setText(tvShow.getDescription());
        tv_genre.setText(tvShow.getGenres());
    }

    private void setDetails() {
        Glide.with(this).load(movie.getCover()).into(movieCover);
        Glide.with(this).load(movie.getPoster()).into(moviePoster);
        tv_title.setText(movie.getTitle());
        tv_director.setText(movie.getDirector());
        tv_description.setText(movie.getDescription());
        tv_genre.setText(movie.getGenres());
    }

    private void setCast(String id) {
        String [][] c;
        if (id.charAt(0) == 'm'){
            c = MovieData.checkId(id);
            casts.addAll(MovieData.getCastData(c));
        }else{
            c = TvShowData.checkId(id);
            casts.addAll(TvShowData.getCastsData(c));
        }
        rv_cast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        CastAdapter castAdapter = new CastAdapter(this);
        castAdapter.setCasts(casts);
        rv_cast.setAdapter(castAdapter);
    }
}
