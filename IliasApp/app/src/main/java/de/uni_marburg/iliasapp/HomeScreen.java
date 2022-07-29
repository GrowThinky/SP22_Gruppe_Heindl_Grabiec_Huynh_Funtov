package de.uni_marburg.iliasapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

import de.uni_marburg.iliasapp.data.Modul;
/*
Diese Klasse repräsentiert die Startseite, von der aus weitere Funktionen ausgeführt werden können
Außerdem kann über die Startseite die Sprache geändert werden
 */
public class HomeScreen extends AppCompatActivity {

    public static ArrayList<Modul> modulListe = new ArrayList<>();
    Bundle bundle;
    public String sessionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
         bundle = getIntent().getExtras();
         sessionId = bundle.getString("sid");

         Button changeLang = findViewById(R.id.changelanguage);
         changeLang.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 showChangeLanguageDialog();
             }
         });

        AssetManager assetManager = getAssets();

    }

    /**
     * Methode um Sprache zu ändern
     */
    private void showChangeLanguageDialog() {
        final String[] listItems = {getString(R.string.deutsch),getString(R.string.englisch)};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeScreen.this);
        mBuilder.setTitle(R.string.spracheWahl);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    //setze auf Deutsch
                    setLocale("de");
                    recreate();
                }
                //setze auf Englisch
                else if(i==1){
                    setLocale("en");
                    recreate();
                }
                //schließe Auswahlfenster
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    /**
     * Hilfsmethode um Sprache zu ändern
     * @param lang ausgewählte Sprache im AlertDialog
     */
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }


    public void buttonVeranstaltungsplan(View view) {

        //Starte Veranstaltungsplan (Main Activity)
        Intent veranstaltungsplanClass = new Intent(this, ModulSuche.class);
        veranstaltungsplanClass.putExtra("sid",sessionId);
        startActivity(veranstaltungsplanClass);
    }

    public void buttonRaumplan(View view) {
        // Startet Raumsuche (Raumsuche)
        Intent raumsuche = new Intent(this, Raumsuche.class);
        startActivity(raumsuche);
    }

    public void buttonmVeranstaltungen(View view) {
        //Starte Veranstaltungsplan (Main Activity)
        Intent meineVeranstaltungsplanClass = new Intent(this, MeineVeranstaltungen.class);


        startActivity(meineVeranstaltungsplanClass);
    }

    public void buttonmKalender(View view) {
        //Starte Veranstaltungsplan (Main Activity)
        Intent meineKalenderClass = new Intent(this, Kalender.class);


        startActivity(meineKalenderClass);
    }
    public void buttonmMensa(View view) {
        //Starte Mensa
        Intent meineMensaClass = new Intent(this, Mensa.class);


        startActivity(meineMensaClass);
    }
}