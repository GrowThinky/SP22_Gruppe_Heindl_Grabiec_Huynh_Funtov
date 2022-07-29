package de.uni_marburg.iliasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.uni_marburg.iliasapp.data.Gebaeude;

public class Raumsuche extends AppCompatActivity {
    private List<Gebaeude> gebaeude;
    private String raumInData_2;

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

        raumInData_2 = getIntent().getStringExtra("raum_2");
        EditText editTextRaum = findViewById(R.id.raumsuche);
        editTextRaum.setText(raumInData_2);
    }

    /**
     * Eingegebener Raum wird gesucht und zeigt dann die RaumDetails des gesuchten Raums an.
     * Wenn der gesuchte Raum nicht existiert wird ein Hinweis eingeblendet
     */
    public void raumsucheStarten(View view) {
        String raum;
        EditText editText = findViewById(R.id.raumsuche);
        raum = editText.getText().toString();

        if (raum.trim().length() == 0){
            Toast.makeText(this, "Eingabe ist leer.", Toast.LENGTH_LONG).show();
            return;
        }
        
        String raumNr = raum.substring(0, 4);
        Iterator iterator = gebaeude.iterator();
        boolean isExists = false;



        // Prüft, ob der eingegebene Raum vorhanden ist
        while(iterator.hasNext() && !isExists) {
            Gebaeude tmp = (Gebaeude) iterator.next();
            if (raumNr.equals(tmp.getGebaeudeNr())) {
                isExists = true;

                // Startet die Activity RaumDetails und gibt die Adresse des Raums mit
                Intent intent = new Intent(this, RaumDetails.class);
                intent.putExtra("raum", raum);
                intent.putExtra("adresse", tmp.getAdresse());
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