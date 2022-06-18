package de.uni_marburg.iliasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.common.collect.Lists;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.uni_marburg.iliasapp.data.HomeScreen;
import de.uni_marburg.iliasapp.data.ModulSearchData;

public class MeineVeranstaltungen extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    //RecyclerView recyclerViewM;
    RecyclerViewAdapter adapterM;
    //ArrayList<Modul> ownmodulListe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_veranstaltungen);

        //set up list containing modul infos
        //setUpModulListe(rows);



        // Liste mit Modulen die in dem gesuchten Raum stattfinden
        List<Modul> ownModul = Lists.newArrayList();
        for(Modul m : ModulSearchData.modulListe) {
                ownModul.add(m);
        }
        RecyclerView meineModule = findViewById(R.id.recyclerViewMV);
        meineModule.setLayoutManager(new LinearLayoutManager(this));
        adapterM = new RecyclerViewAdapter(this, ownModul);
        adapterM .setClickListener(this);
        meineModule.setAdapter(adapterM);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapterM.getItem(position).name + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent detailsVeranstaltungClass = new Intent(this, VeranstaltungsDetails.class);
        detailsVeranstaltungClass.putExtra("name", adapterM.getItem(position).name);
        detailsVeranstaltungClass.putExtra("form", adapterM.getItem(position).form);
        detailsVeranstaltungClass.putExtra("tag", adapterM.getItem(position).tag);
        detailsVeranstaltungClass.putExtra("start", adapterM.getItem(position).startTime);
        detailsVeranstaltungClass.putExtra("end", adapterM.getItem(position).endTime);
        detailsVeranstaltungClass.putExtra("raum", adapterM.getItem(position).raum);
        detailsVeranstaltungClass.putExtra("dozent", adapterM.getItem(position).dozent);
        detailsVeranstaltungClass.putExtra("semester", adapterM.getItem(position).semester);
        startActivity(detailsVeranstaltungClass);
    }
}
