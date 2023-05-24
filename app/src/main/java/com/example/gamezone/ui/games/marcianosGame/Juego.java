package com.example.gamezone.ui.games.marcianosGame;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.bumptech.glide.load.resource.gif.GifDrawable;


import com.example.gamezone.R;
import com.example.gamezone.ui.games.marcianosGame.objetosJuego.Asteroide;
import com.example.gamezone.ui.games.marcianosGame.objetosJuego.Escudo;
import com.example.gamezone.ui.games.marcianosGame.objetosJuego.Marciano;
import com.example.gamezone.ui.games.marcianosGame.objetosJuego.Nave;
import com.example.gamezone.ui.games.marcianosGame.objetosJuego.Proyectil;

import java.util.Random;


public class Juego extends View {
    GifDrawable gifDrawable;
    //Objetos del juego
    Nave objNave;
    Asteroide objAsteroide,objAsteroide2;
    Proyectil objProyectil;
    Escudo objEscudo,objEscudo2;
    int porcentajeDeDaño;
    Marciano objMarciano;
    //Variables del juego
    public long tiempoInicial = 0;
    public Canvas canvasJuego;
    public int ancho, alto;
    public boolean frutaSeleccionada=false;
    public boolean tocaPantalla=false;
    public boolean win = false;
    public boolean unDisparoAlaVez = true;
    public boolean traspasaEscudo;
    public boolean aparecenMarcianos = false;
    public int posNaveX, posNaveY, radio, posAsteroideX,
            posAsteroideY,posAsteroide2X, posAsteroide2Y,posProyectilX,
            posProyectilY, posEscudo1X,posEscudo1Y, posEscudo2X,posEscudo2Y,
            posMarcianoX,posMarcianoY;    //La clase GestureDetector se utiliza para detectar gestos en la pantalla, como toques y arrastres.
    public GestureDetector gestos;
    public RectF rectNave, rectAsteroide, rectAsteroide2, rectFondo, rectFGameOver,
            rectFProyectil, rectEscudo1,rectEscudo2, rectMarciano;    //La clase RectF se utiliza para representar un rectángulo con coordenadas de punto flotante.
    public Integer puntuacion = 0;
    public Integer numeroDeVidas = 3;//Indicamos que el jugador comienze con 3 vidas
    public Random random = new Random();
    public Random random2 = new Random();
    boolean explotaAsteroide1 = false;
    boolean explotaAsteroide2 = false;
    boolean explosionPintada = false;
    boolean escudosActivos = false;
    Movie movieExplosion;
    long tiempoActual;
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
    //MUSICA
    MediaPlayer  sonidoDisparo, sonidoExplosion, sonidoMarciano,sonidoEscudo;



