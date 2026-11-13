package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.ProductoDTO;
import com.shoparmas.gta.model.Producto;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

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
}
