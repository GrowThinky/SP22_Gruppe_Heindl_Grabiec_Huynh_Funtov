package de.uni_marburg.iliasapp;

import static de.uni_marburg.iliasapp.data.FeedReaderContract.FeedEntry.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Toast;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import de.uni_marburg.iliasapp.data.FeedReaderContract;
import de.uni_marburg.iliasapp.data.FeedReaderDbHelper;
import de.uni_marburg.iliasapp.data.Modul;
import de.uni_marburg.iliasapp.data.ModulSearchData;

public class MeineVeranstaltungen extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {


    RecyclerViewAdapter adapterM;
    List itemIds = new ArrayList<>();
    List<String> ownModul;
    ArrayList<Modul> modulesToDisplay = new ArrayList<>();
    ModulSearchData modulSearchData;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpActivity();
    }


    @Override
    public void onRestart() {
        super.onRestart();
        modulesToDisplay.clear();
        setUpActivity();
    }

    private void setUpActivity() {
        setContentView(R.layout.activity_meine_veranstaltungen);
        modulSearchData = new ModulSearchData(getApplicationContext());

        ownModul = Lists.newArrayList();
        readDatabase();

        for(String modulName : ownModul) {
            for (Modul m : modulSearchData.modulListe) {
                if (m.name.equals(modulName)) {
                    modulesToDisplay.add(m);
                    break;
                }
            }
        }

        RecyclerView meineModule = findViewById(R.id.recyclerViewMV);
        meineModule.setLayoutManager(new LinearLayoutManager(this));
        adapterM = new RecyclerViewAdapter(this, modulesToDisplay);
        adapterM .setClickListener(this);
        meineModule.setAdapter(adapterM);
        if(modulesToDisplay.isEmpty()){
            showEmptyListMessage();
        }
    }

    private void showEmptyListMessage(){
        Toast.makeText(this, "Nothing to show", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent detailsVeranstaltungClass = new Intent(this, VeranstaltungsDetails.class);
        detailsVeranstaltungClass.putExtra("name", adapterM.getItem(position).name);
        detailsVeranstaltungClass.putExtra("form", adapterM.getItem(position).form);
        detailsVeranstaltungClass.putExtra("tag", adapterM.getItem(position).tag);
        detailsVeranstaltungClass.putExtra("start", adapterM.getItem(position).startTime);
        detailsVeranstaltungClass.putExtra("end", adapterM.getItem(position).endTime);
        detailsVeranstaltungClass.putExtra("raum", adapterM.getItem(position).raum);
        detailsVeranstaltungClass.putExtra("dozent", adapterM.getItem(position).dozent);
        detailsVeranstaltungClass.putExtra("semester", adapterM.getItem(position).semester);
        detailsVeranstaltungClass.putExtra("belegt", true);
        startActivity(detailsVeranstaltungClass);
    }




    public void readDatabase(){

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        Cursor  cursor = db.rawQuery("select * from " + TABLE_NAME,null);

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DOZENT
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_NAME + " DESC";

        // not being used, just reference for later maybe
        Cursor cursorB = db.query(
                TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex("name"));
                if(data != null){
                    ownModul.add(data);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();


    }
}
