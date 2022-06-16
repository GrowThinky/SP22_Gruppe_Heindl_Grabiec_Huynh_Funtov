package de.uni_marburg.iliasapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.uni_marburg.iliasapp.MainActivity;
import de.uni_marburg.iliasapp.MeineVeranstaltungen;
import de.uni_marburg.iliasapp.R;
import de.uni_marburg.iliasapp.Raumsuche;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
    }

    public void buttonVeranstaltungsplan(View view) {


        //Starte Veranstaltungsplan (Main Activity)
        Intent veranstaltungsplanClass = new Intent(this, MainActivity.class);

        startActivity(veranstaltungsplanClass);
    }

    public void buttonRaumplan(View view) {
        // Startet Raumsuche (Raumsuche)
        Intent raumsuche = new Intent(this, Raumsuche.class);
        startActivity(raumsuche);
    }

    public void buttonmVeranstaltungen(View view) {
        //Starte Veranstaltungsplan (Main Activity)
        Intent meineVeranstaltungsplanClass = new Intent(this, MeineVeranstaltungen.class);


        startActivity(meineVeranstaltungsplanClass);
    }
}