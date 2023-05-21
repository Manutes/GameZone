package com.example.gamezone.ui.ranking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class RankingViewModel extends ViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Map<String, Long>> remolachaEasyList = new ArrayList<>();
    ArrayList<Map<String, Long>> remolachaDifficultList = new ArrayList<>();
    ArrayList<Map<String, Long>> clickerGameList = new ArrayList<>();
    ArrayList<Map<String, Long>> marcianitosList = new ArrayList<>();

    ArrayList<String> user = new ArrayList<>();

    public MutableLiveData<ArrayList<Map<String, Long>>> getRemolachaEasyRanking() {
        remolachaEasyList.clear();
        MutableLiveData<ArrayList<Map<String, Long>>> listMutableLiveData = new MutableLiveData<>();
        db.collection("Users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    Map<String, Long> user = new HashMap<>();
                    user.put(documentSnapshot.getId(), Long.parseLong(Objects.requireNonNull(documentSnapshot.getString("RemolachaHeroEasyRecord"))));
                    remolachaEasyList.add(user);
                    listMutableLiveData.postValue(remolachaEasyList);
                }
            }
        });

        return listMutableLiveData;
    }

    public MutableLiveData<ArrayList<Map<String, Long>>> getRemolachaDifficultRanking() {
        remolachaDifficultList.clear();
        MutableLiveData<ArrayList<Map<String, Long>>> listMutableLiveData = new MutableLiveData<>();
        db.collection("Users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    Map<String, Long> user = new HashMap<>();
                    user.put(documentSnapshot.getId(), Long.parseLong(Objects.requireNonNull(documentSnapshot.getString("RemolachaHeroDifficultRecord"))));
                    remolachaDifficultList.add(user);
                    listMutableLiveData.postValue(remolachaDifficultList);
                }
            }
        });
        return listMutableLiveData;
    }

    public MutableLiveData<ArrayList<Map<String, Long>>> getClickerGameRanking() {
        clickerGameList.clear();
        MutableLiveData<ArrayList<Map<String, Long>>> listMutableLiveData = new MutableLiveData<>();
        db.collection("Users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    Map<String, Long> user = new HashMap<>();
                    user.put(documentSnapshot.getId(), Long.parseLong(Objects.requireNonNull(documentSnapshot.getString("ClickerGameRecord"))));
                    Map<String, Long> userSorted =
                            user.entrySet().stream()
                                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                    .collect(Collectors.toMap(
                                            Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                    clickerGameList.add(userSorted);
                    listMutableLiveData.postValue(clickerGameList);
                }
            }
        });
        return listMutableLiveData;
    }

    public MutableLiveData<ArrayList<Map<String, Long>>> getMarcianitosRanking() {
        marcianitosList.clear();
        MutableLiveData<ArrayList<Map<String, Long>>> listMutableLiveData = new MutableLiveData<>();
        db.collection("Users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    Map<String, Long> user = new HashMap<>();
                    user.put(documentSnapshot.getId(), Long.parseLong(Objects.requireNonNull(documentSnapshot.getString("MarcianitosRecord"))));
                    Map<String, Long> userSorted =
                            user.entrySet().stream()
                                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                    .collect(Collectors.toMap(
                                            Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                    marcianitosList.add(userSorted);
                    listMutableLiveData.postValue(marcianitosList);
                }
            }
        });
        return listMutableLiveData;
    }

    public MutableLiveData<ArrayList<String>> getTopUsers(String field, String points) {
        user.clear();
        MutableLiveData<ArrayList<String>>mutableLiveData = new MutableLiveData<>();
        db.collection("Users").whereEqualTo(field, points).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    user.add(documentSnapshot.getId());;
                    mutableLiveData.postValue(user);
                }
            }
        });
        return mutableLiveData;
    }
}
