package com.uc.contohbottomnav;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    ActionBar myActionBar;
    SharedPreferences userPref;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    myActionBar.setTitle(R.string.title_plasticride);
                    myActionBar.setIcon(R.drawable.cek);
                    FragmentHome home = new FragmentHome();
                    FragmentTransaction ftHome = getSupportFragmentManager().beginTransaction();
                    ftHome.replace(R.id.frame_konten_main, home, "Home");
                    ftHome.commit();
                    return true;
                case R.id.navigation_history:
                    myActionBar.setTitle(R.string.title_history);
                    FragmentHistory history = new FragmentHistory();
                    FragmentTransaction ftHistory = getSupportFragmentManager().beginTransaction();
                    ftHistory.replace(R.id.frame_konten_main, history, "History");
                    ftHistory.commit();
                    return true;
                case R.id.navigation_inbox:
                    myActionBar.setTitle(R.string.title_inbox);
                    FragmentInbox inbox = new FragmentInbox();
                    FragmentTransaction ftInbox = getSupportFragmentManager().beginTransaction();
                    ftInbox.replace(R.id.frame_konten_main, inbox, "Inbox");
                    ftInbox.commit();
                    return true;
                case R.id.navigation_info:
                    myActionBar.setTitle(R.string.title_information);
                    FragmentInfo info = new FragmentInfo();
                    FragmentTransaction ftInfo = getSupportFragmentManager().beginTransaction();
                    ftInfo.replace(R.id.frame_konten_main, info, "Information");
                    ftInfo.commit();
                    return true;
                case R.id.navigation_account:
                    myActionBar.setTitle(R.string.title_account);
                    FragmentAccount account = new FragmentAccount();
                    FragmentTransaction ftAccount = getSupportFragmentManager().beginTransaction();
                    ftAccount.replace(R.id.frame_konten_main, account, "Account");
                    ftAccount.commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //untuk merubah title action bar setiap kali pindah menu fragment
        myActionBar = getSupportActionBar();
        //untuk ngeset awalnya muncul di menu HOME
        myActionBar.setTitle(R.string.title_plasticride);
        FragmentHome home = new FragmentHome();
        FragmentTransaction ftHome = getSupportFragmentManager().beginTransaction();
        ftHome.replace(R.id.frame_konten_main, home, "Home");
        ftHome.commit();
        userPref = getSharedPreferences("user", MODE_PRIVATE);

        BottomNavigationView navigation = findViewById(R.id.nav_menu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);
    }


    //ini fungsi untuk membuat bottom navigationnya nempel & tidak bergerak-gerak
    @SuppressLint("RestrictedAPI")
    public  void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }
    }

    //untuk buat apps kalau diklik back, minta tekan sekali lagi baru keluar dari apps
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
        Toast.makeText(MainActivity.this, "Press again to exit", Toast.LENGTH_SHORT).show();
    }

}
