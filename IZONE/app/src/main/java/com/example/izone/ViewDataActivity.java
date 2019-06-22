package com.example.izone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ViewDataActivity extends AppCompatActivity {

    ImageView cover;
    TextView name, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        getSupportActionBar().setTitle(extras.getString("name"));

        cover = findViewById(R.id.cover_photo);
        name = findViewById(R.id.cover_name);
        desc = findViewById(R.id.desc);

        Glide.with(this)
                .load(extras.getString("photo"))
                .into(cover);

        name.setText(extras.getString("name"));
        desc.setText(extras.getString("desc"));
    }
}
