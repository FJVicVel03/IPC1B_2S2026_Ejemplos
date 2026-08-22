package org.example.view;

import org.example.controller.AnimalController;
import org.example.model.Animal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Vista del módulo de animales, construida 100% por código en Java Swing.
 * No contiene lógica de negocio, solo captura y delega eventos al controlador.
 */
public class PanelAnimales extends JPanel {
    private final AnimalController controller;

    // Componentes del formulario
    private JTextField txtId;
    private JTextField txtNombre;
    private JComboBox<String> cbEspecie;
    private JTextField txtEdad;
    private JComboBox<String> cbEstadoClinico;
    private JButton btnRegistrar;
    private JButton btnEliminar;

    // Tabla de visualización
    private JTable tablaAnimales;
    private DefaultTableModel tableModel;

    public PanelAnimales(AnimalController controller) {
        this.controller = controller;
        this.controller.setView(this);
        inicializarUI();
    }

    private void inicializarUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 1. Formulario de ingreso (Panel Norte / Oeste)
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registro de Animales"));

        panelFormulario.add(new JLabel("Código / ID:"));
        txtId = new JTextField();
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Especie:"));
        cbEspecie = new JComboBox<>(new String[]{"PERRO", "GATO"});
        panelFormulario.add(cbEspecie);

        panelFormulario.add(new JLabel("Edad Estimada:"));
        txtEdad = new JTextField();
        panelFormulario.add(txtEdad);

        panelFormulario.add(new JLabel("Estado Clínico:"));
        cbEstadoClinico = new JComboBox<>(new String[]{"INGRESADO", "EVALUADO", "DISPONIBLE", "ADOPTADO"});
        panelFormulario.add(cbEstadoClinico);

        btnRegistrar = new JButton("Registrar Animal");
        panelFormulario.add(btnRegistrar);

        btnEliminar = new JButton("Eliminar Seleccionado");
        panelFormulario.add(btnEliminar);

        add(panelFormulario, BorderLayout.WEST);

        // 2. Tabla de visualización (Panel Centro)
        String[] columnas = {"ID", "Nombre", "Especie", "Edad", "Estado"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desactivar edición directa en celdas
            }
        };
        tablaAnimales = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaAnimales);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Listado de Animales Rescatados"));
        add(scrollPane, BorderLayout.CENTER);

        // 3. Registro de Eventos delegados al Controlador
        btnRegistrar.addActionListener(e -> {
            String id = txtId.getText();
            String nombre = txtNombre.getText();
            String especie = (String) cbEspecie.getSelectedItem();
            String edad = txtEdad.getText();
            String estado = (String) cbEstadoClinico.getSelectedItem();
            controller.registrarAnimal(id, nombre, especie, edad, estado);
        });

        btnEliminar.addActionListener(e -> {
            int selectedRow = tablaAnimales.getSelectedRow();
            if (selectedRow >= 0) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
                controller.eliminarAnimal(id);
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un animal de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * Limpia los campos del formulario tras registrar con éxito.
     */
    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        cbEspecie.setSelectedIndex(0);
        cbEstadoClinico.setSelectedIndex(0);
    }

    /**
     * Actualiza las filas de la tabla leyendo el arreglo estático.
     */
    public void actualizarTabla(Animal[] animales, int cantidad) {
        tableModel.setRowCount(0); // Limpiar filas anteriores
        for (int i = 0; i < cantidad; i++) {
            if (animales[i] != null && animales[i].isActivo()) {
                Object[] fila = {
                    animales[i].getId(),
                    animales[i].getNombre(),
                    animales[i].getEspecie(),
                    animales[i].getEdadEstimada(),
                    animales[i].getEstadoClinico()
                };
                tableModel.addRow(fila);
            }
        }
    }
}
