package com.quetzal.controlador;

import com.quetzal.hilos.GeneradorEnemigos;
import com.quetzal.hilos.HiloProyectil;
import com.quetzal.modelo.Enemigo;
import com.quetzal.modelo.Nave;
import com.quetzal.modelo.Proyectil;
import com.quetzal.vista.VentanaJuego;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Controlador principal (C en MVC).
 * Conecta el Modelo con la Vista y administra los hilos del juego.
 */
public class JuegoController {
    private final VentanaJuego vista;
    private Nave nave;
    private final List<Proyectil> proyectiles = new CopyOnWriteArrayList<>();
    private final List<Enemigo> enemigos = new CopyOnWriteArrayList<>();

    private boolean jugando = false;
    private int vidas = 3;
    private int puntos = 0;
    private GeneradorEnemigos generador;

    public JuegoController(VentanaJuego vista) {
        this.vista = vista;
        this.nave = new Nave(50, 250, 9, 1000);
    }

    public void iniciarJuego(String dificultad) {
        // Limpiar entidades previas
        proyectiles.clear();
        enemigos.clear();

        // Configurar estado inicial
        vidas = 3;
        puntos = 0;
        jugando = true;

        nave = new Nave(50, 250, 9, 1000);
        nave.aplicarDificultad(dificultad);

        vista.actualizarHUD(vidas, puntos);

        // Iniciar hilo generador de enemigos
        if (generador != null && generador.isAlive()) {
            generador.interrupt();
        }
        generador = new GeneradorEnemigos(this);
        generador.start();

        vista.repintarJuego();
    }

    public void procesarTecla(int codigo) {
        if (!jugando) return;

        // Movimiento de la nave
        if (codigo == KeyEvent.VK_UP || codigo == KeyEvent.VK_W) nave.mover(0, -1, 800, 520);
        if (codigo == KeyEvent.VK_DOWN || codigo == KeyEvent.VK_S) nave.mover(0, 1, 800, 520);
        if (codigo == KeyEvent.VK_LEFT || codigo == KeyEvent.VK_A) nave.mover(-1, 0, 800, 520);
        if (codigo == KeyEvent.VK_RIGHT || codigo == KeyEvent.VK_D) nave.mover(1, 0, 800, 520);

        // Disparo con barra espaciadora
        if (codigo == KeyEvent.VK_SPACE && nave.puedeDisparar()) {
            nave.registrarDisparo();
            Proyectil p = new Proyectil(nave.getX() + nave.getAncho(), nave.getY() + nave.getAlto() / 2 - 3);
            proyectiles.add(p);
            new HiloProyectil(p, this).start();
        }

        vista.repintarJuego();
    }

    public synchronized void perderVida() {
        if (vidas > 0) {
            vidas--;
            if (vidas <= 0) {
                jugando = false;
            }
            vista.actualizarHUD(vidas, puntos);
            vista.repintarJuego();
        }
    }

    public synchronized void sumarPuntos(int pts) {
        puntos += pts;
        vista.actualizarHUD(vidas, puntos);
        vista.repintarJuego();
    }

    public void repintar() {
        vista.repintarJuego();
    }

    public void agregarEnemigo(Enemigo e) { enemigos.add(e); }
    public void removerEnemigo(Enemigo e) { enemigos.remove(e); }
    public void removerProyectil(Proyectil p) { proyectiles.remove(p); }

    public Nave getNave() { return nave; }
    public List<Proyectil> getProyectiles() { return proyectiles; }
    public List<Enemigo> getEnemigos() { return enemigos; }
    public boolean isJugando() { return jugando; }
}
