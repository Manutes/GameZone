package com.example.gamezone.ui.firebase;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.gamezone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class Firebase {

    public final FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

    public void checkUser() {
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    public void createAccount(String email, String password, Context context) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                        updateUI(user);
                        Toast.makeText(context, context.getString(R.string.register_successful_text),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, context.getString(R.string.register_error_text),
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    public void signIn(String email, String password, Context context) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mFirebaseAuth.getCurrentUser();

                        String userName = user.getUid();

                        Toast.makeText(context, context.getString(R.string.welcome_text) + userName, Toast.LENGTH_SHORT).show();

                        updateUI(user);
                    } else {
                        Toast.makeText(context, context.getString(R.string.credentials_login_error_text), Toast.LENGTH_LONG).show();
                        updateUI(null);
                    }
                });
    }

    public void signOut() {
        mFirebaseAuth.signOut();
    }
    private void updateUI(FirebaseUser user) {

    }

    public void changeUsername (String username, Context context) {

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).updateProfile(profileUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Nombre de usuario cambiado con éxito", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
