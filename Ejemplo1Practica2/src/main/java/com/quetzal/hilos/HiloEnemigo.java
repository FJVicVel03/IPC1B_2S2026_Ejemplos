package com.quetzal.hilos;

import com.quetzal.controlador.JuegoController;
import com.quetzal.modelo.Enemigo;

public class HiloEnemigo extends Thread{
    private final Enemigo enemigo;
    private final JuegoController controller;

    public HiloEnemigo(Enemigo enemigo, JuegoController controller) {
        this.enemigo = enemigo;
        this.controller = controller;
    }

    @Override
    public void run() {
        while(controller.isJugando() && enemigo.isViva()) {
            enemigo.avanzar();

            //si el enemigo no colisiona, va a la izquierda hasta que salga de la pantalla
            if (enemigo.getX() < -40)
            {
                enemigo.setViva(false);
                break;
            }

            //Colisiona con la nava del jugador
            if(enemigo.colisionaCon(controller.getNave()))
            {
                enemigo.setViva(false);
                controller.perderVida();
                break;
            }

            controller.repintar();

            try
            {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        controller.removerEnemigo(enemigo);
    }
}
