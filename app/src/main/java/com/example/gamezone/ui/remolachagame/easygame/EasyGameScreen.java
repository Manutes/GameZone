package com.example.gamezone.ui.remolachagame.easygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.gamezone.R;

import java.util.Random;

public class EasyGameScreen extends View {

    public int width, height;
    public int posX, posY, radio;
    public int farmerX, farmerY, bearX, bearY;
    public int beetX, beetY, goldenBeetX, goldenBeetY;
    private RectF rectBasket;
    private final Random random = new Random();
    private Boolean goldenBeetCaught = false;
    public Integer score = 0;
    public MediaPlayer[] audio = new MediaPlayer[2];

    public EasyGameScreen(Context context) {
        super(context);
    }

    public EasyGameScreen(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EasyGameScreen(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        audio[0] = MediaPlayer.create(context, R.raw.normal);
        audio[1] = MediaPlayer.create(context, R.raw.yija);
        audio[0].start();
        audio[0].setLooping(true);
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
        setBearEnemy(canvas);
    }

    private void setScore(Canvas canvas) {
        Paint points = new Paint();

        points.setTextAlign(Paint.Align.RIGHT);
        points.setTextSize(100);
        points.setColor(Color.WHITE);
        points.setTypeface(setCustomTypeface());
        canvas.drawText(score.toString(), 150, 150, points);
    }

    private Typeface setCustomTypeface() {
        Typeface typeface;
        if (Build.VERSION.SDK_INT >= 28) {
            Typeface typefaceA = ResourcesCompat.getFont(getContext(), R.font.fuente);
            typeface = Typeface.create(typefaceA, 700, false);
        } else {
            typeface = ResourcesCompat.getFont(getContext(), R.font.fuente);
        }
        return typeface;
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

        if ((score == 15 || score == 30) && !goldenBeetCaught) {
            RectF rectGoldenBeet = new RectF((goldenBeetX - radio), (goldenBeetY - radio), (goldenBeetX + radio), (goldenBeetY + radio));
            Bitmap bitmapGoldenBeet = BitmapFactory.decodeResource(getResources(), R.drawable.remolachaoro);
            canvas.drawBitmap(bitmapGoldenBeet, null, rectGoldenBeet, goldenBeet);

            if (goldenBeetY > height) {
                goldenBeetY = 50;
                goldenBeetX = random.nextInt(width);
            }

            if (RectF.intersects(rectBasket, rectGoldenBeet)) {
                score += 5;
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