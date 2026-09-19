package com.quetzal.vista;

import com.quetzal.controlador.JuegoController;
import com.quetzal.modelo.Enemigo;
import com.quetzal.modelo.Nave;
import com.quetzal.modelo.Proyectil;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Vista principal (JFrame) con panel de control superior y lienzo de juego.
 */
public class VentanaJuego extends JFrame {
    private final JComboBox<String> comboDificultad;
    private final JButton btnIniciar;
    private final JLabel lblEstado;
    private final PanelLienzo panelLienzo;
    private JuegoController controller;

    public VentanaJuego() {
        super("Quetzal Space Defender - Ejemplo IPC1 (MVC)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Barra de control superior (HUD / Opciones)
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelSuperior.setBackground(new Color(22, 27, 34));

        JLabel lblDificultad = new JLabel("Dificultad:");
        lblDificultad.setForeground(Color.WHITE);
        panelSuperior.add(lblDificultad);

        comboDificultad = new JComboBox<>(new String[]{"Fácil", "Normal", "Difícil"});
        panelSuperior.add(comboDificultad);

        btnIniciar = new JButton("Iniciar Partida");
        panelSuperior.add(btnIniciar);

        lblEstado = new JLabel("Vidas: 3 | Puntos: 0");
        lblEstado.setForeground(Color.YELLOW);
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelSuperior.add(lblEstado);

        add(panelSuperior, BorderLayout.NORTH);

        // 2. Lienzo gráfico del juego
        panelLienzo = new PanelLienzo();
        panelLienzo.setPreferredSize(new Dimension(800, 520));
        add(panelLienzo, BorderLayout.CENTER);

        // Capturar teclado en el panel
        panelLienzo.setFocusable(true);
        panelLienzo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (controller != null) {
                    controller.procesarTecla(e.getKeyCode());
                }
            }
        });
    }

    public void setController(JuegoController controller) {
        this.controller = controller;
        btnIniciar.addActionListener(e -> {
            controller.iniciarJuego((String) comboDificultad.getSelectedItem());
            panelLienzo.requestFocusInWindow();
        });
    }

    public void actualizarHUD(int vidas, int puntos) {
        lblEstado.setText("Vidas: " + vidas + " | Puntos: " + puntos);
    }

    public void repintarJuego() {
        panelLienzo.repaint();
    }

    // Panel interno que dibuja los elementos
    private class PanelLienzo extends JPanel {
        public PanelLienzo() {
            setBackground(new Color(13, 17, 23));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (controller == null) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dibujar Nave
            Nave nave = controller.getNave();
            if (nave != null) {
                Polygon p = new Polygon();
                p.addPoint(nave.getX(), nave.getY());
                p.addPoint(nave.getX() + nave.getAncho(), nave.getY() + nave.getAlto() / 2);
                p.addPoint(nave.getX(), nave.getY() + nave.getAlto());
                g2.setColor(new Color(56, 189, 248));
                g2.fillPolygon(p);
            }

            // Dibujar Proyectiles
            g2.setColor(Color.CYAN);
            for (Proyectil py : controller.getProyectiles()) {
                if (py.isViva()) {
                    g2.fillRect(py.getX(), py.getY(), py.getAncho(), py.getAlto());
                }
            }

            // Dibujar Enemigos
            for (Enemigo e : controller.getEnemigos()) {
                if (e.isViva()) {
                    Polygon pe = new Polygon();
                    pe.addPoint(e.getX() + e.getAncho(), e.getY());
                    pe.addPoint(e.getX(), e.getY() + e.getAlto() / 2);
                    pe.addPoint(e.getX() + e.getAncho(), e.getY() + e.getAlto());
                    g2.setColor(new Color(239, 68, 68));
                    g2.fillPolygon(pe);
                }
            }

            // Mensaje de Fin de Juego
            if (!controller.isJugando()) {
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 28));
                g2.drawString("PRESIONA 'INICIAR PARTIDA'", 180, 250);
            }
        }
    }
}
