package com.example.gamezone.remolachagame.difficultgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.gamezone.R;

import java.util.Random;

public class DifficultGameScreen extends View {

    public int width, height;
    public int posX, posY, radio;
    public int farmerX, farmerY, nephewX, nephewY, bearX, bearY;
    public int beetX, beetY, goldenBeetX, goldenBeetY;
    private RectF rectBasket;
    private final Random random = new Random();
    private Boolean goldenBeetCaught = false;
    public Integer score = 0;
    public MediaPlayer[] audio = new MediaPlayer[3];

    public DifficultGameScreen(Context context) {
        super(context);
    }

    public DifficultGameScreen(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        audio[0] = MediaPlayer.create(context, R.raw.dificil);
        audio[1] = MediaPlayer.create(context, R.raw.yija);
        audio[2] = MediaPlayer.create(context, R.raw.gameover);
        audio[0].start();
        audio[0].setOnCompletionListener(mp -> audio[0].start());
    }

    public DifficultGameScreen(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                posX = (int) event.getX();
                this.invalidate();
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setScore(canvas);
        setBasketPosition(canvas);
        setBeetScore(canvas);
        setGoldenBeetScore(canvas);
        setFarmerEnemy(canvas);
        setNephewEnemy(canvas);
        setBearEnemy(canvas);
    }

    private void setScore(Canvas canvas) {
        Paint points = new Paint();

        points.setTextAlign(Paint.Align.RIGHT);
        points.setTextSize(100);
        points.setColor(Color.WHITE);
        canvas.drawText(score.toString(), 150, 150, points);
    }

    private void setBasketPosition(Canvas canvas) {
        Paint basket = new Paint();

        rectBasket = new RectF((posX - radio), (posY - radio), (posX + radio), (posY + radio));
        Bitmap bitmapBasket = BitmapFactory.decodeResource(getResources(), R.drawable.picnic);
        canvas.drawBitmap(bitmapBasket, null, rectBasket, basket);
    }

    private void setBeetScore(Canvas canvas) {
        Paint beet = new Paint();

        RectF rectBeet = new RectF((beetX - radio), (beetY - radio), (beetX + radio), (beetY + radio));
        Bitmap bitmapBeet = BitmapFactory.decodeResource(getResources(), R.drawable.remolacha);
        canvas.drawBitmap(bitmapBeet, null, rectBeet, beet);

        if (beetY > height) {
            score -= 1;
            beetY = 50;
            beetX = random.nextInt(width);
        }

        if (RectF.intersects(rectBasket, rectBeet)) {
            score += 1;
            beetY = 50;
            beetX = random.nextInt(width);
        }
    }

    private void setGoldenBeetScore(Canvas canvas) {
        Paint goldenBeet = new Paint();

        if ((score == 25 || score == 50 || score == 75) && !goldenBeetCaught) {
            RectF rectGoldenBeet = new RectF((goldenBeetX - radio), (goldenBeetY - radio), (goldenBeetX + radio), (goldenBeetY + radio));
            Bitmap bitmapGoldenBeet = BitmapFactory.decodeResource(getResources(), R.drawable.remolachaoro);
            canvas.drawBitmap(bitmapGoldenBeet, null, rectGoldenBeet, goldenBeet);

            if (goldenBeetY > height) {
                goldenBeetY = 50;
                goldenBeetX = random.nextInt(width);
            }

            if (RectF.intersects(rectBasket, rectGoldenBeet)) {
                score += 10;
                beetY = 50;
                beetX = random.nextInt(width);
                goldenBeetCaught = true;
            }
        }
    }

    private void setFarmerEnemy(Canvas canvas) {
        Paint farmer = new Paint();

        RectF rectFarmer = new RectF((farmerX - radio), (farmerY - radio), (farmerX + radio), (farmerY + radio));
        Bitmap bitmapFarmer = BitmapFactory.decodeResource(getResources(), R.drawable.granjero);
        canvas.drawBitmap(bitmapFarmer, null, rectFarmer, farmer);

        if (farmerY > height) {
            farmerY = 50;
            farmerX = random.nextInt(width);
        }

        if (RectF.intersects(rectBasket, rectFarmer)) {
            score -= 3;
            farmerY = 50;
            farmerX = random.nextInt(width);
        }
    }

    private void setNephewEnemy(Canvas canvas) {
        Paint nephew = new Paint();

        RectF rectNephew = new RectF((nephewX - radio), (nephewY - radio), (nephewX + radio), (nephewY + radio));
        Bitmap mvl_bitmapNephew = BitmapFactory.decodeResource(getResources(), R.drawable.sobrino);
        canvas.drawBitmap(mvl_bitmapNephew, null, rectNephew, nephew);

        if (nephewY > height) {
            nephewY = 50;
            nephewX = random.nextInt(width);
        }

        if (RectF.intersects(rectBasket, rectNephew)) {
            score -= 5;
            nephewY = 50;
            nephewX = random.nextInt(width);
        }
    }

    private void setBearEnemy(Canvas canvas) {
        Paint bear = new Paint();

        if (score >= 20) {
            RectF rectBear = new RectF((bearX - radio), (bearY - radio), (bearX + radio), (bearY + radio));
            Bitmap bitmapBear = BitmapFactory.decodeResource(getResources(), R.drawable.oso);
            canvas.drawBitmap(bitmapBear, null, rectBear, bear);

            if (bearY > height) {
                bearY = 50;
                bearX = random.nextInt(width);
            }

            if (RectF.intersects(rectBasket, rectBear)) {
                score -= 5;
                bearY = 50;
                bearX = random.nextInt(width);
            }
        }
    }
}
