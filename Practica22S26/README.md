# Quetzal Space Defender - Guía Rápida para Clase (1 Hora)
### Demostración Base en Vivo para Laboratorio IPC1 (Patrón MVC + Hilos)

Esta versión está **reducida a lo esencial** para que puedas explicarla y codificarla en vivo con tus alumnos en **45 a 60 minutos**, sin perder la arquitectura formal ni los requerimientos clave de la práctica.

---

## ⏱️ Ruta de Desarrollo en Clase (Paso a Paso en 50 minutos)

### Paso 1: El Modelo (10 min)
Enseña herencia básica y encapsulamiento:
1. `modelo/Entidad.java`: `x`, `y`, `ancho`, `alto`, `viva`, `getLimites()` y `colisionaCon()`.
2. `modelo/Nave.java`: Hereda de `Entidad`. Añade `velocidad`, `tiempoRecargaMs` (según dificultad Fácil/Normal/Difícil) y `puedeDisparar()`.
3. `modelo/Proyectil.java`: Hereda de `Entidad`, avanza hacia la derecha (+X).
4. `modelo/Enemigo.java`: Hereda de `Entidad`, avanza hacia la izquierda (-X).

### Paso 2: La Concurrencia / Hilos (15 min)
Explica cómo crear un hilo con `extends Thread` y su método `run()`:
1. `hilos/HiloProyectil.java`: Loop mientras esté viva -> `proyectil.avanzar()`, verificar límites, comprobar colisión contra enemigos, notificar repintado y `Thread.sleep(25)`.
2. `hilos/HiloEnemigo.java`: Loop mientras esté viva -> `enemigo.avanzar()`, verificar colisión contra la nave del jugador, restar vida y `Thread.sleep(35)`.
3. `hilos/GeneradorEnemigos.java`: Loop continuo -> genera un `Enemigo` en el borde derecho cada 1.5s y lanza su respectivo `HiloEnemigo`.

### Paso 3: La Vista (10 min)
Una sola ventana visual sencilla y directa:
1. `vista/VentanaJuego.java`:
   - Barra superior (Norte): Selector de dificultad (`JComboBox`), botón "Iniciar Partida" y marcador de vidas/puntos (`JLabel`).
   - Lienzo central: `JPanel` que sobreescribe `paintComponent(Graphics g)` para dibujar la nave (azul), proyectiles (celestes) y enemigos (rojos).
   - Escuchador de teclado (`KeyListener`) que delega las teclas al controlador.

### Paso 4: El Controlador y Main (15 min)
Une el modelo y la vista:
1. `controlador/JuegoController.java`:
   - `iniciarJuego(dificultad)`: Reinicia vidas a 3, puntos a 0, aplica dificultad y arranca `GeneradorEnemigos`.
   - `procesarTecla(codigo)`: Mueve la nave con W/A/S/D o flechas, y con Espacio dispara creando un `Proyectil` y su `HiloProyectil`.
   - Métodos `perderVida()` y `sumarPuntos()`.
2. `Main.java`: 15 líneas para instanciar la vista, el controlador y hacer visible la ventana.

---

## 🎯 Lo que queda de tarea para los Alumnos
1. **Obstáculos y Premios**: Crear `SnitchEspacial` (150 pts + destruir enemigos), `AsteroideBludger` (bloquear nave 2 seg) y `ContenedorQuaffle` (+10 pts).
2. **Persistencia en Vectores**: Guardar historial de partidas en un arreglo `Partida[]` y ordenarlo con Algoritmo Burbuja.
3. **Reporte HTML y Gráfica**: Exportar la tabla y la gráfica con JFreeChart usando `java.io.PrintWriter`.
4. **Documentación**: Manual técnico y de usuario.

---

## 🚀 Compilar y Ejecutar

```powershell
# Compilar
javac -encoding UTF-8 -d target/classes (Get-ChildItem -Recurse -Filter *.java src/main/java).FullName

# Ejecutar
java -cp target/classes com.quetzal.Main
```
