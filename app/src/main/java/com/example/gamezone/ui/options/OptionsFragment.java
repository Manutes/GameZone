package com.example.gamezone.ui.options;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gamezone.R;
import com.example.gamezone.databinding.DialogChangeUsernameBinding;
import com.example.gamezone.databinding.FragmentOptionsBinding;
import com.example.gamezone.ui.firebase.Firebase;
import com.example.gamezone.ui.games.GamesFragment;
import com.example.gamezone.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class OptionsFragment extends Fragment {

    FragmentOptionsBinding binding;
    DialogChangeUsernameBinding alertBinding;


    Firebase firebase = new Firebase();

    com.example.gamezone.ui.sharedpreferences.SharedPreferences sharedPreferences = new com.example.gamezone.ui.sharedpreferences.SharedPreferences();

    int SELECT_PICTURE = 200;

    CharSequence newUsername = "";

    public OptionsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOptionsBinding.inflate(inflater, null, false);
        alertBinding = DialogChangeUsernameBinding.inflate(LayoutInflater.from(requireContext()), null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUi();
    }

    private void setUi() {
        setUserTextWatcher(alertBinding.tilUsername);

        binding.tvName.setText(firebase.mFirebaseAuth.getCurrentUser().getDisplayName());
        binding.tvEmail.setText(firebase.mFirebaseAuth.getCurrentUser().getEmail());

        binding.btnLogout.setOnClickListener(view1 -> signOut());

        binding.imgBtn.setOnClickListener(view1 -> openGallery());

        binding.btnChangeUserName.setOnClickListener(view1 -> setAlertDialog());

    }

    private void setAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setPositiveButton(R.string.dialog_accept_button, (dialogInterface, i) -> {
            changeUsername();
            goToHome();
        });
        dialog.setNegativeButton(R.string.dialog_cancel_button, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.hostFragment, new OptionsFragment()).commit();
        });
        dialog.setView(alertBinding.getRoot());
        dialog.create();
        dialog.show();

    }

    private void setUserTextWatcher(TextInputLayout til) {
        Objects.requireNonNull(til.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newUsername = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void changeUsername() {
        firebase.changeUsername(newUsername.toString(), requireContext());
    }

    private void goToHome() {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.hostFragment, new GamesFragment()).commit();
        BottomNavigationView navBar = requireActivity().findViewById(R.id.navBar);
        navBar.setSelectedItemId(R.id.games);

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