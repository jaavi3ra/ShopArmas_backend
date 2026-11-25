-- Datos iniciales para inicializar una base Neon vacia.

INSERT INTO Rol (nombre_rol, descripcion) VALUES
('Administrador', 'Gestiona y administra la tienda'),
('Usuario', 'Usuario común de la plataforma')
ON CONFLICT (nombre_rol) DO NOTHING;

INSERT INTO EstadoArma (nombre_estado, descripcion) VALUES
('Nuevo', 'Producto sin uso'),
('Usado', 'Producto que ha tenido uso'),
('Restaurado', 'Producto reparado o restaurado')
ON CONFLICT (nombre_estado) DO NOTHING;

INSERT INTO EstadoVenta (nombre_estado, descripcion) VALUES
('Pendiente', 'Venta pendiente de confirmación'),
('Pagada', 'Pago confirmado'),
('En preparación', 'Pedido en preparación'),
('Enviada', 'Pedido despachado'),
('Entregada', 'Pedido recibido'),
('Cancelada', 'Venta cancelada')
ON CONFLICT (nombre_estado) DO NOTHING;

INSERT INTO MetodoEnvio (nombre_metodo, descripcion, costo) VALUES
('Retiro en tienda', 'Retiro presencial', 0.00),
('Despacho a domicilio', 'Entrega en dirección registrada', 0.00)
ON CONFLICT (nombre_metodo) DO NOTHING;

INSERT INTO Categoria (nombre_categoria, descripcion) VALUES
('Pistolas', 'Armas cortas'),
('Subfusil', 'Subfusiles y armas automáticas compactas'),
('Rifles', 'Rifles y armas de precisión'),
('Escopetas', 'Escopetas'),
('Cuerpo a cuerpo', 'Armas blancas y de combate cercano'),
('Municion', 'Munición y suministros')
ON CONFLICT (nombre_categoria) DO NOTHING;

INSERT INTO Subcategoria (nombre_subcategoria, descripcion, id_categoria)
SELECT v.nombre_subcategoria, v.descripcion, c.id_categoria
FROM (VALUES
    ('Pistolas', 'Pistolas de mano', 'Pistolas'),
    ('Subfusiles', 'Subfusiles compactos', 'Subfusil'),
    ('Rifles de precisión', 'Rifles de largo alcance', 'Rifles'),
    ('Escopetas de combate', 'Escopetas tácticas', 'Escopetas'),
    ('Armas blancas', 'Armas de combate cuerpo a cuerpo', 'Cuerpo a cuerpo'),
    ('Munición', 'Munición para armas', 'Municion')
) AS v(nombre_subcategoria, descripcion, nombre_categoria)
JOIN Categoria c ON c.nombre_categoria = v.nombre_categoria
ON CONFLICT (nombre_subcategoria, id_categoria) DO NOTHING;

INSERT INTO Producto
(nombre_producto, slug, descripcion, precio, puntos_rp, stock, id_estado_arma, id_subcategoria, dano, precision_arma, alcance, es_drop)
SELECT
    v.nombre_producto,
    v.slug,
    v.descripcion,
    v.precio,
    v.puntos_rp,
    v.stock,
    ea.id_estado_arma,
    sc.id_subcategoria,
    v.dano,
    v.precision_arma,
    v.alcance,
    v.es_drop
FROM (VALUES
    ('Desert Eagle', 'desert-eagle', 'Pistola de alto daño.', 12500.00, 150, 4, 'Nuevo', 'Pistolas', 85, 70, 65, FALSE),
    ('Micro SMG', 'micro-smg', 'Subfusil compacto.', 8900.00, 90, 4, 'Nuevo', 'Subfusiles', 50, 62, 45, FALSE),
    ('Bolt Action Sniper Rifle', 'ba-sniper-rifle', 'Rifle de precisión de cerrojo.', 18600.00, 210, 4, 'Nuevo', 'Rifles de precisión', 98, 78, 82, TRUE),
    ('Combat Shotgun', 'combat-shotgun', 'Escopeta de combate.', 16200.00, 185, 4, 'Nuevo', 'Escopetas de combate', 92, 55, 38, FALSE)
) AS v(nombre_producto, slug, descripcion, precio, puntos_rp, stock, nombre_estado, nombre_subcategoria, dano, precision_arma, alcance, es_drop)
JOIN EstadoArma ea ON ea.nombre_estado = v.nombre_estado
JOIN Subcategoria sc ON sc.nombre_subcategoria = v.nombre_subcategoria
ON CONFLICT (slug) DO NOTHING;
