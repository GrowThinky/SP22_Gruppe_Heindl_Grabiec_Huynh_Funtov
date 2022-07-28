package de.uni_marburg.iliasapp.data;

import android.content.Context;
import android.content.res.AssetManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 *  Data holder class that reads all module infos from the provided data.xls file and
 *  instantiates a Modul instance from each row's info ito be stored in ArrayList modulListe.
 *
 */
public class ModulSearchData {

    public static ArrayList<Modul> modulListe = new ArrayList<>();


    public ModulSearchData(Context context) {
        // access xls file in assets directory:
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        Document doc = null;

        try {
            inputStream = assetManager.open("data.xls");
            doc = Jsoup.parse(inputStream, "UTF-8", "http://example.com/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // store data as list
        Elements rows = doc.getElementsByTag("Row");
        rows.remove(0);  // dropping the column names
        int nrRows = rows.size();
        int nrColumns = rows.get(0).getElementsByTag("Cell").size();

        setUpModulListe(rows);
    }

    /**
     * Generates a Modul instance
     */
    public Modul makeModul(String id, String name, String form, String semester, String tag, String von, String bis, String raum, String dozent, int course_id) {
        return new Modul(id, name, form, semester, tag, von, bis, raum, dozent, course_id);
    }

    /**
     *  Takes the relevant Modul information from the generated Elements List to call makeModul
     *   for each datapoint.
     */
    public void setUpModulListe(Elements rows) {
        for (Element row : rows) {
            Elements cells = row.getElementsByTag("Cell");
            modulListe.add(makeModul(
                    cells.get(0).text(),  // id
                    cells.get(1).text(),  // Name
                    cells.get(2).text(),  // Veranstaltungsform
                    cells.get(7).text(),  // Semester
                    cells.get(10).text(), // Tag
                    cells.get(11).text(), // von
                    cells.get(12).text(), // bis
                    cells.get(16).text(), // Raum
                    cells.get(19).text(), // Dozent
                    8174));      // Course Id

        }
    }
}
