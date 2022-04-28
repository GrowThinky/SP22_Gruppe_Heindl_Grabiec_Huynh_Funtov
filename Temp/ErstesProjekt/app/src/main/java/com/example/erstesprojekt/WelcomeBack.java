package com.example.erstesprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class WelcomeBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_back);

        //Ändere den Text der Menübar
        String benutzername = getIntent().getStringExtra("benutzername");
        TextView menübarText = (TextView)findViewById(R.id.txt_WelcomeBack);
        menübarText.setText("Welcome back, " + benutzername);

    }

    //Button logout
    public void buttonLogout(View view){
        finish();
    }

    //Button Change Password
    public void buttonChangePassword(View view){
        //Holle das neue Password aus dem Textfeld
        EditText textfieldnewPassword = findViewById(R.id.txt_newPassword);
        String newPassword = textfieldnewPassword.getText().toString();

        //schicke das neue Password an die alte Activität
        Intent intent= getIntent();
        intent.putExtra("password", newPassword);
        setResult(RESULT_OK, intent);

        //Beende die aktuelle Aktivität
        finish();

    }



}