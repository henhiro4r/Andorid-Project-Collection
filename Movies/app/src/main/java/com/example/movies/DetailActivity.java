package com.example.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movies.adapter.CastAdapter;
import com.example.movies.model.Cast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ImageView moviePoster, movieCover;
    TextView tv_title, tv_description;
    RecyclerView rv_cast;
    ArrayList<Cast> casts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        moviePoster = findViewById(R.id.detail_poster);
        movieCover = findViewById(R.id.detail_cover);
        tv_title = findViewById(R.id.detail_title);
        tv_description = findViewById(R.id.detail_description);
        rv_cast = findViewById(R.id.rv_cast);
        rv_cast.setHasFixedSize(true);
        movieCover.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        setCast();
    }

    private void setCast() {
         casts = new ArrayList<>();
//         casts.addAll();
         rv_cast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
         CastAdapter castAdapter = new CastAdapter(this);
         castAdapter.setCasts(casts);
         rv_cast.setAdapter(castAdapter);
    }
}
