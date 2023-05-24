package com.example.gamezone.ui.games.marcianosGame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.example.gamezone.R;
import com.example.gamezone.ui.games.marcianosGame.objetosJuego.Asteroide;
import com.example.gamezone.ui.games.marcianosGame.objetosJuego.Escudo;
import com.example.gamezone.ui.games.marcianosGame.objetosJuego.Marciano;
import com.example.gamezone.ui.games.marcianosGame.objetosJuego.Nave;
import com.example.gamezone.ui.games.marcianosGame.objetosJuego.Proyectil;

import java.util.Random;


public class Juego extends View {
    //Objetos del juego
    Nave objNave;
    Asteroide objAsteroide,objAsteroide2;
    Proyectil objProyectil;
    Escudo objEscudo,objEscudo2;
    int porcentajeDeDano;
    Marciano objMarciano;
    //Variables del juego
    public long tiempoInicial = 0;
    public Canvas canvasJuego;
    public int ancho, alto;
    public boolean tocaPantalla=false;
    public boolean win = false;
    public boolean unDisparoAlaVez = true;
    public boolean traspasaEscudo;
    public boolean aparecenMarcianos = false;
    public int posNaveX, posNaveY, radio, posAsteroideX,
            posAsteroideY,posAsteroide2X, posAsteroide2Y,posProyectilX,
            posProyectilY, posEscudo1X,posEscudo1Y, posEscudo2X,posEscudo2Y,
            posMarcianoX,posMarcianoY;    //La clase GestureDetector se utiliza para detectar gestos en la pantalla, como toques y arrastres.
    public RectF rectNave, rectAsteroide, rectAsteroide2, rectFondo, rectFGameOver,
            rectFProyectil, rectEscudo1,rectEscudo2, rectMarciano;    //La clase RectF se utiliza para representar un rectángulo con coordenadas de punto flotante.
    public Integer puntuacion = 0;
    public Random random = new Random();
    public Random random2 = new Random();
    boolean explotaAsteroide1 = false;
    boolean explotaAsteroide2 = false;
    boolean explosionPintada = false;
    boolean escudosActivos = false;
    Movie movieExplosion;
    int duracionTotal;
    boolean explotaMarciano = false;
    boolean explotaEscudo = false;
    boolean colisionNaveMarciano = false;
    boolean movimientoRestringido = false;
    boolean disparoRestringido = false;
    boolean solouno = true;


    //Birmaps
    Bitmap bitmapGameOver = BitmapFactory.decodeResource(getResources(), R.drawable.gameover2);
    Bitmap bitmapWin = BitmapFactory.decodeResource(getResources(),R.drawable.winlogo);


    public Juego(Context context) {
        super(context);
    }

    public Juego(Context context, AttributeSet attrs) {
        super(context, attrs);
        win = false;
        solouno = true;
        disparoRestringido = false;
        movimientoRestringido = false;
        explotaMarciano = false;
        explotaEscudo = false;
        colisionNaveMarciano = false;
        rectAsteroide = new RectF(0,0,50,10);
        rectAsteroide2 = new RectF(0,0,50,10);
        rectMarciano = new RectF((posMarcianoX - radio),(posMarcianoY - radio),(posMarcianoX + radio),(posMarcianoY + radio));
        objMarciano = new Marciano(context,rectMarciano);
        objEscudo = new Escudo(context,rectMarciano);
        escudosActivos = false;
        traspasaEscudo = false;
        rectEscudo1 = new RectF(0,0,50,10);
        rectEscudo2 = new RectF(0,0,50,10);
        objEscudo = new Escudo(context,rectEscudo1);
        objEscudo2 = new Escudo(context,rectEscudo2);
        //Inicializamos las variables
        rectNave = new RectF((posNaveX - radio), (posNaveY - radio), (posNaveX + radio), (posNaveY + radio));
        objNave = new Nave(context,rectNave);
        objAsteroide = new Asteroide(context,rectAsteroide);
        objAsteroide2 = new Asteroide(context,rectAsteroide2);
        rectFProyectil = new RectF((posProyectilX - radio), (posProyectilY - radio * 2), (posProyectilX + radio), (posProyectilY - radio));
        objProyectil = new Proyectil(context,rectFProyectil);
        explotaAsteroide1 = false;
        explotaAsteroide2 = false;
    }

