package com.quetzal.modelo;

import java.awt.*;

public class Entidad {
    protected int x, y;
    protected int ancho, alto;
    protected boolean viva = true;


    public Entidad(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }


    public Rectangle getLimites()
    {
        return new Rectangle(x, y, ancho, alto);
    }

    public boolean colisionaCon(Entidad otra)
    {
        return this.viva && otra.viva && this.getLimites().intersects(otra.getLimites());
    }

    // Getters y setters
    public int getX(){return x;}
    public void setX(int x){this.x = x;}
    public int getY(){return y;}
    public void setY(int y){this.y = y;}
    public int getAncho(){return ancho;}
    public void setAncho(int ancho){this.ancho = ancho;}
    public int getAlto(){return alto;}
    public void setAlto(int alto){this.alto = alto;}
    public boolean isViva(){return viva;}
    public void setViva(boolean viva){this.viva = viva;}
}
