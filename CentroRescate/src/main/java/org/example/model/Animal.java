package org.example.model;

/**
 * Representa un animal rescatado en el refugio.
 */
public class Animal {
    private String id;
    private String nombre;
    private String especie; // "PERRO" o "GATO"
    private int edadEstimada;
    private String estadoClinico; // "INGRESADO", "EVALUADO", "DISPONIBLE", "ADOPTADO"
    private boolean activo; // Para eliminación lógica

    public Animal(String id, String nombre, String especie, int edadEstimada, String estadoClinico, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.edadEstimada = edadEstimada;
        this.estadoClinico = estadoClinico;
        this.activo = activo;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getEdadEstimada() {
        return edadEstimada;
    }

    public void setEdadEstimada(int edadEstimada) {
        this.edadEstimada = edadEstimada;
    }

    public String getEstadoClinico() {
        return estadoClinico;
    }

    public void setEstadoClinico(String estadoClinico) {
        this.estadoClinico = estadoClinico;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
