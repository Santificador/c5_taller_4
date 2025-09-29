# Coffee Sales App

Aplicación backend para la gestión de ventas de café desarrollada con Quarkus Framework.

## Requisitos previos

- JDK 17 o superior
- Maven 3.8+
- Docker y Docker Compose
- Postman (para pruebas)

## Configuración del proyecto

1. Clonar el repositorio:
```bash
git clone https://github.com/yourusername/coffee-sales-app.git
cd coffee-sales-app
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

- `GET /api/coffees` - Obtener todos los cafés
- `GET /api/coffees/{id}` - Obtener un café por ID
- `POST /api/coffees` - Crear un nuevo café
- `PUT /api/coffees/{id}` - Actualizar un café existente
- `DELETE /api/coffees/{id}` - Eliminar un café

### Gestión de Clientes

- `GET /api/customers` - Obtener todos los clientes
- `GET /api/customers/{id}` - Obtener un cliente por ID
- `POST /api/customers` - Crear un nuevo cliente
- `PUT /api/customers/{id}` - Actualizar un cliente existente
- `DELETE /api/customers/{id}` - Eliminar un cliente

### Gestión de Ventas

- `GET /api/sales` - Obtener todas las ventas
- `GET /api/sales/{id}` - Obtener una venta por ID
- `POST /api/sales` - Registrar una nueva venta
- `GET /api/sales/customer/{customerId}` - Obtener ventas por cliente
- `GET /api/sales/coffee/{coffeeId}` - Obtener ventas por tipo de café
- `GET /api/sales/date-range?startDate=&endDate=` - Obtener ventas por rango de fechas
- `GET /api/sales/revenue?startDate=&endDate=` - Calcular ingresos totales por período
- `GET /api/sales/revenue/customer/{customerId}` - Calcular ingresos por cliente

## Ejemplos de uso con Postman

### Crear un nuevo café

```http
POST http://localhost:8080/api/coffees
Content-Type: application/json

{
    "name": "Café Colombia",
    "description": "Café de origen colombiano, sabor suave y aromático",
    "price": 12.99,
    "origin": "Colombia",
    "roastType": "Medium"
}
```

### Crear un nuevo cliente

```http
POST http://localhost:8080/api/customers
Content-Type: application/json

{
    "firstName": "Juan",
    "lastName": "Pérez",
    "email": "juan.perez@email.com",
    "phoneNumber": "+1234567890",
    "address": "Calle Principal 123"
}
```

### Registrar una venta

```http
POST http://localhost:8080/api/sales
Content-Type: application/json

{
    "coffee": {
        "id": 1
    },
    "customer": {
        "id": 1
    },
    "quantity": 2
}
```

## Estructura del proyecto

```
coffee-sales-app/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── coffeesales/
│       │           ├── controller/
│       │           ├── model/
│       │           ├── repository/
│       │           └── service/
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
docker build -t coffee-sales-app .
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
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.