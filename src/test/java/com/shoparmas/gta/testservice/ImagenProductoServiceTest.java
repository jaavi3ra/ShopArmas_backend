package com.shoparmas.gta.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.shoparmas.gta.DTO.ImagenProductoDTO;
import com.shoparmas.gta.model.ImagenProducto;
import com.shoparmas.gta.model.Producto;
import com.shoparmas.gta.repository.ImagenProductoRepository;
import com.shoparmas.gta.service.ImagenProductoService;

@ExtendWith(MockitoExtension.class)
class ImagenProductoServiceTest {

    @Mock
    private ImagenProductoRepository imagenProductoRepository;

    @InjectMocks
    private ImagenProductoService service;

    @Test
    void obtenerImagenProductoPorId_DeberiaMapearEntidad() {
        // GIVEN
        ImagenProducto entity = entidadExistente();
        when(imagenProductoRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        ImagenProductoDTO resultado = service.obtenerImagenProductoPorId(7);

        // THEN
        assertEquals(7, resultado.getIdImagen());
        assertEquals("https://example.test/inicial.png", resultado.getUrlImagen());
        assertEquals(21, resultado.getIdProducto());
    }

    @Test
    void obtenerImagenProductoPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(imagenProductoRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerImagenProductoPorId(7));

        // THEN
        assertEquals("Imagen de producto no encontrada con ID: 7", error.getMessage());
    }

    @Test
    void eliminarImagenProducto_DeberiaEliminarEntidadExistente() {
        // GIVEN
        ImagenProducto entity = entidadExistente();
        when(imagenProductoRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarImagenProducto(7);

        // THEN
        verify(imagenProductoRepository).delete(entity);
    }

    @Test
    void crearImagenProducto_DeberiaPropagarErrorDeIntegridad() {
        // GIVEN
        ImagenProductoDTO solicitud = new ImagenProductoDTO();
        solicitud.setUrlImagen("https://example.test/nueva.png");
        DataIntegrityViolationException rechazo = new DataIntegrityViolationException("Restricción de integridad");
        when(imagenProductoRepository.save(any(ImagenProducto.class))).thenThrow(rechazo);

        // WHEN
        DataIntegrityViolationException error = assertThrows(DataIntegrityViolationException.class,
                () -> service.crearImagenProducto(solicitud));

        // THEN
        assertSame(rechazo, error);
        verify(imagenProductoRepository).save(any(ImagenProducto.class));
    }

    @Test
    void actualizarImagenProducto_DeberiaGuardarCambios() {
        // GIVEN
        ImagenProducto entity = entidadExistente();
        when(imagenProductoRepository.findById(7)).thenReturn(Optional.of(entity));
        ImagenProductoDTO solicitud = new ImagenProductoDTO();
        solicitud.setUrlImagen("https://example.test/nueva.png");
        when(imagenProductoRepository.save(entity)).thenReturn(entity);

        // WHEN
        ImagenProductoDTO resultado = service.actualizarImagenProducto(7, solicitud);

        // THEN
        verify(imagenProductoRepository).save(entity);
        assertEquals(7, resultado.getIdImagen());
        assertEquals("https://example.test/nueva.png", resultado.getUrlImagen());
    }

    private ImagenProducto entidadExistente() {
        ImagenProducto entity = new ImagenProducto();
        entity.setIdImagen(7);
        entity.setUrlImagen("https://example.test/inicial.png");
        Producto producto = new Producto();
        producto.setIdProducto(21);
        entity.setProducto(producto);
        return entity;
    }
}
