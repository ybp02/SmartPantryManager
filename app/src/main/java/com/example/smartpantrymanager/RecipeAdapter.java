package com.example.smartpantrymanager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<String[]> recipeList;

    public RecipeAdapter(List<String[]> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] recipe = recipeList.get(position);
        holder.tvName.setText(recipe[0]); // Name
        holder.tvIngredients.setText("Needs: " + recipe[1].replace(",", ", "));

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && pos < recipeList.size()) {
                String[] currentRecipe = recipeList.get(pos);
                Intent intent = new Intent(v.getContext(), RecipeDetailActivity.class);
                intent.putExtra("RECIPE_NAME", currentRecipe[0]);
                intent.putExtra("RECIPE_INGREDIENTS", currentRecipe[1]);
                intent.putExtra("RECIPE_STEPS", currentRecipe[2]);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvIngredients;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvRecipeName);
            tvIngredients = itemView.findViewById(R.id.tvRecipeIngredients);
        }
    }
}
