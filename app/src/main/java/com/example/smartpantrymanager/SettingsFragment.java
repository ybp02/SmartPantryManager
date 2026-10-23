package com.example.smartpantrymanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SwitchMaterial switchAlerts = view.findViewById(R.id.switchExpiryAlerts);

        // Load saved preference
        SharedPreferences prefs = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean alertsEnabled = prefs.getBoolean("alerts_enabled", true);
        switchAlerts.setChecked(alertsEnabled);

        // Save preference when clicked
        switchAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("alerts_enabled", isChecked);
            editor.apply(); // Saves it permanently
        });

        return view;
    }
}