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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MeineVeranstaltungen extends AppCompatActivity implements SearchView.OnQueryTextListener, RecyclerViewAdapter.ItemClickListener {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<Modul> ownmodulListe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_veranstaltungen);

        // data to populate the RecyclerView with
        ArrayList<String> results = new ArrayList<>();

        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        Document doc = null;

        try {
            inputStream = assetManager.open("data.xls");
            doc = Jsoup.parse(inputStream, "UTF-8", "http://example.com/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements rows = doc.getElementsByTag("Row");
        //Elements rowsi = doc.getElementsContainingText("Analysis");
        rows.remove(0);
        int nrRows = rows.size();
        int nrColumns = rows.get(0).getElementsByTag("Cell").size();

        //set up list containing modul infos
        setUpModulListe(rows);


        // set up the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, ownmodulListe);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    //Dummy-Daten "die ersten 6 Veranstaltungen werden momentan besucht
    public void setUpModulListe(Elements rows) {
        for (Element row : rows) {
            Elements cells = row.getElementsByTag("Cell");
            ownmodulListe.add(makeModul(
                    cells.get(0).text(),
                    cells.get(1).text(),
                    cells.get(2).text(),
                    cells.get(7).text(),
                    cells.get(10).text(),
                    cells.get(11).text(),
                    cells.get(12).text(),
                    cells.get(16).text(),
                    cells.get(19).text()));
        }
    }

    public Modul makeModul(String id, String name, String form, String semester, String tag, String von, String bis, String raum, String dozent) {
        return new Modul(id, name, form, semester, tag, von, bis, raum, dozent);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position).name + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent detailsVeranstaltungClass = new Intent(this, VeranstaltungsDetails.class);
        detailsVeranstaltungClass.putExtra("name", adapter.getItem(position).name);
        detailsVeranstaltungClass.putExtra("form", adapter.getItem(position).form);
        detailsVeranstaltungClass.putExtra("tag", adapter.getItem(position).tag);
        detailsVeranstaltungClass.putExtra("start", adapter.getItem(position).startTime);
        detailsVeranstaltungClass.putExtra("end", adapter.getItem(position).endTime);
        detailsVeranstaltungClass.putExtra("raum", adapter.getItem(position).raum);
        detailsVeranstaltungClass.putExtra("dozent", adapter.getItem(position).dozent);
        detailsVeranstaltungClass.putExtra("semester", adapter.getItem(position).semester);
        startActivity(detailsVeranstaltungClass);
    }
}
