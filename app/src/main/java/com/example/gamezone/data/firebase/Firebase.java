package com.example.gamezone.data.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.gamezone.R;
import com.example.gamezone.data.database.Firestore;
import com.example.gamezone.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class Firebase {

    public final FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

    Firestore db = new Firestore();

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
                        updateUI(Objects.requireNonNull(user), context);
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
                        //updateUI(Objects.requireNonNull(user), context);
                        String userName = user.getDisplayName();
                        Toast.makeText(context, context.getString(R.string.welcome_text) + " " + userName, Toast.LENGTH_SHORT).show();
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

    private void goToMain(Context context, Activity activity) {
        Intent intent = new Intent(context, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
