package com.example.gamezone.ui.ranking;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class RankingViewModel extends ViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Long> remolachaEasyList = new ArrayList<>();
    ArrayList<Long> remolachaDifficultList = new ArrayList<>();
    ArrayList<Long> clickerGameList = new ArrayList<>();
    ArrayList<Long> marcianitosList = new ArrayList<>();

    public MutableLiveData<ArrayList<Long>> getRemolachaEasyRanking() {
        remolachaEasyList.clear();
        MutableLiveData<ArrayList<Long>> listMutableLiveData = new MutableLiveData<>();
        db.collection("Users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    remolachaEasyList.add(Long.parseLong(Objects.requireNonNull(documentSnapshot.getString("RemolachaHeroEasyRecord"))));
                    remolachaEasyList.sort(Collections.reverseOrder());
                    listMutableLiveData.postValue(remolachaEasyList);

                }
            }
        });

        return listMutableLiveData;
    }

    public MutableLiveData<ArrayList<Long>> getRemolachaDifficultRanking() {
        remolachaDifficultList.clear();
        MutableLiveData<ArrayList<Long>> listMutableLiveData = new MutableLiveData<>();
        db.collection("Users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    remolachaDifficultList.add(Long.parseLong(Objects.requireNonNull(documentSnapshot.getString("RemolachaHeroDifficultRecord"))));
                    remolachaDifficultList.sort(Collections.reverseOrder());
                    listMutableLiveData.postValue(remolachaDifficultList);
                }
            }
        });
        return listMutableLiveData;
    }

    public MutableLiveData<ArrayList<Long>> getClickerGameRanking() {
        clickerGameList.clear();
        MutableLiveData<ArrayList<Long>> listMutableLiveData = new MutableLiveData<>();
        db.collection("Users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    clickerGameList.add(Long.parseLong(Objects.requireNonNull(documentSnapshot.getString("ClickerGameRecord"))));
                    clickerGameList.sort(Collections.reverseOrder());
                    listMutableLiveData.postValue(clickerGameList);
                }
            }
        });
        return listMutableLiveData;
    }

    public MutableLiveData<ArrayList<Long>> getMarcianitosRanking() {
        marcianitosList.clear();
        MutableLiveData<ArrayList<Long>> listMutableLiveData = new MutableLiveData<>();
        db.collection("Users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    marcianitosList.add(Long.parseLong(Objects.requireNonNull(documentSnapshot.getString("MarcianitosRecord"))));
                    marcianitosList.sort(Collections.reverseOrder());
                    listMutableLiveData.postValue(marcianitosList);
                }
            }
        });
        return listMutableLiveData;
    }
}
