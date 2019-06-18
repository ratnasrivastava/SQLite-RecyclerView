package com.example.a51006705.sqliterecyclerview;


import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by 51006705 on 18-06-2019.
 */

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public GroceryAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }


    public class GroceryViewHolder extends RecyclerView.ViewHolder {


        public TextView nameText;
        public TextView countText;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.textView_ItemName);
            countText = itemView.findViewById(R.id.textView_ItemCount);
        }
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.recyler_view_items, viewGroup, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder groceryViewHolder, int i) {
        if (!mCursor.moveToPosition(i)) {
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_NAME));
        int amount = mCursor.getInt(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLOUMN_AMOUNT));
        //id for deleting items
        long id = mCursor.getLong(mCursor.getColumnIndex(GroceryContract.GroceryEntry._ID));

        groceryViewHolder.nameText.setText(name);
        groceryViewHolder.countText.setText(String.valueOf(amount));
        //to identify which item to remove
        groceryViewHolder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }


}
