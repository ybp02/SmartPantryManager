package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Get the data passed from the previous screen
        String name = getIntent().getStringExtra("RECIPE_NAME");
        String ingredients = getIntent().getStringExtra("RECIPE_INGREDIENTS");
        String steps = getIntent().getStringExtra("RECIPE_STEPS");

        // Find the views
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvIngredients = findViewById(R.id.tvDetailIngredients);
        TextView tvSteps = findViewById(R.id.tvDetailSteps);

        // Set the text
        tvTitle.setText(name);
        tvIngredients.setText("Ingredients: " + ingredients.replace(",", ", "));
        tvSteps.setText(steps);
    }
}