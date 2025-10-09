# Patrón Repository en Java con Quarkus

## 1. Introducción

### ¿Qué es el patrón Repository?

El patrón Repository es un patrón de diseño que actúa como una capa de abstracción entre la lógica de negocio de una aplicación y la capa de acceso a datos. Su principal objetivo es encapsular la lógica necesaria para acceder a las fuentes de datos, proporcionando una interfaz más orientada a objetos para trabajar con los datos.

En términos simples, el Repository funciona como una colección en memoria de objetos del dominio, ocultando los detalles de cómo se almacenan, recuperan o persisten estos objetos en la base de datos.

### Problema que resuelve

El patrón Repository resuelve varios problemas fundamentales en el desarrollo de aplicaciones:

- **Acoplamiento directo con la tecnología de persistencia**: Sin este patrón, la lógica de negocio estaría fuertemente acoplada a la tecnología específica de base de datos (JPA, JDBC, etc.), dificultando cambios futuros.

- **Duplicación de código**: Las consultas y operaciones de acceso a datos tienden a repetirse en diferentes partes de la aplicación.

- **Dificultad para realizar pruebas**: Al tener la lógica de acceso a datos dispersa, es complicado crear pruebas unitarias efectivas.

- **Violación del principio de responsabilidad única**: Las clases de servicio terminan mezclando lógica de negocio con lógica de acceso a datos.

### Ejemplo conceptual del flujo

El flujo típico en una arquitectura con el patrón Repository sigue esta estructura:

```
Controller/Resource (REST API)
         ↓
    Service (Lógica de Negocio)
         ↓
    Repository (Acceso a Datos)
         ↓
    Entity (Modelo de Datos)
         ↓
    Base de Datos
```

**Flujo de ejemplo - Consulta de un producto:**

1. El **Controller** recibe una petición HTTP para obtener un producto por ID
2. El **Service** recibe la solicitud y aplica reglas de negocio si es necesario
3. El **Repository** ejecuta la consulta a la base de datos utilizando el ID
4. La **Entity** (Producto) se mapea desde la base de datos
5. Los datos regresan por el mismo flujo hasta llegar al Controller que responde al cliente

---

## 2. Ventajas del Patrón Repository

### 1. Separación de responsabilidades (Separation of Concerns)

El patrón Repository mantiene la lógica de acceso a datos completamente separada de la lógica de negocio. Esto permite que los servicios se concentren exclusivamente en las reglas del negocio, mientras que los repositorios manejan todas las operaciones de persistencia.

**Ejemplo práctico:** Un servicio de facturación no necesita saber si los datos se almacenan en PostgreSQL, MongoDB o cualquier otra base de datos.

### 2. Facilita las pruebas unitarias (Testability)

Al abstraer el acceso a datos detrás de interfaces, es extremadamente fácil crear implementaciones mock o stub para las pruebas unitarias, sin necesidad de una base de datos real.

**Ventaja en Quarkus:** Con la inyección de dependencias de CDI, podemos reemplazar fácilmente las implementaciones reales por mocks en nuestros tests.

### 3. Reutilización de código

Las consultas y operaciones comunes se centralizan en un único lugar, evitando la duplicación de código a lo largo de toda la aplicación.

**Ejemplo:** Un método `findByEstado(String estado)` puede ser utilizado por múltiples servicios sin necesidad de reescribir la consulta.

### 4. Mantenibilidad y evolución del código

Cuando necesitamos modificar la forma en que accedemos a los datos (cambiar consultas, optimizar queries, cambiar de ORM), solo debemos modificar el Repository, sin tocar la lógica de negocio.

**Escenario real:** Si decidimos agregar caché a nivel de repositorio, el cambio es transparente para todas las capas superiores.

### 5. Aprovechamiento de Panache en Quarkus

Quarkus ofrece Panache, que simplifica enormemente el patrón Repository eliminando código boilerplate. Con Panache obtenemos:

- Métodos CRUD predefinidos (persist, delete, find, list)
- Query methods simplificados
- Active Record o Repository pattern a elección
- Paginación integrada
- Compatibilidad total con Hibernate pero con sintaxis más limpia

---

## 3. Desventajas del Patrón Repository

### 1. Complejidad adicional en proyectos pequeños (Over-engineering)

Para aplicaciones simples o prototipos rápidos, implementar el patrón Repository puede agregar capas innecesarias de abstracción. 

**Problema:** En un CRUD básico con 3-4 entidades, crear repositorios, interfaces y servicios puede resultar en más código del necesario, ralentizando el desarrollo inicial.

**Cuándo ocurre:** Proyectos con menos de 5 entidades, sin lógica de negocio compleja, o aplicaciones de corta vida útil.

