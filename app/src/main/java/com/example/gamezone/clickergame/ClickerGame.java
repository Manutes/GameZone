package com.example.clickergamem13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int score = 0;
    private int clickValue = 1;
    private int clickValueCost = 10;
    private int clickSpeed = 1000;
    private int clickSpeedCost = 50;
    private Button clickButton;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreTextView = findViewById(R.id.score_text_view);
        clickButton = findViewById(R.id.click_button);

        loadGame();

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score += clickValue;
                updateScoreTextView();
            }
        });

        Button clickValueButton = findViewById(R.id.click_value_button);
        clickValueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (score >= clickValueCost) {
                    score -= clickValueCost;
                    clickValue++;
                    clickValueCost *= 2;
                    updateScoreTextView();
                    updateClickValueButton();
                }
            }
        });

        Button clickSpeedButton = findViewById(R.id.click_speed_button);
        clickSpeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (score >= clickSpeedCost) {
                    score -= clickSpeedCost;
                    clickSpeed /= 2;
                    clickSpeedCost *= 2;
                    updateScoreTextView();
                    updateClickSpeedButton();
                    startAutoClicker();
                }
            }
        });

        startAutoClicker();
    }

    private void startAutoClicker() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                score += clickValue;
                updateScoreTextView();
                handler.postDelayed(this, clickSpeed);
            }
        }, clickSpeed);
    }

    private void updateScoreTextView() {
        scoreTextView.setText("Score: " + score);
    }

    private void updateClickValueButton() {
        Button clickValueButton = findViewById(R.id.click_value_button);
        clickValueButton.setText("Increase Click Value (+1): " + clickValueCost + " Coins");
    }

    private void updateClickSpeedButton() {
        Button clickSpeedButton = findViewById(R.id.click_speed_button);
        clickSpeedButton.setText("Increase Click Speed: " + clickSpeedCost + " Coins");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveGame();
    }

    private void saveGame() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("score", score);
        editor.putInt("clickValue", clickValue);
        editor.putInt("clickValueCost", clickValueCost);
        editor.putInt("clickSpeed", clickSpeed);
        editor.putInt("clickSpeedCost", clickSpeedCost);
        editor.apply();
    }

    private void loadGame() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        score = prefs.getInt("score", 0);
        clickValue = prefs.getInt("clickValue", 1);
        clickValueCost = prefs.getInt("clickValueCost", 10);
        clickSpeed = prefs.getInt("clickSpeed", 1000);
        clickSpeedCost = prefs.getInt("clickSpeedCost", 50);
        updateScoreTextView();
        updateClickValueButton();
        updateClickSpeedButton();
    }
}
