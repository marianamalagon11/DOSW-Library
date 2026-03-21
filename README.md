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

Este diagrama representa la arquitectura general del sistema de biblioteca.
Library Front simboliza la capa de presentación, es decir, la interfaz a través de la cual los usuarios interactúan enviando solicitudes (como registros o préstamos). Esta capa se comunica con Library Core, que contiene toda la lógica del negocio como gestión de usuarios, libros y préstamos. Finalmente, Library Core interactúa directamente con la base de datos (DB) para almacenar y recuperar la información necesaria. De este modo, se logra una separación clara entre la presentación, la lógica del dominio y el almacenamiento de datos.



---
### Diagrama de componentes especifico de la biblioteca
![dComponetesEspecífico.png](images/dComponetesEspec%C3%ADfico.png)

En este diagrama se detalla cómo se conectan los componentes internos para cada flujo.
Los controladores (UserController, BookController, LoanController) reciben las solicitudes desde el exterior y manejan los DTOs. Los controladores redirigen las operaciones a los servicios (UserService, BookService, LoanService), los cuales contienen las reglas y validaciones de negocio.
Antes de procesar la solicitud, los validadores (UserValidator, BookValidator, LoanValidator) aseguran que los datos cumplen con las reglas necesarias.
Los mapeadores (UserMapper, BookMapper, LoanMapper) facilitan la conversión entre entidades de dominio y DTOs, asegurando que solo la información específica y necesaria se exponga o se procese.
Este diseño modular promueve la mantenibilidad y la correcta separación de responsabilidades.

---
### Diagrama de clases
![DiagramaDeClases.png](images/DiagramaDeClases.png)

Este diagrama muestra las principales entidades del sistema:

Book: Representa los libros disponibles en la biblioteca, con atributos como id, title y author.
User: Modela los usuarios registrados, identificados principalmente por su id y name.
Loan: Representa cada préstamo de un libro a un usuario, asociando a ambos mediante los campos book y user. Además, mantiene información sobre las fechas de préstamo y devolución, estado del préstamo (status) e identificador único (id).
Las flechas indican las relaciones entre clases: un Loan siempre está vinculado a un Book y a un User, reflejando así cómo se modelan y relacionan los datos en la aplicación.

---
## Pruebas

---
## Evidencias de ejecución

### 1. **Ejecución de la API**
- La API se levantó correctamente usando Spring Boot.
- Endpoints disponibles:
![endpointsSwagger.png](images/endpointsSwagger.png)

Link del video de la ejecución:
https://youtu.be/e89HZGdI1U0
---

### 2. **Cobertura y análisis estático**

- El análisis de cobertura se realizó con JaCoCo.
- El resultado actual de la cobertura:
![jacoco.png](images/jacoco.png)
  El reporte de cobertura de pruebas fue generado usando la herramienta JaCoCo.
  La tabla muestra el porcentaje de instrucciones y ramas cubiertas por las pruebas unitarias y funcionales en las principales clases del sistema. Este análisis evidencia que los tests ejecutan más del 86% de las líneas de código, demostrando un nivel adecuado de cobertura y verificando el correcto funcionamiento de la lógica central de la biblioteca.
- SonarQube:
![sonar.png](images/sonar.png)
  SonarQube se utilizó para realizar un análisis estático del código, evaluando aspectos de seguridad, mantenibilidad y calidad general.
  Como se observa en el informe, no se detectaron vulnerabilidades ni puntos críticos (“bugs” o “code smells”).

---

### 3. **Ejecución de pruebas unitarias**
- Las pruebas se ejecutaron mediante Maven:
![terminalTest.png](images/terminalTest.png)