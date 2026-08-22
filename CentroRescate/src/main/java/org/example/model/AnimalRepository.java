package org.example.model;

/**
 * Gestor del arreglo estático para Animales (Capa Modelo - Gestor de Datos).
 * Los estudiantes deberán implementar la búsqueda por código, nombre, especie o estado,
 * así como la inserción, modificación y eliminación lógica.
 */
public class AnimalRepository {
    private static final int MAX_ANIMALES = 100;
    private static final Animal[] animales = new Animal[MAX_ANIMALES];
    private static int totalAnimales = 0;

    /**
     * Guarda un nuevo animal en el arreglo.
     * TODO: Los estudiantes deben implementar la validación de ID no duplicado.
     */
    public boolean save(Animal animal) {
        if (totalAnimales >= MAX_ANIMALES) {
            return false;
        }
        animales[totalAnimales] = animal;
        totalAnimales++;
        return true;
    }

    /**
     * Busca un animal activo por su ID/Código.
     */
    public Animal findById(String id) {
        for (int i = 0; i < totalAnimales; i++) {
            if (animales[i] != null && animales[i].getId().equals(id) && animales[i].isActivo()) {
                return animales[i];
            }
        }
        return null;
    }

    /**
     * Búsqueda por filtros sugeridos en la guía.
     * TODO: Los estudiantes completarán la lógica para buscar por nombre, especie y estado.
     */
    public Animal[] findByFilters(String query, String especie, String estado) {
        return animales;
    }

    /**
     * Elimina lógicamente un animal del sistema.
     */
    public boolean deleteById(String id) {
        Animal animal = findById(id);
        if (animal != null) {
            animal.setActivo(false); // Eliminación lógica
            return true;
        }
        return false;
    }

    public Animal[] findAll() {
        return animales;
    }

    public int getTotalAnimales() {
        return totalAnimales;
    }

    public static void setAnimales(Animal[] nuevosAnimales, int cantidad) {
        if (cantidad <= MAX_ANIMALES) {
            System.arraycopy(nuevosAnimales, 0, animales, 0, cantidad);
            totalAnimales = cantidad;
        }
    }
}
