package com.example.gamezone.ui.ranking;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RankingViewModel extends ViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Map<String, Long>> remolachaEasyList = new ArrayList<>();
    ArrayList<Map<String, Long>> remolachaDifficultList = new ArrayList<>();
    ArrayList<Map<String, Long>> clickerGameList = new ArrayList<>();
    ArrayList<Map<String, Long>> marcianitosList = new ArrayList<>();

    public MutableLiveData<ArrayList<Map<String, Long>>> getRemolachaEasyRanking() {
        remolachaEasyList.clear();
        MutableLiveData<ArrayList<Map<String, Long>>> listMutableLiveData = new MutableLiveData<>();
        db.collection("Users").orderBy("RemolachaHeroEasyRecord", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    Map<String, Long> user = new HashMap<>();
                    user.put(documentSnapshot.getId(), Long.parseLong(Objects.requireNonNull(documentSnapshot.get("RemolachaHeroEasyRecord")).toString()));
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
        db.collection("Users").orderBy("RemolachaHeroDifficultRecord", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    Map<String, Long> user = new HashMap<>();
                    user.put(documentSnapshot.getId(), Long.parseLong(Objects.requireNonNull(documentSnapshot.get("RemolachaHeroDifficultRecord")).toString()));
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
        db.collection("Users").orderBy("ClickerGameRecord", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    Map<String, Long> user = new HashMap<>();
                    user.put(documentSnapshot.getId(), Long.parseLong(Objects.requireNonNull(documentSnapshot.get("ClickerGameRecord")).toString()));
                    clickerGameList.add(user);
                    listMutableLiveData.postValue(clickerGameList);
                }
            }
        });
        return listMutableLiveData;
    }

    public MutableLiveData<ArrayList<Map<String, Long>>> getMarcianitosRanking() {
        marcianitosList.clear();
        MutableLiveData<ArrayList<Map<String, Long>>> listMutableLiveData = new MutableLiveData<>();
        db.collection("Users").orderBy("MarcianitosRecord", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    Map<String, Long> user = new HashMap<>();
                    user.put(documentSnapshot.getId(), Long.parseLong(Objects.requireNonNull(documentSnapshot.get("MarcianitosRecord")).toString()));
                    marcianitosList.add(user);
                    listMutableLiveData.postValue(marcianitosList);
                }
            }
        });
        return listMutableLiveData;
    }
}
