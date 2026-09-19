package com.quetzal.modelo;

/**
 * Nave del jugador. Controla movimiento y cadencia de disparo según dificultad.
 */
public class Nave extends Entidad {
    private int velocidad;
    private long tiempoRecargaMs;
    private long ultimoDisparoMs;

    public Nave(int x, int y, int velocidad, long tiempoRecargaMs) {
        super(x, y, 40, 30);
        this.velocidad = velocidad;
        this.tiempoRecargaMs = tiempoRecargaMs;
        this.ultimoDisparoMs = 0;
    }

    public void mover(int dx, int dy, int maxAncho, int maxAlto) {
        this.x += dx * velocidad;
        this.y += dy * velocidad;

        // Limitar dentro de la pantalla (área izquierda)
        if (this.x < 10) this.x = 10;
        if (this.x > maxAncho / 2) this.x = maxAncho / 2;
        if (this.y < 40) this.y = 40;
        if (this.y > maxAlto - 70) this.y = maxAlto - 70;
    }

    public boolean puedeDisparar() {
        return (System.currentTimeMillis() - ultimoDisparoMs) >= tiempoRecargaMs;
    }

    public void registrarDisparo() {
        this.ultimoDisparoMs = System.currentTimeMillis();
    }

    public void aplicarDificultad(String dificultad) {
        // Ajustes según tabla del enunciado:
        // Fácil: Alta velocidad (14), Disparo lento (2000 ms)
        // Normal: Velocidad media (9), Disparo medio (1000 ms)
        // Difícil: Velocidad baja (5), Disparo rápido (300 ms)
        if (dificultad.equalsIgnoreCase("Fácil")) {
            this.velocidad = 14;
            this.tiempoRecargaMs = 2000;
        } else if (dificultad.equalsIgnoreCase("Difícil")) {
            this.velocidad = 5;
            this.tiempoRecargaMs = 300;
        } else { // Normal por defecto
            this.velocidad = 9;
            this.tiempoRecargaMs = 1000;
        }
    }
}
