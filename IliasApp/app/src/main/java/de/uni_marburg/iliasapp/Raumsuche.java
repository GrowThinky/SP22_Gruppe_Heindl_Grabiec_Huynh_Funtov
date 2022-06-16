package de.uni_marburg.iliasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Raumsuche extends AppCompatActivity {
    private List<Gebaeude> gebaeude;
    private String raumindata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raumsuche);

        // Liste mit allen Gebäuden und Adressen
        gebaeude = new ArrayList() {
            {
                add(new Gebaeude("2050", "Universitätsstraße 24, 35032 Marburg", "50.80647", "8.76602"));
                add(new Gebaeude("2311", "Marbacher Weg 8, 35032 Marburg","50.81328","8.76308"));
                add(new Gebaeude("2370", "Biegenstraße 14, 35032 Marburg", "50.81090", "8.77360"));
                add(new Gebaeude("2410", "Deutschhausstraße 10, 35037 Marburg", "50.81533", "8.77031"));
                add(new Gebaeude("2670", "Bahnhofstraße 7, 35032 Marburg", "50.81647", "8.77024"));
                add(new Gebaeude("3060", "Hans-Meerwein-Straße 6, 35032 Marburg", "50.80929", "8.81106"));
                add(new Gebaeude("3071", "Hans-Meerwein-Straße 8, 35032 Marburg", "50.80981", "8.80975"));
            }
        };

        raumindata = getIntent().getStringExtra("raum");
    }

    public void raumsucheStarten(View view) {
        String raum;
        String raumNr;
        EditText editText = findViewById(R.id.raumsuche);
        if (editText.equals(null)){
            raum = raumindata;
            System.out.println(raumindata);
            raumNr = raum.substring(0, 4);
        }
        else {
            raum = editText.getText().toString();
            raumNr = raum.substring(0, 4);
        }

        Iterator iterator = gebaeude.iterator();
        boolean isExists = false;

        // Prüft, ob der eingegebene Raum vorhanden ist
        while(iterator.hasNext() && !isExists) {
            Gebaeude tmp = (Gebaeude) iterator.next();
            if (raumNr.equals(tmp.getGebaeudeNr())) {
                isExists = true;
                Intent intent = new Intent(this, RaumDetails.class);
                intent.putExtra("raum", raum);
                intent.putExtra("adressadresse", tmp.getAdresse());
                intent.putExtra("breitengrad", tmp.getBreitengrad());
                intent.putExtra("laengengrad", tmp.getLaengengrad());
                startActivity(intent);
            }
        }

        // Eingegeben Raum ist nicht vorhanden
        if (!isExists) {
            Toast.makeText(this, "Diesen Raum gibt es nicht.", Toast.LENGTH_LONG).show();
        }
    }
}