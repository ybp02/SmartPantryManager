package com.example.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {

    private List<PantryItem> pantryList;
    private Context context;

    public PantryAdapter(List<PantryItem> pantryList, Context context) {
        this.pantryList = pantryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PantryItem item = pantryList.get(position);
        holder.tvName.setText(item.getName());
        holder.tvDetails.setText(item.getDetails());

        holder.btnDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && pos < pantryList.size()) {
                PantryItem currentItem = pantryList.get(pos);
                DatabaseHelper db = new DatabaseHelper(context);
                db.deletePantryItem(currentItem.getName());

                pantryList.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, pantryList.size());
            }
        });

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && pos < pantryList.size()) {
                PantryItem currentItem = pantryList.get(pos);
                Intent intent = new Intent(context, AddEditActivity.class);
                intent.putExtra("IS_EDIT", true);
                intent.putExtra("OLD_NAME", currentItem.getName());
                intent.putExtra("CURRENT_NAME", currentItem.getName());
                intent.putExtra("CURRENT_QTY", currentItem.getDetails());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pantryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDetails;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvDetails = itemView.findViewById(R.id.tvItemDetails);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
