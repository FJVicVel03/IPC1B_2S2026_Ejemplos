package org.example;

import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal que define el esqueleto del Sistema de Estacionamiento.
 * Proporciona la estructura del menu, inicializacion del tablero con entrada/salida
 * y stubs de metodos para ser completados por el estudiante.
 */
public class Main {

    // Constantes de dimensiones y costos
    private static final int TAMANIO_TABLERO = 10;
    private static final int TAMANIO_ESTACIONAMIENTO = 8;
    private static final double TARIFA_FIJA = 10.00;

    // Matriz del tablero de 10x10
    // Contiene: '=' para via, 'E' para entrada, 'S' para salida,
    // 'L' para espacio de estacionamiento libre, y la placa (ej: P401JZQ) para espacios ocupados.
    private static String[][] tablero = new String[TAMANIO_TABLERO][TAMANIO_TABLERO];

    // Variables para el control de la entrada y salida aleatorias
    private static int filaEntrada = -1;
    private static int colEntrada = -1;
    private static int filaSalida = -1;
    private static int colSalida = -1;

    // Variables de control financiero y operativo
    private static int totalVehiculosIngresados = 0;
    private static double totalIngresosRecaudados = 0.0;

    // Scanner global para lectura de datos desde la consola
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicializar el tablero de 10x10 y ubicar los puntos de acceso
        inicializarTablero();

        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();
            String opcionInput = scanner.nextLine().trim();

