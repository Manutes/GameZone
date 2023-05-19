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
import java.util.Objects;

public class Firestore {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void writeNewUser(String userId, String name, String email, Context context) {
        User newUser = new User(name, email);
        Map<String, Object> user = new HashMap<>();
        Map<String, Integer> remolachaScores = new HashMap<>();
        remolachaScores.put("EasyRecord", null);
        remolachaScores.put("DifficultRecord", null);
        remolachaScores.put("LastScore", null);
        user.put("Name", newUser.username);
        user.put("Email", newUser.email);
        user.put("Photo", setDefaultImage(context, R.drawable.consola));
        user.put("RemolachaHero", remolachaScores);
        db.collection("Users").document(userId).set(user);
    }

    public void updateUsername(FirebaseUser currentUser, String newUsername) {
        Map<String, Object> user = new HashMap<>();
        user.put("Name", newUsername);
        db.collection("Users").document(currentUser.getUid()).update(user);
    }

    public void getScore (String id, String field) {
        db.collection("Users").document(id)
                .collection("RemolachaHero").document().get().addOnSuccessListener(documentSnapshot -> {
                    int score = Integer.parseInt(Objects.requireNonNull(documentSnapshot.getString(field)));
                });
    }

    public void updateEasyRecord(FirebaseUser currentUser, int newEasyRecord) {
        Map<String, Object> user = new HashMap<>();
        Map<String, Integer> field = new HashMap<>();
        field.put("EasyRecord", newEasyRecord);
        field.put("DifficultRecord", null);
        field.put("LastScore", null);
        user.put("RemolachaHero", field);
        db.collection("Users").document(currentUser.getUid()).update(user);
    }

    public void updateDifficultRecord(FirebaseUser currentUser, int newDifficultRecord) {
        Map<String, Object> user = new HashMap<>();
        Map<String, Integer> field = new HashMap<>();
        field.put("EasyRecord", null);
        field.put("DifficultRecord", newDifficultRecord);
        field.put("LastScore", null);
        user.put("RemolachaHero", field);
        db.collection("Users").document(currentUser.getUid()).update(user);
    }

    public void updateLastScore(FirebaseUser currentUser, int lastScore) {
        Map<String, Object> user = new HashMap<>();
        Map<String, Integer> field = new HashMap<>();
        field.put("EasyRecord", null);
        field.put("DifficultRecord", null);
        field.put("LastScore", lastScore);
        user.put("RemolachaHero", field);
        db.collection("Users").document(currentUser.getUid()).update(user);
    }

    public Task<DocumentSnapshot> getUserDocument (String id) {
        return db.collection("Users").document(id).get();
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
