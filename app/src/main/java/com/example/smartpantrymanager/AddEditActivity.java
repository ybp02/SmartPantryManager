package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        TextInputEditText etName = findViewById(R.id.etName);
        TextInputEditText etQuantity = findViewById(R.id.etQuantity);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String qty = etQuantity.getText().toString().trim();

            // Input validation
            if (name.isEmpty()) {
                etName.setError("Name is required");
                return;
            }
            if (qty.isEmpty()) {
                etQuantity.setError("Quantity is required");
                return;
            }

            // Save to Database!
            DatabaseHelper db = new DatabaseHelper(this);
            db.addPantryItem(name, qty);

            Toast.makeText(this, "Saved: " + name, Toast.LENGTH_SHORT).show();
            finish(); // Closes the screen and goes back
        });
    }
}