            switch (opcionInput) {
                case "1":
                    ingresarVehiculo();
                    break;
                case "2":
                    retirarVehiculo();
                    break;
                case "3":
                    mostrarTablero();
                    break;
                case "4":
                    buscarVehiculoPorPlaca();
                    break;
                case "5":
                    mostrarRutaMasCorta();
                    break;
                case "6":
                    mostrarIngresos();
                    break;
                case "7":
                    System.out.println("Finalizando la ejecucion del programa.");
                    continuar = false;
                    break;
                default:
                    System.out.println("Error: Opcion no valida. Ingrese un valor numerico entre 1 y 7.");
            }
            System.out.println(); // Salto de linea decorativo para legibilidad
        }
    }

    /**
     * Muestra en consola el menu principal de la aplicacion.
     */
    private static void mostrarMenuPrincipal() {
        System.out.println("===== SISTEMA DE ESTACIONAMIENTO =====");
        System.out.println("1. Ingresar vehiculo");
        System.out.println("2. Retirar vehiculo");
        System.out.println("3. Mostrar estacionamiento");
        System.out.println("4. Buscar vehiculo por placa");
        System.out.println("5. Mostrar ruta mas corta entre entrada y salida");
        System.out.println("6. Mostrar ingresos");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    /**
     * Inicializa la estructura del tablero.
     * Llena el perimetro con la via exterior ('='), los espacios internos libres ('L')
     * y genera aleatoriamente la Entrada ('E') y Salida ('S') en el perimetro excluyendo esquinas.
     */
    private static void inicializarTablero() {
        // 1. Inicializar todas las celdas
        for (int r = 0; r < TAMANIO_TABLERO; r++) {
            for (int c = 0; c < TAMANIO_TABLERO; c++) {
                if (r == 0 || r == TAMANIO_TABLERO - 1 || c == 0 || c == TAMANIO_TABLERO - 1) {
                    tablero[r][c] = "="; // Via exterior
                } else {
                    tablero[r][c] = "L"; // Lugar libre interno
                }
            }
        }

        // 2. Generar Entrada (E) y Salida (S) aleatorias sobre el perimetro
        Random random = new Random();
        
        // Coordenadas de entrada
        int[] entrada = generarCoordenadaPerimetralAleatoria(random);
        filaEntrada = entrada[0];
        colEntrada = entrada[1];
        tablero[filaEntrada][colEntrada] = "E";

        // Coordenadas de salida (deben ser diferentes a la entrada)
        int[] salida;
        do {
            salida = generarCoordenadaPerimetralAleatoria(random);
        } while (salida[0] == filaEntrada && salida[1] == colEntrada);
        
        filaSalida = salida[0];
        colSalida = salida[1];
        tablero[filaSalida][colSalida] = "S";
    }

    /**
     * Genera de forma aleatoria una coordenada en el perimetro del tablero
     * de 10x10, excluyendo los cuatro vertices (esquinas).
     * 
     * @param random Instancia del generador pseudoaleatorio.
     * @return Arreglo unidimensional de dos elementos [fila, columna].
     */
    private static int[] generarCoordenadaPerimetralAleatoria(Random random) {
        // Elegir aleatoriamente uno de los 4 bordes:
        // 0 = Borde Superior (fila 0, columna 1 a 8)
        // 1 = Borde Derecho (fila 1 a 8, columna 9)
        // 2 = Borde Inferior (fila 9, columna 1 a 8)
        // 3 = Borde Izquierdo (fila 1 a 8, columna 0)
        int borde = random.nextInt(4);
        int indiceBorde = random.nextInt(TAMANIO_ESTACIONAMIENTO) + 1; // Rango [1, 8]

        switch (borde) {
            case 0:
                return new int[]{0, indiceBorde};
            case 1:
                return new int[]{indiceBorde, TAMANIO_TABLERO - 1};
            case 2:
                return new int[]{TAMANIO_TABLERO - 1, indiceBorde};
            case 3:
                return new int[]{indiceBorde, 0};
            default:
                return new int[]{0, 1};
        }
    }

    /**
     * Requerimiento 3: Mostrar estacionamiento.
     * Imprime en pantalla el tablero de 10x10 incluyendo numeros de fila y columna,
     * y muestra el conteo final de espacios libres y ocupados.
     */
    private static void mostrarTablero() {
        System.out.println("\nRepresentacion de la via y parqueo actual:");
        
        // Imprimir encabezado de columnas (indices internos 1 a 8)
        System.out.print("  ");
        for (int c = 1; c <= TAMANIO_ESTACIONAMIENTO; c++) {
            System.out.print(" " + c);
        }
        System.out.println();

        int espaciosLibres = 0;
        int espaciosOcupados = 0;

        for (int r = 0; r < TAMANIO_TABLERO; r++) {
            // Imprimir numero de fila si es una fila interna (1 a 8), de lo contrario espacio en blanco
            if (r > 0 && r < TAMANIO_TABLERO - 1) {
                System.out.print(r + " ");
            } else {
                System.out.print("  ");
            }

            for (int c = 0; c < TAMANIO_TABLERO; c++) {
                String valor = tablero[r][c];
                
                // Mapeo de caracteres para visualizacion:
                // Si el valor no es '=', 'E', 'S' o 'L', significa que es una placa.
                // En ese caso, para no alterar la cuadricula de visualizacion, se representa como 'A' (Automovil).
                if (valor.equals("=")) {
                    System.out.print(" =");
                } else if (valor.equals("E")) {
                    System.out.print(" E");
                } else if (valor.equals("S")) {
                    System.out.print(" S");
                } else if (valor.equals("L")) {
                    System.out.print(" L");
                    if (r > 0 && r < TAMANIO_TABLERO - 1 && c > 0 && c < TAMANIO_TABLERO - 1) {
                        espaciosLibres++;
                    }
                } else {
                    // Es un vehiculo registrado (placa)
                    System.out.print(" A");
                    if (r > 0 && r < TAMANIO_TABLERO - 1 && c > 0 && c < TAMANIO_TABLERO - 1) {
                        espaciosOcupados++;
                    }
                }
            }
            System.out.println();
        }

        // Mostrar resumen de conteo
        System.out.println("Resumen del estado:");
        System.out.println("Espacios libres: " + espaciosLibres);
        System.out.println("Espacios ocupados: " + espaciosOcupados);
    }

    /**
     * Requerimiento 1: Ingresar vehiculo.
     * Gestiona el flujo para registrar una placa en una posicion valida e internamente cobrar la tarifa.
     */
    private static void ingresarVehiculo() {
        System.out.println("\n--- INGRESAR VEHICULO ---");
        
        // TODO: Estudiante debe completar este metodo.
        // Pasos recomendados:
        // 1. Validar si el estacionamiento esta lleno (espacios ocupados == 64).
        // 2. Solicitar y validar el ingreso de la placa (formato P###LLL: empieza con 'P', 3 numeros y 3 letras mayusculas).
        // 3. Validar que la placa no este previamente registrada en el tablero (evitar duplicados).
        // 4. Solicitar y validar la fila y columna elegida por el usuario (ambas deben estar en el rango [1, 8]).
        // 5. Validar que la celda destino en el tablero este libre ('L') y no ocupada ('A').
        // 6. Realizar el proceso de cobro de la tarifa fija de Q10.00:
        //    - Solicitar el pago entregado.
        //    - Validar que no sea negativo y que sea suficiente (>= Q10.00).
        //    - De lo contrario, solicitar el pago de nuevo de forma iterativa.
        //    - Calcular y mostrar el cambio.
        // 7. Registrar definitivamente la placa en la celda correspondiente del tablero.
        // 8. Actualizar las variables globales de ingresos (totalVehiculosIngresados++ y totalIngresosRecaudados += TARIFA_FIJA).
        
        System.out.println("Funcionalidad en desarrollo. Complete este metodo utilizando arreglos nativos.");
    }

    /**
     * Requerimiento 2: Retirar vehiculo.
     * Libera un espacio previamente ocupado buscando el vehiculo mediante su placa.
     */
    private static void retirarVehiculo() {
        System.out.println("\n--- RETIRAR VEHICULO ---");
        
        // TODO: Estudiante debe completar este metodo.
        // Pasos recomendados:
        // 1. Solicitar la placa del vehiculo a retirar.
        // 2. Validar que la placa cumpla con el formato adecuado antes de iniciar la busqueda.
        // 3. Recorrer la matriz de parqueo interna (filas 1 a 8, columnas 1 a 8).
        // 4. Si se encuentra la placa coincidente:
        //    - Mostrar en consola la fila y columna que ocupaba.
        //    - Reemplazar el valor de esa celda por "L" (Lugar libre).
        //    - Confirmar el retiro exitoso.
        // 5. Si tras recorrer la matriz no se encuentra coincidencia, mostrar un mensaje de error indicando que la placa no existe.
        
        System.out.println("Funcionalidad en desarrollo. Complete este metodo utilizando arreglos nativos.");
    }

    /**
     * Requerimiento 4: Buscar vehiculo por placa.
     * Busca la posicion de un automovil estacionado y despliega su fila y columna.
     */
    private static void buscarVehiculoPorPlaca() {
        System.out.println("\n--- BUSCAR VEHICULO ---");
        
        // TODO: Estudiante debe completar este metodo.
        // Pasos recomendados:
        // 1. Solicitar la placa a buscar.
        // 2. Validar el formato de la placa.
        // 3. Recorrer la seccion interna del tablero.
        // 4. Si se localiza la placa, mostrar las coordenadas en formato Fila y Columna.
        // 5. Si no se encuentra, notificar que no se encuentra estacionado en el sistema.
        
        System.out.println("Funcionalidad en desarrollo. Complete este metodo utilizando arreglos nativos.");
    }

    /**
     * Requerimiento 5: Mostrar ruta mas corta entre entrada y salida.
     * Calcula las distancias en sentido horario y antihorario alrededor de la via perimetral externa
     * y recomienda la opcion mas corta.
     */
    private static void mostrarRutaMasCorta() {
        System.out.println("\n--- RUTA MAS CORTA PERIMETRAL ---");
        System.out.println("Entrada en: fila " + filaEntrada + ", columna " + colEntrada);
        System.out.println("Salida en: fila " + filaSalida + ", columna " + colSalida);
        
        // TODO: Estudiante debe completar este metodo.
        // Pasos recomendados:
        // La via perimetral externa del tablero de 10x10 consta de 36 posiciones en total.
        // 1. Mapear o indexar de forma logica la secuencia circular de celdas que conforman la via exterior.
        //    - Por ejemplo, representarlo como un ciclo cerrado que inicia en (0,0), recorre la fila 0, baja por col 9,
        //      recorre fila 9 y sube por col 0.
        // 2. Localizar la posicion relativa de la Entrada (E) y la Salida (S) dentro de esa secuencia lineal circular.
        // 3. Calcular la distancia en sentido horario (avanzando indices) y antihorario (retrocediendo indices).
        // 4. Comparar ambas distancias obtenidas en pasos.
        // 5. Desplegar los resultados de ambas rutas e indicar la recomendacion (ruta menor o indistinto en caso de empate).
        
        System.out.println("Funcionalidad en desarrollo. Complete este metodo utilizando algoritmos de recorrido lineal circular.");
    }

    /**
     * Requerimiento 6: Mostrar ingresos.
     * Despliega las estadisticas financieras acumuladas durante la ejecucion actual.
     */
    private static void mostrarIngresos() {
        System.out.println("\n===== INGRESAS ACUMULADOS =====");
        System.out.println("Vehiculos cobrados: " + totalVehiculosIngresados);
        System.out.println("Tarifa fija por vehiculo: Q" + String.format("%.2f", TARIFA_FIJA));
        System.out.println("Total recaudado: Q" + String.format("%.2f", totalIngresosRecaudados));
    }
}
