package com.example.gamezone.ui.firebase;

import android.content.Context;
import android.widget.Toast;

import com.example.gamezone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    private void updateUI(FirebaseUser user) {

    }
}
