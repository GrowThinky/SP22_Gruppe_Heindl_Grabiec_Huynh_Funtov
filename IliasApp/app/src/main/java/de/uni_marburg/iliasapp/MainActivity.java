package de.uni_marburg.iliasapp;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// html webscrapper // jsoup  txt

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, RecyclerViewAdapter.ItemClickListener {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<Modul> modulListe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        rows.remove(0);
        int nrRows = rows.size();
        int nrColumns = rows.get(0).getElementsByTag("Cell").size();

        //set up list containing modul infos
        setUpModulListe(rows);


        // set up the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, modulListe);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /** constructs full list of Modules from list of .xls file's Row Elements.
     */
    public void setUpModulListe (Elements rows){
        for (Element row : rows) {
            Elements cells = row.getElementsByTag("Cell");
            modulListe.add(makeModul(
                    cells.get(0).text(),
                    cells.get(1).text(),
                    cells.get(10).text(),
                    cells.get(11).text(),
                    cells.get(12).text(),
                    cells.get(16).text()));
        }
    }

    /** Generates a Modul instance
     */
    public Modul makeModul(String id, String name, String tag, String von, String bis, String raum){
        return new Modul(id,name, tag,von ,bis, raum);
    }


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position).name + " on row number " + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view, menu);
        final MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);


        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        List<Modul> filtered = Lists.newArrayList();
        for(Modul m : modulListe) {
            if(m.name.contains(query)) {
                filtered.add(m);
            }
        }
        adapter = new RecyclerViewAdapter(this, filtered);
        recyclerView.swapAdapter(adapter, false);
        Toast.makeText(this, "Showing results for " + query +"." , Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }



}
