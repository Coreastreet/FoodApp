package com.justin.android.foodapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    // private Noodles[] noodlesDataset = Noodles.noodles;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String NOODLES = "Noodles_Database";
    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Log.i("MainActivity", "Hello World");
        // get the recycler view
        // set the toggle button for navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this,
                        drawer, toolbar, R.string.nav_open_drawer,
                        R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView = (RecyclerView) findViewById(R.id.noodleRecycler);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //specify an adapter
        try {
            SQLiteOpenHelper NoodleDatabaseHelper = new NoodleDatabaseHelper(this);
            db = NoodleDatabaseHelper.getReadableDatabase();
            cursor = db.query(NOODLES, new String[] {"_id", "NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "PRICE"}, //"_id = ?" , new String[] {Integer.toString(noodleID)}
                    null, null , null, null, null);
            mAdapter = new MyAdapter(cursor, this);
            recyclerView.setAdapter(mAdapter);
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        mAdapter.setListener(new MyAdapter.Listener(){
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, NoodleDetailActivity.class);
                intent.putExtra(NoodleDetailActivity.EXTRA_NOODLE_ID, position);
                (MainActivity.this).startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "Destroying the activity");
        cursor.close();
        db.close();
    }
}
