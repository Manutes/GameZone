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

public class Marciano extends View {

    public RectF rectMarciano;
    Integer radio;
    Bitmap bitmapMarciano;
    public int width;
    public int height;
    boolean explosionMarciano;
    Long tiempoActual;
    Movie movieExplosion;
    int duracionTotal;
    int tiempoFrameActual;

    public Marciano(Context context, RectF rectAsteroide){
        super(context);
        this.rectMarciano = rectAsteroide;
        bitmapMarciano = BitmapFactory.decodeResource(getResources(), R.drawable.marciano);
        explosionMarciano = false;
        movieExplosion  = Movie.decodeStream(getResources().openRawResource(R.raw.explosion2));
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Aquí dibuja el cuadrado rojo en el lienzo (canvas)
        Paint paintAsteroide= new Paint();
        canvas.drawBitmap(bitmapMarciano,null, rectMarciano,paintAsteroide);
    }

    public void setRectMarciano(RectF rectMarciano){
        this.rectMarciano = rectMarciano;
    }

    public void setPositionMarciano(int x, int y, int radio){
        rectMarciano = new RectF((x-radio),(y-radio),(x+radio),(y+radio));
    }

    public void finalizaMarciano(Canvas canvas, Integer posAsteroideX, Integer posAsteroideY){
        super.draw(canvas);
        if (explosionMarciano == false) {
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

            // Verificar si se ha alcanzado el final del GIF
            if (tiempoFrameActual > duracionTotal - 10) {
                // Si se ha alcanzado el final, marcar la explosión como pintada y detener la animación
                explosionMarciano = true;
            }
        }
    }


    public void setExplosionMarciano(boolean b){
        explosionMarciano = b;
    }

    public int getDuracionTotal(){
        return duracionTotal;
    }

    public int getTiempoFrameActual(){
        return tiempoFrameActual;
    }


}
