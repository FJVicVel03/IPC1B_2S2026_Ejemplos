package org.example.model;

/**
 * Adaptador legado para mantener compatibilidad.
 * Se recomienda usar AnimalModel como la capa del Modelo en MVC.
 */
@Deprecated
public class AnimalRepository {
    private final AnimalModel model = new AnimalModel();

    public boolean save(Animal animal) {
       return model.save(animal);
    }

    public Animal findById(String id) {
       return model.findById(id);
    }

    public Animal[] findByFilters(String query, String especie, String estado) {
       return model.findByFilters(query, especie, estado);
    }

    public boolean deleteById(String id) {
       return model.deleteById(id);
    }

    public Animal[] findAll() {
       return model.findAll();
    }

    public int getTotalAnimales() {
       return model.getTotalAnimales();
    }

    public static void setAnimales(Animal[] nuevosAnimales, int cantidad) {
       AnimalModel.setAnimales(nuevosAnimales, cantidad);
    }
}
