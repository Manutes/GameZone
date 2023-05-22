package com.example.gamezone.ui.games.marcianosGame.objetosJuego;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;


import com.example.gamezone.R;

import java.io.InputStream;

    public class fondoJuego extends androidx.appcompat.widget.AppCompatImageView{
        private Movie movie;
        private long movieStart;

        public fondoJuego(Context context, AttributeSet attrs) {
            super(context, attrs);
            setFocusable(true);
            @SuppressLint("ResourceType") InputStream is = context.getResources().openRawResource(R.drawable.espacio1);
            movie = Movie.decodeStream(is);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.TRANSPARENT);
            super.onDraw(canvas);
            //todo, IMPORTANTE Para hacer mas grande el gif tienes que agrandar el canvas, en la linea de codigo de
            //abajo hacemos que el canvas sea el triple de grande y entonces al pintar el GIF se ve
            // 3 veces mas grande
            canvas.scale(4f, 4f);
            long now = android.os.SystemClock.uptimeMillis();
            if (movieStart == 0) {
                movieStart = now;
            }
            int relTime = (int) ((now - movieStart) % movie.duration());
            movie.setTime(relTime);
            //antes movie.draw(canvas, getWidth() - movie.width(), getHeight() - movie.height());
            movie.draw(canvas, 0 ,0);
            this.invalidate();

        }

    }

