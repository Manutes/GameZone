package com.example.gamezone.ui.games.marcianosGame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Movie;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.example.gamezone.R;
import com.example.gamezone.ui.games.marcianosGame.Juego;
import com.example.gamezone.ui.games.marcianosGame.objetosJuego.fondoJuego;

import java.util.Timer;
import java.util.TimerTask;
/*
todo, BUGS
1 - (RESUELTO) Los asteroides no aparecen al cargar por primera vez el juego sino la segunda vez que los cargas.
                El boolean explosión asteroide lo pongo en false al comenzar el metodo onCreate
2 - Borrar las imagenes de drawable innecesarias y metodos innecesarios
3 - (HECHO A MEDIAS) -> Revisar porque las explosiones duran tan poco. Resuelto en Asteroides

                        OJO revisar el condicional de la logica
                        de los asteroides en la clase Juego
                        una vez revisada encapsularla en el objeto Asteroide para que se cumpla
                        en ambos objetos sin tener dos condicionales grandes.

                        Una vez hecho eso aplicamos la misma logica pero con el resto
                        de objetos. Excepto la puta nave, la nave la dejas
                        tranquila no seas puto perfeccionista que no acabas nunca joder.

  3.1 - (ENCAPSULAR)uando los asteroides explotan cuando chocan con la nave
  3.2 - (FALTA) Cuando los marcianos explotan cuando mueren
  3.3 - (FALTA) Cuando los marcianos chocan con la base

4 - Revisar porque en el gameOver cuando los objetos explotan porque no desaparecen. Deben
    desaparecer primero y luego explotar.
5 - Te deja traspasar el escudo por los laterales, imposibilitar eso.
6 - Resolver el hecho de que cuando caen asteroides lo hacen juntos a veces, no pueden aparecer
7 - En la misma posición


todo, Tareas pendientes
1 - Programar o mas asteroides y que exploten despues de 3 toques con el proyectil o deshabilitar
    los proyectiles al principio
2 - Programar nuevamente el tema de las vidas y su Game Over
3 - Programar tambien el gameOver cuando el Marciano llegue a la base
2 - Programar los sonidos correctamente
 */

    public class MarcianosMainActivity extends AppCompatActivity {
        /*
    El código crea un objeto de la clase Juego y un objeto de la clase GifImageView,
    y también define una lista llamada listaProyectiles para almacenar objetos de la clase Proyectil.
    */

        public MediaPlayer mp;
        public Juego juego;
        public fondoJuego gif;
        private Handler handler = new Handler();

        @SuppressLint({"MissingInflatedId", "WrongViewCast"})
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_marcianos);

            /*
            En el método onCreate, se establece el diseño de la actividad y
            se obtienen referencias a los elementos de la interfaz de usuario,
            como un FrameLayout y una vista de imagen GIF.
             */
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
            gif = findViewById(R.id.gif_View);
            juego = findViewById(R.id.Pantalla);

            /*
            Se agrega un observador de layout para obtener e inicializar las variables del juego.
             */
            setMediaPlayer();
            ViewTreeObserver obs = juego.getViewTreeObserver();
            obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //Te inicializa el tiempo inicial cuando empieza el juego.
                    juego.tiempoInicial = System.currentTimeMillis();
                    juego.ancho = juego.getWidth();
                    juego.alto = juego.getHeight();
                    //Aqui se establece donde estará la posición de la nave
                    juego.posNaveX = juego.ancho / 2;
                    //Indicamos que nuestra nave se establezca en la posición 50 osea en la
                    //parte inferior de la pantalla
                    juego.posNaveY = juego.alto - 50;
                    juego.radio = 100;
                    //Aqui se establece el punto en el que se pinta el asteroide
                    //para que seguidamente caiga hacia abajo
                    juego.posAsteroideY = 0; //cambiar a 0
                    juego.posAsteroide2Y = -300; //cambiar a -100

                    //todo Boleans de Aparicion de personajes
                    juego.explotaAsteroide1 = false;
                    juego.explotaAsteroide2 = false;
                    juego.explosionPintada = false;
                    juego.escudosActivos = false;
                    juego.aparecenMarcianos = false;
                    juego.disparoRestringido = true;

                    //todo Posicion de los Escudos
                    juego.posEscudo1X = 300;
                    juego.posEscudo1Y = 0;
                    juego.posEscudo2X = 800;
                    juego.win = false;

                    //todo boolean traspasa
                    juego.traspasaEscudo = false;

                    //todo Posicion de los Marcianos
                    juego.posMarcianoX = 0;
                    juego.posMarcianoY = 100;

                    //todo Movie de Explosion
                    juego.movieExplosion  = Movie.decodeStream(getResources().openRawResource(R.raw.explosion2)); // Reemplaza "explosion" con el nombre de tu archivo GIF
                    //Estas dos lineas solo las hacemos para saber cuanto dura el gif
                    juego.duracionTotal = juego.movieExplosion.duration();

                }
            }   );


            /*
            Se crea un temporizador que ejecuta una tarea cada 9 milisegundos
             */
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        /*
                        Dentro de la tarea del temporizador, se ejecuta el código en el método run del objeto Runnable,
                        que se ejecuta en el hilo principal de la interfaz de usuario.
                         */
                        public void run() {
                            //todo Tiempo actual del juego.
                            long tiempoActual = System.currentTimeMillis();
                            //todo Calcula el tiempo transcurrido mediante el resto de tiempoActual - Tiempo inicial
                            long tiempoTranscurrido = tiempoActual - juego.tiempoInicial;
                            //todo Tiempo de los asteroides y movimiento
                            //Si el tiempo que ha transcurrido es mayor a 10 segundos entonces
                            //los asteroides bajan.
                            if (tiempoTranscurrido <= 10000) { // 10 segundos
                                if(!juego.explotaAsteroide1 || !juego.explotaAsteroide2){
                                    juego.posAsteroideY += 20;
                                    //todo Con este handler conseguimos que
                                    //un asteroide empieze a caer un segundo
                                    //despues del otro y a una velocidad reducida
                                    //para darle mas vida al juego
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Establecer explota como falso después de 2 segundos
                                            juego.posAsteroide2Y += 20;
                                        }
                                    }, 1000); // 1000 milisegundos = 1 segundo
                                }
                            } else{
                                juego.disparoRestringido = false;
                                juego.explotaAsteroide1 = true;
                                juego.explotaAsteroide2 = true;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Establecer explota como falso después de 2 segundos
                                        juego.posAsteroideY = -500;
                                        juego.posAsteroide2Y = -500;
                                        juego.explotaAsteroide1 = false;
                                        juego.explotaAsteroide2 = false;

                                    }
                                }, 500); // 2000 milisegundos = 2 segundos

                                juego.escudosActivos = true;
                            }

                            //todo, Logica de interseccion entre nave y asteroide 1
                            if(RectF.intersects(juego.rectNave,juego.rectAsteroide)){
                                juego.explotaAsteroide1 = true;
                                juego.porcentajeDeDaño++;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Establecer explota como falso después de 2 segundos
                                        juego.posAsteroideY = -200;
                                        juego.posAsteroideX = juego.random.nextInt(juego.ancho);
                                        juego.explotaAsteroide1 = false;
                                    }
                                }, 500); // 2000 milisegundos = 2 segundos
                            }

                            //todo, Logica de interseccion entre nave  y asteroide 2
                            if(RectF.intersects(juego.rectNave,juego.rectAsteroide2)){
                                juego.explotaAsteroide2 = true;
                                juego.porcentajeDeDaño++;
                             //   juego.numeroDeVidas = juego.numeroDeVidas - 1;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Establecer explota como falso después de 2 segundos
                                        juego.posAsteroide2Y = -200;
                                        juego.posAsteroide2X = juego.random2.nextInt(juego.ancho);
                                        juego.explotaAsteroide2 = false;
                                    }
                                }, 500); // 2000 milisegundos = 2 segundos
                            }


                            //todo Movimiento de los escudos
                            if(tiempoTranscurrido > 10000 && tiempoTranscurrido <= 11300){
                                juego.posEscudo1Y += 10;
                                juego.posEscudo2Y += 10;
                            }
                            //todo Logica de movimiento de los Marcianos

                            if(!juego.explotaMarciano){
                                if(tiempoTranscurrido > 11300 && tiempoTranscurrido <= 12300 ){
                                    juego.aparecenMarcianos = true;
                                    juego.posMarcianoX +=10;
                                }else if(tiempoTranscurrido > 12300 && tiempoTranscurrido <= 12400){
                                    juego.posMarcianoY +=10;
                                }else if(tiempoTranscurrido > 12400 && tiempoTranscurrido <= 13400){
                                    juego.posMarcianoX -=10;
                                }else if(tiempoTranscurrido > 13400 && tiempoTranscurrido <= 13500 ){
                                    juego.posMarcianoY += 10;
                                }else if(tiempoTranscurrido > 13500 && tiempoTranscurrido <= 14500){
                                    juego.posMarcianoX += 10;
                                }else if(tiempoTranscurrido > 14500 && tiempoTranscurrido <= 14600){
                                    juego.posMarcianoY +=10;
                                }else if(tiempoTranscurrido > 14600 && tiempoTranscurrido <= 15600){
                                    juego.posMarcianoX -= 10;
                                }else if(tiempoTranscurrido > 15600 && tiempoTranscurrido <= 15700){
                                    juego.posMarcianoY +=10;
                                }else if(tiempoTranscurrido > 15700 && tiempoTranscurrido <= 16700){
                                    juego.posMarcianoX += 10;
                                }else if(tiempoTranscurrido > 16700 && tiempoTranscurrido <= 16800){
                                    juego.posMarcianoY += 10;
                                }else if(tiempoTranscurrido > 16800 && tiempoTranscurrido <= 17800){
                                    juego.posMarcianoX -= 10;
                                }else if(tiempoTranscurrido > 17800 && tiempoTranscurrido <= 17900){
                                    juego.posMarcianoY += 10;
                                }else if(tiempoTranscurrido > 17900 && tiempoTranscurrido <= 18900){
                                    juego.posMarcianoX += 10;
                                }else if(tiempoTranscurrido > 18900 && tiempoTranscurrido <= 19000){
                                    juego.posMarcianoY += 10;
                                }else if(tiempoTranscurrido > 19000 && tiempoTranscurrido <= 20000) {
                                    juego.posMarcianoX -= 10;
                                }else if(tiempoTranscurrido > 20000 && tiempoTranscurrido <= 20100) {
                                    juego.posMarcianoY += 10;
                                }else if(tiempoTranscurrido > 20100 && tiempoTranscurrido <= 21100) {
                                    juego.posMarcianoX += 10;
                                }else if(tiempoTranscurrido > 21100 && tiempoTranscurrido <= 21200) {
                                    juego.posMarcianoY += 10;
                                }else if(tiempoTranscurrido > 21200 && tiempoTranscurrido <= 22200) {
                                    juego.posMarcianoX -= 10;
                                }else if(tiempoTranscurrido > 22200 && tiempoTranscurrido <= 22300) {
                                    juego.posMarcianoY += 10;
                                }else if(tiempoTranscurrido > 22300 && tiempoTranscurrido <= 23300) {
                                    juego.posMarcianoX += 10;
                                }else if(tiempoTranscurrido > 23300 && tiempoTranscurrido <= 23400) {
                                    juego.posMarcianoY += 10;
                                }else if(tiempoTranscurrido > 23400 && tiempoTranscurrido <= 23500) {
                                    juego.posMarcianoX -= 10;
                                }else if(tiempoTranscurrido > 23500 && tiempoTranscurrido <= 23600) {
                                    juego.posMarcianoY += 10;
                                }else if(tiempoTranscurrido > 23600 && tiempoTranscurrido <= 24600) {
                                    juego.posMarcianoX += 10;
                                }else if(tiempoTranscurrido > 24600 && tiempoTranscurrido <= 24700) {
                                    juego.posMarcianoY += 10;
                                }else if(tiempoTranscurrido > 24700 && tiempoTranscurrido <= 25700) {
                                    juego.posMarcianoX -= 10;
                                }
                                //todo FIN de la Logica de movimiento de los Marcianos
                            }


                            //todo Logica de intersección entre el proyectil y el Marciano
                            if(RectF.intersects(juego.rectFProyectil,juego.rectMarciano)){ //Esto hay que ponerlo en el hilo principal
                                juego.aparecenMarcianos = false;
                                juego.explotaMarciano = true;
                                //juego.puntuacion++;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(!juego.win){
                                            juego.puntuacion = juego.puntuacion + 50;
                                        }

                                        juego.posMarcianoY = -500;
                                        juego.win = true;
                                    }
                                }, 500); // 2000 milisegundos = 2 segundos
                            }

                            //todo logica de intersección entre los marcianos y el escudo = Game Over
                            if(RectF.intersects(juego.rectMarciano,juego.rectEscudo2)){
                                juego.aparecenMarcianos = false;
                                juego.explotaEscudo = true;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Establecer explota como falso después de 2 segundos
                                        juego.posEscudo1Y = -10000;
                                        juego.posEscudo2Y = -10000;
                                        juego.porcentajeDeDaño = 100;
                                    }
                                }, 500); // 2000 milisegundos = 2 segundos
                            }

                            //todo Logica de intersección entre Nave y Marciano = Game Over
                            if(RectF.intersects(juego.rectNave,juego.rectMarciano)){
                                juego.movimientoRestringido = true;
                                juego.aparecenMarcianos = false;
                                juego.colisionNaveMarciano = true;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Establecer explota como falso después de 2 segundos

                                        juego.posMarcianoY = -10000;
                                        juego.posMarcianoX = -10000;
                                        juego.posNaveX = -10000;
                                        juego.posNaveY = -10000;
                                        juego.porcentajeDeDaño = 100;
                                    }
                                }, 500);
                            }

                            //todo Trayectoria del proyectil
                            if(juego.tocaPantalla == true && juego.unDisparoAlaVez==true){
                                juego.posProyectilX = juego.posNaveX;
                                juego.posProyectilY = juego.posNaveY;
                                juego.unDisparoAlaVez=false;
                            }else{juego.posProyectilY -=10;}

                            //todo Invalidate
                            juego.invalidate();
                        }
                    });
                }
            }, 0, 9); //El period lo podrias sustituir por una variable que sea la velocidad. Es un numero Entero.
                                    //Una variable de tipo int
        }

        private void setMediaPlayer() {
            if (mp != null) {
                mp.release();
            }
            mp = MediaPlayer.create(this, R.raw.bandasonora);
            mp.start();
            mp.setOnCompletionListener(MediaPlayer::start);
        }


        @Override
        protected void onPause() {
            super.onPause();
            if (mp != null && mp.isPlaying()) {
                mp.pause();
            }
        }

        @Override
        protected void onResume() {
            super.onResume();
            setMediaPlayer();
        }

        @Override
        protected void onRestart() {
            super.onRestart();
            setMediaPlayer();
        }

        @Override
        protected void onStop() {
            super.onStop();
            if (mp != null) {
                mp.release();
                mp = null;
            }
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (mp != null) {
                mp.release();
                mp = null;
            }
        }

    }