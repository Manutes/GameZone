package com.example.gamezone.ui.options;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gamezone.databinding.FragmentOptionsBinding;
import com.example.gamezone.ui.firebase.Firebase;
import com.example.gamezone.ui.login.LoginActivity;


public class OptionsFragment extends Fragment {

    FragmentOptionsBinding binding;

    Firebase firebase = new Firebase();

    com.example.gamezone.ui.sharedpreferences.SharedPreferences sharedPreferences = new com.example.gamezone.ui.sharedpreferences.SharedPreferences();

    public OptionsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOptionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvName.setText(firebase.mFirebaseAuth.getCurrentUser().getDisplayName());
        binding.tvEmail.setText(firebase.mFirebaseAuth.getCurrentUser().getEmail());

        binding.btnLogout.setOnClickListener(view1 -> signOut());
    }

    private void signOut() {
        firebase.signOut();
        sharedPreferences.removePrefs(requireActivity());
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        startActivity(intent);
    }
}