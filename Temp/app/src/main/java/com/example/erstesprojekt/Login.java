package com.example.erstesprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    String benutzername;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hole zum start die Benutzerdaten aus der Register Activity
        benutzername = getIntent().getStringExtra("benutzername");
        password = getIntent().getStringExtra("password");



    }
}