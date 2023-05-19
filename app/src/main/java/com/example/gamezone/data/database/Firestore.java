package com.example.gamezone.data.database;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.AnyRes;

import com.example.gamezone.R;
import com.example.gamezone.data.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Firestore {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void writeNewUser(String userId, String name, String email, Context context) {
        User newUser = new User(name, email);
        Map<String, Object> user = new HashMap<>();
        user.put("Name", newUser.username);
        user.put("Email", newUser.email);
        user.put("Photo", setDefaultImage(context, R.drawable.consola));
        user.put("RemolachaHeroEasyRecord", null);
        user.put("RemolachaHeroDifficultRecord", null);
        user.put("RemolachaHeroLastScore", null);
        db.collection("Users").document(userId).set(user);
    }

    public void updateUsername(FirebaseUser currentUser, String newUsername) {
        Map<String, Object> user = new HashMap<>();
        user.put("Name", newUsername);
        db.collection("Users").document(currentUser.getUid()).update(user);
    }

    public void updateScores(FirebaseUser currentUser, String field, String newScore) {
        Map<String, Object> user = new HashMap<>();
        user.put(field, newScore);
        db.collection("Users").document(currentUser.getUid()).update(user);
    }

    public Task<DocumentSnapshot> getUserDocument (String userId) {
        return db.collection("Users").document(userId).get();
    }

    public void updateProfilePhoto(FirebaseUser currentUser, String newUri) {
        Map<String, String> user = new HashMap<>();
        user.put("Name", currentUser.getDisplayName());
        user.put("Email", currentUser.getEmail());
        user.put("Photo", newUri);
        db.collection("Users").document(currentUser.getUid()).set(user);
    }
    public Uri setDefaultImage(Context context,
                                   @AnyRes int drawableId) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
    }
}
