Smart Campus API

Práctica 1 de Arquitectura y Servicios de Internet, Máster UBU.
Autor: Noel Del Río González.

Qué es

Es una API REST que guarda y consulta los sensores, las aulas, las medidas y las incidencias de un campus universitario.
No tiene pantallas: se usa enviando peticiones y recibe las respuestas en JSON. Está hecha con Java 21 y Spring Boot 3.5,
y los datos se guardan en una base de datos MySQL 8.4 que se arranca con Docker.

El código está separado en tres capas. El controlador recibe la petición y comprueba los datos, el servicio aplica las
reglas y el repositorio lee y escribe en la base de datos.

Qué hace falta

Java 21 y Docker Desktop. Maven no hace falta, porque el proyecto lo descarga solo la primera vez.

Cómo arrancarlo

Primero hay que abrir Docker Desktop y esperar a que esté en marcha. Después, en una terminal dentro de esta carpeta,
se escribe docker compose up -d --wait para arrancar la base de datos. La primera vez crea las tablas y mete los datos
de ejemplo de la carpeta sql, que tiene que estar al lado de esta.

Luego se arranca la API escribiendo .\mvnw.cmd spring-boot:run en la misma terminal. Cuando sale el mensaje Started
SmartCampusApplication, ya está funcionando en la dirección http://localhost:8080.

Para dejar la base de datos como al principio se escribe docker compose down -v y después otra vez docker compose up
-d --wait.

Cómo probarlo

La forma más cómoda es con Postman. Se importa el archivo SmartCampus.postman_collection.json de la carpeta postman y
se lanza la colección entera con Run collection. Tienen que salir todas las comprobaciones bien.

También se puede abrir http://localhost:8080/swagger-ui.html en el navegador, donde salen todas las operaciones y se
pueden probar desde ahí.

Qué operaciones tiene

Para sensores, aulas e incidencias se puede listar todo, ver uno por su número, crear, modificar y borrar. Las
direcciones son /api/sensors, /api/classrooms y /api/incidents.

Las medidas siempre son de un sensor. Se guardan y se consultan en /api/sensors/1/measurements, cambiando el 1 por el
número del sensor, y en /api/measurements se ven todas.

Si todo va bien, la API responde 200 al consultar o modificar, 201 al crear y 204 al borrar. Si hay un problema
responde 400 cuando los datos no son válidos, 404 cuando no existe lo que se busca y 409 cuando choca con algo que ya
existe.

Decisiones que he tomado

El enunciado no decía cómo tenían que ser las operaciones de las medidas. Las he puesto dentro de cada sensor porque una
medida siempre pertenece a uno.

Una incidencia nueva siempre empieza abierta y con la fecha del momento, así que para crearla solo se manda el título
y la descripción. Para cerrarla se modifica y se le pone el estado CLOSED, así se queda guardada en el histórico.

Los estados y los tipos tienen valores fijos para que no se cuelen datos raros. Un sensor puede estar ACTIVE, INACTIVE o
MAINTENANCE, y ser de tipo TEMPERATURE, HUMIDITY, CO2, OCCUPANCY o LIGHT. Una incidencia puede estar OPEN,
IN_PROGRESS o CLOSED.

Un sensor que ya tiene medidas no se puede borrar, para no perder sus datos. Si ya no se usa, se pasa a INACTIVE.
