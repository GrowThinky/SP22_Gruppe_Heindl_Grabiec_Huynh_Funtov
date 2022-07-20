package de.uni_marburg.iliasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.uni_marburg.iliasapp.data.FeedReaderContract;
import de.uni_marburg.iliasapp.data.FeedReaderDbHelper;

public class VeranstaltungsDetails extends AppCompatActivity {

    private String nameInData;
    private String formInData;
    private String tagInData;
    private String startInData;
    private String endInData;
    private String raumInData;
    private String dozentInData;
    private String semesterInData;
    private SQLiteDatabase db;


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
        //semesterInData = getIntent().getStringExtra("semester").toString();
        setTextfieldVeranstaltungDetails();
    }

    private void setTextfieldVeranstaltungDetails() {
        TextView textfieldname = findViewById(R.id.textName);
        textfieldname.setText("Name der Veranstaltung: " + "\n" + nameInData);

        TextView textfieldform = findViewById(R.id.textForm);
        textfieldform.setText("Veranstaltungsform: " + "\n" + formInData);

        TextView textfieldtag = findViewById(R.id.textTag);
        textfieldtag.setText("Findet statt am: " + "\n" + tagInData);

        TextView textfieldzeit = findViewById(R.id.textZeit);
        textfieldzeit.setText("Findet statt von: " + "\n" + startInData +" bis " + endInData + " Uhr" );

        TextView textfieldraum = findViewById(R.id.textRaum);
        textfieldraum.setText("Findet statt in Raum: " + "\n" + raumInData);

        TextView textfielddozent = findViewById(R.id.textDozent);
        textfielddozent.setText("Verantwortlicher: " + "\n" + dozentInData);

    }

    public void buttonSpeichern(View view){
        addToMeineModule(nameInData,dozentInData);
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
    }

}


