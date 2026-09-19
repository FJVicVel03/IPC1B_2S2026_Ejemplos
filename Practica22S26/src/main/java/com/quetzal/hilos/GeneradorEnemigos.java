package com.quetzal.hilos;

import com.quetzal.controlador.JuegoController;
import com.quetzal.modelo.Enemigo;
import java.util.Random;

/**
 * Hilo continuo que genera enemigos en el borde derecho (X máximo).
 */
public class GeneradorEnemigos extends Thread {
    private final JuegoController controller;
    private final Random random = new Random();

    public GeneradorEnemigos(JuegoController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while (controller.isJugando()) {
            // Posición Y aleatoria entre 50 y 480
            int y = 50 + random.nextInt(430);
            int velocidad = 4 + random.nextInt(4);

            Enemigo nuevo = new Enemigo(800, y, velocidad);
            controller.agregarEnemigo(nuevo);

            // Iniciar hilo independiente para el enemigo creado
            new HiloEnemigo(nuevo, controller).start();

            // Intervalo entre spawns (1.5 segundos)
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
