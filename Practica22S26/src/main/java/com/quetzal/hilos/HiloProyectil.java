package com.quetzal.hilos;

import com.quetzal.controlador.JuegoController;
import com.quetzal.modelo.Enemigo;
import com.quetzal.modelo.Proyectil;

/**
 * Hilo independiente que mueve un proyectil hacia la derecha (+X).
 */
public class HiloProyectil extends Thread {
    private final Proyectil proyectil;
    private final JuegoController controller;

    public HiloProyectil(Proyectil proyectil, JuegoController controller) {
        this.proyectil = proyectil;
        this.controller = controller;
    }

    @Override
    public void run() {
        while (controller.isJugando() && proyectil.isViva()) {
            proyectil.avanzar();

            // Salir de la pantalla
            if (proyectil.getX() > 800) {
                proyectil.setViva(false);
                break;
            }

            // Colisión con enemigos
            for (Enemigo e : controller.getEnemigos()) {
                if (e.isViva() && proyectil.colisionaCon(e)) {
                    proyectil.setViva(false);
                    e.setViva(false);
                    controller.sumarPuntos(e.getPuntos());
                    break;
                }
            }

            controller.repintar();

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }
        }
        controller.removerProyectil(proyectil);
    }
}
