package com.quetzal.hilos;

import com.quetzal.controlador.JuegoController;
import com.quetzal.modelo.Enemigo;

/**
 * Hilo independiente que mueve un enemigo hacia la izquierda (-X).
 */
public class HiloEnemigo extends Thread {
    private final Enemigo enemigo;
    private final JuegoController controller;

    public HiloEnemigo(Enemigo enemigo, JuegoController controller) {
        this.enemigo = enemigo;
        this.controller = controller;
    }

    @Override
    public void run() {
        while (controller.isJugando() && enemigo.isViva()) {
            enemigo.avanzar();

            // Salir de la pantalla izquierda
            if (enemigo.getX() < -40) {
                enemigo.setViva(false);
                break;
            }

            // Colisión con la nave del jugador
            if (enemigo.colisionaCon(controller.getNave())) {
                enemigo.setViva(false);
                controller.perderVida();
                break;
            }

            controller.repintar();

            try {
                Thread.sleep(35);
            } catch (InterruptedException e) {
                break;
            }
        }
        controller.removerEnemigo(enemigo);
    }
}
