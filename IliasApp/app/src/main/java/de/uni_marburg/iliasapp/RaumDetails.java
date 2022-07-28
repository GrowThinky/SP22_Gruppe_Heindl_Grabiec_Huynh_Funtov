package de.uni_marburg.iliasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.common.collect.Lists;

import java.util.List;

import de.uni_marburg.iliasapp.data.Modul;
import de.uni_marburg.iliasapp.data.ModulSearchData;

public class RaumDetails extends AppCompatActivity {
    private String raum;
    private String adresse;
    private String breitengrad;
    private String laengengrad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raum_details);

        Intent intent = getIntent();
        raum = intent.getStringExtra("raum");
        adresse = intent.getStringExtra("adresse");
        breitengrad = intent.getStringExtra("breitengrad");
        laengengrad = intent.getStringExtra("laengengrad");

        TextView raumNr = findViewById(R.id.raum_nr);
        raumNr.setText("Raum: " + raum);
        TextView adresseVomRaum = findViewById(R.id.adresse);
        adresseVomRaum.setText("Adresse: " + adresse);

        // Liste mit Modulen die in dem gesuchten Raum stattfinden
        List<Modul> raumBelegung = Lists.newArrayList();
        for(Modul m : ModulSearchData.modulListe) {
            if (m.getRaum().contains(raum)) {
                raumBelegung.add(m);
            }
        }

        // RecyclerView wird mit Modulen gefüllt die in dem Raum stattfinden
        RecyclerView raum_belegt = findViewById(R.id.raum_belegt);
        raum_belegt.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, raumBelegung);
        raum_belegt.setAdapter(adapter);
    }

    /**
     * Zeigt den Raum auf Google Maps an
     */
    public void aufMapsAnzeigen(View view) {
        // Erzeugt URL mit den Koordinaten des gesuchten Raums
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + breitengrad + "," + laengengrad + "(" + raum + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Startet Google Maps
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}