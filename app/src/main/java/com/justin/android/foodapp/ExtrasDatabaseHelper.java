package com.justin.android.foodapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExtrasDatabaseHelper extends SQLiteOpenHelper {

    private static final String EXTRAS = "Extras_Database";
    private static final int DB_VERSION = 1; // change to higher number for new versions.
    private static Resources res = null;
    private static String packageName;
    private String[] extrasNames;
    //private int[] isIntegerArray;
    //private int[] isStringArray;
    private int[] booleanValuesArray;
    private String[] stringValuesArray;

    ExtrasDatabaseHelper(Context context) {
        super(context, EXTRAS, null, DB_VERSION);
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
        extrasNames = res.getStringArray(R.array.noodle_extras_names);
        // isIntegerArray = res.getIntArray(R.array.is_integer_array);
        // isStringArray = res.getIntArray(R.array.is_string_array);
        booleanValuesArray = res.getIntArray(R.array.boolean_value_array);
        stringValuesArray = res.getStringArray(R.array.string_value_array);
        Log.i("ExtrasDatabase", String.format("About to create Extras Database. oldVersion: %d", oldVersion));
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE Extras_Database (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "BOOLEAN_VALUE INTEGER,"
                    + "STRING_VALUE TEXT);");
            for (int j = 0; j < extrasNames.length; j++) {
                // drawableId = res.getIdentifier(noodleDrawableNames[j], "drawable", packageName);
                insertNoodle(db, extrasNames[j], booleanValuesArray[j], stringValuesArray[j]);
                // Log.i("DatabaseHelper", Integer.toString(drawableId));
            }
        }
        // for existing users, they get an new column added onto their database;
        // whereas, new users can immediately get the complete database created for them.
        if (oldVersion < 2) {
            // db.execSQL("ALTER TABLE NOODLE ADD COLUMN FAVOURITE NUMERIC")
        }
    }

    private static void insertNoodle(SQLiteDatabase db, String name, int booleanValue, String stringValue) {
        ContentValues extrasValues = new ContentValues();
        extrasValues.put("NAME", name);
        extrasValues.put("BOOLEAN_VALUE", booleanValue);
        extrasValues.put("STRING_VALUE", stringValue);

        db.insert(EXTRAS, null, extrasValues);
    }

}
