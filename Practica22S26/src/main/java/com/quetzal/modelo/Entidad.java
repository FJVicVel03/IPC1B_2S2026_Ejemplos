package com.quetzal.modelo;

import java.awt.Rectangle;

/**
 * Clase base para todas las entidades espaciales (POO - Herencia).
 */
public abstract class Entidad {
    protected int x, y;
    protected int ancho, alto;
    protected boolean viva = true;

    public Entidad(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public Rectangle getLimites() {
        return new Rectangle(x, y, ancho, alto);
    }

    public boolean colisionaCon(Entidad otra) {
        return this.viva && otra.isViva() && this.getLimites().intersects(otra.getLimites());
    }

    // Getters y Setters básicos
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public boolean isViva() { return viva; }
    public void setViva(boolean viva) { this.viva = viva; }
}
