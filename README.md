# DOSW-Library

---

## Descripción

Este sistema permite a los usuarios tomar prestados libros de la biblioteca.  
El sistema gestiona los préstamos, verifica la disponibilidad de los libros, y mantiene un registro de los libros prestados.

- Los usuarios pueden agregar libros, obtener todos los libros, obtener un libro por su código de identificación y actualizar su disponibilidad.
- Se pueden registrar usuarios, obtener todos los usuarios registrados y obtener un usuario usando su identificación.

---

### Diagrama de componentes de la biblioteca
![dComponentes.png](images/dComponentes.png)

---
### Diagrama de componentes especifico de la biblioteca
![dComponetesEspecífico.png](images/dComponetesEspec%C3%ADfico.png)

---
### Diagrama de clases
![diagramaDeClases.png](images/diagramaDeClases.png)

---
## Pruebas

---
## Evidencias de ejecución

### 1. **Ejecución de la API**
- La API se levantó correctamente usando Spring Boot.
- Endpoints disponibles:
![endPoints.png](images/endPoints.png)

---

### 2. **Cobertura y análisis estático**

- El análisis de cobertura se realizó con JaCoCo.
- El resultado actual de la cobertura:
![jacoco.png](images/jacoco.png)
- SonarQube:
![sonar.png](images/sonar.png)

---

### 3. **Ejecución de pruebas unitarias**
- Las pruebas se ejecutaron mediante Maven:
![terminalTest.png](images/terminalTest.png)