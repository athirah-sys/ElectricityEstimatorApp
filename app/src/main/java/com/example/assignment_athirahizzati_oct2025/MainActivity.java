package com.example.assignment_athirahizzati_oct2025;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static MainActivity ma; // used by other activities to refresh list
    DataHelper dbcenter;
    ListView listView;
    String[] register;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ma = this;

        dbcenter = new DataHelper(this);
        listView = findViewById(R.id.listView);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalculateActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnAbout).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        });

        refreshList();
    }

    public void refreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM electricity ORDER BY id DESC", null);
        register = new String[cursor.getCount()];

        if (cursor.getCount() == 0) {
            register = new String[]{"No records yet. Click + to add."};
            listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, register));
            return;
        }

        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            String month = cursor.getString(cursor.getColumnIndexOrThrow("month"));
            double finalCost = cursor.getDouble(cursor.getColumnIndexOrThrow("finalCost"));
            register[cc] = month + " - RM " + String.format("%.2f", finalCost);
        }

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, register));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // get cursor position -> id value
            cursor.moveToPosition(position);
            int rowId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("id", rowId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }
}
