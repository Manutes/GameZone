package com.example.gamezone.ui.options;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gamezone.R;
import com.example.gamezone.databinding.FragmentOptionsBinding;
import com.example.gamezone.ui.firebase.Firebase;
import com.example.gamezone.ui.login.LoginActivity;


public class OptionsFragment extends Fragment {

    FragmentOptionsBinding binding;

    Firebase firebase = new Firebase();

    com.example.gamezone.ui.sharedpreferences.SharedPreferences sharedPreferences = new com.example.gamezone.ui.sharedpreferences.SharedPreferences();

    int SELECT_PICTURE = 200;

    String newUsername = "";

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

        setUi();
    }

    private void setUi() {
        binding.tvName.setText(firebase.mFirebaseAuth.getCurrentUser().getUid());
        binding.tvEmail.setText(firebase.mFirebaseAuth.getCurrentUser().getEmail());

        binding.btnLogout.setOnClickListener(view1 -> signOut());

        binding.imgBtn.setOnClickListener(view1 -> openGallery());

        binding.btnChangeUserName.setOnClickListener(view1 -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
            dialog.setTitle(R.string.dialog_change_username_text);
            dialog.setPositiveButton(R.string.dialog_accept_button, (dialogInterface, i) -> firebase.changeUsername(newUsername, requireContext()));
            dialog.setNegativeButton(R.string.dialog_cancel_button, (dialogInterface, i) -> dialogInterface.dismiss());
            dialog.create();
            dialog.show();
        });
    }

    private void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    binding.imgPhoto.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void signOut() {
        firebase.signOut();
        sharedPreferences.removePrefs(requireActivity());
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        startActivity(intent);
    }
}