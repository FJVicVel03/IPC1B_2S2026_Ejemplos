package com.quetzal.modelo;

public class Nave extends Entidad{

    private int velocidad;
    private long tiempoRecargaMs;
    private long ultimoDisparoMs;

    public Nave(int x, int y, int velocidad, long tiempoRecargaMs) {
        super(x, y, 36, 26);
        this.velocidad = velocidad;
        this.tiempoRecargaMs = tiempoRecargaMs;
        this.ultimoDisparoMs = 0;
    }

    public void mover(int dx, int dy, int maxAncho, int maxAlto)
    {
        this.x += dx * velocidad;
        this.y += dy * velocidad;

        // Limitar la posición de la nave dentro de los límites del juego
        if(this.x < 10) this.x = 10;
        if(this.x > maxAncho/2) this.x = maxAncho/2;
        if(this.y < 40) this.y = 40;
        if(this.y > maxAlto - 80) this.y = maxAlto - 80;
    }

    public boolean puedeDisparar() {
        return(System.currentTimeMillis() - ultimoDisparoMs) >= tiempoRecargaMs;
    }
    public void registrarDisparo()
    {
        this.ultimoDisparoMs = System.currentTimeMillis();
    }
    public void aplicarDificultad(String dificultad)
    {
        switch (dificultad.toUpperCase()) {
            case "FACIL" -> {
                this.velocidad = 14;
                this.tiempoRecargaMs = 2000;
            }
            case "NORMAL" -> {
                this.velocidad = 5;
                this.tiempoRecargaMs = 300;
            }
            case "DIFICIL" -> {
                this.velocidad = 9;
                this.tiempoRecargaMs = 1000;
            }
            default -> {
                this.velocidad = 9;
                this.tiempoRecargaMs = 1000;
            }
        }
    }
}
