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

public class PantryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Load the fragment_pantry.xml layout
        View view = inflater.inflate(R.layout.fragment_pantry, container, false);

        RecyclerView recyclerPantry = view.findViewById(R.id.recyclerPantry);
        TextView tvEmptyPantry = view.findViewById(R.id.tvEmptyPantry);


        DatabaseHelper db = new DatabaseHelper(getContext());
        List<PantryItem> myPantry = db.getAllPantryItems();

        // 2. Set up the list and adapter
        if (myPantry.isEmpty()) {
            tvEmptyPantry.setVisibility(View.VISIBLE);
        } else {
            tvEmptyPantry.setVisibility(View.GONE);
            PantryAdapter adapter = new PantryAdapter(myPantry);
            recyclerPantry.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerPantry.setAdapter(adapter);
        }
        // Make the + button open the Add Screen
        com.google.android.material.floatingactionbutton.FloatingActionButton fab = view.findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(getActivity(), AddEditActivity.class);
            startActivity(intent);
        });

        return view;
    }
}