    //El método onTouchEvent() es un método que se ejecuta cuando se produce un evento de toque en
    // la vista. En este método se controla el movimiento de la cesta en la pantalla según la posición
    // del dedo del usuario y se redibuja la vista utilizando el método invalidate().
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!movimientoRestringido){
            //Necesitas la posición de X y Y
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    traspasaEscudo = false;
                    tocaPantalla = true;
                    break;
                case MotionEvent.ACTION_UP:
                    tocaPantalla = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    //Configuramos el movimiento tanto por X como por Y
                    //junto con la logica de los escudos para que
                    //no puedan ser traspasados
                    if (traspasaEscudo) {
                        if(posNaveY< 1600){
                            posNaveX = (int) event.getX();
                        }
                    } else{
                        posNaveX = (int) event.getX();
                        posNaveY = (int) event.getY();
                    }
                    //Le damos un radio 100
                    radio = 100;
                    // invalidate llama al onDraw y vuelve a pintar la bola
                    this.invalidate();
                    break;

            }
        }
        return true;
    }

    // El mismo constructor que el anterior pero se le pasa un entero mas que parece definir el estilo
    public Juego(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvasJuego = canvas;

        //Definimos los objetos a pintar
        Paint Puntuacion = new Paint();
        Paint porcentajeDeDano = new Paint();
        Paint gameOverFondo = new Paint();
        Paint gameOverLogo = new Paint();

        Paint winFondo = new Paint();
        winFondo.setColor(Color.BLACK);
        winFondo.setStyle(Paint.Style.FILL_AND_STROKE);

        //Inicializamos los Paints
        Puntuacion.setTextAlign(Paint.Align.RIGHT);
        Puntuacion.setTextSize(50);
        Puntuacion.setColor(Color.WHITE);

        porcentajeDeDano.setTextAlign(Paint.Align.LEFT);
        porcentajeDeDano.setTextSize(50);
        porcentajeDeDano.setColor(Color.RED);


        //Inicializamos las variables de la pantalla de GameOVer
        //Revisar si hacen falta estas lineas.
        gameOverFondo.setColor(Color.BLACK);
        gameOverFondo.setStyle(Paint.Style.FILL_AND_STROKE);
        gameOverLogo.setColor(Color.WHITE);
        gameOverLogo.setStyle(Paint.Style.FILL_AND_STROKE);


        //Cuando el jugador pierda, osea cuando el porcentaje de daño recibido
        //sea mayor o igual a 100 aparece la pantalla de GameOver y muestra la puntuación obtenida
        if (this.porcentajeDeDano >= 100) {
            //Pintamos el fondo del gameOver
            canvas.drawRect(new Rect(0, 0, (ancho), (alto)), gameOverFondo);
            movimientoRestringido = true;
            //Pintamos el logo del gameOver
            //Establecemos el ancho del logo del Bitmap GameOver
            int anchoBtmpGameOver = bitmapGameOver.getWidth() - 512;
            int altoBtmpGameOver = bitmapGameOver.getHeight() - 512;

            //Obtenemos la altura y lo ancho de la pantalla de nuestro dispositivo.
            int alturaDePantalla = getResources().getDisplayMetrics().widthPixels;
            int anchoDePantalla = getResources().getDisplayMetrics().heightPixels;

            //Establecemos las coordenadas de la izquierda y la parte superior
            //para colocar nuestroRecFGameOver centrado en la pantalla
            int izquierda = (alturaDePantalla - anchoBtmpGameOver) / 2;
            int parteSuperior = (anchoDePantalla - altoBtmpGameOver) / 3;

            //Creamos un RectF que nos servirá para colocar nuestro Logo de GameOver
            rectFGameOver = new RectF(izquierda, parteSuperior, izquierda + anchoBtmpGameOver, parteSuperior + altoBtmpGameOver);
            canvas.drawBitmap(bitmapGameOver, null, rectFGameOver, gameOverLogo);

            Paint puntuacionTotal = new Paint();
            puntuacionTotal.setTextAlign(Paint.Align.LEFT);
            puntuacionTotal.setTextSize(70);
            puntuacionTotal.setColor(Color.RED);
            canvas.drawText("Puntuación total: "+Integer.toString(puntuacion), 250, 1400, puntuacionTotal);



        } else {
            //todo, LOGICA DE INICIO DEL JUEGO
            //Este else lo que hace es que solo pinta todos los objetos del juego
            //en caso de que el jugador no haya perdido el juego

            rectFondo = new RectF(0, 0, 0 + ancho, 0 + alto);

            //Logica de los Escudos
            if(escudosActivos){

                //Pintamos los escudos
                rectEscudo1 = new RectF((posEscudo1X - radio),(posEscudo1Y-radio),(posEscudo1X+radio),(posEscudo1Y+radio));
                rectEscudo2 = new RectF((posEscudo2X - radio),(posEscudo2Y-radio),(posEscudo2X+radio),(posEscudo2Y+radio));
                objEscudo.setRectEscudo(rectEscudo1);
                objEscudo2.setRectEscudo(rectEscudo2);
                objEscudo.draw(canvas);
                objEscudo2.draw(canvas);

                //todo Logica de nave con escudo
                if(RectF.intersects(rectNave,rectEscudo1)||RectF.intersects(rectNave,rectEscudo2)){
                    traspasaEscudo = true;
                }else{traspasaEscudo = false;}
                //todo Logica de proyectil con Escudo
                if(RectF.intersects(rectFProyectil,rectEscudo1)||RectF.intersects(rectFProyectil,rectEscudo2)) {
                    unDisparoAlaVez = true;
                }
                //Pintamos los marcianos
                if(aparecenMarcianos){
                    rectMarciano = new RectF((posMarcianoX - radio),(posMarcianoY - radio),(posMarcianoX + radio),(posMarcianoY + radio));
                    objMarciano.setRectMarciano(rectMarciano);
                    objMarciano.draw(canvas);
                }else if(explotaMarciano){
                    //Explotamos los marcianos
                    objAsteroide2.explotaAsteroide(canvas,posMarcianoX,posMarcianoY);
                }else if(explotaEscudo){
                    //Explotamos los Escudos
                    objAsteroide2.explotaAsteroide(canvas,posEscudo2X,posEscudo2Y);
                    objAsteroide.explotaAsteroide(canvas,posEscudo1X,posEscudo2Y);
                    //todo Explotamos Nave y Marciano
                }else if(colisionNaveMarciano){
                    objAsteroide2.explotaAsteroide(canvas,posNaveX,posNaveY);
                    objAsteroide.explotaAsteroide(canvas,posMarcianoX,posMarcianoY);
                }

            }



            //todo Objeto Nave
            //Pintamos la nave en la misma posición del rectNave
            //el rectNave coge su posición del metodo onTouchEvent
            //setRectNave actualiza la posición de la nave
            //segun el rectNave
            rectNave = new RectF((posNaveX - radio), (posNaveY - radio), (posNaveX + radio), (posNaveY + radio));
            objNave.setRectNave(rectNave);
            objNave.draw(canvas);

            //todo Logica del Proyectil
            //Funciona de esta manera, si el disparo no esta Restringido entonces
            //la nave puede disparar. Para disparar lo que hace es pintar el objeto
            //proyectil estableciendo su posición mediante el RectF, dicho RectF
            //se situa justo arriba de la nave
            if(!disparoRestringido){
                rectFProyectil = new RectF((posProyectilX - radio), (posProyectilY - radio * 2), (posProyectilX + radio), (posProyectilY - radio));
                objProyectil.setRectProyectil(rectFProyectil);
                objProyectil.draw(canvas);

                //Si la posición del proyectil supera el limite superior de la pantalla entonces
                //vuelve a pintarse justo arriba de la nave
                if(posProyectilY<0){
                    unDisparoAlaVez = true;
                }
            }

            //todo INICIO DE LOGICA DE LOS ASTEROIDES
            //Este boolean lo que marca es el tiempo de vida de los asteroides
            //Cuando el boolean sea true los asteroides explotaran y dejaran de pintarse
            //sus objetos en pantalla.
                if(explotaAsteroide1 ==false){
                    //Establece el rectF de los asteroides
                    rectAsteroide = new RectF((posAsteroideX - radio), (posAsteroideY - radio), (posAsteroideX + radio), (posAsteroideY + radio));
                    //Pone los asteriodes arriba cuando llegan al limite inferior de la pantalla
                    if (posAsteroideY > alto) {
                        puntuacion++; //Cada vez que se esquiva un asteroide la puntuación suma 1 punto
                        posAsteroideY = 0;//Se vuelve a pintar en la posición 0 osea arriba de la pantalla
                        posAsteroideX = random.nextInt(ancho); //Establecemos la X como random
                    }
                    //Pinta los asterioides
                    objAsteroide.setRectAsteroide(rectAsteroide);
                    objAsteroide.draw(canvas);
                }else{
                    //Explota los asteriodes una vez ha pasado el tiempo indicado en el hilo principal
                    objAsteroide.explotaAsteroide(canvas,posAsteroideX,posAsteroideY);
                }
            //Establecemos la misma logica pero para el asteroide 2
            if(explotaAsteroide2 ==false){
                //Establece el rectF de los asteroides
                rectAsteroide2 = new RectF((posAsteroide2X - radio), (posAsteroide2Y - radio), (posAsteroide2X + radio), (posAsteroide2Y + radio));
                //Pone los asteriodes arriba cuando llegan abajo de la pantalla
                if (posAsteroide2Y > alto) {
                    puntuacion++;
                    posAsteroide2Y = 0;
                    posAsteroide2X = random2.nextInt(ancho);
                }
                //Pinta los asterioides
                objAsteroide2.setRectAsteroide(rectAsteroide2);
                objAsteroide2.draw(canvas);

                //Calculamos las intersecciones entre los objetos
                if(RectF.intersects(rectFProyectil, rectAsteroide2)){
                    unDisparoAlaVez = true;
                }
            }else{
                //Explota los asteriodes una vez ha pasado el tiempo indicado en
                //el hilo principal
                objAsteroide2.explotaAsteroide(canvas,posAsteroide2X,posAsteroide2Y);
            }
            //todo FIN DE LOGICA DE LOS ASTERIODES

            //todo, Pintamos por pantalla la puntuación y el porcentaje de daño del jugador
            canvas.drawText("Puntuación: "+puntuacion.toString(), 350, 150, Puntuacion);
            canvas.drawText("Daño recibido: "+Integer.toString(this.porcentajeDeDano)+"%", 600, 150, porcentajeDeDano);

        }

        //Este boolean Win establece si hemos ganado la partida
        //Si la hemos ganado restringe el movimiento del jugador, deja de pintar los objetos
        //del juego por pantalla y muestra la pantalla de Victoria junto con la puntuación total obtenida
        if(win == true){
            movimientoRestringido = true;
            canvas.drawRect(new Rect(0, 0, (ancho), (alto)), winFondo);
            int anchoBtmpWin = bitmapWin.getWidth() - 512;
            int altoBtmpWin= bitmapWin.getHeight() - 512;
            int alturaDePantalla = getResources().getDisplayMetrics().widthPixels;
            int anchoDePantalla = getResources().getDisplayMetrics().heightPixels;
            int izquierda = (alturaDePantalla - anchoBtmpWin) / 2;
            int parteSuperior = (anchoDePantalla - altoBtmpWin) / 3;

            //Creamos un RectF que nos servirá para colocar nuestro Logo de GameOver
            rectFGameOver = new RectF(izquierda, parteSuperior, izquierda + anchoBtmpWin, parteSuperior + altoBtmpWin);
            canvas.drawBitmap(bitmapWin, null, rectFGameOver, gameOverLogo);
            Paint puntuacionTotal = new Paint();
            puntuacionTotal.setTextAlign(Paint.Align.LEFT);
            puntuacionTotal.setTextSize(70);
            puntuacionTotal.setColor(Color.rgb(255, 215, 0));
            canvas.drawText("Puntuación total: "+Integer.toString(puntuacion), 250, 1400, puntuacionTotal);
        }
    }
}