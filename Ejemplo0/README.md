# Guía de Uso de Git y GitHub

Esta guía proporciona una introducción técnica al sistema de control de versiones Git y a la plataforma de alojamiento de código GitHub. Está diseñada para guiarle desde la creación e inicialización de un repositorio hasta la gestión diaria de versiones mediante comandos de consola.

---

## 1. Conceptos Fundamentales

* **Git**: Es un sistema de control de versiones distribuido de código abierto. Permite registrar los cambios realizados en los archivos de un proyecto a lo largo del tiempo, facilitando la colaboración y la reversión de modificaciones a estados previos.
* **GitHub**: Es una plataforma de alojamiento en la nube que utiliza Git. Proporciona una interfaz gráfica para administrar repositorios, colaborar en equipo, rastrear problemas y gestionar flujos de integración y despliegue continuos.

---

## 2. Creación de un Repositorio en GitHub

Al crear un nuevo repositorio en la interfaz web de GitHub, se presentan diversas opciones de configuración. A continuación se detalla el propósito de cada una:

### Nombre del Repositorio (Repository Name)
Identificador único para el proyecto. Se recomienda utilizar caracteres alfanuméricos, guiones medios (`-`) o guiones bajos (`_`) en lugar de espacios.

### Descripción (Description)
Campo opcional para describir brevemente el propósito, tecnologías o alcance del proyecto. Ayuda a documentar el repositorio de cara a otros desarrolladores.

### Visibilidad (Public / Private)
* **Public (Público)**: Cualquier usuario en Internet puede ver el contenido del repositorio, clonarlo e inspeccionar su historial de versiones. Solo los colaboradores autorizados pueden realizar modificaciones directas.
* **Private (Privado)**: El repositorio es accesible únicamente para el propietario y los colaboradores explícitamente invitados.

### Inicialización del Repositorio
GitHub ofrece la opción de crear el repositorio con elementos iniciales preconfigurados:

* **Add a README file (Añadir un archivo README)**: Crea un archivo en formato Markdown (`README.md`) en la raíz del repositorio. Este archivo suele contener la descripción del proyecto, instrucciones de instalación y uso.
* **Add .gitignore (Añadir archivo .gitignore)**: Permite seleccionar una plantilla de exclusión según el lenguaje o framework de desarrollo utilizado (por ejemplo, Java, Node.js, Python). Este archivo indica a Git qué archivos o directorios locales no deben ser rastreados (como dependencias descargadas, archivos de configuración local, ejecutables o variables de entorno).
* **Choose a license (Elegir una licencia)**: Establece los términos legales bajo los cuales otros desarrolladores pueden usar, modificar y distribuir el código del repositorio (ej. MIT, Apache 2.0, GNU GPLv3).

---

## 3. Flujo de Trabajo con Git en el Entorno Local

Para interactuar con Git a través de la terminal de comandos, se deben seguir las siguientes instrucciones y buenas prácticas:

### Configuración Inicial
Antes de comenzar a utilizar Git, es necesario definir la identidad global del usuario (solo se requiere realizar una vez en el sistema local):
```bash
git config --global user.name "Nombre de Usuario"
git config --global user.email "correo@ejemplo.com"
```

### Caso A: Clonar un Repositorio Existente
Si el repositorio ya existe en GitHub y desea trabajar en él de forma local:

1. **Obtener la URL**: En la página de GitHub del repositorio, copie la URL de clonación (HTTPS o SSH).
2. **Ejecutar el comando de clonación**:
   ```bash
   git clone URL_DEL_REPOSITORIO
   ```
   *Este comando descargará el historial completo y creará una copia del proyecto en una copia de trabajo local.*

### Caso B: Crear un Repositorio desde Cero de Forma Local
Si tiene un proyecto local y desea subirlo a GitHub por primera vez:

1. **Navegar al directorio del proyecto** en la terminal.
2. **Inicializar Git** en la carpeta:
   ```bash
   git init
   ```
   *Esto crea un subdirectorio oculto `.git` donde se almacenará el historial de control de versiones.*
3. **Vincular el repositorio local con GitHub**:
   ```bash
   git remote add origin URL_DEL_REPOSITORIO_EN_GITHUB
   ```
   *El término `origin` es el nombre estándar por defecto para referirse al servidor remoto.*

---

## 4. Ciclo de Vida de los Archivos en Git

El trabajo diario con Git sigue un ciclo de preparación, confirmación y envío de cambios.

### Paso 1: Consultar el Estado del Repositorio
Para visualizar qué archivos han sido modificados, creados o eliminados y en qué estado del ciclo de vida se encuentran:
```bash
git status
```

### Paso 2: Agregar Archivos al Área de Preparación (Staging Area)
El área de preparación permite seleccionar qué cambios específicos formarán parte del próximo punto de restauración.

* **Agregar un archivo específico**:
  ```bash
  git add nombre_del_archivo.txt
  ```
* **Agregar todos los cambios detectados**:
  ```bash
  git add .
  ```

### Paso 3: Confirmar los Cambios (Commit)
Crea una instantánea o punto de restauración en el historial local con los archivos que se encuentran en el área de preparación. Cada commit debe ir acompañado de un mensaje formal y descriptivo:
```bash
git commit -m "Implementar funcionalidad de autenticación de usuarios"
```

### Paso 4: Enviar los Cambios a GitHub (Push)
Sube los commits realizados localmente al repositorio remoto. La primera vez que se sube una rama al repositorio remoto, se suele utilizar la bandera `-u` para establecer la relación de seguimiento:
```bash
git push -u origin main
```
*En las siguientes ocasiones en la misma rama, basta con ejecutar:*
```bash
git push
```

### Paso 5: Descargar Cambios desde GitHub (Pull)
Para integrar los cambios que otros colaboradores hayan subido al repositorio remoto en su copia de trabajo local:
```bash
git pull origin main
```

---

## 5. Gestión de Ramas (Branching)

Las ramas permiten desarrollar nuevas funcionalidades o corregir errores en entornos aislados sin afectar la línea de código principal (`main` o `master`).

* **Crear una nueva rama**:
  ```bash
  git branch nombre_de_la_rama
  ```
* **Cambiar de rama**:
  ```bash
  git checkout nombre_de_la_rama
  ```
* **Crear y cambiar a una nueva rama en un solo paso**:
  ```bash
  git checkout -b nueva_rama
  ```
* **Listar ramas locales**:
  ```bash
  git branch
  ```
* **Fusionar cambios** (unir otra rama a la rama actual en la que se encuentra posicionado):
  ```bash
  git merge nombre_de_la_rama
  ```
