package com.justin.android.foodapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import static com.justin.android.foodapp.NoodleDetailActivity.EXTRA_NOODLE_ID;

public class MyExtrasAdapter extends
        RecyclerView.Adapter<MyExtrasAdapter.ViewHolder> {

        private static final String NOODLES = "Noodles_Database";

        private static final int Name = 1;
        // private static final int IsBoolean = 2;
        // private static final int IsString = 3;
        private static final int BooleanValue = 2;
        private static final int StringValue = 3;

        private static final int IMAGE_DESCRIPTION_TYPE = 0;
        private static final int ITEM_TYPE = 1;

        private int noodle_id;
        private Cursor cursorHolder;
        private Context contextHolder;
        // private String noodleType;
        // private String temperature;
        // private int withOomori;
        // private int withExtraDippingSauce;
        private Listener listener;

        interface Listener {
            void onClick(int position);
        }

        public void setListener(MyExtrasAdapter.Listener listener) {
            this.listener = listener;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            private Context context;

            private CardView cardView;
            private CardView topCardView;
            private TextView extraOptionName;
            private TextView hiddenRightText;
            private ImageView rightArrowIcon;

            private ImageView foodImage;
            private TextView foodDescription;
            private CardView topCardViewHolder;

            public ViewHolder(CardView v, int viewType) {
                super(v);
                if (viewType == 0) {
                    topCardViewHolder = v;
                    foodDescription = (TextView) topCardViewHolder.findViewById(R.id.description2);
                    foodImage = (ImageView) topCardViewHolder.findViewById(R.id.noodleImage2);
                } else {
                    cardView = v;
                    extraOptionName = (TextView) cardView.findViewById(R.id.textView2);
                    hiddenRightText = (TextView) cardView.findViewById(R.id.hiddenText);
                    rightArrowIcon = (ImageView) cardView.findViewById(R.id.rightArrow);
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return IMAGE_DESCRIPTION_TYPE;
            } else {
                return ITEM_TYPE;
            }
        }

        @Override
        public MyExtrasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == IMAGE_DESCRIPTION_TYPE) {
                CardView topCardView =
                        (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.top_image_description, parent, false);
                return new ViewHolder(topCardView, viewType);
            } else {
                CardView cardView =
                        (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.extra_choices, parent, false);
                return new ViewHolder(cardView, viewType);
            }
        }

        @Override
        public void onBindViewHolder(MyExtrasAdapter.ViewHolder holder, final int position) {

            if (position == 0) {
                SQLiteOpenHelper NoodleDatabaseHelper = new NoodleDatabaseHelper(holder.topCardViewHolder.getContext());
                SQLiteDatabase db = NoodleDatabaseHelper.getReadableDatabase();
                Cursor newCursor = db.query(NOODLES, new String[]{"_id", "NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "PRICE"}, //"_id = ?" , new String[] {Integer.toString(noodleID)}
                        null, null, null, null, null);

                newCursor.moveToPosition(noodle_id);
                String image_name = newCursor.getString(3);
                int image_number = holder.foodDescription.getResources().getIdentifier(image_name, "drawable", contextHolder.getPackageName());
                holder.foodImage.setImageResource(image_number);
                holder.foodDescription.setText(newCursor.getString(2));

                newCursor.close();
                db.close();
            } else {
                cursorHolder.moveToPosition(position-1);
                holder.extraOptionName.setText(cursorHolder.getString(Name));
                if (!cursorHolder.getString(StringValue).isEmpty()) {
                    holder.hiddenRightText.setVisibility(View.VISIBLE);
                    holder.hiddenRightText.setText(cursorHolder.getString(StringValue));
                } else {
                    //holder.hiddenRightText.setVisibility(View.GONE);
                    holder.rightArrowIcon.setImageResource(R.drawable.ic_chevron_right_black_24dp);
                }

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

        @Override
        public int getItemCount() {
            return cursorHolder.getCount() + 1;
        }

        public MyExtrasAdapter(Cursor cursor, Context context, int id) {
            cursorHolder = cursor;
            contextHolder = context;
            noodle_id = id;
        }

}
