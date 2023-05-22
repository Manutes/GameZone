package com.example.gamezone.ui.games.marcianosGame.objetosJuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.example.gamezone.R;

public class Escudo extends View {

    public RectF rectEscudo;
    Integer radio;
    Bitmap bitmapEscudo;
    public int width;
    public int height;
    boolean explosionEscudoPintada;
    Long tiempoActual;
    Movie movieExplosion;
    int duracionTotal;
    int tiempoFrameActual;

    public Escudo(Context context, RectF rectAsteroide){
        super(context);
        this.rectEscudo = rectAsteroide;
        bitmapEscudo = BitmapFactory.decodeResource(getResources(), R.drawable.escudo2);
      //  width = bitmapEscudo.getWidth();
      //  height = bitmapEscudo.getHeight();
        explosionEscudoPintada = false;
        movieExplosion  = Movie.decodeStream(getResources().openRawResource(R.raw.explosion2));
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Aquí dibuja el cuadrado rojo en el lienzo (canvas)
        Paint paintAsteroide= new Paint();
        paintAsteroide.setColor(Color.WHITE);
        paintAsteroide.setStyle(Paint.Style.FILL);
        canvas.drawBitmap(bitmapEscudo,null,rectEscudo,paintAsteroide);
    }

    public void setRectEscudo(RectF rectEscudo){
        this.rectEscudo = rectEscudo;
    }

    public void finalizaEscudo(Canvas canvas, Integer posX, Integer posY){
        super.draw(canvas);
        if (explosionEscudoPintada == false) {
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
                explosionEscudoPintada = true;
            }
        }
    }


    public void setExplosionEscudoPintada(boolean b){
        explosionEscudoPintada = b;
    }

    public int getDuracionTotal(){
        return duracionTotal;
    }

    public int getTiempoFrameActual(){
        return tiempoFrameActual;
    }


}
