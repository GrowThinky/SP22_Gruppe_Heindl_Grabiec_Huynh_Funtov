package de.uni_marburg.iliasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void buttonVeranstaltungsplan(View view) {


        //Starte Veranstaltungsplan (Main Activity)
        Intent veranstaltungsplanClass = new Intent(this, MainActivity.class);

        startActivity(veranstaltungsplanClass);
    }
    public void buttonKalender(View view) {


        //Starte Kalender
        Intent kalenderClass = new Intent(this, Kalender.class);

        startActivity(kalenderClass);
    }
}