package com.example.assignment_athirahizzati_oct2025;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class CalculateActivity extends AppCompatActivity {

    Spinner spinnerMonth;
    EditText editUnits, editRebate;
    Button btnCalculate, btnSave, btnCancel;
    TextView textTotalCharges, textFinalCost;
    DataHelper dbHelper;
    double lastTotal = 0.0, lastFinal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        dbHelper = new DataHelper(this);

        spinnerMonth = findViewById(R.id.spinnerMonth);
        editUnits = findViewById(R.id.editUnits);
        editRebate = findViewById(R.id.editRebate);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        textTotalCharges = findViewById(R.id.textTotalCharges);
        textFinalCost = findViewById(R.id.textFinalCost);

        // populate months
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);

        btnCalculate.setOnClickListener(v -> calculate());

        btnSave.setOnClickListener(v -> {
            if (!validateInput()) return;

            String month = spinnerMonth.getSelectedItem().toString();
            int units = Integer.parseInt(editUnits.getText().toString());
            double rebatePercent = Double.parseDouble(editRebate.getText().toString()) / 100.0;

            // ensure calculation done
            if (lastTotal == 0 && lastFinal == 0) {
                calculate(); // compute if not computed
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO electricity (month, units, totalCharges, rebate, finalCost) VALUES (" +
                    "'" + month + "'," +
                    units + "," +
                    lastTotal + "," +
                    rebatePercent + "," +
                    lastFinal + ")");

            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
            MainActivity.ma.refreshList();
            finish();
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    private boolean validateInput() {
        String unitsStr = editUnits.getText().toString().trim();
        String rebateStr = editRebate.getText().toString().trim();

        if (unitsStr.isEmpty()) {
            editUnits.setError("Please enter units used (kWh)");
            editUnits.requestFocus();
            return false;
        }
        int units;
        try {
            units = Integer.parseInt(unitsStr);
            if (units < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            editUnits.setError("Units must be a non-negative integer");
            editUnits.requestFocus();
            return false;
        }

        if (rebateStr.isEmpty()) {
            editRebate.setError("Please enter rebate (0-5)");
            editRebate.requestFocus();
            return false;
        }
        double rebate;
        try {
            rebate = Double.parseDouble(rebateStr);
            if (rebate < 0 || rebate > 5) {
                editRebate.setError("Rebate must be between 0 and 5");
                editRebate.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            editRebate.setError("Enter rebate as number (0-5)");
            editRebate.requestFocus();
            return false;
        }
        return true;
    }

    private void calculate() {
        if (!validateInput()) return;

        int units = Integer.parseInt(editUnits.getText().toString().trim());
        double rebatePercent = Double.parseDouble(editRebate.getText().toString().trim()) / 100.0;

        double total = calculateCharges(units);
        double finalCost = total - (total * rebatePercent);

        lastTotal = total;
        lastFinal = finalCost;

        textTotalCharges.setText("Total Charges: RM " + String.format("%.3f", total));
        textFinalCost.setText("Final Cost after rebate: RM " + String.format("%.2f", finalCost));
    }

    private double calculateCharges(int units) {
        double total = 0;
        if (units <= 200) {
            total = units * 0.218;
        } else if (units <= 300) {
            total = (200 * 0.218) + (units - 200) * 0.334;
        } else if (units <= 600) {
            total = (200 * 0.218) + (100 * 0.334) + (units - 300) * 0.516;
        } else {
            total = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + (units - 600) * 0.546;
        }
        return total;
    }
}
