package com.example.gamezone.data.firebase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.gamezone.R;
import com.example.gamezone.data.database.Firestore;
import com.example.gamezone.databinding.DialogChangeUsernameBinding;
import com.example.gamezone.ui.MainActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class Firebase {

    public final FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    CharSequence userName = "";
    Firestore db = new Firestore();
    DialogChangeUsernameBinding alertBinding;

    public void checkUser() {
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    public void createAccount(String email, String password, Context context, Activity activity) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        alertBinding = DialogChangeUsernameBinding.inflate(LayoutInflater.from(context), null, false);

                        setUserTextWatcher(alertBinding.tilUsername);

                        FirebaseUser user = mFirebaseAuth.getCurrentUser();

                        setDefaultUsername(Objects.requireNonNull(user));
                        updateUI(Objects.requireNonNull(user), context);
                        setAlertDialog(email, password, context, activity);

                        Toast.makeText(context, context.getString(R.string.register_successful_text),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, context.getString(R.string.register_error_text),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void signIn(String email, String password, Context context, Activity activity) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        goToMain(context, activity);
                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    } else {
                        Toast.makeText(context, context.getString(R.string.credentials_login_error_text), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void signOut() {
        mFirebaseAuth.signOut();
    }
    private void updateUI(FirebaseUser user, Context context) {
        db.writeNewUser( user.getUid(), user.getDisplayName(), user.getEmail(), context);
    }

    public void changeUsername (String username, Context context) {

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).updateProfile(profileUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, context.getString(R.string.change_username_success_text) + " " + username, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDefaultUsername (FirebaseUser user) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getUid())
                .build();

        Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).updateProfile(profileUpdates);
    }

    private void goToMain(Context context, Activity activity) {
        Intent intent = new Intent(context, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }


    private void setAlertDialog(String email, String password, Context context, Activity activity) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setPositiveButton(R.string.dialog_accept_button, (dialogInterface, i) -> {
            changeNewUsername(context);
            signIn(email, password, context, activity);
        });

        dialog.setView(alertBinding.getRoot());
        dialog.create();
        dialog.show();
    }

    private void changeNewUsername(Context context) {
        changeUsername(userName.toString(), context);
        db.updateUsername(Objects.requireNonNull(mFirebaseAuth.getCurrentUser()), userName.toString());
    }

    private void setUserTextWatcher(TextInputLayout til) {
        Objects.requireNonNull(til.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userName = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
