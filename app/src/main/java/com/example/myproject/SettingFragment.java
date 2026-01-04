package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button logoutButton = view.findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                sp.edit().putBoolean("isLoggedIn", false).apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

                getActivity().finish();
            }
        });

        return view;
    }
}
