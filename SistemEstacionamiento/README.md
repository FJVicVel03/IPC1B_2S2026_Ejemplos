# Guia Tecnica y Documentacion del Esqueleto: Sistema de Estacionamiento

Este documento proporciona una explicacion tecnica detallada del esqueleto de codigo implementado para el sistema de gestion de estacionamiento, disenado bajo las restricciones del curso Introduccion a la Programacion y Computacion 1. El codigo base sirve como punto de partida estructurado para que el estudiante complete las funcionalidades requeridas.

---

## 1. Estructura y Funcionamiento del Codigo Base

El codigo se encuentra en el archivo [Main.java](file:///c:/Users/ferna/Desktop/RepositorioLocal/IPC1B_2S2026_Ejemplos/SistemEstacionamiento/src/main/java/org/example/Main.java). Utiliza un enfoque modular dividiendo la logica en metodos especificos para evitar la saturacion del punto de entrada `main`.

### Variables de Estado Globals

*   `tablero`: Una matriz bidimensional de tipo `String` de dimensiones 10x10. Se prefiere el tipo `String` sobre `char` para permitir almacenar directamente las placas de los vehiculos (que contienen multiples caracteres) en lugar de utilizar multiples estructuras de datos.
*   `filaEntrada`, `colEntrada`, `filaSalida`, `colSalida`: Variables enteras que guardan de forma permanente las coordenadas del punto de entrada ('E') y salida ('S'). Esto evita tener que buscar estas posiciones recurrentemente en la matriz.
*   `totalVehiculosIngresados`, `totalIngresosRecaudados`: Variables acumuladoras para las estadisticas financieras del sistema.

### Inicializacion del Tablero (`inicializarTablero`)

El tablero se divide en dos secciones logicas utilizando una sola matriz bidimensional de 10x10:
1.  **Via Exterior (Perimetro)**: Filas `0` y `9`, y Columnas `0` y `9`. Representa la calle circundante mediante el caracter `=`.
2.  **Parqueo Interno (Nucleo)**: Filas `1` a `8` y Columnas `1` a `8`. Representa los 64 espacios disponibles. Inicialmente se llenan con `L` (Lugar libre).

Para posicionar la Entrada (`E`) y la Salida (`S`):
1.  Se generan coordenadas utilizando el metodo auxiliar `generarCoordenadaPerimetralAleatoria`. Este metodo selecciona aleatoriamente uno de los cuatro lados de la via exterior y calcula una posicion entre la fila/columna 1 y 8, previniendo que coincida con las cuatro esquinas: `(0,0)`, `(0,9)`, `(9,0)` y `(9,9)`.
2.  Se asegura que la Salida (`S`) no se ubique en la misma coordenada que la Entrada (`E`) mediante un ciclo `do-while`.

---

## 2. Logica de Indices y Representacion del Tablero

### Visualizacion en Consola (`mostrarTablero`)

El metodo `mostrarTablero` recorre la matriz bidimensional de 10x10 e imprime los caracteres formateados en la consola. Para facilitar la lectura del usuario novato, se implementa una logica de indices virtuales:
*   Se imprime un encabezado numerico horizontal de columnas `1` a `8` alineado con los espacios de parqueo internos.
*   Al inicio de cada fila interna (de `1` a `8`), se imprime el numero de fila correspondiente.
*   **Abstraccion de datos**: En la matriz `tablero`, si un espacio de parqueo esta ocupado, se guarda la cadena completa de la placa (por ejemplo, `"P401JZQ"`). Sin embargo, al imprimir el tablero, el codigo evalua si el valor en la posicion `(r, c)` no pertenece a los simbolos reservados (`=`, `E`, `S`, `L`). Si detecta una placa, imprime en su lugar el caracter `A` (Automovil), manteniendo la simetria visual de la cuadricula de consola y ocultando la informacion interna de la placa para no saturar la pantalla.

---

## 3. Instrucciones de Compilacion y Ejecucion

El proyecto esta configurado con soporte para Maven, pero al ser un desarrollo de consola sin dependencias complejas, se puede compilar y ejecutar directamente desde la terminal del sistema operativo utilizando las herramientas estandar del Java Development Kit (JDK).

### Compilacion Manual
Navegar con la terminal hacia la carpeta raiz del proyecto (`SistemEstacionamiento`) y ejecutar:
```bash
javac src/main/java/org/example/Main.java
```

### Ejecucion Directa
Para ejecutar la aplicacion desde la terminal en la misma carpeta raiz:
```bash
java src/main/java/org/example/Main.java
```
*Nota: A partir de Java 11, es posible ejecutar archivos de codigo fuente unico directamente con el comando `java` sin compilar previamente de forma explicita.*

---

## 4. Recomendaciones e Instrucciones de Continuacion para el Estudiante

A continuacion, se presenta una secuencia logica sugerida para implementar las funcionalidades restantes dentro de los stubs de metodos provistos:

### Fase 1: Validacion de la Placa (`ingresarVehiculo`)

La validacion de la placa es fundamental para garantizar la integridad de los datos. Se recomienda verificar los siguientes criterios:
1.  **Longitud**: Debe poseer exactamente 7 caracteres (`placa.length() == 7`).
2.  **Prefijo**: El primer caracter debe ser obligatoriamente la letra 'P' mayuscula (`placa.charAt(0) == 'P'`).
3.  **Digitos**: Los caracteres en las posiciones indexadas 1, 2 y 3 deben ser digitos numericos. Puede utilizar `Character.isDigit(placa.charAt(i))`.
4.  **Sufijo**: Los caracteres en las posiciones indexadas 4, 5 y 6 deben ser letras mayusculas. Puede usar `Character.isUpperCase(placa.charAt(i))`.
5.  **Unicidad**: Recorra la submatriz interna (filas 1 a 8, columnas 1 a 8) y valide que ninguna celda contenga una cadena igual a la ingresada, previniendo asi vehiculos duplicados.

### Fase 2: Robustez de la Entrada de Datos

Para evitar que la aplicacion finalice inesperadamente cuando el usuario ingresa tipos de datos incorrectos en el menu o en la seleccion de filas y columnas, reemplace la lectura directa de enteros por lecturas de tipo cadena y posterior conversion controlada:
```java
try {
    int fila = Integer.parseInt(scanner.nextLine().trim());
    // Proceder con la validacion de rango [1, 8]
} catch (NumberFormatException e) {
    System.out.println("Error: Debe ingresar un valor numerico entero valido.");
}
```

### Fase 3: Logica de Cobro e Ingresos

1.  Antes de registrar la placa en la submatriz, implemente un ciclo iterativo (`while`) que solicite el cobro de la tarifa fija (Q10.00).
2.  Valide que el monto sea un valor real positivo y que sea igual o mayor al costo de la tarifa.
3.  Calcule el cambio restando el pago del cliente menos la tarifa fija (`cambio = pago - 10.00`).
4.  Una vez completado el pago de manera exitosa, actualice la matriz `tablero[fila][columna] = placa` e incremente las variables globales de reporte financiero.

### Fase 4: Busqueda y Retiro (`buscarVehiculoPorPlaca` y `retirarVehiculo`)

Ambas opciones se basan en recorrer la submatriz bidimensional interna utilizando dos ciclos `for` anidados:
*   En `buscarVehiculoPorPlaca`, si la celda actual es igual a la placa buscada, imprima los indices de fila y columna actuales.
*   En `retirarVehiculo`, realice el mismo recorrido. Al encontrar la placa, asigne el valor `"L"` a dicha celda para liberar el espacio e informe al usuario.

### Fase 5: Algoritmo de la Ruta Mas Corta (`mostrarRutaMasCorta`)

Para calcular las distancias sobre la via exterior perimetral de 10x10 (que consta de 36 posiciones en total):
1.  **Indexacion Perimetral**: Imagine el perimetro como un arreglo circular unidimensional de 36 elementos.
2.  Mapee cada coordenada perimetral `(fila, columna)` a un unico indice entero `i` en el rango de `0` a `35`. Una formula sugerida para recorrer en sentido horario es:
    *   Si la celda esta en la fila superior (fila 0): `i = columna`. (indices 0 a 9)
    *   Si esta en la columna derecha (columna 9): `i = 9 + fila`. (indices 10 a 18, descontando la esquina duplicada)
    *   Si esta en la fila inferior (fila 9): `i = 9 + 9 + (9 - columna)`. (indices 19 a 27)
    *   Si esta en la columna izquierda (columna 0): `i = 27 + (9 - fila)`. (indices 28 a 35)
3.  Calcule el indice de la Entrada (`i_E`) y de la Salida (`i_S`) mediante esta logica.
4.  La distancia en sentido horario sera:
    *   Si `i_S > i_E`, `distanciaHoraria = i_S - i_E`.
    *   De lo contrario, `distanciaHoraria = (36 - i_E) + i_S`.
5.  La distancia en sentido antihorario sera simplemente la diferencia complementaria: `distanciaAntihoraria = 36 - distanciaHoraria`.
6.  Compare ambos resultados y recomiende la distancia mas corta de forma tecnica y objetiva.

---

## 5. Implementacion de la Ruta Perimetral y Calculo de Distancias

### Area de Trabajo
El desarrollo se realizo en el archivo de codigo fuente [Main.java](file:///c:/Users/ferna/Desktop/RepositorioLocal/IPC1B_2S2026_Ejemplos/SistemEstacionamiento/src/main/java/org/example/Main.java), especificamente en los siguientes metodos:
*   `mostrarRutaMasCorta()`: Contiene la logica de despliegue y comparacion.
*   `mapearCoordenadaAPosicionPerimetral(int, int)`: Metodo auxiliar para conversion geometrica.

### Modificaciones Realizadas
*   Se removio el mensaje de funcionalidad en desarrollo en la opcion de calculo de ruta.
*   Se implemento el mapeo matematico que traduce cualquier coordenada bidimensional `(fila, columna)` perteneciente al perimetro exterior en un indice lineal continuo en el rango de `0` a `35`.
*   Se anadio el calculo modular para obtener la distancia horaria y la diferencia complementaria para la distancia antihoraria.
*   Se incorporo una estructura condicional que compara los resultados de ambos recorridos para recomendar la ruta mas corta o indicar indiferencia en caso de igualdad de posiciones.
*   Se formatearon las salidas impresas para mostrar indices en formato 1-based (sumando +1 a los indices de matriz) para que el estudiante visualice los resultados en la misma nomenclatura numerica de la consola.

### Justificacion Tecnica
Este enfoque de mapeo unidimensional circular es preferible sobre algoritmos de busqueda en grafos o busqueda en anchura (BFS) por las siguientes razones:
1.  **Complejidad Algoritmica**: El calculo de la distancia se realiza en tiempo constante $O(1)$ y espacio auxiliar $O(1)$, evitando iteraciones innecesarias o almacenamiento de estructuras de datos auxiliares.
2.  **Restricciones de la Practica**: Al no estar permitido el uso de colecciones del framework de Java (como `Queue`, `ArrayList` o clases de nodos), la indexacion matematica del perimetro es la solucion mas limpia y robusta implementable utilizando exclusivamente tipos primitivos y arreglos nativos.
3.  **Mantenibilidad**: La logica es comprensible para un estudiante novato, permitiendole analizar la relacion geometrica del perimetro de una matriz de forma abstracta.