    public Juego(Context context) {
        super(context);
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


        escudosActivos = false;
        rectEscudo1 = new RectF(0,0,50,10);
        rectEscudo2 = new RectF(0,0,50,10);
        objEscudo = new Escudo(context,rectEscudo1);
        objEscudo2 = new Escudo(context,rectEscudo2);
        traspasaEscudo = false;

        //Inicializamos todas las variables
        rectNave = new RectF((posNaveX - radio), (posNaveY - radio), (posNaveX + radio), (posNaveY + radio));
        objNave = new Nave(context,rectNave);
        objAsteroide = new Asteroide(context,rectAsteroide);
        objAsteroide2 = new Asteroide(context,rectAsteroide2);
        rectFProyectil = new RectF((posProyectilX - radio), (posProyectilY - radio * 2), (posProyectilX + radio), (posProyectilY - radio));
        objProyectil = new Proyectil(context,rectFProyectil);

        sonidoMarciano = MediaPlayer.create(context,R.raw.movimientomarciano);
        sonidoExplosion = MediaPlayer.create(context,R.raw.sonidoexplosion);
        sonidoEscudo = MediaPlayer.create(context,R.raw.escudo);
        sonidoDisparo = MediaPlayer.create(context,R.raw.disparonave);

        explotaAsteroide1 = false;
        explotaAsteroide2 = false;



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
        sonidoMarciano = MediaPlayer.create(context,R.raw.movimientomarciano);
        sonidoExplosion = MediaPlayer.create(context,R.raw.sonidoexplosion);
        sonidoEscudo = MediaPlayer.create(context,R.raw.escudo);
        sonidoDisparo = MediaPlayer.create(context,R.raw.disparonave);
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
                //Es posible eliminar el movimiento vertical poniendo el case y haciendo un break;
                case MotionEvent.ACTION_DOWN:
                    // Si cuando salga por pantalla el mensaje de gameOver el usuario pulsa en algun
                    //tlugar el programa vuelve a la interfaz principal a veces me da error, no se porque

                    if(numeroDeVidas <= 0){
                        //Volvemos a la activity Main activity
                        Intent intent = new Intent(getContext(), MarcianosMainActivity.class);
                        getContext().startActivity(intent);
                    }

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

                    //Le damos un radio 50
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
        escudosActivos = false;
        traspasaEscudo = false;
        rectEscudo1 = new RectF(0,0,50,10);
        rectEscudo2 = new RectF(0,0,50,10);
        objEscudo = new Escudo(context,rectEscudo1);
        objEscudo2 = new Escudo(context,rectEscudo2);
        //Inicializamos variables
        rectNave = new RectF((posNaveX - radio), (posNaveY - radio), (posNaveX + radio), (posNaveY + radio));
        objNave = new Nave(context,rectNave);
        objAsteroide = new Asteroide(context,rectAsteroide);
        objAsteroide2 = new Asteroide(context,rectAsteroide2);
        rectFProyectil = new RectF((posProyectilX - radio), (posProyectilY - radio * 2), (posProyectilX + radio), (posProyectilY - radio));
        objProyectil = new Proyectil(context,rectFProyectil);
        explotaAsteroide1 = false;
        explotaAsteroide2 = false;
        sonidoMarciano = MediaPlayer.create(context,R.raw.movimientomarciano);
        sonidoExplosion = MediaPlayer.create(context,R.raw.sonidoexplosion);
        sonidoEscudo = MediaPlayer.create(context,R.raw.escudo);
        sonidoDisparo = MediaPlayer.create(context,R.raw.disparonave);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvasJuego = canvas;

        //Definimos los objetos a pintar
        Paint Puntuacion = new Paint();
        Paint Vidas = new Paint();
        Paint gameOverFondo = new Paint();
        Paint gameOverLogo = new Paint();

        Paint winFondo = new Paint();
        winFondo.setColor(Color.BLACK);
        winFondo.setStyle(Paint.Style.FILL_AND_STROKE);

        //Inicializamos los Paints
        Puntuacion.setTextAlign(Paint.Align.RIGHT);
        Puntuacion.setTextSize(50);
        Puntuacion.setColor(Color.WHITE);

        //Creamos un PAINT que mostrará el numero de vidas que tiene el jugador
        //si llega a 0 GameOver
        Vidas.setTextAlign(Paint.Align.LEFT);
        Vidas.setTextSize(50);
        Vidas.setColor(Color.RED);


        //Inicializamos las variables de la pantalla de GameOVer
        //Revisar si hacen falta estas lineas.
        gameOverFondo.setColor(Color.BLACK);
        gameOverFondo.setStyle(Paint.Style.FILL_AND_STROKE);
        gameOverLogo.setColor(Color.WHITE);
        gameOverLogo.setStyle(Paint.Style.FILL_AND_STROKE);

        //todo, Cuando el numero de vidas sea menor a 0 osea
        // cuando el jugador pierda todas las vidas lanza un mensaje de gameOverFondo.
        if (porcentajeDeDaño >= 100) {
            //todo, pintamos el fondo del gameOver
            canvas.drawRect(new Rect(0, 0, (ancho), (alto)), gameOverFondo);
            movimientoRestringido = true;
            //todo, pintamos el logo del gameOver

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
            //en caso de que el jugador no haya perdido todas sus vidas.
            //todo Fondo
            rectFondo = new RectF(0, 0, 0 + ancho, 0 + alto);


            //todo Escudos
            if(escudosActivos){

                //todo Pintamos los escudos
                rectEscudo1 = new RectF((posEscudo1X - radio),(posEscudo1Y-radio),(posEscudo1X+radio),(posEscudo1Y+radio));
                rectEscudo2 = new RectF((posEscudo2X - radio),(posEscudo2Y-radio),(posEscudo2X+radio),(posEscudo2Y+radio));
                objEscudo.setRectEscudo(rectEscudo1);
                objEscudo2.setRectEscudo(rectEscudo2);
                objEscudo.draw(canvas);
                objEscudo2.draw(canvas);

                //todo Logica de intersección con los escudos:

                //todo Logica de nave con escudo
                if(RectF.intersects(rectNave,rectEscudo1)||RectF.intersects(rectNave,rectEscudo2)){
                    traspasaEscudo = true;
                }else{traspasaEscudo = false;}
                //todo Logica de proyectil con Escudo
                if(RectF.intersects(rectFProyectil,rectEscudo1)||RectF.intersects(rectFProyectil,rectEscudo2)) {
                    unDisparoAlaVez = true;
                }
                //todo Pintamos los marcianos
                if(aparecenMarcianos){
                    rectMarciano = new RectF((posMarcianoX - radio),(posMarcianoY - radio),(posMarcianoX + radio),(posMarcianoY + radio));
                    objMarciano.setRectMarciano(rectMarciano);
                    objMarciano.draw(canvas);
                }else if(explotaMarciano){
                    //todo Explotamos los marcianos
                    objAsteroide2.explotaAsteroide(canvas,posMarcianoX,posMarcianoY);
                }else if(explotaEscudo){
                    //todo Explotamos los Escudos
                    //Utilizamos estos metodos para la explosión
                    //los cuales funcionan bien. Habria que encapsularlos
                    //En una clase "Explosion" si da tiempo
                    objAsteroide2.explotaAsteroide(canvas,posEscudo2X,posEscudo2Y);
                    objAsteroide.explotaAsteroide(canvas,posEscudo1X,posEscudo2Y);
                    //todo Explotamos Nave y Marciano
                }else if(colisionNaveMarciano){
                    objAsteroide2.explotaAsteroide(canvas,posNaveX,posNaveY);
                    objAsteroide.explotaAsteroide(canvas,posMarcianoX,posMarcianoY);
                }

            }



            //todo Nave
            //Pintamos la nave en la misma posición del rectNave
            //el rectNave coge su posición del metodo onTouchEvent
            //setRectNave actualiza la posición de la nave
            //segun el rectNave
            rectNave = new RectF((posNaveX - radio), (posNaveY - radio), (posNaveX + radio), (posNaveY + radio));
            objNave.setRectNave(rectNave);
            objNave.draw(canvas);


            if(!disparoRestringido){
                //todo Proyectil
                rectFProyectil = new RectF((posProyectilX - radio), (posProyectilY - radio * 2), (posProyectilX + radio), (posProyectilY - radio));
                objProyectil.setRectProyectil(rectFProyectil);
                objProyectil.draw(canvas);

                if(posProyectilY<0){
                    unDisparoAlaVez = true;
                }


            }

            //todo INICIO DE LOGICA DE LOS ASTEROIDES
                if(explotaAsteroide1 ==false){
                    //Establece el rectF de los asteroides
                    rectAsteroide = new RectF((posAsteroideX - radio), (posAsteroideY - radio), (posAsteroideX + radio), (posAsteroideY + radio));
                    //Pone los asteriodes arriba cuando llegan abajo de la pantalla
                    if (posAsteroideY > alto) {
                        puntuacion++;
                        posAsteroideY = 0;
                        posAsteroideX = random.nextInt(ancho);
                    }
                    //Pinta los asterioides
                    objAsteroide.setRectAsteroide(rectAsteroide);
                    objAsteroide.draw(canvas);

                    //Calculamos las intersecciones entre los objetos
                    if(RectF.intersects(rectFProyectil, rectAsteroide)){
                        unDisparoAlaVez = true;
                    }

                }else{
                    //todo, Explota los asteriodes una vez ha pasado el tiempo indicado en el hilo principal
                    objAsteroide.explotaAsteroide(canvas,posAsteroideX,posAsteroideY);
                }

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
                //todo, Explota los asteriodes una vez ha pasado el tiempo indicado en
                //el hilo principal
                objAsteroide2.explotaAsteroide(canvas,posAsteroide2X,posAsteroide2Y);
            }


            //todo FIN DE LOGICA DE LOS ASTERIODES

            //todo, Pintamos por pantalla la puntuación y el numero de vidas del jugador

            canvas.drawText("Puntuación: "+puntuacion.toString(), 350, 150, Puntuacion);
            canvas.drawText("Daño recibido: "+Integer.toString(porcentajeDeDaño)+"%", 600, 150, Vidas);

        }

        if(win == true){
            movimientoRestringido = true;
            //todo, pintamos el logo del gameOver
            //todo, pintamos el fondo del gameOver
            canvas.drawRect(new Rect(0, 0, (ancho), (alto)), winFondo);

            //Establecemos el ancho del logo del Bitmap GameOver
            int anchoBtmpWin = bitmapWin.getWidth() - 512;
            int altoBtmpWin= bitmapWin.getHeight() - 512;

            //Obtenemos la altura y lo ancho de la pantalla de nuestro dispositivo.
            int alturaDePantalla = getResources().getDisplayMetrics().widthPixels;
            int anchoDePantalla = getResources().getDisplayMetrics().heightPixels;

            //Establecemos las coordenadas de la izquierda y la parte superior
            //para colocar nuestroRecFGameOver centrado en la pantalla
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