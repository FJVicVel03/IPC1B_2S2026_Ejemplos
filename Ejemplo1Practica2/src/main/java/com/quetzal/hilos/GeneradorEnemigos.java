package com.quetzal.hilos;

import com.quetzal.controlador.JuegoController;
import com.quetzal.modelo.Enemigo;

import java.util.Random;

public class GeneradorEnemigos extends Thread {
    private final JuegoController controller;
    private final Random random = new Random();


    public GeneradorEnemigos(JuegoController controller) {
        this.controller = controller;
    }


    @Override
    public void run()
    {
        while(controller.isJugando())
        {
            //posición Y aleatorias entre 40 y 400
            int y = 40 + random.nextInt(360);
            int velocidad = 4 + random.nextInt(3); // velocidad entre 4 y 6

            Enemigo enemigo = new Enemigo(800, y, velocidad);
            controller.agregarEnemigo(enemigo);

            //Iniciar el hilo
            new HiloEnemigo(enemigo, controller).start();

            //spawn de enemigos cada 2 segundos
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
