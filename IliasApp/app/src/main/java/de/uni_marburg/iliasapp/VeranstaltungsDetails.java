package de.uni_marburg.iliasapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import de.uni_marburg.iliasapp.data.FeedReaderContract;
import de.uni_marburg.iliasapp.data.FeedReaderDbHelper;
/*
Die Klasse repräsentiert die Veranstaltungsdetails eines Moduls
 */
public class VeranstaltungsDetails extends AppCompatActivity {

    private String nameInData;
    private String formInData;
    private String tagInData;
    private String startInData;
    private String endInData;
    private String raumInData;
    private String dozentInData;
    private SQLiteDatabase db;
    private boolean belegt;
    public Button speichernButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veranstaltungs_details);


        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());
        // Gets the data repository in write mode
        db = dbHelper.getWritableDatabase();


        //Hole zum start die Benutzerdaten aus der MeineVeranstaltung Activity
        nameInData = getIntent().getStringExtra("name");
        formInData = getIntent().getStringExtra("form");
        tagInData = getIntent().getStringExtra("tag");
        startInData = getIntent().getStringExtra("start");
        endInData = getIntent().getStringExtra("end");
        raumInData = getIntent().getStringExtra("raum");
        dozentInData = getIntent().getStringExtra("dozent");
        belegt = getIntent().getBooleanExtra("belegt", false);
        //semesterInData = getIntent().getStringExtra("semester").toString();
        setTextfieldVeranstaltungDetails();
    }



    private void setTextfieldVeranstaltungDetails() {
        TextView textfieldname = findViewById(R.id.textName);
        textfieldname.setText(getString(R.string.Name) + "\n" + nameInData);

        TextView textfieldform = findViewById(R.id.textForm);
        textfieldform.setText(getString(R.string.Form) + "\n" + formInData);

        TextView textfieldtag = findViewById(R.id.textTag);
        textfieldtag.setText(getString(R.string.tag) + "\n" + tagInData);

        TextView textfieldzeit = findViewById(R.id.textZeit);
        textfieldzeit.setText(getString(R.string.von) + "\n" + startInData +getString(R.string.bis) + endInData + getString(R.string.zeit) );

        TextView textfieldraum = findViewById(R.id.textRaum);
        textfieldraum.setText(getString(R.string.raum) + "\n" + raumInData);

        TextView textfielddozent = findViewById(R.id.textDozent);
        textfielddozent.setText(getString(R.string.dozent) + "\n" + dozentInData);

        speichernButton = findViewById(R.id.button_Speichern);

        if(belegt){
            speichernButton.setText(R.string.entfernen);
            speichernButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    deleteFromMeineModule(nameInData);
                    showMessage("Removed");
                    finish();
                }
            });

        } else {
            speichernButton.setText(R.string.speichern);
            speichernButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    addToMeineModule(nameInData,dozentInData);
                    showMessage("Saved to 'Meine Module' !");

                }
            });

        }

    }

    public void deleteFromMeineModule(String nameToDelete){
        // Define 'where' part of query.
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_NAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { nameToDelete };
        // Issue SQL statement.
        int deletedRows = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }



    public void buttonRaumdetails(View view) {

        //Starte Raumdetails
        Intent detailsraumdplanClass = new Intent(this, Raumsuche.class);
        detailsraumdplanClass.putExtra("raum_2", raumInData);
        startActivity(detailsraumdplanClass);
    }

    public void addToMeineModule(String name,String dozent) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME, name);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DOZENT, dozent);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        speichernButton.setText("Gespeichert!");
        speichernButton.setClickable(false);
        speichernButton.setAlpha(.5f);
    }


    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message , Toast.LENGTH_SHORT).show();
    }



}


