package com.example.a51006705.sqliterecyclerview;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mEditTextItemName;
    private TextView mTextViewAmount;
    private int mAmount = 0;
    private SQLiteDatabase mDataBase;
    private GroceryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextItemName = findViewById(R.id.editText_item_name);
        mTextViewAmount = findViewById(R.id.textView_amount);

        GroceryDBHelper dbHelper = new GroceryDBHelper(this);
        mDataBase = dbHelper.getWritableDatabase();

        Button buttonIncrease = findViewById(R.id.button_increase);
        Button buttonDecrease = findViewById(R.id.button_decrease);
        Button buttonAdd = findViewById(R.id.button_add);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GroceryAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
        //for moving left or right
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
             removeItem((long) viewHolder.itemView.getTag());

            }
        }).attachToRecyclerView(recyclerView);

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAmount++;
                mTextViewAmount.setText(String.valueOf(mAmount));
            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAmount > 0) {
                    mAmount--;
                    mTextViewAmount.setText(String.valueOf(mAmount));
                }
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditTextItemName.getText().toString().trim().length() == 0 || mAmount == 0){
                    return;
                }

                String name = mEditTextItemName.getText().toString().trim();
                ContentValues cv = new ContentValues();
                cv.put(GroceryContract.GroceryEntry.COLUMN_NAME, name);
                cv.put(GroceryContract.GroceryEntry.COLOUMN_AMOUNT, mAmount);

                mDataBase.insert(GroceryContract.GroceryEntry.TABLE_NAME, null, cv);
                mAdapter.swapCursor(getAllItems());
                mEditTextItemName.getText().clear();
                mAmount = 0;
                mTextViewAmount.setText(String.valueOf(mAmount));
            }
        });
    }

    private void removeItem(long id) {
        mDataBase.delete(GroceryContract.GroceryEntry.TABLE_NAME, GroceryContract.GroceryEntry._ID+"="+id, null);
        mAdapter.swapCursor(getAllItems());

    }

    private Cursor getAllItems(){
        return mDataBase.query(GroceryContract.GroceryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                GroceryContract.GroceryEntry.COLUMN_TIMESTAMP+ " DESC");
    }
}
