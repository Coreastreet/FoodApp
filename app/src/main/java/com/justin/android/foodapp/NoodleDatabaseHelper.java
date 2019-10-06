package com.justin.android.foodapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoodleDatabaseHelper extends SQLiteOpenHelper {

    private static final String NOODLES = "Noodles_Database";
    private static final int DB_VERSION = 1; // change to higher number for new versions.
    private static Resources res = null;
    private static String packageName;
    private String[] noodleNames;
    private String[] noodleDescriptions;
    private String[] noodleDrawableNames;
    private int drawableId;
    private int[] noodlePrices;

    NoodleDatabaseHelper(Context context) {
        super(context, NOODLES, null, DB_VERSION);
        res = context.getResources();
        packageName = context.getPackageName();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        noodleNames = res.getStringArray(R.array.noodle_names);
        noodleDescriptions = res.getStringArray(R.array.noodle_description);
        noodleDrawableNames = res.getStringArray(R.array.noodle_drawables);
        noodlePrices = res.getIntArray(R.array.noodle_prices);
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE Noodles_Database (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID TEXT, "
                    + "PRICE INTEGER);");
            for (int j = 0; j < noodleNames.length; j++) {
                insertNoodle(db, noodleNames[j], noodleDescriptions[j], noodleDrawableNames[j], noodlePrices[j]);
                Log.i("DatabaseHelper", String.format("String name of Image: %s", noodleDrawableNames[j]));
            }
        }
        // for existing users, they get an new column added onto their database;
        // whereas, new users can immediately get the complete database created for them.
        if (oldVersion < 2) {
            // db.execSQL("ALTER TABLE NOODLE ADD COLUMN FAVOURITE NUMERIC")
        }
    }

    private static void insertNoodle(SQLiteDatabase db, String name, String description, String drawable_name, int price) {
        ContentValues noodleValues = new ContentValues();
         noodleValues.put("NAME", name);
         noodleValues.put("DESCRIPTION", description);
         noodleValues.put("IMAGE_RESOURCE_ID", drawable_name);
         noodleValues.put("PRICE", price);
         db.insert(NOODLES, null, noodleValues);
    }

}
