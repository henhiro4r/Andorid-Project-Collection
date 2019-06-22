package com.example.volume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText width, height, length;
    Button calculate;
    TextView result;

    private static final String STATE_RESULT = "state_result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        width = findViewById(R.id.edt_width);
        length = findViewById(R.id.edt_length);
        height = findViewById(R.id.edt_height);
        calculate = findViewById(R.id.btn_calculate);
        result = findViewById(R.id.tv_result);

        calculate.setOnClickListener(this);

        if (savedInstanceState != null){
            String res = savedInstanceState.getString(STATE_RESULT);
            result.setText(res);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_calculate){
            String inLenght = length.getText().toString().trim();
            String inHeight = height.getText().toString().trim();
            String inWidth = width.getText().toString().trim();

            boolean isEmpty = false;
            boolean invalidDouble = false;

            if (TextUtils.isEmpty(inLenght)){
                isEmpty = true;
                length.setError("Field ini tidak boleh kosong");
            }
            if (TextUtils.isEmpty(inHeight)){
                isEmpty = true;
                height.setError("Field ini tidak boleh kosong");
            }
            if (TextUtils.isEmpty(inWidth)){
                isEmpty = true;
                width.setError("Field ini tidak boleh kosong");
            }

            Double l = toDouble(inLenght);
            Double w = toDouble(inWidth);
            Double h = toDouble(inHeight);

            if (l == null){
                invalidDouble = true;
                length.setError("Field ini harus berupa angka valid");
            }
            if (w == null){
                invalidDouble = true;
                width.setError("Field ini harus berupa angka valid");
            }
            if (h == null){
                invalidDouble = true;
                height.setError("Field ini harus berupa angka valid");
            }

            if (!isEmpty && !invalidDouble){
                double vol = l*w*h;
                result.setText(String.valueOf(vol));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_RESULT, result.getText().toString());
    }

    Double toDouble(String in){
        try{
            return Double.valueOf(in);
        }catch (NumberFormatException e){
            return null;
        }
    }
}
