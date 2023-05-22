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

public class Asteroide extends View {

    public RectF rectAsteroide;
    Integer radio;
    Bitmap bitmapAsteroide;
    public int width;
    public int height;
    boolean explosionPintada;
    Long tiempoActual;
    Movie movieExplosion;
    int duracionTotal;
    int tiempoFrameActual;

    public Asteroide(Context context, RectF rectAsteroide){
        super(context);
        this.rectAsteroide = rectAsteroide;
        bitmapAsteroide = BitmapFactory.decodeResource(getResources(), R.drawable.asteroide);
        width = bitmapAsteroide.getWidth();
        height = bitmapAsteroide.getHeight();
        explosionPintada = false;
        movieExplosion  = Movie.decodeStream(getResources().openRawResource(R.raw.explosion2));
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Aquí dibuja el cuadrado rojo en el lienzo (canvas)
        Paint paintAsteroide= new Paint();
        canvas.drawBitmap(bitmapAsteroide,null,rectAsteroide,paintAsteroide);
    }

    public void setRectAsteroide(RectF rectAsteroide){
        this.rectAsteroide = rectAsteroide;
    }

    public void explotaAsteroide(Canvas canvas, Integer posAsteroideX, Integer posAsteroideY){
        super.onDraw(canvas);
        if (explosionPintada == false) {
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
            movieExplosion.draw(canvas, posAsteroideX, posAsteroideY);
        }
    }

    public void setExplosionPintada(boolean b){
        explosionPintada = b;
    }

    public int getDuracionTotal(){
        return duracionTotal;
    }

    public int getTiempoFrameActual(){
        return tiempoFrameActual;
    }


}
