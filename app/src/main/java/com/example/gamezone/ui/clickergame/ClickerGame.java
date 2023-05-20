package com.example.gamezone.ui.clickergame;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamezone.R;
import com.example.gamezone.data.database.Firestore;
import com.example.gamezone.data.firebase.Firebase;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Objects;

public class ClickerGame extends AppCompatActivity {

    private long score = 0L;
    private long clickValue = 1L;
    private long clickValueCost = 10L;
    private long clickSpeed = 1000L;
    private long clickSpeedCost = 50L;
    private ImageButton clickButton;
    private TextView scoreTextView;
    private MediaPlayer mediaPlayer;
    private final Firestore firestore = new Firestore();
    private final Firebase firebase = new Firebase();

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
                clickSpeedCost *= 2;
                updateScoreTextView();
                updateClickSpeedButton();
                startAutoClicker();
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
        if (score >= 10000000000L) {
            clickButton.setImageResource(R.drawable.fondo_nueve);
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

    @Override
    protected void onPause() {
        super.onPause();
        saveGame();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveGame();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveGame();
        finish();
    }

    private void saveGame() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("score", score);
        editor.putLong("clickValue", clickValue);
        editor.putLong("clickValueCost", clickValueCost);
        editor.putLong("clickSpeed", clickSpeed);
        editor.putLong("clickSpeedCost", clickSpeedCost);
        editor.apply();
        firestore.updateScores(Objects.requireNonNull(firebase.mFirebaseAuth.getCurrentUser()), "ClickerGameRecord", String.valueOf(score));
    }

    private void loadGame() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        long defaultScore = 0L;
        long defaultClickValue = 1L;
        long defaultClickValueCost = 10L;
        long defaultClickSpeed = 1000L;
        long defaultClickSpeedCost = 50L;

        score = prefs.getLong("score", defaultScore);
        clickValue = prefs.getLong("clickValue", defaultClickValue);
        clickValueCost = prefs.getLong("clickValueCost", defaultClickValueCost);
        clickSpeed = prefs.getLong("clickSpeed", defaultClickSpeed);
        clickSpeedCost = prefs.getLong("clickSpeedCost", defaultClickSpeedCost);
        updateScoreTextView();
        updateClickValueButton();
        updateClickSpeedButton();
    }
}
