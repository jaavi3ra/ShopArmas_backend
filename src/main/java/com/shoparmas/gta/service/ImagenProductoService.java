package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.ImagenProductoDTO;
import com.shoparmas.gta.model.ImagenProducto;
import org.springframework.stereotype.Service;

@Service
public class ImagenProductoService {

    private ImagenProductoDTO convertirADTO(ImagenProducto imagenProducto) {
        ImagenProductoDTO dto = new ImagenProductoDTO();

        dto.setIdImagen(imagenProducto.getIdImagen());
        dto.setIdProducto(imagenProducto.getProducto().getIdProducto());
        dto.setUrlImagen(imagenProducto.getUrlImagen());
        dto.setImagenPrincipal(imagenProducto.getImagenPrincipal());
        dto.setFechaRegistro(imagenProducto.getFechaRegistro());

        return dto;
    }
}
