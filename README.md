# README #

Este LÉAME normalmente documentaría la guia de stilos de desarrollo de la aplicación 
y los pasos necesarios para poner en marcha su aplicación.

## Guía de estilo detallada para el desarrollo de la aplicación ##

La guía de estilos es una herramienta importante para mantener la coherencia y la calidad del código en una aplicación 
de Spring Boot. Esta guía establece las convenciones y las prácticas recomendadas para el desarrollo de la aplicación.

### Estructura del Proyecto 

Se va a utilizar una estructura de proyectos modular, la cual separa los modulos o contextos de la aplicación, 
cabe aclarar que debemos revisar si existen funcionalidades que esen fuerte acopladas no deberiamos separalas en modulos.
Por ejemplo: El caso de inventario va muy relacionado con productos, stock, variantes, adicionales.

- store_atc
    - src
        - main
            - java
                - com
                    - ecommerce
                        - store 
                          - SignatureBackApplication.java
                          - sale
                            - controller
                                - SaleController.class
                            - dto
                                - SaleCreateRequest.class
                            - entity
                                - Sale.class
                            - repository
                                - SaleRepository.class
                            - service
                                - SaleService.class
                                - impl
                                  - SaleServiceImpl.class 
            - resources
                - application.yml
    - docs
        - README.md

* Utilizar nombres claros y concisos para los paquetes, clases, métodos y variables.

### Convenciones de codificación ###
#### Utilizar el estilo de codificación convencional para Java, que incluye: ####

* Nombres de clase en CamelCase, comenzando con una letra mayúscula.
* Nombres de métodos y variables en camelCase, comenzando con una letra minúscula (Por ejemplo: getUserById()).
* La indentación de 4 espacios.
* Los nombres de las clases entidades deben ser sustantivos en singular y en mayúscula la primera letra de cada palabra (por ejemplo, User, Order).
* Para la base de datos las tablas en plural, y los atributos no deben agregar la anteposicion de la tabla a excepccion del ID.
* Definir un comentario a cada atributo de la tabla(Por ejemplo: g@Column(name = "user_id", columnDefinition = "COMMENT 'Identificador unico del usuario'"))
* Utilizar comentarios en el código para explicar la funcionalidad y el propósito de las clases, métodos y variables.
* Evitar líneas de código que sean demasiado largas (más de 120 caracteres).
* Utilizar excepciones de manera adecuada y manejarlas de forma apropiada. No ignorar o suprimir excepciones.
* Utilizar las anotaciones proporcionadas por Spring Framework para manejar la inyección de dependencias, la configuración y el mapeo de rutas.

#### Patrones de Diseño: ####

* Utilizar patrones de diseño comunes para mejorar la estructura y escalabilidad de la aplicación, como el patrón de Inyección de Dependencias y el patrón de Fábrica.
* Evitar utilizar patrones de diseño complejos o poco comunes, a menos que sea necesario para resolver un problema específico.
* Proporcionar difentes maneras de instanciar objetos, no utilizar seters a excepcion que se un caso especifico.

#### Rendimiento ####

* Asegurar que la aplicación sea eficiente y tenga un buen rendimiento.
* Utilizar las herramientas proporcionadas por Spring Framework,como la optimización de consultas, para mejorar el rendimiento de la aplicación.

#### Documentación ####

* Documentar en lo posible clases, metodos y variables
* Proporcionar documentación clara y detallada de la aplicación, incluyendo la estructura del proyecto, la configuración y los requisitos del sistema.
* Proporcionar documentación de los servicios de la API REST, incluyendo los parámetros de entrada, las respuestas y los códigos
