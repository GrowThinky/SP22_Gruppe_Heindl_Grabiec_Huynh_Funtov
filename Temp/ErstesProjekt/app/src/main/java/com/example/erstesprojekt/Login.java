package com.example.erstesprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    String benutzernameInDatenbank;
    String passwordInDatenbank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hole zum start die Benutzerdaten aus der Register Activity
        benutzernameInDatenbank = getIntent().getStringExtra("benutzername");
        passwordInDatenbank = getIntent().getStringExtra("password");
    }
    //Button für Login
    public void buttonLogin(View view){
        //Holle Daten Aus Textfeldern
        EditText textfieldBenutzername = findViewById(R.id.txt_benutername2);
        String benutzernameLogin = textfieldBenutzername.getText().toString();

        EditText textfieldPassword = findViewById(R.id.txt_password2);
        String passwordLogin = textfieldPassword.getText().toString();
        

        //Wenn Password richtig öffne Welcome Fenster
        if(benutzernameLogin.equals(benutzernameLogin) && passwordInDatenbank.equals(passwordLogin)){
            //Starte Welcome Back Activity
            Intent welcomeBackClass = new Intent(this, WelcomeBack.class);
            welcomeBackClass.putExtra("benutzername", benutzernameLogin);
            welcomeBackClass.putExtra("password", passwordLogin);
            //startActivity(welcomeBackClass);
            //Spezieller Aufruf: öffnet eine Activität und erwartet beim der Schließung ein Ergebnis
            startActivityForResult(welcomeBackClass, 1);

        }else {
            //Starte Login refused Activity
            Intent loginRefusedClass = new Intent(this, LoginRefused.class);
            loginRefusedClass.putExtra("benutzername", benutzernameLogin);
            loginRefusedClass.putExtra("password", passwordLogin);
            startActivity(loginRefusedClass);
        }
    }

    //Wenn der benutzer das Password geändet hat, man also wieder zurück zum Login kommt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            passwordInDatenbank = data.getStringExtra("password");
    }




}