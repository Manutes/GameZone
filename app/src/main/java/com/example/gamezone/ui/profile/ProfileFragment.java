package com.example.gamezone.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.example.gamezone.R;
import com.example.gamezone.data.database.Firestore;
import com.example.gamezone.data.firebase.Firebase;
import com.example.gamezone.data.sharedpreferences.SharedPreferences;
import com.example.gamezone.databinding.DialogChangeUsernameBinding;
import com.example.gamezone.databinding.FragmentProfileBinding;
import com.example.gamezone.ui.home.HomeFragment;
import com.example.gamezone.ui.login.LoginActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    DialogChangeUsernameBinding alertBinding;

    Firestore db = new Firestore();
    Firebase firebase = new Firebase();

    SharedPreferences sharedPreferences = new SharedPreferences();

    int SELECT_PICTURE = 200;

    CharSequence newUsername = "";
    boolean usernameExists = false;

    boolean permissionsGranted = false;
    int REQUEST_CODE = 200;

    ArrayList<String> usernameList = new ArrayList<>();

    Uri selectedImageUri;

    public ProfileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, null, false);
        alertBinding = DialogChangeUsernameBinding.inflate(LayoutInflater.from(requireContext()), null, false);
        checkPermissions();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUi();
    }

    private void setUi() {
        setUserTextWatcher(alertBinding.tilUsername);

        setPhotoUri();

        binding.tvName.setText(Objects.requireNonNull(firebase.mFirebaseAuth.getCurrentUser()).getDisplayName());
        binding.tvEmail.setText(firebase.mFirebaseAuth.getCurrentUser().getEmail());

        binding.btnLogout.setOnClickListener(view1 -> signOut());

        binding.imgBtn.setOnClickListener(view1 -> {
            if (permissionsGranted) {
                openGallery();
            } else {
                setPermissionsAlertDialog();
            }
        });

        binding.btnChangeUserName.setOnClickListener(view1 -> setAlertDialog());

    }

    private void setPhotoUri() {
        Task<DocumentSnapshot> doc = db.getUserDocument(Objects.requireNonNull(firebase.mFirebaseAuth.getCurrentUser()).getUid());
        doc.addOnSuccessListener(documentSnapshot -> {
            try {
                binding.imgPhoto.setImageURI(Uri.parse(documentSnapshot.getString("Photo")));
            } catch (SecurityException e) {
                binding.imgPhoto.setImageDrawable(requireContext().getResources().getDrawable(R.drawable.consola));

            }
        });

    }

    private void setAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setPositiveButton(R.string.dialog_accept_button, (dialogInterface, i) -> {
            changeUsername();
            goToHome();
        });
        dialog.setNegativeButton(R.string.dialog_cancel_button, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.hostFragment, new ProfileFragment()).commit();
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
                checkNewUserName().observe(getViewLifecycleOwner(), users -> usernameList = users);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void changeUsername() {
        usernameExists = usernameList.contains(newUsername.toString());
        if (!usernameExists) {
            firebase.changeUsername(newUsername.toString(), requireContext());
            db.updateUsername(Objects.requireNonNull(firebase.mFirebaseAuth.getCurrentUser()), newUsername.toString());
        } else {
            Toast.makeText(requireContext(), requireContext().getString(R.string.text_username_exists), Toast.LENGTH_SHORT).show();
        }
    }

    private void goToHome() {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.hostFragment, new HomeFragment()).commit();
        BottomNavigationView navBar = requireActivity().findViewById(R.id.navBar);
        navBar.setSelectedItemId(R.id.games);

    }

    private void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_OPEN_DOCUMENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    binding.imgPhoto.setImageURI(selectedImageUri);
                    db.updateProfilePhoto(Objects.requireNonNull(firebase.mFirebaseAuth.getCurrentUser()), selectedImageUri.toString());
                    Toast.makeText(requireContext(), selectedImageUri.toString(), Toast.LENGTH_SHORT).show();
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

    private MutableLiveData<ArrayList<String>> checkNewUserName() {
        usernameList.clear();
        MutableLiveData<ArrayList<String>> mutableLiveData = new MutableLiveData<>();
        ArrayList<String> list = new ArrayList<>();
        db.getAllUsers().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    list.add(documentSnapshot.getString("Name"));
                    mutableLiveData.postValue(list);
                }
            }
        });
        return mutableLiveData;
    }


    private void checkPermissions() {
        int permissions = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT <= 32) {
            if (permissions != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                permissionsGranted = false;
            } else {
                permissionsGranted = true;
            }
        } else {
            if (permissions != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_CODE);
                permissionsGranted = false;
            } else {
                permissionsGranted = true;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            permissionsGranted = permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void setPermissionsAlertDialog() {
        new AlertDialog.Builder(requireContext())
                .setMessage(R.string.allow_permissions_text)
                .setPositiveButton("Aceptar", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .create().show();
    }
}