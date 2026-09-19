package com.quetzal.modelo;

/**
 * Nave enemiga que se desplaza hacia la izquierda (-X).
 */
public class Enemigo extends Entidad {
    private final int velocidad;
    private final int puntos = 20;

    public Enemigo(int x, int y, int velocidad) {
        super(x, y, 36, 26);
        this.velocidad = velocidad;
    }

    public void avanzar() {
        this.x -= velocidad;
    }

    public int getPuntos() {
        return puntos;
    }
}