### 2. Posible impacto en el rendimiento

Cada capa adicional de abstracción introduce un pequeño overhead. Además, si no se diseña correctamente, puede llevar a problemas como el "N+1 query problem" o la carga ineficiente de datos.

**Ejemplo concreto:** Si el Repository no utiliza correctamente las estrategias de fetch (EAGER/LAZY) o no implementa joins apropiados, podemos terminar con múltiples consultas a la base de datos cuando una sola bastaría.

**En Quarkus:** Aunque Panache optimiza muchas operaciones, todavía es responsabilidad del desarrollador escribir queries eficientes para casos complejos.

### 3. Curva de aprendizaje y convenciones del equipo

El equipo de desarrollo debe comprender claramente cuándo usar el Repository, cuándo usar el Service, y qué responsabilidades tiene cada capa.

**Problemas comunes:**
- Desarrolladores que colocan lógica de negocio en el Repository
- Servicios que ejecutan queries directamente, ignorando el Repository
- Inconsistencia en la nomenclatura de métodos entre diferentes repositorios

**Solución:** Requiere documentación clara, code reviews estrictos y acuerdos de equipo sobre las convenciones a seguir.

---

## 4. Aplicación del Patrón en tu Proyecto

### Nombre del proyecto

**Sistema de Ventas de Café**

### Descripción de la entidad principal

La entidad principal es `Venta`, que representa las transacciones de venta de café realizadas en el sistema. Esta entidad contiene información sobre el café vendido, el cliente que realizó la compra, la cantidad de productos, el precio unitario y el monto total de la transacción.

La entidad `Venta` tiene relaciones con las entidades `Cafe` (producto vendido) y `Cliente` (comprador), y calcula automáticamente el monto total antes de persistirse en la base de datos mediante el método anotado con `@PrePersist`.

### Código del Repositorio

#### Entidad: Libro

```java
package com.bibliotech.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "libros")
public class Libro extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(nullable = false, length = 150)
    private String autor;

    @Column(unique = true, nullable = false, length = 13)
    private String isbn;

    @Column(nullable = false, length = 100)
    private String categoria;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    @Column(name = "copias_disponibles")
    private Integer copiasDisponibles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoLibro estado;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public Integer getCopiasDisponibles() {
        return copiasDisponibles;
    }

    public void setCopiasDisponibles(Integer copiasDisponibles) {
        this.copiasDisponibles = copiasDisponibles;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    public void setEstado(EstadoLibro estado) {
        this.estado = estado;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    // Enum para el estado del libro
    public enum EstadoLibro {
        DISPONIBLE,
        PRESTADO,
        RESERVADO,
        EN_REPARACION,
        DADO_DE_BAJA
    }
}
```

#### Repositorio: LibroRepository

```java
package com.bibliotech.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import com.bibliotech.model.Libro;
import com.bibliotech.model.Libro.EstadoLibro;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LibroRepository implements PanacheRepository<Libro> {

    /**
     * Busca libros por categoría
     * @param categoria Categoría del libro (ej: "Ficción", "Ciencia", "Historia")
     * @return Lista de libros que pertenecen a la categoría especificada
     */
    public List<Libro> findByCategoria(String categoria) {
        return list("categoria", categoria);
    }

    /**
     * Busca libros por autor
     * @param autor Nombre del autor
     * @return Lista de libros del autor especificado
     */
    public List<Libro> findByAutor(String autor) {
        return list("autor", autor);
    }

    /**
     * Busca libros disponibles para préstamo
     * @return Lista de libros con estado DISPONIBLE y copias disponibles > 0
     */
    public List<Libro> findLibrosDisponibles() {
        return list("estado = ?1 and copiasDisponibles > 0", EstadoLibro.DISPONIBLE);
    }

    /**
     * Busca un libro por su ISBN
     * @param isbn Código ISBN del libro
     * @return Optional con el libro si existe
     */
    public Optional<Libro> findByIsbn(String isbn) {
        return find("isbn", isbn).firstResultOptional();
    }

    /**
     * Busca libros por título (búsqueda parcial, case insensitive)
     * @param titulo Parte del título a buscar
     * @return Lista de libros cuyo título contiene el texto especificado
     */
    public List<Libro> searchByTitulo(String titulo) {
        return list("lower(titulo) like lower(?1)", "%" + titulo + "%");
    }

    /**
     * Obtiene libros publicados después de un año específico
     * @param anio Año mínimo de publicación
     * @return Lista de libros publicados después del año especificado
     */
    public List<Libro> findByAnioPublicacionMayorA(Integer anio) {
        return list("anioPublicacion > ?1", anio);
    }

    /**
     * Cuenta la cantidad de libros por categoría
     * @param categoria Categoría a contar
     * @return Cantidad de libros en la categoría
     */
    public long countByCategoria(String categoria) {
        return count("categoria", categoria);
    }

    /**
     * Busca libros por estado
     * @param estado Estado del libro (DISPONIBLE, PRESTADO, etc.)
     * @return Lista de libros con el estado especificado
     */
    public List<Libro> findByEstado(EstadoLibro estado) {
        return list("estado", estado);
    }

    /**
     * Actualiza el estado de un libro
     * @param id ID del libro
     * @param nuevoEstado Nuevo estado a asignar
     * @return true si se actualizó correctamente
     */
    public boolean actualizarEstado(Long id, EstadoLibro nuevoEstado) {
        return update("estado = ?1 where id = ?2", nuevoEstado, id) > 0;
    }

    /**
     * Decrementa las copias disponibles de un libro (cuando se presta)
     * @param id ID del libro
     * @return true si se actualizó correctamente
     */
    public boolean decrementarCopias(Long id) {
        return update("copiasDisponibles = copiasDisponibles - 1 where id = ?1 and copiasDisponibles > 0", id) > 0;
    }

    /**
     * Incrementa las copias disponibles de un libro (cuando se devuelve)
     * @param id ID del libro
     * @return true si se actualizó correctamente
     */
    public boolean incrementarCopias(Long id) {
        return update("copiasDisponibles = copiasDisponibles + 1 where id = ?1", id) > 0;
    }
}
```

