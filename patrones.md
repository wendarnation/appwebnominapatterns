# Patrones de Diseño Implementados



## 1. **Principio de Responsabilidad Única (SRP)**

El **Principio de Responsabilidad Única** establece que cada clase debe tener una única razón para cambiar, es decir, debe tener una única responsabilidad.

- **EmpleadoController**: Este controlador se encarga únicamente de la lógica de presentación, delegando la lógica de negocio al servicio `EmpleadoService`. Las funciones como `doGet` y `doPost` manejan el enrutamiento y control de flujo de las solicitudes (como `mostrarEmpleados` y `buscarSalario`), pero no realizan ninguna operación de negocio ni cálculo.
  
- **EmpleadoDAO**: La clase `EmpleadoDAO` está encargada únicamente de la interacción con la base de datos para la entidad `Empleado`. Su responsabilidad se limita al acceso y manipulación de datos, separando esta lógica de la lógica de negocio.

## 2. **Principio de Inversión de Dependencia (DIP)**

El **Principio de Inversión de Dependencia** sugiere que las clases de alto nivel no deben depender de las clases de bajo nivel, sino de abstracciones (interfaces o clases abstractas).

- **EmpleadoController y EmpleadoService**: `EmpleadoController` depende de la interfaz `IEmpleadoDAO` en lugar de una implementación concreta de acceso a datos. Esta inyección de dependencias permite cambiar la implementación del acceso a datos sin afectar la lógica del controlador o servicio.

## 3. **Patrón de Inyección de Dependencias (DI)**

El **Patrón de Inyección de Dependencias** facilita la configuración y gestión de las dependencias en la aplicación.

- **EmpleadoService**: Este servicio recibe una instancia de `IEmpleadoDAO` a través de su constructor. Esto permite la flexibilidad de cambiar la implementación de `EmpleadoDAO` sin modificar el código de `EmpleadoService`, promoviendo la separación de responsabilidades y facilitando el mantenimiento.

## 4. **Patrón DAO (Data Access Object)**

El **Patrón DAO** es utilizado para separar la lógica de acceso a datos de la lógica de negocio.

- **EmpleadoDAO e IEmpleadoDAO**: `EmpleadoDAO` implementa la interfaz `IEmpleadoDAO`, proporcionando métodos para realizar operaciones de actualización, consulta, eliminación y persistencia de datos en la base de datos. Este patrón ayuda a desacoplar la lógica de acceso a datos de la lógica de negocio, lo que facilita la reutilización y mantenimiento del código.

## 5. **Patrón DTO (Data Transfer Object)**

El **Patrón DTO** se utiliza para transferir datos entre capas de la aplicación sin exponer directamente los detalles de la entidad o modelo de datos.

- **EmpleadoDTO**: Este objeto es utilizado para transferir datos de la entidad `Empleado` entre la capa de presentación y la capa de negocio. `EmpleadoDTO` proporciona una representación más segura y controlada de los datos sin revelar detalles de la entidad directamente.

## 6. **Patrón de Controlador Frontal (Front Controller)**

El **Patrón Front Controller** centraliza el control de las solicitudes en un solo punto, facilitando la gestión de las peticiones.

- **EmpleadoController**: Actúa como el controlador principal para todas las solicitudes relacionadas con `Empleado`. Se encarga de enrutar las solicitudes, delegando el manejo de las operaciones a las capas correspondientes (presentación, negocio, persistencia).

## 7. **Principio de Abierto/Cerrado (OCP)**

El **Principio de Abierto/Cerrado** sugiere que una clase debe estar abierta para su extensión, pero cerrada para su modificación.

- **Interfaces IEmpleadoDAO e IEmpleadoService**: Las interfaces `IEmpleadoDAO` e `IEmpleadoService` permiten extender la funcionalidad del acceso a datos y la lógica de negocio sin modificar el código existente. Si es necesario agregar nuevas funcionalidades, se pueden crear nuevas implementaciones sin afectar el código que ya está en uso.

## 8. **Patrón Singleton**

El **Patrón Singleton** asegura que una clase tenga una única instancia a lo largo de la ejecución del programa y proporciona un punto de acceso global a esa instancia.

- **Conexion**: Solo hay una instancia de la clase `Conexion` en toda la aplicación, lo que ayuda a administrar mejor los recursos (en este caso, las conexiones a la base de datos).

## Conclusión

Al implementar estos patrones de diseño, el proyecto se vuelve más modular, flexible y fácil de mantener. Cada patrón contribuye a la separación de responsabilidades, la facilidad de extensión y la reducción del acoplamiento entre las diferentes capas de la aplicación.
