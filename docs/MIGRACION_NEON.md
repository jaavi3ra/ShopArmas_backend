# Migración de MySQL a Neon (PostgreSQL), Despliegue y Monitoreo

## 1. Conexión directa vs. pooling en Neon
- **`DB_URL` (conexión con pooling / `-pooler`)**: usada por la aplicación (`spring.datasource.url` / HikariCP) para atender tráfico en tiempo de ejecución.
- **`DB_DIRECT_URL` (conexión directa sin `-pooler`)**: usada por Flyway (`spring.flyway.url`) para ejecutar migraciones y adquirir bloqueos DDL sin interferencias del pooler de transacciones (PgBouncer).

## 2. Procedimiento de migración y validación (MySQL -> Neon)
No se modifican datos productivos ni se reemplaza la conexión de producción sin autorización explícita. Para migrar datos existentes desde el contenedor MySQL legado (`mysql` / volumen `mysql_data`):

1. **Respaldar MySQL actual**:
   ```bash
   docker compose --profile legacy-mysql up -d mysql
   docker compose exec mysql mysqldump -u gta_app -p tienda_armas_gta > backup_mysql_$(date +%F).sql
   ```
2. **Inicializar esquema en una base/branch vacía de Neon**:
   Al arrancar la aplicación apuntando a Neon (`DB_URL` y `DB_DIRECT_URL`), Flyway ejecuta automáticamente `V1__init_schema.sql` y `V2__seed_initial_data.sql`.
3. **Migrar datos existentes (si hay datos de usuarios/ventas adicionales al seed inicial)**:
   - Usar `pgloader` o exportar cada tabla desde MySQL a CSV respetando el orden de dependencias (`Rol`, `Usuario`, `EstadoArma`, `Categoria`, `Subcategoria`, `Producto`, `ImagenProducto`, `Favorito`, `Region`, `Comuna`, `Direccion`, `EstadoVenta`, `MetodoEnvio`, `Venta`, `DetalleVenta`, `Cartera`) evitando duplicar los catálogos sembrados por `V2__seed_initial_data.sql`.
   - Actualizar las secuencias `IDENTITY` en PostgreSQL después de importar IDs explícitos:
     ```sql
     SELECT setval(pg_get_serial_sequence('usuario', 'id_usuario'), COALESCE(MAX(id_usuario), 1)) FROM Usuario;
     SELECT setval(pg_get_serial_sequence('producto', 'id_producto'), COALESCE(MAX(id_producto), 1)) FROM Producto;
     SELECT setval(pg_get_serial_sequence('venta', 'id_venta'), COALESCE(MAX(id_venta), 1)) FROM Venta;
     ```
4. **Validación previa al corte**:
   - Comparar conteo de filas por tabla entre MySQL y Neon.
   - Validar `GET /health` -> HTTP `200` (`{"status":"UP"}`).
   - Probar lectura y escritura de prueba en la API antes de cambiar la conexión productiva.
   - Conservar el contenedor y volumen `mysql_data` hasta confirmar la transición.

## 3. Requisitos pendientes para publicar por HTTPS y activar UptimeRobot
Para exponer `https://<dominio>/health` y activar el monitor en UptimeRobot hace falta:
1. Desplegar el contenedor en un hosting/servidor con IP pública o plataforma PaaS (por ejemplo Render, Railway, Fly.io, Cloud Run o VPS con Caddy/Nginx + Let's Encrypt).
2. Configurar un dominio con certificado TLS válido (HTTPS) enrutando al puerto `8080` del contenedor.
3. Definir las variables `DB_URL`, `DB_DIRECT_URL`, `DB_USERNAME` y `DB_PASSWORD` de Neon en el entorno de producción.
4. Crear el monitor en UptimeRobot con `monitoring/uptimerobot-monitor.json` (reemplazando `<UPTIMEROBOT_API_KEY>` y `<DOMINIO_PUBLICO>`):
   ```bash
   curl -X POST https://api.uptimerobot.com/v2/newMonitor \
     -H "Content-Type: application/json" \
     -d @monitoring/uptimerobot-monitor.json
   ```
   *(En UptimeRobot `type: 1` corresponde a HTTP(s), `http_method: 2` a `GET` y `keyword_type: 2` verifica que la respuesta contenga `"status":"UP"`.)*
