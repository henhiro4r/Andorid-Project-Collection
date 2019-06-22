package com.example.myintentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static android.security.KeyChain.EXTRA_NAME;

public class MoveWithDataActivity extends AppCompatActivity {

    public static final String EXTRA_AGE = "extra_age";
    public static final String EXTRA_NAME = "extra_name";
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_with_data);

        data = findViewById(R.id.tv_data_received);
        String name = getIntent().getStringExtra(EXTRA_NAME);
        int age = getIntent().getIntExtra(EXTRA_AGE, 0);
        String txt = "Name : " + name + ",\nYour Age : " + age;
        data.setText(txt);
    }
}
