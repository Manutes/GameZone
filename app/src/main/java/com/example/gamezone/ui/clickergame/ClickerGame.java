package com.example.gamezone.ui.clickergame;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamezone.R;

import java.text.DecimalFormat;
import java.text.MessageFormat;

public class ClickerGame extends AppCompatActivity {

    private int score = 0;
    private int clickValue = 1;
    private int clickValueCost = 10;
    private int clickSpeed = 1000;
    private int clickSpeedCost = 50;
    private int upgradeCost = 100;
    private int bonus = 1;
    private int bay = 0;
    private ImageButton clickButton;
    private TextView scoreTextView;
    private MediaPlayer mediaPlayer;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.clickergame_main);

        scoreTextView = findViewById(R.id.score_text_view);
        clickButton = findViewById(R.id.click_button);

        mediaPlayer = MediaPlayer.create(this, R.raw.clic);
        MediaPlayer mediaPlayer2 = MediaPlayer.create(this, R.raw.bootup);

        loadGame();

        mediaPlayer2.seekTo(0);
        mediaPlayer2.start();

        ImageButton clickButton = findViewById(R.id.click_button);
        clickButton.setOnClickListener(view -> {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
            score += clickValue;
            updateScoreTextView();
        });

        clickButton.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Define la animación de presionado del botón
                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 0.95f);
                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 0.95f);
                scaleDownX.setDuration(100);
                scaleDownY.setDuration(100);

                AnimatorSet scaleDown = new AnimatorSet();
                scaleDown.play(scaleDownX).with(scaleDownY);

                // Ejecuta la animación de presionado del botón
                scaleDown.start();
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                // Define la animación de soltar el botón
                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(v, "scaleX", 1f);
                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(v, "scaleY", 1f);
                scaleDownX.setDuration(100);
                scaleDownY.setDuration(100);

                AnimatorSet scaleDown = new AnimatorSet();
                scaleDown.play(scaleDownX).with(scaleDownY);
                // Ejecuta la animación de soltar el botón
                scaleDown.start();
            }
            return false;
        });

        Button clickValueButton = findViewById(R.id.click_value_button);
        clickValueButton.setOnClickListener(view -> {
            if (score >= clickValueCost) {
                score -= clickValueCost;
                clickValue++;
                clickValue=clickValue*bonus;
                clickValueCost *= 2;
                updateScoreTextView();
                updateClickValueButton();
            }
        });

        Button clickSpeedButton = findViewById(R.id.click_speed_button);
        clickSpeedButton.setOnClickListener(view -> {
            if (score >= clickSpeedCost) {
                score -= clickSpeedCost;
                clickSpeed /= 2;
                clickSpeedCost *= 2;updateScoreTextView();
                updateClickSpeedButton();
                startAutoClicker();
            }
        });

        Button upgradeButton = findViewById(R.id.upgrade_pc);
        upgradeButton.setOnClickListener(view -> {
            ImageView coinBg = findViewById(R.id.coinBg);
            if (score >= upgradeCost) {
                if(bay == 0) {
                    bay++;
                    coinBg.setImageResource(R.drawable.coin_bg2);
                } else if (bay == 1){
                    coinBg.setImageResource(R.drawable.coin_bg3);
                }
                upgradeCost = upgradeCost * 2;
                score = 0;
                bonus++;
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
        scoreTextView.setText(MessageFormat.format("{0}{1}{2}", getString(R.string.money_text), " ", format(score)));

        // Cambiar la imagen del botón si se ha superado el umbral
        if (score >= 1000) {
            clickButton.setImageResource(R.drawable.fondo_dos);
        }
        if (score >= 10000) {
            clickButton.setImageResource(R.drawable.fondo_tres);
        }
        if (score >= 100000) {
            clickButton.setImageResource(R.drawable.fondo_cuatro);
        }
        if (score >= 1000000) {
            clickButton.setImageResource(R.drawable.fondo_cinco);
        }
        if (score >= 10000000) {
            clickButton.setImageResource(R.drawable.fondo_seis);
        }
        if (score >= 100000000) {
            clickButton.setImageResource(R.drawable.fondo_siete);
        }
        if (score >= 1000000000) {
            clickButton.setImageResource(R.drawable.fondo_ocho);
        }

    }

    public static String format(float value) {
        String[] arr = {"", "K", "M", "B", "T", "P", "E"};
        int index = 0;
        while ((value / 1000) >= 1) {
            value = value / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%s %s", decimalFormat.format(value), arr[index]);
    }

    private void updateClickValueButton() {
        Button clickValueButton = findViewById(R.id.click_value_button);
        clickValueButton.setText(MessageFormat.format("{0}{1}{2}{3}{4}", getString(R.string.button_increase_click_value), " ", clickValueCost, " ", getString(R.string.coins_text)));
    }

    private void updateClickSpeedButton() {
        Button clickSpeedButton = findViewById(R.id.click_speed_button);
        clickSpeedButton.setText(MessageFormat.format("{0}{1}{2}{3}{4}", getString(R.string.button_increase_click_speed), " ", clickSpeedCost, " ", getString(R.string.coins_text)));
    }

    private void updateUpgradeButton() {
        Button clickSpeedButton = findViewById(R.id.upgrade_pc);
        clickSpeedButton.setText(MessageFormat.format("{0}{1}{2}{3}{4}", getString(R.string.button_upgrade_pc), " ", upgradeCost, " ", getString(R.string.coins_text)));
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
