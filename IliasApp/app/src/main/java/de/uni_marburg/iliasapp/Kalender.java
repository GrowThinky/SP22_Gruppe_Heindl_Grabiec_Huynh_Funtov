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

public class Kalender extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender);
        //creatNewKaldendereintrag("Datenbanken", "10:30", "14:00", "Mi");



    }

    public void creatNewKaldendereintrag(View view){
        creatNewKaldendereintrag("Datenbanken", "10:30", "14:00", "Mi");
    }
    private void creatNewKaldendereintrag(String name, String von, String bis, String wochentag) {


        //Finde Grid
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.kalender_grid);
        //Erstelle Button(Kalendereintrag)
        Button button = new Button(this);
        //Position im Kalender
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        //Wochentag bestimmen
       switch(wochentag) {
            case "Mo": param.columnSpec = GridLayout.spec(2);; break;
            case "Di": param.columnSpec = GridLayout.spec(3);; break;
            case "Mi": param.columnSpec = GridLayout.spec(4);; break;
            case "Do": param.columnSpec = GridLayout.spec(5);; break;
            case "Fr": param.columnSpec = GridLayout.spec(6);; break;
            case "Sa": param.columnSpec = GridLayout.spec(7);; break;
            case "So": param.columnSpec = GridLayout.spec(8);; break;
        }


        //Zeile
        int ofset = 6; //Bsp. 10 Uhr ist in reihe 3 also 10 - 7
        int startDesEintrags = Integer.valueOf(von.substring(0,1)) - ofset;

        //Button Eigenschafen
        button.setText(name);
        param.columnSpec = GridLayout.spec(3, 1);
        param.height = 200;
        param.width = 500;


        button.setLayoutParams(param);
        //Im Kalender einfügen
       gridLayout.addView(button);

    }
    private void positionImKalenderfinden(){}

}