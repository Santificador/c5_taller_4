# Sistema de Ventas de Café

Sistema backend para la gestión de ventas de café desarrollado con Quarkus Framework.

## Requisitos previos

- JDK 17 o superior
- Maven 3.8+
- Docker y Docker Compose
- Postman (para pruebas)

## Configuración del proyecto

1. Clonar el repositorio:
```bash
git clone https://github.com/tuusuario/sistema-ventas-cafe.git
cd sistema-ventas-cafe
```

2. Compilar el proyecto:
```bash
mvn clean package
```

3. Iniciar la aplicación con Docker Compose:
```bash
docker-compose up -d
```

La aplicación estará disponible en `http://localhost:8080`

## Endpoints de la API

### Gestión de Cafés

- `GET /api/cafes` - Obtener todos los cafés
- `GET /api/cafes/{id}` - Obtener un café por ID
- `POST /api/cafes` - Crear un nuevo café
- `PUT /api/cafes/{id}` - Actualizar un café existente
- `DELETE /api/cafes/{id}` - Eliminar un café

### Gestión de Clientes

- `GET /api/clientes` - Obtener todos los clientes
- `GET /api/clientes/{id}` - Obtener un cliente por ID
- `POST /api/clientes` - Crear un nuevo cliente
- `PUT /api/clientes/{id}` - Actualizar un cliente existente
- `DELETE /api/clientes/{id}` - Eliminar un cliente

### Gestión de Ventas

- `GET /api/ventas` - Obtener todas las ventas
- `GET /api/ventas/{id}` - Obtener una venta por ID
- `POST /api/ventas` - Registrar una nueva venta
- `GET /api/ventas/cliente/{clienteId}` - Obtener ventas por cliente
- `GET /api/ventas/cafe/{cafeId}` - Obtener ventas por tipo de café
- `GET /api/ventas/rango-fechas?fechaInicio=&fechaFin=` - Obtener ventas por rango de fechas
- `GET /api/ventas/ingresos?fechaInicio=&fechaFin=` - Calcular ingresos totales por período
- `GET /api/ventas/ingresos/cliente/{clienteId}` - Calcular ingresos por cliente

## Ejemplos de uso con Postman

### Crear un nuevo café

```http
POST http://localhost:8080/api/cafes
Content-Type: application/json

{
    "nombre": "Café Colombia",
    "descripcion": "Café de origen colombiano, sabor suave y aromático",
    "precio": 12.99,
    "origen": "Colombia",
    "tipoTostado": "Medio"
}
```

### Crear un nuevo cliente

```http
POST http://localhost:8080/api/clientes
Content-Type: application/json

{
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan.perez@email.com",
    "telefono": "+1234567890",
    "direccion": "Calle Principal 123"
}
```

### Registrar una venta

```http
POST http://localhost:8080/api/ventas
Content-Type: application/json

{
    "cafe": {
        "id": 1
    },
    "cliente": {
        "id": 1
    },
    "cantidad": 2
}
```

## Estructura del proyecto

```
sistema-ventas-cafe/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── ventascafe/
│       │           ├── controlador/
│       │           ├── modelo/
│       │           ├── repositorio/
│       │           └── servicio/
│       └── resources/
│           └── application.properties
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```

## Desarrollo

Para ejecutar la aplicación en modo desarrollo:

```bash
./mvnw compile quarkus:dev
```

## Pruebas

Para ejecutar las pruebas:

```bash
./mvnw test
```

## Despliegue

1. Construir la imagen Docker:
```bash
docker build -t sistema-ventas-cafe .
```

2. Iniciar los servicios:
```bash
docker-compose up -d
```

3. Verificar los logs:
```bash
docker-compose logs -f app
```

4. Detener los servicios:
```bash
docker-compose down
```

## Contribución

1. Fork el repositorio
2. Crea una rama para tu función (`git checkout -b funcion/NuevaFuncion`)
3. Commit tus cambios (`git commit -m 'Agregar nueva función'`)
4. Push a la rama (`git push origin funcion/NuevaFuncion`)
5. Abre un Pull Request

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.