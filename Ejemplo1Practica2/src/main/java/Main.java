import com.quetzal.controlador.JuegoController;
import com.quetzal.vista.VentanaJuego;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaJuego ventana = new VentanaJuego();
            JuegoController controller = new JuegoController(ventana);
            ventana.setController(controller);
            ventana.setVisible(true);
        });
    }
}
