package com.example.erstesprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class LoginRefused extends AppCompatActivity {

    String code = "5826";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_refused);

        EditText myTextBox = (EditText) findViewById(R.id.txt_enterCode);

        //Wenn benutzer was in Textfeld eingibt
        myTextBox.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //Wenn code past erscheint button
                if(code.equals(myTextBox.getText().toString()))
                    findViewById(R.id.button_tryAgain).setVisibility(View.VISIBLE);

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

    }
    //Button Try again
    public void buttonTryAgain(View view){
        finish();
    }


}