### Ejemplo de uso en un Service

```java
package com.bibliotech.service;

import com.bibliotech.model.Libro;
import com.bibliotech.model.Libro.EstadoLibro;
import com.bibliotech.repository.LibroRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LibroService {

    @Inject
    LibroRepository libroRepository;

    /**
     * Obtiene todos los libros disponibles para préstamo
     */
    public List<Libro> obtenerLibrosDisponibles() {
        return libroRepository.findLibrosDisponibles();
    }

    /**
     * Busca libros por categoría
     */
    public List<Libro> buscarPorCategoria(String categoria) {
        return libroRepository.findByCategoria(categoria);
    }

    /**
     * Registra un nuevo libro en el sistema
     */
    @Transactional
    public Libro registrarLibro(Libro libro) {
        // Lógica de negocio: validar que no exista un libro con el mismo ISBN
        Optional<Libro> existente = libroRepository.findByIsbn(libro.getIsbn());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un libro con el ISBN: " + libro.getIsbn());
        }
        
        // Establecer valores por defecto
        libro.setEstado(EstadoLibro.DISPONIBLE);
        libro.setFechaRegistro(java.time.LocalDate.now());
        
        libroRepository.persist(libro);
        return libro;
    }

    /**
     * Procesa el préstamo de un libro
     */
    @Transactional
    public boolean prestarLibro(Long libroId) {
        Optional<Libro> libroOpt = libroRepository.findByIdOptional(libroId);
        
        if (libroOpt.isEmpty()) {
            throw new IllegalArgumentException("Libro no encontrado");
        }
        
        Libro libro = libroOpt.get();
        
        // Validar que el libro esté disponible
        if (libro.getEstado() != EstadoLibro.DISPONIBLE || libro.getCopiasDisponibles() <= 0) {
            return false;
        }
        
        // Decrementar copias disponibles
        boolean actualizado = libroRepository.decrementarCopias(libroId);
        
        // Si no quedan más copias, cambiar el estado
        if (actualizado && libro.getCopiasDisponibles() - 1 == 0) {
            libroRepository.actualizarEstado(libroId, EstadoLibro.PRESTADO);
        }
        
        return actualizado;
    }
}
```

### Configuración necesaria en application.properties

```properties
# Configuración de base de datos
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=bibliotech_user
quarkus.datasource.password=bibliotech_pass
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/bibliotech_db

# Hibernate
quarkus.hibernate-orm.database.generation=update
quarkus.hibernate-orm.log.sql=true
```

---

## Conclusión

El patrón Repository en Quarkus, especialmente cuando se combina con Panache, ofrece una forma elegante y eficiente de manejar el acceso a datos. Si bien agrega una capa de abstracción adicional, los beneficios en términos de mantenibilidad, testabilidad y separación de responsabilidades justifican ampliamente su uso en proyectos de mediana a gran escala.

En el proyecto BiblioTech, el patrón Repository nos permite mantener toda la lógica de acceso a datos de los libros centralizada, facilitando futuras modificaciones y asegurando que la lógica de negocio permanezca limpia y enfocada en las reglas del dominio de la biblioteca.