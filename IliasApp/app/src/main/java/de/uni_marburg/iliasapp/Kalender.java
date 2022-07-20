package de.uni_marburg.iliasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.*;

import com.google.common.collect.Lists;

import java.util.List;

import de.uni_marburg.iliasapp.data.ModulSearchData;

public class Kalender extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender);
        creatNewKaldendereintrag("Datenbanken", "10:30", "14:30", "Mi");
        creatNewKaldendereintrag("SUR", "14:30", "15:00", "Mo");

        //List<Modul> ownModul = Lists.newArrayList();
        for(Modul m : ModulSearchData.modulListe) {
            //ownModul.add(m);
            //creatNewKaldendereintrag(m.name,m.startTime,m.endTime,m.tag);
        }

    }

    /**
     * Erzeugt einen einen Button und fügt ihn im Gridlayout ein
     * @param name Information aus der API für darüber wie die Veranstaltung heißt
     * @param von Information aus der API für darüber an welchem Tag die Veranstaltung statfindet
     * @param bis Information aus der API für darüber zu welcher Stunde die Veranstaltung endet
     * @param wochentag Information aus der API für darüber an welchem Tag die Veranstaltung statfindet
     */
    public void creatNewKaldendereintrag(String name, String von, String bis, String wochentag) {

        //Finde Grid
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.kalender_grid);
        //Erstelle Button(Kalendereintrag)
        Button button = new Button(this);
        //Button Eigenschafen
        androidx.gridlayout.widget.GridLayout.LayoutParams param = positionImKalenderfinden(wochentag, von, bis);
        button.setText(name);
        param = buttonDesign(param);
        button = buttonDesign2(button);
        button.setLayoutParams(param);
        //Im Kalender einfügen
       gridLayout.addView(button);

    }


    /**
     * Findet die Reihe und spalte im Gridlayout für den Button/Kalendereintrag
     * @param wochentag Information aus der API für darüber an welchem Tag die Veranstaltung statfindet
     * @param von Information aus der API für darüber zu welcher Stunde die Veranstaltung anfängt
     * @param bis Information aus der API für darüber zu welcher Stunde die Veranstaltung endet
     * @return gibt ein Layout wieder der dem Button zugeteilt wird
     */
    private androidx.gridlayout.widget.GridLayout.LayoutParams positionImKalenderfinden(String wochentag, String von, String bis){
        //Position im Kalender
        androidx.gridlayout.widget.GridLayout.LayoutParams param = new androidx.gridlayout.widget.GridLayout.LayoutParams();
        //Wochentag bestimmen
        switch(wochentag) {
            case "Mo": param.columnSpec = androidx.gridlayout.widget.GridLayout.spec(1); break;
            case "Di": param.columnSpec = androidx.gridlayout.widget.GridLayout.spec(2); break;
            case "Mi": param.columnSpec = androidx.gridlayout.widget.GridLayout.spec(3); break;
            case "Do": param.columnSpec = androidx.gridlayout.widget.GridLayout.spec(4); break;
            case "Fr": param.columnSpec = androidx.gridlayout.widget.GridLayout.spec(5); break;
            case "Sa": param.columnSpec = androidx.gridlayout.widget.GridLayout.spec(6); break;
            case "So": param.columnSpec = androidx.gridlayout.widget.GridLayout.spec(7); break;
        }

        //Start Zeile
        int ofset = 7; //Bsp. 10 Uhr ist in reihe 3 also 10 - 7
        int startDesEintrags = Integer.valueOf(von.substring(0,2)) - ofset;
        param.rowSpec = androidx.gridlayout.widget.GridLayout.spec(startDesEintrags);
        //Ende Zeile da eine Veranstaltung mehrere Studen gehen kann
        int endeZeile = Integer.valueOf(bis.substring(0,2)) - Integer.valueOf(von.substring(0,2)) - 1;
        if(Integer.valueOf(bis.substring(3,5)) > 0)
            endeZeile++;

        int counter = 0;
        while (endeZeile > 0){
            androidx.gridlayout.widget.GridLayout.LayoutParams param2 = new androidx.gridlayout.widget.GridLayout.LayoutParams();; // param2 wird hoch gezählt und param wird später ausgegeben
            param2.columnSpec = param.columnSpec;
            endeZeile--;
            counter++;
            androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.kalender_grid);
            Button button = new Button(this);
            param2.rowSpec = androidx.gridlayout.widget.GridLayout.spec(startDesEintrags + counter);
            param2 = buttonDesign(param2);
            button = buttonDesign2(button);
            button.setLayoutParams(param2);
            gridLayout.addView(button);
        }

        return param;
    }

    /**
     * Verändert das aussehen eines Buttons
     * @param param Layout vom Button
     * @return verändetes Layout vom Button
     */
    private androidx.gridlayout.widget.GridLayout.LayoutParams buttonDesign(androidx.gridlayout.widget.GridLayout.LayoutParams param){
        param.height = 200;
        param.width = 200;
        param.setMargins(0,0,0,0);
        return param;
    }

    /**
     * Verändert das aussehen eines Buttons
     * @param button der zu verändern ist
     * @return veränderter Button
     */
    private Button buttonDesign2(Button button){
        button.setPadding(0,0,0,0);
        button.setTop(0);
        button.setBottom(0);
        button.setAllCaps(false);
        button.setTextSize(10);
        return button;
    }

}