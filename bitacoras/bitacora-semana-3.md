# Bitácora — Semana 3

## 1. Datos de la actividad

- **Estudiante:** Juan Marcos Beltran
- **Semana:** 3
- **Tema principal:** Encontrar un dato entre un millon


---

## 2. Casos de prueba mínimos (paso 37)


| Caso |    Tamaño | Posición esperada | Lineal (resultado) | Lineal (comparaciones) | Binaria (resultado) | Binaria (comparaciones) | Estado |
|---|----------:|------------------:|-------------------:|-----------------------:|--------------------:|---:|---|
| Primer elemento |        10 |                 0 |                  0 |                      1 |                   0 | 3 | OK |
| Elemento intermedio |        10 |                 5 |                  5 |                      6 |                   5 | 3 | OK |
| Último elemento |        10 |                 9 |                  9 |                     10 |                   9 | 4 | OK |
| Elemento inexistente |        10 |                -1 |                 -1 |                     10 |                  -1 | 4 | OK |
| Primer elemento |   1000000 |                 0 |                  0 |                      1 |                   0 | 19 | OK |
| Elemento intermedio |   1000000 |            500000 |             500000 |                 500001 |              500000 | 19 | OK |
| Último elemento |   1000000 |            999999 |             999999 |                1000000 |             999.999 | 20 | OK |
| Elemento inexistente |   1000000 |                -1 |                 -1 |                1000000 |                  -1 | 20 | OK |


---

## 3. Tabla de mediciones (paso 38)

| Tamaño | Lineal (comparaciones) | Binaria (comparaciones) | Relación lineal / binaria |        Tiempo lineal (µs) |         Tiempo binaria (µs) |
|---:|---:|---:|---:|--------------------------:|----------------------------:|
| 1.000 | 1.000 | 10 | 100,0 |                      28,1 |                        39,7 |
| 100.000 | 100.000 | 17 | 5.882,4 |                    4200,6 |                        42,9 |
| 1.000.000 | 1.000.000 | 20 | 50.000,0 |                          63646,2  |                             57,5 |

---

## 4. Traza de la búsqueda binaria (paso 39)

 Arreglo `[0, 1, 2, 3]`, objetivo `3`.

### 4.1 Versión incorrecta (`inicio = medio`)



| Paso |        inicio | fin |         medio |   valor medio | comparación | acción              |
|---|--------------:|----:|--------------:|--------------:|---|---------------------|
| 1 |             0 |   3 |             1 |             1 | 1 < 3 | inicio = medio -> 1 |
| 2 |             1 |   3 |             2 |             2 | 2 < 3 | inicio = medio -> 2 |
| 3 | 2 |   3 | 2 |             2 | 2 < 3 | inicio = medio -> 2 |
| 4 | 2|   3 | 2|             2 | 2 < 3| inicio = medio -> 2 |



### 4.2 Versión corregida (`inicio = medio + 1`)

| Paso | inicio | fin | medio | valor medio | comparación | acción                  |
|---|-------:|---:|------:|------------:|-------------|-------------------------|
| 1 |      0 | 3	 |     1 |           1 | 1 < 3       | inicio = medio +1 -> 2  |
| 2 |      2 | 3	|     2 |           2 | 2 < 3       | inicio = medio + 1 -> 3 |
| 3 |      3	 | 3	|     3	 |           3	 | 3 = 3       | devuelve 3              |

---

## 5. Preguntas de pensamiento crítico (paso 44)

Responder con argumentos, no solo con respuestas técnicas o de código.

### Pregunta 1

Una empresa tiene un millón de registros y realiza únicamente cinco búsquedas durante todo el día. ¿Tiene sentido diseñar toda la estrategia de almacenamiento alrededor de una búsqueda binaria? ¿Qué otros costos o factores considerarías?

**Respuesta:** No creo que valga la pena organizar todo alrededor de la busqueda binaria porque para poder usarla hay que ordenar los datos, y ordenar un millón de registros cuesta mas que hacer cinco busquedas lineales.

### Pregunta 2

Un algoritmo puede ser mucho más rápido que otro y, sin embargo, producir una respuesta incorrecta. ¿Por qué consideras que la corrección debe analizarse antes que la eficiencia?

**Respuesta:** Un algoritmo que no encuentra datos que sí existen es peor que uno lento que siempre acierta, como paso con la binaria por PM2.5. Primero hay que comprobar que el resultado sea correcto y después preocuparse por la velocidad.

### Pregunta 3

Imagina que una plataforma consulta constantemente por `timestamp`, pero ocasionalmente necesita consultar por `PM2.5`. ¿Qué consecuencias tendría organizar los datos pensando principalmente en uno de estos campos? No respondas solamente desde el código: considera el funcionamiento de la plataforma.

**Respuesta:** Si ordeno los datos por timestamp, las consultas principales serian muy rapidas con busqueda binaria, pero las de PM2.5 tendrian que ser lineales. En cambio si ordenara por PM2.5, perderia la ventaja en la consulta que más se usa. Lo logico seria optimizar el campo que mas se consulta y aceptar que el otro sea mas lento.

### Pregunta 4

Supón que tienes un conjunto de datos perfectamente ordenado y alguien modifica algunos registros sin conservar el orden. ¿Qué riesgos aparecen si el sistema continúa utilizando búsqueda binaria sin verificar las condiciones de los datos?

**Respuesta:** La busqueda binaria empezaria a fallar sin avisar y podría decir que un dato no existe cuando sí está. Nadie notaria el error porque el programa no se cae, solo da resultados falsos. Por eso habria que verificar el orden antes de usarla.

### Pregunta 5

En ingeniería de software suele decirse: *"Que funcione no significa que sea una buena solución."* Relaciona esta afirmación con lo aprendido en las semanas 1, 2 y 3 del proyecto. ¿Qué ha cambiado en la manera en que analizas una solución desde que comenzó el proyecto?
 
**Respuesta:** Antes quedaba contento con que el programa corriera y diera el resultado, pero ahora me pregunto cuanto cuesta, con cuantos datos deja de servir y que condiciones necesita para ser correcto.

---

## 6. Uso de la IA

Para este taller use Claude como apoyo desde el paso 37 de la guia. Me ayudo a entender que se pedia y me dio el codigo de pruebasMinimas() y de la medicion de tiempos en experimentoDos(), que integre manualmente. Tambien me explico como hacer la traza de la busqueda binaria, me dio un ejemplo de tabla y una plantilla de bitacora. Los pasos 1 al 36, las mediciones y las conclusiones son mios, y verifique el codigo ejecutandolo.
