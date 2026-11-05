-- Base de datos única para Grove Market, tienda ficticia de armas de GTA.
-- Ejecutar en MySQL 8.0 o superior. Los saldos y productos son datos de demostración.

CREATE DATABASE IF NOT EXISTS tienda_armas_gta
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE tienda_armas_gta;

-- 1. Roles disponibles para las cuentas de la tienda.
CREATE TABLE Rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- 2. Cuentas de usuario, saldo ficticio y puntos RP.
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_rol INT NOT NULL,
    nombre_usuario VARCHAR(80) NOT NULL,
    correo VARCHAR(254) NOT NULL UNIQUE,
    contrasena_hash VARCHAR(255) NOT NULL,
    saldo DECIMAL(12,2) NOT NULL DEFAULT 100000,
    puntos_rp INT NOT NULL DEFAULT 0,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES Rol(id_rol),
    CONSTRAINT chk_usuario_saldo CHECK (saldo >= 0),
    CONSTRAINT chk_usuario_rp CHECK (puntos_rp >= 0)
);

-- 3. Estados de conservación de las armas/productos.
CREATE TABLE EstadoArma (
    id_estado_arma INT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- 4. Categorías que se usan como filtros en el catálogo de la página.
CREATE TABLE Categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- 5. Subcategorías dentro de cada categoría del catálogo.
CREATE TABLE Subcategoria (
    id_subcategoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_subcategoria VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    id_categoria INT NOT NULL,
    CONSTRAINT fk_subcategoria_categoria FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria),
    CONSTRAINT uq_subcategoria_categoria UNIQUE (nombre_subcategoria, id_categoria)
);

-- 6. Inventario: descripción, precio, unidades, categoría y estadísticas GTA.
CREATE TABLE Producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(150) NOT NULL,
    slug VARCHAR(160) NOT NULL UNIQUE,
    descripcion TEXT,
    precio DECIMAL(12,2) NOT NULL,
    puntos_rp INT NOT NULL DEFAULT 0,
    stock INT NOT NULL DEFAULT 0,
    id_estado_arma INT NOT NULL,
    id_subcategoria INT NOT NULL,
    dano TINYINT UNSIGNED NOT NULL DEFAULT 0,
    precision_arma TINYINT UNSIGNED NOT NULL DEFAULT 0,
    alcance TINYINT UNSIGNED NOT NULL DEFAULT 0,
    es_drop BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_producto_estado FOREIGN KEY (id_estado_arma) REFERENCES EstadoArma(id_estado_arma),
    CONSTRAINT fk_producto_subcategoria FOREIGN KEY (id_subcategoria) REFERENCES Subcategoria(id_subcategoria),
    CONSTRAINT chk_producto_precio CHECK (precio >= 0),
    CONSTRAINT chk_producto_stock CHECK (stock >= 0),
    CONSTRAINT chk_producto_rp CHECK (puntos_rp >= 0),
    CONSTRAINT chk_producto_dano CHECK (dano <= 100),
    CONSTRAINT chk_producto_precision CHECK (precision_arma <= 100),
    CONSTRAINT chk_producto_alcance CHECK (alcance <= 100)
);

-- 7. Imágenes asociadas a cada ficha de producto.
CREATE TABLE ImagenProducto (
    id_imagen INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    url_imagen VARCHAR(500) NOT NULL,
    imagen_principal BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_imagen_producto FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE
);

-- 8. Armas que cada usuario marcó como favoritas.
CREATE TABLE Favorito (
    id_usuario INT NOT NULL,
    id_producto INT NOT NULL,
    fecha_agregado DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_producto),
    CONSTRAINT fk_favorito_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_favorito_producto FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE
);

