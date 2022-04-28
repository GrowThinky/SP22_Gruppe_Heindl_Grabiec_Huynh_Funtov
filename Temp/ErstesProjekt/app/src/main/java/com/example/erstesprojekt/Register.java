package com.example.erstesprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Button um sich zu Regestrieren
    public void buttonSignUp(View view){
        EditText textfieldBenutzername = findViewById(R.id.txt_benutername1);
        String benutzername = textfieldBenutzername.getText().toString();

        EditText textfieldPassword = findViewById(R.id.txt_password1);
        String password = textfieldPassword.getText().toString();


        //Starte Login Activity
        Intent loginClass = new Intent(this, Login.class);

        loginClass.putExtra("benutzername", benutzername);
        loginClass.putExtra("password", password);
        startActivity(loginClass);
    }
}