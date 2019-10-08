package com.justin.android.foodapp;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Cursor cursor;
    private SQLiteDatabase db;
    private static final String NOODLES = "Noodles_Database";

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.noodleRecycler);

        // create and set a layoutManager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //specify and set a custom adapter
        try {
            SQLiteOpenHelper NoodleDatabaseHelper = new NoodleDatabaseHelper(this.getActivity());
            db = NoodleDatabaseHelper.getReadableDatabase();
            cursor = db.query(NOODLES, new String[]{"_id", "NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "PRICE"}, //"_id = ?" , new String[] {Integer.toString(noodleID)}
                    null, null, null, null, null);
            mAdapter = new MyAdapter(cursor, this.getActivity());
            mRecyclerView.setAdapter(mAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this.getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        mAdapter.setListener(new MyAdapter.Listener(){
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), NoodleDetailActivity.class);
                intent.putExtra(NoodleDetailActivity.EXTRA_NOODLE_ID, position);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("MainActivity", "Destroying the activity");
        cursor.close();
        db.close();
    }

}

/**




 **/