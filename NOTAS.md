¿Cuál es la raíz del Agregado Investigador?  
La raíz del agregado es la clase Investigador, que es la única marcada con @Entity dentro del paquete investigadores.

¿Qué vive dentro del límite?  
Los campos que forman parte del agregado son:

id (identidad de la entidad), nombreCompleto, correoInstitucional (ahora un Value Object que valida el dominio @uptc.edu.co), grupoInvestigacion

¿Por qué Publicacion NO está dentro de este límite?  
Una publicación pertenece a otro agregado independiente. Un investigador puede tener muchas publicaciones, y meterlas dentro del agregado violaría la regla de Vernon de mantener agregados pequeños. Además, cada publicación se gestiona en MongoDB, lo que permite que evolucionen y se desplieguen de forma independiente.

¿Qué pasaría si alguien agrega un campo List<Publicacion> publicaciones directo en Investigador?  
Se rompería el límite del agregado: dos transacciones sobre el mismo investigador podrían pisarse la lista de publicaciones de otra persona, el agregado dejaría de ser pequeño y publicaciones perdería independencia para evolucionar o desplegarse. Esto generaría cargas y transacciones enormes en cada cambio a un investigador.