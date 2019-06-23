package com.example.movie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movie.Model.Movie;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    TextView tvTitle, tvReleaseDate, tvGenre, tvDirector, tvOverview;
    ImageView ivImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tvTitle = findViewById(R.id.tv_title);
        tvReleaseDate = findViewById(R.id.tv_releaseDate);
        tvGenre = findViewById(R.id.tv_genre);
        tvDirector = findViewById(R.id.tv_director);
        tvOverview = findViewById(R.id.movie_desc);
        ivImages = findViewById(R.id.movie_image);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        getSupportActionBar().setTitle(movie.getTitle() + " Details");
        tvTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvGenre.setText(movie.getGenres());
        tvDirector.setText(movie.getDirector());
        tvOverview.setText(movie.getDescription());
        Glide.with(this).load(movie.getPhoto()).apply(new RequestOptions().override(150,200)).into(ivImages);
    }
}
