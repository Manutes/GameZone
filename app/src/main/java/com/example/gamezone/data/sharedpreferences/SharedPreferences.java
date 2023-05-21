package com.example.gamezone.data.sharedpreferences;

import android.app.Activity;
import android.content.Context;

public class SharedPreferences {

    String PREFS_KEY = "GamezonePrefs";

    public void savePrefs (String user, String password, Activity activity) {
        android.content.SharedPreferences prefs = activity.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", user);
        editor.putString("password", password);
        editor.apply();
    }

    public String getPrefs (String item, Activity activity) {
        android.content.SharedPreferences prefs = activity.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        return prefs.getString(item, "");
    }

    public void removePrefs (Activity activity) {
        android.content.SharedPreferences prefs = activity.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
