package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.ProductoDTO;
import com.shoparmas.gta.model.Producto;
import com.shoparmas.gta.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    @Autowired 
    private ProductoRepository productoRepository;

    private Producto buscarProducto(Integer idProducto) {
        return productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));
    }

    private ProductoDTO convertirADTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();

        dto.setIdProducto(producto.getIdProducto());
        dto.setNombreProducto(producto.getNombreProducto());
        dto.setSlug(producto.getSlug());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setPuntosRp(producto.getPuntosRp());
        dto.setStock(producto.getStock());
        dto.setIdEstadoArma(producto.getEstadoArma().getIdEstadoArma());
        dto.setIdSubcategoria(producto.getSubcategoria().getIdSubcategoria());
        dto.setDano(producto.getDano());
        dto.setPrecisionArma(producto.getPrecisionArma());
        dto.setAlcance(producto.getAlcance());
        dto.setEsDrop(producto.getEsDrop());
        dto.setFechaRegistro(producto.getFechaRegistro());
        dto.setActivo(producto.getActivo());

        return dto;
    }

    public ProductoDTO obtenerProductoPorId(Integer idProducto) {
        Producto producto = buscarProducto(idProducto);
        return convertirADTO(producto);
    }

    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        try {
            Producto producto = new Producto();
            producto.setNombreProducto(productoDTO.getNombreProducto());
            producto.setSlug(productoDTO.getSlug());
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setPuntosRp(productoDTO.getPuntosRp());
            producto.setStock(productoDTO.getStock());
            producto.setEstadoArma(null); // Asignar el estado del arma según corresponda
            producto.setSubcategoria(null); // Asignar la subcategoría según corresponda
            producto.setDano(productoDTO.getDano());
            producto.setPrecisionArma(productoDTO.getPrecisionArma());
            producto.setAlcance(productoDTO.getAlcance());
            producto.setEsDrop(productoDTO.getEsDrop());
            producto.setFechaRegistro(productoDTO.getFechaRegistro());
            producto.setActivo(productoDTO.getActivo());

            Producto guardado = productoRepository.save(producto);
            return convertirADTO(guardado);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public ProductoDTO actualizarProducto(Integer idProducto, ProductoDTO productoDTO) {
        Producto producto = buscarProducto(idProducto);
        producto.setNombreProducto(productoDTO.getNombreProducto());
        producto.setSlug(productoDTO.getSlug());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setPuntosRp(productoDTO.getPuntosRp());
        producto.setStock(productoDTO.getStock());
        producto.setEstadoArma(null); // Asignar el estado del arma según corresponda
        producto.setSubcategoria(null); // Asignar la subcategoría según corresponda
        producto.setDano(productoDTO.getDano());
        producto.setPrecisionArma(productoDTO.getPrecisionArma());
        producto.setAlcance(productoDTO.getAlcance());
        producto.setEsDrop(productoDTO.getEsDrop());
        producto.setFechaRegistro(productoDTO.getFechaRegistro());
        producto.setActivo(productoDTO.getActivo());

        Producto actualizado = productoRepository.save(producto);
        return convertirADTO(actualizado);
    }

    public void eliminarProducto(Integer idProducto) {
        Producto producto = buscarProducto(idProducto);
        productoRepository.delete(producto);
    }
}
