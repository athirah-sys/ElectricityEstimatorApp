package com.example.assignment_athirahizzati_oct2025;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView tvMonth, tvUnits, tvTotal, tvRebate, tvFinal;
    DataHelper dbHelper;
    int recordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvMonth = findViewById(R.id.tvMonth);
        tvUnits = findViewById(R.id.tvUnits);
        tvTotal = findViewById(R.id.tvTotal);
        tvRebate = findViewById(R.id.tvRebate);
        tvFinal = findViewById(R.id.tvFinal);

        dbHelper = new DataHelper(this);
        recordId = getIntent().getIntExtra("id", 0);

        loadData();

        findViewById(R.id.btnDelete).setOnClickListener(v -> confirmDelete());
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void loadData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM electricity WHERE id=" + recordId, null);

        if (c.moveToFirst()) {

            // Month
            tvMonth.setText(c.getString(c.getColumnIndexOrThrow("month")));

            // Units
            tvUnits.setText(String.valueOf(c.getInt(c.getColumnIndexOrThrow("units"))));

            // Total Charges → FIXED TO 2 DECIMALS
            double totalCharges = c.getDouble(c.getColumnIndexOrThrow("totalCharges"));
            tvTotal.setText("RM " + String.format("%.2f", totalCharges));

            // Rebate → convert decimal back to percentage
            double rebatePercent = c.getDouble(c.getColumnIndexOrThrow("rebate")) * 100;
            tvRebate.setText(String.format("%.2f%%", rebatePercent));

            // Final Cost → already 2 decimals
            double finalCost = c.getDouble(c.getColumnIndexOrThrow("finalCost"));
            tvFinal.setText("RM " + String.format("%.2f", finalCost));
        }

        c.close();
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete record")
                .setMessage("Are you sure you want to delete this record?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("DELETE FROM electricity WHERE id=" + recordId);
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                    MainActivity.ma.refreshList();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
