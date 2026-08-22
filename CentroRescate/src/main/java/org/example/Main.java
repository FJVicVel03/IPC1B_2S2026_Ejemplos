package org.example;

import org.example.view.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal que inicializa el sistema.
 */
public class Main {
    public static void main(String[] args) {
        // Establecer un aspecto nativo y moderno
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ejecutar la interfaz gráfica en el hilo de despacho de eventos de Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
