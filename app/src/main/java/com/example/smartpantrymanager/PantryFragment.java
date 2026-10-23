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
import java.util.List;

public class PantryFragment extends Fragment {

    private RecyclerView recyclerPantry;
    private TextView tvEmptyPantry;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry, container, false);
        recyclerPantry = view.findViewById(R.id.recyclerPantry);
        tvEmptyPantry = view.findViewById(R.id.tvEmptyPantry);

        // Make the + button open the Add Screen
        com.google.android.material.floatingactionbutton.FloatingActionButton fab = view.findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(getActivity(), AddEditActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // This runs EVERY TIME you go to the Pantry tab (even after adding an item)
        loadPantryItems();
    }

    private void loadPantryItems() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<PantryItem> myPantry = db.getAllPantryItems();

        if (myPantry.isEmpty()) {
            tvEmptyPantry.setVisibility(View.VISIBLE);
            recyclerPantry.setVisibility(View.GONE);
        } else {
            tvEmptyPantry.setVisibility(View.GONE);
            recyclerPantry.setVisibility(View.VISIBLE);

            // We pass getContext() to the adapter now
            PantryAdapter adapter = new PantryAdapter(myPantry, getContext());
            recyclerPantry.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerPantry.setAdapter(adapter);
        }
    }
}