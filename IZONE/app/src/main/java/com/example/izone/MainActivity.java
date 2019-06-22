package com.example.izone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.izone.Adapter.CardViewMemberAdapter;
import com.example.izone.Model.Member;
import com.example.izone.Model.MemberData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvCategory;
    ArrayList<Member> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("IZ*ONE Member");

        rvCategory = findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);

        list.addAll(MemberData.getListData());
        showData();
    }

    private void showData(){
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        CardViewMemberAdapter cardViewMemberAdapter = new CardViewMemberAdapter(this);
        cardViewMemberAdapter.setListMember(list);
        rvCategory.setAdapter(cardViewMemberAdapter);

        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedMember(list.get(position));
            }
        });
    }

    private void showSelectedMember(Member member){
        Bundle extras = new Bundle();
        extras.putString("name", member.getName());
        extras.putString("desc", member.getDescription());
        extras.putString("photo", member.getPhoto2());
        Intent showMember = new Intent(MainActivity.this, ViewDataActivity.class);
        showMember.putExtras(extras);
        startActivity(showMember);
    }

    public boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(a);
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(MainActivity.this, "Press again to exit!", Toast.LENGTH_SHORT).show();
    }
}
