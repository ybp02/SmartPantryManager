package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
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
        TextView tvTitle = findViewById(R.id.tvFormTitle); // Make sure this ID matches your XML

        // Check if we are EDITING an existing item
        boolean isEdit = getIntent().getBooleanExtra("IS_EDIT", false);
        String oldName = "";

        if (isEdit) {
            tvTitle.setText("Edit Ingredient");
            btnSave.setText("Update Ingredient");

            oldName = getIntent().getStringExtra("OLD_NAME");
            String currentName = getIntent().getStringExtra("CURRENT_NAME");
            String currentQty = getIntent().getStringExtra("CURRENT_QTY");

            // Pre-fill the text boxes
            etName.setText(currentName);
            etQuantity.setText(currentQty);
        }

        String finalOldName = oldName; // Needed for lambda
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String qty = etQuantity.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError("Name is required");
                return;
            }
            if (qty.isEmpty()) {
                etQuantity.setError("Quantity is required");
                return;
            }

            DatabaseHelper db = new DatabaseHelper(this);

            if (isEdit) {
                // Update existing
                db.updatePantryItem(finalOldName, name, qty);
                Toast.makeText(this, "Updated: " + name, Toast.LENGTH_SHORT).show();
            } else {
                // Create new
                db.addPantryItem(name, qty);
                Toast.makeText(this, "Saved: " + name, Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}