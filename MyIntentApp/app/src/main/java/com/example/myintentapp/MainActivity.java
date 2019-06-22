package com.example.myintentapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnMoveActivity;
    Button moveWithData;
    Button btnDialPhone;
    Button btnMoveWithObject;
    Button btnMoveForResult;
    TextView tvResult;

    private int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moveWithData = findViewById(R.id.btn_move_activity_data);
        btnMoveActivity = findViewById(R.id.btn_move_activity);
        btnDialPhone = findViewById(R.id.btn_dial_number);
        btnMoveWithObject = findViewById(R.id.btn_move_activity_object);
        btnMoveForResult = findViewById(R.id.btn_move_for_result);
        tvResult = findViewById(R.id.tv_result);

        btnMoveActivity.setOnClickListener(this);
        moveWithData.setOnClickListener(this);
        btnDialPhone.setOnClickListener(this);
        btnMoveWithObject.setOnClickListener(this);
        btnMoveForResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_move_activity:
                Intent move = new Intent(MainActivity.this, MoveActivity.class);
                startActivity(move);
                break;
            case R.id.btn_move_activity_data:
                Intent moveWData = new Intent(MainActivity.this, MoveWithDataActivity.class);
                moveWData.putExtra(MoveWithDataActivity.EXTRA_NAME, "Henry");
                moveWData.putExtra(MoveWithDataActivity.EXTRA_AGE, 20);
                startActivity(moveWData);
                break;
            case R.id.btn_move_activity_object:
                Person person = new Person();
                person.setName("Kim Yoo Jung");
                person.setAge(20);
                person.setEmail("henrydavidlie@gmail.com");
                person.setCity("Seoul");
                Intent moveWObject = new Intent(MainActivity.this, MoveWithObjectActivity.class);
                moveWObject.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person);
                startActivity(moveWObject);
                break;
            case R.id.btn_dial_number:
                String phone = "087828587697";
                Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
                startActivity(dial);
                break;
            case R.id.btn_move_for_result:
                Intent moveResult = new Intent(MainActivity.this, MoveForResultActivity.class);
                startActivityForResult(moveResult, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == MoveForResultActivity.RESULT_CODE) {
                int selectedValue = data.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0);
                tvResult.setText(String.format("Hasil : %s", selectedValue));
            }
        }
    }
}
