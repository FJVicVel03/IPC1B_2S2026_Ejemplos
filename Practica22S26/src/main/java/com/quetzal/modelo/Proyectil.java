package com.quetzal.modelo;

/**
 * Proyectil disparado por la nave (+X).
 */
public class Proyectil extends Entidad {
    private final int velocidad = 12;

    public Proyectil(int x, int y) {
        super(x, y, 14, 6);
    }

    public void avanzar() {
        this.x += velocidad;
    }
}
