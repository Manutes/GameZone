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
        db.collection("Users").document(userId).set(user);
    }

    public void updateUsername(FirebaseUser currentUser, String newUsername) {
        Map<String, Object> user = new HashMap<>();
        user.put("Name", newUsername);
        user.put("Email", currentUser.getEmail());
        Task<DocumentSnapshot> doc = getUserDocument(currentUser.getUid());
        doc.addOnSuccessListener(documentSnapshot -> {
            user.put("Photo", documentSnapshot.getString("Photo"));
        });
        db.collection("Users").document(currentUser.getUid()).set(user);
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