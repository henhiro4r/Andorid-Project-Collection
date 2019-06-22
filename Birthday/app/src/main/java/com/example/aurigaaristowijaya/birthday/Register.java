package com.example.aurigaaristowijaya.birthday;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText regName;
    EditText regMail;
    EditText regPass;
    EditText regCpass;
    Button regBtn;
    Button btnlog;

    ProgressBar loadingreg;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        regName = findViewById(R.id.addName);
        regMail = findViewById(R.id.addMail);
        regPass = findViewById(R.id.addPass);
        regCpass = findViewById(R.id.addCPass);
        regBtn = findViewById(R.id.btnAdd);
        loadingreg = findViewById(R.id.loadinglog);
        btnlog = findViewById(R.id.btnlog);

        loadingreg.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log = new Intent(Register.this, Login.class);
                startActivity(log);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regBtn.setVisibility(View.INVISIBLE);
                loadingreg.setVisibility(View.VISIBLE);
                final String name = regName.getText().toString();
                final String email = regMail.getText().toString();
                final String pass1 = regPass.getText().toString();
                final String pass2 = regCpass.getText().toString();

                if (name.isEmpty() || email.isEmpty() || pass1.isEmpty()){
                    showMessage(getString(R.string.please_fill_all_fields));
                    regBtn.setVisibility(View.VISIBLE);
                    loadingreg.setVisibility(View.INVISIBLE);
                    if  (!pass1.equals(pass2)){
                        showMessage(getString(R.string.password_didnt_match));
                        regBtn.setVisibility(View.VISIBLE);
                        loadingreg.setVisibility(View.INVISIBLE);
                    }
                }else{
                    createAccount(name,email,pass1);
                }
            }
        });

    }

    private void createAccount(final String name, String email, String pass1) {

        mAuth.createUserWithEmailAndPassword(email,pass1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    showMessage(getString(R.string.acc_created));
                    updateUI();
                }else {
                    showMessage(getString(R.string.something_wrong) + task.getException().getMessage());
                    regBtn.setVisibility(View.VISIBLE);
                    loadingreg.setVisibility(View.INVISIBLE);
                }
            }
        });

    }


    private void updateUI() {
        mAuth.signOut();
        Intent login = new Intent(Register.this, Login.class);
        startActivity(login);
    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}