package com.example.aurigaaristowijaya.birthday;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    Button btnreg;
    EditText logMail;
    EditText logPass;
    Button logBtn;
    ProgressBar loadinglog;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().setTitle(getString(R.string.sign_in));

        btnreg = findViewById(R.id.btnReg);
        logBtn = findViewById(R.id.btnAdd);
        logMail = findViewById(R.id.addName);
        logPass = findViewById(R.id.addMail);
        loadinglog = findViewById(R.id.loadinglog);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadinglog.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logBtn.setVisibility(View.INVISIBLE);
                loadinglog.setVisibility(View.VISIBLE);
                final String email = logMail.getText().toString();
                final String pass = logPass.getText().toString();

                if (email.isEmpty() || pass.isEmpty()){
                    showMessage(getString(R.string.please_fill_all_fields));
                    logBtn.setVisibility(View.VISIBLE);
                    loadinglog.setVisibility(View.INVISIBLE);
                }else{
                    checkAccount(email,pass);
                }
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(Login.this, Register.class);
                startActivity(reg);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null){
            String uid = mAuth.getCurrentUser().getUid();
            Intent home = new Intent(Login.this, MainActivity.class);
            home.putExtra("u",uid);
            startActivity(home);
        }
    }

    private void checkAccount(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUi(user);
                }else{
                    showMessage(getString(R.string.email_password_wrong));
                    logBtn.setVisibility(View.VISIBLE);
                    loadinglog.setVisibility(View.INVISIBLE);
                    updateUi(null);
                }
            }
        });
    }

    private void updateUi(FirebaseUser currentUser) {
        if (currentUser != null){
            String uid = currentUser.getUid();
            createdb(currentUser);
            Intent home = new Intent(getApplicationContext(), MainActivity.class);
            home.putExtra("u",uid);
            startActivity(home);
        }
    }

    private void createdb(FirebaseUser currentUser) {
        final String userid = currentUser.getUid();
        mDatabase.child("user").child(userid).child("settings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    SettingsData s = new SettingsData(false, false,"00:00",false,false, false, false, false, "-","-","0");
                    mDatabase.child("user").child(userid).child("settings").setValue(s);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
