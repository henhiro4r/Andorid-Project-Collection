package com.example.movie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private String[] dataName = {"Jang Won-young", "Miyawaki Sakura", "Jo Yu-ri", "Choi Ye-na", "Kwon Eun-bi", "Yabuki Nako", "Honda Hitomi", "Kim Min-ju", "Kang Hye-won", "Ahn Yu-jin", "Kim Chae-won","Kim Chae-yeon"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.lv_movie);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, dataName);
        listView.setAdapter(adapter);
    }
}
