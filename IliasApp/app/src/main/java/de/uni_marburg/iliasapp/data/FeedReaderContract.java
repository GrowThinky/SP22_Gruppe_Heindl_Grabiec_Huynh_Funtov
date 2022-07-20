package de.uni_marburg.iliasapp.data;

import android.provider.BaseColumns;

    public final class FeedReaderContract {

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.COLUMN_NAME_NAME + " TEXT," +
                        FeedEntry.COLUMN_NAME_von + " TEXT," +
                        FeedEntry.COLUMN_NAME_bis + " TEXT," +
                        FeedEntry.COLUMN_NAME_DOZENT + " TEXT)";


        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;



        // To prevent someone from accidentally instantiating the contract class,
        // make the constructor private.
        private FeedReaderContract() {}

        /* Inner class that defines the table contents */
        public class FeedEntry implements BaseColumns {
            public static final String TABLE_NAME = "entry";
            public static final String COLUMN_NAME_NAME = "name";
            public static final String COLUMN_NAME_von = "von";
            public static final String COLUMN_NAME_bis = "bis";
            public static final String COLUMN_NAME_DOZENT = "dozent";
        }
    }






