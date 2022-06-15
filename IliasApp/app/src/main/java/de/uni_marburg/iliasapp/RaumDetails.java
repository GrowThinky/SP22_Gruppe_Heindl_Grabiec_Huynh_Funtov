package de.uni_marburg.iliasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Lists;

import java.util.List;

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
    }

    // Zeigt den Raum auf Google Maps an
    public void aufMapsAnzeigen(View view) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + breitengrad + "," + laengengrad + "(" + raum + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}