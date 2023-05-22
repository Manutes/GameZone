package com.example.gamezone.ui.games.marcianosGame.objetosJuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.example.gamezone.R;


public class Nave extends View{

    public RectF rectNave;
    public Integer numeroDeVidas = 3;
    public Integer puntuacion = 0;
    Integer radio;
    Bitmap bitmapnave;
    boolean explosionNavePintada;
    Movie movieExplosion;
    int duracionTotal;
    int tiempoFrameActual;
    Long tiempoActual;


        public Nave(Context context,RectF rectJuego) {
            super(context);
            rectNave = rectJuego;
            bitmapnave = BitmapFactory.decodeResource(getResources(), R.drawable.nave);

        }

        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            // Aquí dibuja el cuadrado rojo en el lienzo (canvas)
            Paint Nave = new Paint();
            canvas.drawBitmap(bitmapnave,null,rectNave, Nave);
        }

        public void setRectNave(RectF rectJuego){
            rectNave = rectJuego;
        }


    public void finalizaNave(Canvas canvas, Integer posX, Integer posY){
        super.draw(canvas);
        if (explosionNavePintada == false) {
            // Cargar el recurso de película (GIF) en un objeto Movie
            movieExplosion = Movie.decodeStream(getResources().openRawResource(R.raw.explosion2)); // Reemplaza "explosion" con el nombre de tu archivo GIF

            // Obtener el tiempo actual del GIF
            tiempoActual = android.os.SystemClock.uptimeMillis();

            // Obtener el tiempo de duración total del GIF
            //No se quita pero por lo menos ahora podemos controlar la duración
            duracionTotal = movieExplosion.duration();


            // Calcular el tiempo de fotograma actual teniendo en cuenta el loop
            tiempoFrameActual = (int) (tiempoActual % duracionTotal);

            // Establecer el tiempo de fotograma actual del GIF
            movieExplosion.setTime(tiempoFrameActual);

            // Dibujar el fotograma actual del GIF en el Canvas
            movieExplosion.draw(canvas, posX, posY);

            // Verificar si se ha alcanzado el final del GIF
            if (tiempoFrameActual > duracionTotal - 10) {
                // Si se ha alcanzado el final, marcar la explosión como pintada y detener la animación
                explosionNavePintada = true;
            }
        }
    }


    }


