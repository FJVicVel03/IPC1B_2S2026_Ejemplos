package com.quetzal.controlador;

import com.quetzal.hilos.GeneradorEnemigos;
import com.quetzal.hilos.HiloProyectil;
import com.quetzal.modelo.Enemigo;
import com.quetzal.modelo.Nave;
import com.quetzal.modelo.Proyectil;
import com.quetzal.vista.VentanaJuego;

import java.awt.event.KeyEvent;

public class JuegoController {

    private final VentanaJuego vista;
    private Nave nave;
    //lista de proyectiles almacenados en un array
    private final Proyectil[] proyectiles = new Proyectil[100];
    private final Enemigo[] enemigos = new Enemigo[100];

    private boolean jugando = false;
    private int vidas = 3;
    private int puntos = 0;
    private GeneradorEnemigos generadorEnemigos;

    public JuegoController(VentanaJuego vista) {
        this.vista = vista;
        this.nave = new Nave(50, 250, 9, 1000);
    }

    public void iniciarJuego(String dificultad) {
        //Limipiar entidades previas
        for (int i = 0; i < proyectiles.length; i++) {
            proyectiles[i] = null;
        }
        for (int i = 0; i < enemigos.length; i++) {
            enemigos[i] = null;
        }

        vidas = 3;
        puntos = 0;
        jugando = true;

        nave = new Nave(50, 250, 9, 1000);
        nave.aplicarDificultad(dificultad);

        vista.actualizarHUD(vidas, puntos);
        //Iniciamos el hilo generador de enemigos
        if (generadorEnemigos != null && generadorEnemigos.isAlive()) {
            generadorEnemigos.interrupt();
        }
        generadorEnemigos = new GeneradorEnemigos(this);
        generadorEnemigos.start();

        vista.repintarJuego();
    }

        public void procesarTecla(int codigo)
        {
            if(!jugando) return;

            if(codigo == KeyEvent.VK_UP || codigo == KeyEvent.VK_W)
            {
                nave.mover(0, -1, 800, 500);
            }
            else if(codigo == KeyEvent.VK_DOWN || codigo == KeyEvent.VK_S)
            {
                nave.mover(0, 1, 800, 500);
            }
            else if(codigo == KeyEvent.VK_LEFT || codigo == KeyEvent.VK_A)
            {
                nave.mover(-1, 0, 800, 500);
            }
            else if(codigo == KeyEvent.VK_RIGHT || codigo == KeyEvent.VK_D)
            {
                nave.mover(1, 0, 800, 500);
            }

            else if(codigo == KeyEvent.VK_SPACE && nave.puedeDisparar())
            {
                nave.registrarDisparo();
                Proyectil p = new Proyectil(nave.getX() + nave.getAncho(), nave.getY() + nave.getAlto()/2 - 3);
                //agregar el proyectil al array de proyectiles
                for(int i = 0; i < proyectiles.length; i++)
                {
                    if(proyectiles[i] == null)
                    {
                        proyectiles[i] = p;
                        break;
                    }
                }
                new HiloProyectil(p, this).start();
            }

            vista.repintarJuego();
        }

        public synchronized  void perderVida()
        {
            if(vidas > 0)
            {
                vidas--;
                if(vidas <= 0)
                {
                    jugando = false;
                }
                vista.actualizarHUD(vidas, puntos);
                vista.repintarJuego();

            }


        }

        public synchronized  void sumarPuntos(int pts)
        {
            puntos += pts;
            vista.actualizarHUD(vidas, puntos);
            vista.repintarJuego();
        }

        public void repintar()
        {
            vista.repintarJuego();
        }

        public void agregarEnemigo(Enemigo e) {
            //agregar el enemigo al array de enemigos
            for (int i = 0; i < enemigos.length; i++) {
                if (enemigos[i] == null) {
                    enemigos[i] = e;
                    break;
                }
            }
        }
        public void removerEnemigo(Enemigo e) {
            //remover el enemigo del array de enemigos
            for (int i = 0; i < enemigos.length; i++) {
                if (enemigos[i] == e) {
                    enemigos[i] = null;
                    break;
                }
            }
        }
        public void removerProyectil(Proyectil p) {
            //remover el proyectil del array de proyectiles
            for (int i = 0; i < proyectiles.length; i++) {
                if (proyectiles[i] == p) {
                    proyectiles[i] = null;
                    break;
                }
            }
        }


        public Nave getNave() { return nave; }

    public Proyectil[] getProyectiles() { return proyectiles; }
    public Enemigo[] getEnemigos() { return enemigos; }
    public boolean isJugando() { return jugando; }

}

