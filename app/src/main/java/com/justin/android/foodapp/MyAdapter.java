package com.justin.android.foodapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Listener listener;
    private Context context;
    private Cursor cursor;

    interface Listener {
        void onClick(int position);
    }

    // set listener to the implementation of given interface
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView textView;
        public ImageView imageView;
        public MyViewHolder(View v) {
            super(v);
            cardView = v.findViewById(R.id.card_view);
            textView = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.food_image);
        }
    }
    // contructor; pass the data from Java class Noodles to MyAdapter
    public MyAdapter(Cursor cursorInput, Context contextInput) {
        cursor = cursorInput;
        context = contextInput;
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    // create a new ViewHolder and place the views in layout my_text_view inside.
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        cursor.moveToPosition(position);

        holder.textView.setText(cursor.getString(1));

        Context tempContext = holder.textView.getContext();
        String image_name = cursor.getString(3);
        int resID = tempContext.getResources().getIdentifier(image_name, "drawable", tempContext.getPackageName());

        holder.imageView.setImageResource(resID);

        //final CardView cardView = holder.cardView;
        // when clicked, send an intent from cardview
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            // if listener is present, call the implementation of onClick set by the parent
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }

        });
    }

}
