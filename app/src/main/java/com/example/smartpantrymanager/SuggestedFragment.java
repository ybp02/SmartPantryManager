package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SuggestedFragment extends Fragment {

    private RecyclerView recyclerSuggested;
    private TextView tvEmpty;
    private RecipeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggested, container, false);
        recyclerSuggested = view.findViewById(R.id.recyclerSuggested);
        tvEmpty = view.findViewById(R.id.tvEmptySuggested);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSuggestions();
    }

    private void loadSuggestions() {
        if (getContext() == null) return;
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<PantryItem> myPantry = db.getAllPantryItems();
        List<String[]> allRecipes = db.getAllRecipes();
        List<String[]> suggestedRecipes = new ArrayList<>();

        for (String[] recipe : allRecipes) {
            String ingredientsString = recipe[1];
            String[] requiredIngredients = ingredientsString.split(",");
            boolean canMakeIt = true;

            for (String req : requiredIngredients) {
                String[] parts = req.split(":");
                String reqName = parts[0].trim().toLowerCase();
                boolean hasIngredient = false;

                for (PantryItem item : myPantry) {
                    String pantryName = item.getName().trim().toLowerCase();

                    if (pantryName.equals(reqName) || pantryName.equals(reqName + "s") || reqName.equals(pantryName + "s")) {
                        hasIngredient = true;
                        break;
                    }
                }

                if (!hasIngredient) {
                    canMakeIt = false;
                    break;
                }
            }

            if (canMakeIt) {
                suggestedRecipes.add(recipe);
            }
        }

        if (suggestedRecipes.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerSuggested.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerSuggested.setVisibility(View.VISIBLE);
            adapter = new RecipeAdapter(suggestedRecipes);
            recyclerSuggested.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerSuggested.setAdapter(adapter);
        }
    }
}
