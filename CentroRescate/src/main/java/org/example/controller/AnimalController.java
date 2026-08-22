package org.example.controller;

import org.example.model.Animal;
import org.example.model.AnimalRepository;
import org.example.view.PanelAnimales;
import javax.swing.JOptionPane;

/**
 * Controlador para el módulo de Animales.
 * Administra la lógica de negocio, validaciones y la comunicación entre
 * el Panel de la Vista y el Repositorio del Modelo.
 */
public class AnimalController {
    private final AnimalRepository repository;
    private PanelAnimales view;

    public AnimalController(AnimalRepository repository) {
        this.repository = repository;
    }

    public void setView(PanelAnimales view) {
        this.view = view;
    }

    /**
     * Registra un nuevo animal aplicando validaciones de negocio.
     */
    public void registrarAnimal(String id, String nombre, String especie, String edadStr, String estadoClinico) {
        // 1. Validación de campos vacíos
        if (id.trim().isEmpty() || nombre.trim().isEmpty() || edadStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Todos los campos son obligatorios.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Validación de ID único
        if (repository.findById(id) != null) {
            JOptionPane.showMessageDialog(view, "Ya existe un animal registrado con el código: " + id, "Error de duplicado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Validación de tipo numérico para la edad
        int edad;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "La edad debe ser un número entero positivo.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4. Creación del objeto y almacenamiento en el arreglo estático
        Animal nuevoAnimal = new Animal(id, nombre, especie, edad, estadoClinico, true);
        boolean guardado = repository.save(nuevoAnimal);

        if (guardado) {
            JOptionPane.showMessageDialog(view, "Animal registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            view.limpiarCampos();
            view.actualizarTabla(repository.findAll(), repository.getTotalAnimales());
        } else {
            JOptionPane.showMessageDialog(view, "No se pudo guardar el animal. Refugio lleno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina lógicamente un animal por su ID.
     */
    public void eliminarAnimal(String id) {
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Seleccione un animal para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(view, "¿Está seguro de eliminar este animal?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = repository.deleteById(id);
            if (eliminado) {
                JOptionPane.showMessageDialog(view, "Animal eliminado lógicamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                view.actualizarTabla(repository.findAll(), repository.getTotalAnimales());
            } else {
                JOptionPane.showMessageDialog(view, "No se encontró el animal a eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
