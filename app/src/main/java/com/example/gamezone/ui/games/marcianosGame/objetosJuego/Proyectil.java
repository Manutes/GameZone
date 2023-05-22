package com.example.gamezone.ui.games.marcianosGame.objetosJuego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.example.gamezone.R;

public class Proyectil extends View {
    public RectF rectProyectil;
    Bitmap bitmapProyectil;

    public Proyectil(Context context, RectF rectProyectil){
        super(context);
        this.rectProyectil = rectProyectil;
        bitmapProyectil = BitmapFactory.decodeResource(getResources(), R.drawable.punto);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Aquí dibuja el cuadrado rojo en el lienzo (canvas)
        Paint Nave = new Paint();
        canvas.drawBitmap(bitmapProyectil,null,rectProyectil, Nave);
    }

    public void setRectProyectil(RectF rectProyectil){
        this.rectProyectil = rectProyectil;
    }

}
