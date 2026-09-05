package org.example.utils;

import org.example.model.Animal;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportGenerator {
    public static boolean generarReporteAnimales(Animal[] animales, int cantidad) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = "ReporteAnimales_" + timestamp + ".html";
        
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n<title>Reporte de Animales</title>\n");
        html.append("<style>\n");
        html.append("body { font-family: Arial, sans-serif; margin: 20px; }\n");
        html.append("h1 { color: #333; }\n");
        html.append("table { border-collapse: collapse; width: 100%; }\n");
        html.append("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
        html.append("th { background-color: #f2f2f2; }\n");
        html.append("</style>\n</head>\n<body>\n");
        html.append("<h1>Reporte de Animales Rescatados</h1>\n");
        html.append("<p>Fecha de generaci&oacute;n: ").append(new Date().toString()).append("</p>\n");
        html.append("<table>\n<tr><th>ID</th><th>Nombre</th><th>Especie</th><th>Edad</th><th>Estado Cl&iacute;nico</th></tr>\n");
        
        for (int i = 0; i < cantidad; i++) {
            if (animales[i] != null && animales[i].isActivo()) {
                html.append("<tr>");
                html.append("<td>").append(animales[i].getId()).append("</td>");
                html.append("<td>").append(animales[i].getNombre()).append("</td>");
                html.append("<td>").append(animales[i].getEspecie()).append("</td>");
                html.append("<td>").append(animales[i].getEdadEstimada()).append("</td>");
                html.append("<td>").append(animales[i].getEstadoClinico()).append("</td>");
                html.append("</tr>\n");
            }
        }
        
        html.append("</table>\n</body>\n</html>");
        
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(html.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
