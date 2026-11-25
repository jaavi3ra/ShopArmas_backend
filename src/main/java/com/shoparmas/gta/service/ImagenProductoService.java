package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.ImagenProductoDTO;
import com.shoparmas.gta.model.ImagenProducto;
import com.shoparmas.gta.repository.ImagenProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImagenProductoService {
    @Autowired 
    private ImagenProductoRepository imagenProductoRepository;

    private ImagenProducto buscarImagenProducto(Integer idImagen) {
        log.info("Buscando imagen de producto con ID: {}", idImagen);
        return imagenProductoRepository.findById(idImagen)
                .orElseThrow(() -> new RuntimeException("Imagen de producto no encontrada con ID: " + idImagen));
    }

    private ImagenProductoDTO convertirADTO(ImagenProducto imagenProducto) {
        ImagenProductoDTO dto = new ImagenProductoDTO();

        dto.setIdImagen(imagenProducto.getIdImagen());
        dto.setIdProducto(imagenProducto.getProducto().getIdProducto());
        dto.setUrlImagen(imagenProducto.getUrlImagen());
        dto.setImagenPrincipal(imagenProducto.getImagenPrincipal());
        dto.setFechaRegistro(imagenProducto.getFechaRegistro());

        return dto;
    }

    public ImagenProductoDTO obtenerImagenProductoPorId(Integer idImagen) {
        ImagenProducto imagenProducto = buscarImagenProducto(idImagen);
        return convertirADTO(imagenProducto);
    }

    public ImagenProductoDTO crearImagenProducto(ImagenProductoDTO imagenProductoDTO) {
        try {
            log.info("Creando imagen de producto para el producto con ID: {}", imagenProductoDTO.getIdProducto());
            ImagenProducto imagenProducto = new ImagenProducto();
            imagenProducto.setUrlImagen(imagenProductoDTO.getUrlImagen());
            imagenProducto.setImagenPrincipal(imagenProductoDTO.getImagenPrincipal());
            imagenProducto.setFechaRegistro(imagenProductoDTO.getFechaRegistro());

            ImagenProducto guardada = imagenProductoRepository.save(imagenProducto);
            return convertirADTO(guardada);
        } catch (RuntimeException e) {
            log.error("Error al crear la imagen de producto para el producto con ID: {}", imagenProductoDTO.getIdProducto(), e);
            throw e;
        }
    }

    public ImagenProductoDTO actualizarImagenProducto(Integer idImagen, ImagenProductoDTO imagenProductoDTO) {
        log.info("Actualizando imagen de producto con ID: {}", idImagen);
        ImagenProducto imagenProducto = buscarImagenProducto(idImagen);
        imagenProducto.setUrlImagen(imagenProductoDTO.getUrlImagen());
        imagenProducto.setImagenPrincipal(imagenProductoDTO.getImagenPrincipal());
        imagenProducto.setFechaRegistro(imagenProductoDTO.getFechaRegistro());

        ImagenProducto guardada = imagenProductoRepository.save(imagenProducto);
        return convertirADTO(guardada);
    }

    public void eliminarImagenProducto(Integer idImagen) {
        log.info("Eliminando imagen de producto con ID: {}", idImagen);
        ImagenProducto imagenProducto = buscarImagenProducto(idImagen);
        imagenProductoRepository.delete(imagenProducto);
    }
}
