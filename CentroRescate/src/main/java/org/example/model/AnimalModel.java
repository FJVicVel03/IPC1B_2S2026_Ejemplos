package org.example.model;

/**
 * Modelo del módulo de animales.
 * Encapsula el arreglo estático, la lógica de almacenamiento y las búsquedas.
 * Esta clase representa la capa del Modelo dentro del patrón MVC.
 */
public class AnimalModel {
    private static final int MAX_ANIMALES = 100;
    private static final Animal[] animales = new Animal[MAX_ANIMALES];
    private static int totalAnimales = 0;

    public boolean save(Animal animal) {
        if (totalAnimales >= MAX_ANIMALES) {
            return false;
        }
        animales[totalAnimales] = animal;
        totalAnimales++;
        return true;
    }

    public Animal findById(String id) {
        for (int i = 0; i < totalAnimales; i++) {
            if (animales[i] != null && animales[i].getId().equals(id) && animales[i].isActivo()) {
                return animales[i];
            }
        }
        return null;
    }

    /**
     * Busca animales activos por texto libre, especie y estado clínico.
     */
    public Animal[] findByFilters(String query, String especie, String estado) {
        String textoBusqueda = query == null ? "" : query.trim().toLowerCase();
        String especieFiltro = especie == null ? "" : especie.trim();
        String estadoFiltro = estado == null ? "" : estado.trim();

        Animal[] resultadoTemporal = new Animal[totalAnimales];
        int contador = 0;

        for (int i = 0; i < totalAnimales; i++) {
            Animal animal = animales[i];
            if (animal == null || !animal.isActivo()) {
                continue;
            }

            boolean coincideTexto = textoBusqueda.isEmpty()
                    || animal.getId().toLowerCase().contains(textoBusqueda)
                    || animal.getNombre().toLowerCase().contains(textoBusqueda)
                    || animal.getEspecie().toLowerCase().contains(textoBusqueda)
                    || animal.getEstadoClinico().toLowerCase().contains(textoBusqueda);

            boolean coincideEspecie = especieFiltro.isEmpty()
                    || especieFiltro.equalsIgnoreCase(animal.getEspecie());

            boolean coincideEstado = estadoFiltro.isEmpty()
                    || estadoFiltro.equalsIgnoreCase(animal.getEstadoClinico());

            if (coincideTexto && coincideEspecie && coincideEstado) {
                resultadoTemporal[contador] = animal;
                contador++;
            }
        }

        Animal[] resultado = new Animal[contador];
        System.arraycopy(resultadoTemporal, 0, resultado, 0, contador);
        return resultado;
    }

    public boolean deleteById(String id) {
        Animal animal = findById(id);
        if (animal != null) {
            animal.setActivo(false);
            return true;
        }
        return false;
    }

    public Animal[] findAll() {
        Animal[] activos = new Animal[totalAnimales];
        int contador = 0;
        for (int i = 0; i < totalAnimales; i++) {
            if (animales[i] != null && animales[i].isActivo()) {
                activos[contador] = animales[i];
                contador++;
            }
        }

        Animal[] resultado = new Animal[contador];
        System.arraycopy(activos, 0, resultado, 0, contador);
        return resultado;
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
