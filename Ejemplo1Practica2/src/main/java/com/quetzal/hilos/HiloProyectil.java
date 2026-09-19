package com.quetzal.hilos;

import com.quetzal.controlador.JuegoController;
import com.quetzal.modelo.Enemigo;
import com.quetzal.modelo.Proyectil;

public class HiloProyectil extends Thread {
    private final Proyectil proyectil;
    private final JuegoController controller;

    public HiloProyectil(Proyectil proyectil, JuegoController controller) {
        this.proyectil = proyectil;
        this.controller = controller;
    }

    @Override
    public void run() {
        while(controller.isJugando() && proyectil.isViva()) {
            proyectil.avanzar();

            //si el proyectil no colisiona, va a la derecha hasta que salga de la pantalla
            if (proyectil.getX() > 800) {
                proyectil.setViva(false);
                break;
            }

            //Colisiona con un enemigo
            for (Enemigo enemigo : controller.getEnemigos()) {
                if (proyectil.colisionaCon(enemigo)) {
                    proyectil.setViva(false);
                    enemigo.setViva(false);
                    controller.sumarPuntos(enemigo.getPuntos());
                    break;
                }
            }

            controller.repintar();

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        controller.removerProyectil(proyectil);
    }
}
