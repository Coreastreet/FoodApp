package com.justin.android.foodapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NoodleDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NOODLE_ID = "noodleID";
    private static final String NOODLES = "Noodles_Database";
    private MyExtrasAdapter my2ndAdapter;
    private Cursor cursorHolder;
    private SQLiteDatabase db;

    private SQLiteDatabase extrasDB;
    private Cursor extrasCursor;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private static final String EXTRAS = "Extras_Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noodle_detail);

        // display the details of noodles by getting noodleID from intent
        int noodleID = (Integer) getIntent().getExtras().get(EXTRA_NOODLE_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //String nameNoodle = cursorName.getString(1);

        // change the title of toolbar depending on the dish selected
        SQLiteOpenHelper NoodleDatabaseHelper = new NoodleDatabaseHelper(this);
        try {
            SQLiteDatabase nameDB = NoodleDatabaseHelper.getReadableDatabase();
            Cursor cursorName = nameDB.query(NOODLES, new String[]{"_id", "NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "PRICE"}, //"_id = ?" , new String[] {Integer.toString(noodleID)}
                    null, null, null, null, null);
            if (cursorName.moveToPosition(noodleID)) {
                getSupportActionBar().setTitle(cursorName.getString(1));
            }
            cursorName.close();
            nameDB.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        recyclerView = (RecyclerView) findViewById(R.id.extras_recycler);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        // SQLiteOpenHelper NoodleDatabaseHelper = new NoodleDatabaseHelper(this);

        SQLiteOpenHelper ExtrasDatabaseHelper = new ExtrasDatabaseHelper(this);

        /** try {
            SQLiteDatabase db = NoodleDatabaseHelper.getReadableDatabase();
            Cursor cursorHolder = db.query(NOODLES, new String[]{"_id", "NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "PRICE"}, //"_id = ?" , new String[] {Integer.toString(noodleID)}
                    null, null, null, null, null);

            if (cursorHolder.moveToPosition(noodleID)) {

                // get the value from the cursor
                String descriptionText = cursorHolder.getString(2);
                int photoId = cursorHolder.getInt(3);

                // get the appropriate view
                ImageView noodleImage = (ImageView) findViewById(R.id.noodleImage);
                TextView noodleDescriptionView = (TextView) findViewById(R.id.description);

                // set the values of the views
                noodleDescriptionView.setText(descriptionText);
                noodleImage.setImageResource(photoId);
            } else {
                Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show();
            }
            cursorHolder.close();
            db.close(); **/
        try {
            extrasDB = ExtrasDatabaseHelper.getReadableDatabase();
            extrasCursor = extrasDB.query(EXTRAS, new String[]{"_id", "NAME", "BOOLEAN_VALUE", "STRING_VALUE"}, //"_id = ?" , new String[] {Integer.toString(noodleID)}
                    null, null, null, null, null);
            my2ndAdapter = new MyExtrasAdapter(extrasCursor, this, noodleID);
            recyclerView.setAdapter(my2ndAdapter);

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        my2ndAdapter.setListener(new MyExtrasAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(NoodleDetailActivity.this, ExtrasActivity.class);
                intent.putExtra(NoodleDetailActivity.EXTRA_NOODLE_ID, position);
                (NoodleDetailActivity.this).startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("NoodleDetail", "Destroying the activity");

        extrasCursor.close();
        extrasDB.close();
    }

}