-- 9. Regiones y comunas para las direcciones de despacho.
CREATE TABLE Region (
    id_region INT AUTO_INCREMENT PRIMARY KEY,
    nombre_region VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Comuna (
    id_comuna INT AUTO_INCREMENT PRIMARY KEY,
    nombre_comuna VARCHAR(100) NOT NULL,
    id_region INT NOT NULL,
    CONSTRAINT fk_comuna_region FOREIGN KEY (id_region) REFERENCES Region(id_region),
    CONSTRAINT uq_comuna_region UNIQUE (nombre_comuna, id_region)
);

-- 10. Direcciones de los usuarios para las compras con despacho.
CREATE TABLE Direccion (
    id_direccion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_comuna INT NOT NULL,
    calle VARCHAR(150) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    departamento VARCHAR(50),
    codigo_postal VARCHAR(15),
    referencia VARCHAR(255),
    CONSTRAINT fk_direccion_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    CONSTRAINT fk_direccion_comuna FOREIGN KEY (id_comuna) REFERENCES Comuna(id_comuna)
);

-- 11. Estados posibles del ciclo de vida de una venta.
CREATE TABLE EstadoVenta (
    id_estado_venta INT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- 12. Opciones de entrega y sus costos.
CREATE TABLE MetodoEnvio (
    id_metodo_envio INT AUTO_INCREMENT PRIMARY KEY,
    nombre_metodo VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    costo DECIMAL(12,2) NOT NULL DEFAULT 0,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_envio_costo CHECK (costo >= 0)
);

-- 13. Cabecera de compra: comprador, dirección, envío, estado y montos.
CREATE TABLE Venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_direccion INT NULL,
    id_estado_venta INT NOT NULL,
    id_metodo_envio INT NOT NULL,
    fecha_venta DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(12,2) NOT NULL,
    costo_envio DECIMAL(12,2) NOT NULL DEFAULT 0,
    total DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_venta_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    CONSTRAINT fk_venta_direccion FOREIGN KEY (id_direccion) REFERENCES Direccion(id_direccion) ON DELETE SET NULL,
    CONSTRAINT fk_venta_estado FOREIGN KEY (id_estado_venta) REFERENCES EstadoVenta(id_estado_venta),
    CONSTRAINT fk_venta_envio FOREIGN KEY (id_metodo_envio) REFERENCES MetodoEnvio(id_metodo_envio),
    CONSTRAINT chk_venta_subtotal CHECK (subtotal >= 0),
    CONSTRAINT chk_venta_costo_envio CHECK (costo_envio >= 0),
    CONSTRAINT chk_venta_total CHECK (total >= 0)
);

-- 14. Líneas de productos comprados y precio histórico al momento de la venta.
CREATE TABLE DetalleVenta (
    id_detalle_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (id_venta) REFERENCES Venta(id_venta),
    CONSTRAINT fk_detalle_producto FOREIGN KEY (id_producto) REFERENCES Producto(id_producto),
    CONSTRAINT chk_detalle_cantidad CHECK (cantidad > 0),
    CONSTRAINT chk_detalle_precio CHECK (precio_unitario >= 0),
    CONSTRAINT chk_detalle_subtotal CHECK (subtotal >= 0)
);

-- 15. Saldo ficticio disponible para el carrito/monedero de demostración.
CREATE TABLE Cartera (
    id_usuario INT PRIMARY KEY,
    saldo DECIMAL(12,2) NOT NULL DEFAULT 100000,
    actualizada_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_cartera_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    CONSTRAINT chk_cartera_saldo CHECK (saldo >= 0)
);

-- 16. Datos iniciales: roles, estados, categorías y métodos de entrega.
INSERT INTO Rol (nombre_rol, descripcion) VALUES
('Administrador', 'Gestiona y administra la tienda'),
('Usuario', 'Usuario común de la plataforma');

INSERT INTO EstadoArma (nombre_estado, descripcion) VALUES
('Nuevo', 'Producto sin uso'),
('Usado', 'Producto que ha tenido uso'),
('Restaurado', 'Producto reparado o restaurado');

INSERT INTO EstadoVenta (nombre_estado, descripcion) VALUES
('Pendiente', 'Venta pendiente de confirmación'),
('Pagada', 'Pago confirmado'),
('En preparación', 'Pedido en preparación'),
('Enviada', 'Pedido despachado'),
('Entregada', 'Pedido recibido'),
('Cancelada', 'Venta cancelada');

INSERT INTO MetodoEnvio (nombre_metodo, descripcion, costo) VALUES
('Retiro en tienda', 'Retiro presencial', 0),
('Despacho a domicilio', 'Entrega en dirección registrada', 0);

INSERT INTO Categoria (nombre_categoria, descripcion) VALUES
('Pistolas', 'Armas cortas'),
('Subfusil', 'Subfusiles y armas automáticas compactas'),
('Rifles', 'Rifles y armas de precisión'),
('Escopetas', 'Escopetas'),
('Cuerpo a cuerpo', 'Armas blancas y de combate cercano'),
('Municion', 'Munición y suministros');

INSERT INTO Subcategoria (nombre_subcategoria, descripcion, id_categoria) VALUES
('Pistolas', 'Pistolas de mano', 1),
('Subfusiles', 'Subfusiles compactos', 2),
('Rifles de precisión', 'Rifles de largo alcance', 3),
('Escopetas de combate', 'Escopetas tácticas', 4),
('Armas blancas', 'Armas de combate cuerpo a cuerpo', 5),
('Munición', 'Munición para armas', 6);

-- 17. Productos visibles en el catálogo publicado. Estadísticas y stock son de demostración.
INSERT INTO Producto
(nombre_producto, slug, descripcion, precio, puntos_rp, stock, id_estado_arma, id_subcategoria, dano, precision_arma, alcance, es_drop)
VALUES
('Desert Eagle', 'desert-eagle', 'Pistola de alto daño.', 12500, 150, 4, 1, 1, 85, 70, 65, FALSE),
('Micro SMG', 'micro-smg', 'Subfusil compacto.', 8900, 90, 4, 1, 2, 50, 62, 45, FALSE),
('Bolt Action Sniper Rifle', 'ba-sniper-rifle', 'Rifle de precisión de cerrojo.', 18600, 210, 4, 1, 3, 98, 78, 82, TRUE),
('Combat Shotgun', 'combat-shotgun', 'Escopeta de combate.', 16200, 185, 4, 1, 4, 92, 55, 38, FALSE);

-- Fin del esquema y los datos de demostración.
