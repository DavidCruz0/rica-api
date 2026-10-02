---------LECCION 3(Taller DDD) ---------

-¿Cuál es la raíz del Agregado Investigador?  
La raíz del agregado es la clase Investigador, que es la única marcada con @Entity dentro del paquete investigadores.

-¿Qué vive dentro del límite?  
Los campos que forman parte del agregado son:

id (identidad de la entidad), nombreCompleto, correoInstitucional (ahora un Value Object que valida el dominio @uptc.edu.co), grupoInvestigacion

-¿Por qué Publicacion NO está dentro de este límite?  
Una publicación pertenece a otro agregado independiente. Un investigador puede tener muchas publicaciones, y meterlas dentro del agregado violaría la regla de Vernon de mantener agregados pequeños. Además, cada publicación se gestiona en MongoDB, lo que permite que evolucionen y se desplieguen de forma independiente.

-¿Qué pasaría si alguien agrega un campo List<Publicacion> publicaciones directo en Investigador?  
Se rompería el límite del agregado: dos transacciones sobre el mismo investigador podrían pisarse la lista de publicaciones de otra persona, el agregado dejaría de ser pequeño y publicaciones perdería independencia para evolucionar o desplegarse. Esto generaría cargas y transacciones enormes en cada cambio a un investigador.


---------LECCION 4(Taller hexagonal) ---------

-InvestigadorRepository es una interfaz, nunca una clase concreta, desde el Tutorial 4. Según la guía (sección 3), ¿es un puerto primario o secundario? Justifica con una frase: ¿quién inicia la llamada, el núcleo o algo externo?
Es un puerto secundario ya que el núcleo inicia la llamada hacia el repositorio para consultar o guardar datos. El repositorio abstrae el acceso a la infraestructura (base de datos).

--InvestigadorController — ¿es un adaptador primario o secundario? ¿Qué tecnología concreta envuelve?
Adaptador primario ya que envuelve la tecnología HTTP/JSON de Spring MVC. Es la entrada al sistema desde el exterior y traduce las peticiones web hacia el puerto primario.

-InvestigadorService hoy es una clase concreta, no una interfaz. InvestigadorController la llama directamente. Según la nota de la guía (sección 6, "Lo que sí falta hoy en rica-api"), ¿qué pieza falta para que exista un puerto primario explícito?
Falta una clase concreta, no interfaz.  a.

-InvestigadorFactory del Taller de la Lección 3 — ¿pertenece al núcleo o a un adaptador? Pista: revisa sus import (sección 5 de la guía, "La prueba del núcleo limpio").
Núcleo pues depende solo de clases del dominio (`Investigador`, `CorreoInstitucional`) y del puerto secundario (`RepositorioInvestigadores`). No tiene dependencias de infraestructura como Spring MVC o JPA, por lo que pertenece al núcleo.

---------LECCION 5(Microservicios) ----------

