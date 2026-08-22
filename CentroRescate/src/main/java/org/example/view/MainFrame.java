package org.example.view;

import org.example.controller.AnimalController;
import org.example.model.AnimalRepository;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación.
 * Actúa como contenedor de los diferentes módulos del refugio.
 */
public class MainFrame extends JFrame {
    private final AnimalRepository animalRepository;
    private final AnimalController animalController;

    public MainFrame() {
        // Inicializar repositorios y controladores
        this.animalRepository = new AnimalRepository();
        this.animalController = new AnimalController(animalRepository);

        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Centro de Rescate Animal - Gestión de Refugio y Adopciones (Base)");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        // 1. Panel de encabezado simple
        JPanel panelEncabezado = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelEncabezado.setBackground(new Color(41, 128, 185));
        JLabel lblTitulo = new JLabel("Panel de Control del Refugio (Esqueleto Base)");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelEncabezado.add(lblTitulo);
        add(panelEncabezado, BorderLayout.NORTH);

        // 2. Módulo de animales (Se puede expandir agregando pestañas JTabbedPane)
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Agregar el panel de animales que sirve de ejemplo para los estudiantes
        PanelAnimales panelAnimales = new PanelAnimales(animalController);
        tabbedPane.addTab("Animales Rescatados", panelAnimales);

        // TODO: Estudiantes agregarán pestañas adicionales:
        // tabbedPane.addTab("Adoptantes", panelAdoptantes);
        // tabbedPane.addTab("Solicitudes", panelSolicitudes);
        // tabbedPane.addTab("Ubicaciones", panelUbicaciones);

        add(tabbedPane, BorderLayout.CENTER);
    }
}
