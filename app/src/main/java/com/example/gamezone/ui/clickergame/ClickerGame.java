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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamezone.R;

public class ClickerGame extends AppCompatActivity {

    private int score = 0;
    private int clickValue = 1;
    private int clickValueCost = 10;
    private int clickSpeed = 1000;
    private int clickSpeedCost = 50;
    private ImageButton clickButton;
    private TextView scoreTextView;
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;

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
        mediaPlayer2 = MediaPlayer.create(this, R.raw.bootup);

        loadGame();

        mediaPlayer2.seekTo(0);
        mediaPlayer2.start();

        ImageButton clickButton = findViewById(R.id.click_button);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
                score += clickValue;
                updateScoreTextView();
            }
        });

        clickButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
        scoreTextView.setText("Dinero: " + score);

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
