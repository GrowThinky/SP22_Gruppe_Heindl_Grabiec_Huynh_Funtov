package de.uni_marburg.iliasapp;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
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
    static ArrayList<Modul> modulListe = new ArrayList<>();

    private Button filterButton;
    private Button moButton, diButton, miButton, doButton, frButton, allButton, üBButton, vLButton, sEButton;

    private ArrayList<String> selectedFilters = new ArrayList<String>();
    private String currentSearchText = "";
    private SearchView searchView;

    private int white, darkGray, blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        initColor();
        lookSelected(allButton);
        selectedFilters.add("all");


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

    private void initColor() {
        white = ContextCompat.getColor(getApplicationContext(), R.color.white);
        darkGray = ContextCompat.getColor(getApplicationContext(), R.color.darkerGray);
        blue = ContextCompat.getColor(getApplicationContext(), R.color.blue);
    }

    private void unselectedAllFilterButton() {
        lookUnSelected(allButton);
        lookUnSelected(moButton);
        lookUnSelected(diButton);
        lookUnSelected(miButton);
        lookUnSelected(doButton);
        lookUnSelected(frButton);
        lookUnSelected(üBButton);
        lookUnSelected(vLButton);
        lookUnSelected(sEButton);


    }

    private void lookSelected(Button parsedButton) {
        parsedButton.setTextColor(white);
        parsedButton.setBackgroundColor(darkGray);
    }

    private void lookUnSelected(Button parsedButton) {
        parsedButton.setTextColor(white);
        parsedButton.setBackgroundColor(blue);
    }

    private void initWidget() {
        allButton = (Button) findViewById(R.id.allFilter);
        moButton = (Button) findViewById(R.id.MoFilter);
        diButton = (Button) findViewById(R.id.DiFilter);
        miButton = (Button) findViewById(R.id.MiFilter);
        doButton = (Button) findViewById(R.id.DoFilter);
        frButton = (Button) findViewById(R.id.FrFilter);
        üBButton = (Button) findViewById(R.id.ÜbungFilter);
        vLButton = (Button) findViewById(R.id.VorlesungFilter);
        sEButton = (Button) findViewById(R.id.SeminarFilter);
    }

    /**
     * constructs full list of Modules from list of .xls file's Row Elements.
     */
    public void setUpModulListe(Elements rows) {
        for (Element row : rows) {
            Elements cells = row.getElementsByTag("Cell");
            modulListe.add(makeModul(
                    cells.get(0).text(),
                    cells.get(1).text(),
                    cells.get(2).text(),
                    cells.get(10).text(),
                    cells.get(11).text(),
                    cells.get(12).text(),
                    cells.get(16).text(),
                    cells.get(19).text()));
        }
    }

    /**
     * Generates a Modul instance
     */
    public Modul makeModul(String id, String name, String form, String tag, String von, String bis, String raum, String dozent) {
        return new Modul(id, name, form, tag, von, bis, raum, dozent);
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
        searchView = (SearchView) searchItem.getActionView();

        //searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(this);


        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
    currentSearchText =query;
    List<Modul> filtered = Lists.newArrayList();
        for(Modul m :modulListe) {
        if (m.name.contains(query) | m.dozent.contains(query)) {
            if (selectedFilters.contains("all")) {
                filtered.add(m);
            } else {
                for (String filter : selectedFilters) {
                    if (m.name.contains(filter) | m.dozent.contains(query)) {
                        filtered.add(m);
                    }
                }
            }
        }
    }

    adapter =new

    RecyclerViewAdapter(this,filtered);
        recyclerView.swapAdapter(adapter,false);
        Toast.makeText(this,"Showing results for "+query +".",Toast.LENGTH_SHORT).

    show();
        return true;
}
    @Override
    public boolean onQueryTextChange(String query){
        return false;
    }


    private void filterListTag(String status){
        if(status != null && !selectedFilters.contains(status))
            selectedFilters.add(status);
        
        List<Modul> filtered = Lists.newArrayList();

        for(Modul m : modulListe) {
            for(String filter: selectedFilters) {
                if (m.tag.contains(filter)) {
                    if (currentSearchText == "") {
                        filtered.add(m);
                    } else {
                        if (m.name.contains(currentSearchText) | m.dozent.contains(currentSearchText)) {
                            filtered.add(m);
                        }
                    }
                }
            }
        }
        adapter = new RecyclerViewAdapter(this, filtered);
        recyclerView.swapAdapter(adapter, false);
    }

    private void filterListForm(String status){
        if(status != null && !selectedFilters.contains(status))
        selectedFilters.add(status);
        List<Modul> filtered = Lists.newArrayList();

        for(Modul m : modulListe) {
            for(String filter: selectedFilters) {
                if (m.form.contains(filter)) {
                    if (currentSearchText == "") {
                        filtered.add(m);
                    } else {
                        if (m.name.contains(currentSearchText) | m.dozent.contains(currentSearchText)) {
                            filtered.add(m);
                        }
                    }
                }
            }
        }
        adapter = new RecyclerViewAdapter(this, filtered);
        recyclerView.swapAdapter(adapter, false);
    }

    public void allFilterTrapped(View view) {
        selectedFilters.clear();
        selectedFilters.add("all");
        
        unselectedAllFilterButton();
        lookSelected(allButton);
        searchView.setQuery("",false);
        searchView.clearFocus();
        adapter = new RecyclerViewAdapter(this, modulListe);
        recyclerView.swapAdapter(adapter, false);
    }

    public void MoFilterTrapped(View view) {
        filterListTag("Mo");
        selectFilter(moButton);
    }

    public void DiFilterTrapped(View view) {
        filterListTag("Di");
        selectFilter(diButton);
    }

    public void MiFilterTrapped(View view) {
        filterListTag("Mi");
        selectFilter(miButton);
    }

    public void DoFilterTrapped(View view) {
        filterListTag("Do");
        selectFilter(doButton);
    }

    public void FrFilterTrapped(View view) {
        filterListTag("Fr");
        selectFilter(frButton);
    }

    public void ÜBFilterTrapped(View view) {
        filterListForm("Übung");
        selectFilter(üBButton);
    }

    public void VLFilterTrapped(View view) {
        filterListForm("Vorlesung");
        selectFilter(vLButton);
    }

    public void SEFilterTrapped(View view) {
        filterListForm("Seminar");
        selectFilter(sEButton);
    }

    private void selectFilter(Button button) {
        lookSelected(button);
        lookUnSelected(allButton);
        selectedFilters.remove("all");
    }

    //Verfügbarkeit muss noch hinzugefügt werden
    public void AktuellFilterTrapped(View view) {
    }
}
