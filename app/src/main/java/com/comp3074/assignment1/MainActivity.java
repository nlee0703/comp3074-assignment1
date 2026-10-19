package com.comp3074.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etHours, etRate, etTax;
    private TextView tvPay, tvOvertime, tvTotal, tvTax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use the layout's Toolbar as the ActionBar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Android 15+ draws edge-to-edge, so pad the toolbar below the status bar
        // and the content above the navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, 0, bars.right, bars.bottom);
            toolbar.setPadding(0, bars.top, 0, 0);
            return insets;
        });

        etHours = findViewById(R.id.etHours);
        etRate = findViewById(R.id.etRate);
        etTax = findViewById(R.id.etTax);
        tvPay = findViewById(R.id.tvPay);
        tvOvertime = findViewById(R.id.tvOvertime);
        tvTotal = findViewById(R.id.tvTotal);
        tvTax = findViewById(R.id.tvTax);

        Button btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(v -> calculate());
    }

    private void calculate() {
        String hoursStr = etHours.getText().toString().trim();
        String rateStr = etRate.getText().toString().trim();
        String taxStr = etTax.getText().toString().trim();

        if (hoursStr.isEmpty() || rateStr.isEmpty() || taxStr.isEmpty()) {
            Toast.makeText(this, R.string.error_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        double hours = Double.parseDouble(hoursStr);
        double rate = Double.parseDouble(rateStr);
        double taxRate = Double.parseDouble(taxStr);

        double pay;
        double overtimePay;
        if (hours <= 40) {
            pay = hours * rate;
            overtimePay = 0;
        } else {
            pay = 40 * rate;
            overtimePay = (hours - 40) * rate * 1.5;
        }
        double totalPay = pay + overtimePay;
        double tax = pay * taxRate;

        tvPay.setText(String.format(Locale.getDefault(), "Pay: $%.2f", pay));
        tvOvertime.setText(String.format(Locale.getDefault(), "Overtime Pay: $%.2f", overtimePay));
        tvTotal.setText(String.format(Locale.getDefault(), "Total Pay: $%.2f", totalPay));
        tvTax.setText(String.format(Locale.getDefault(), "Tax: $%.2f", tax));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
