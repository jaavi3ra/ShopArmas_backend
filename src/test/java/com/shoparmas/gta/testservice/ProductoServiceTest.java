package com.shoparmas.gta.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.shoparmas.gta.DTO.ProductoDTO;
import com.shoparmas.gta.model.EstadoArma;
import com.shoparmas.gta.model.Producto;
import com.shoparmas.gta.model.Subcategoria;
import com.shoparmas.gta.repository.ProductoRepository;
import com.shoparmas.gta.service.ProductoService;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService service;

    @Test
    void obtenerProductoPorId_DeberiaMapearEntidad() {
        // GIVEN
        Producto entity = entidadExistente();
        when(productoRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        ProductoDTO resultado = service.obtenerProductoPorId(7);

        // THEN
        assertEquals(7, resultado.getIdProducto());
        assertEquals("Producto inicial", resultado.getNombreProducto());
        assertEquals(21, resultado.getIdEstadoArma());
        assertEquals(22, resultado.getIdSubcategoria());
    }

    @Test
    void obtenerProductoPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(productoRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerProductoPorId(7));

        // THEN
        assertEquals("Producto no encontrado con ID: 7", error.getMessage());
    }

    @Test
    void eliminarProducto_DeberiaEliminarEntidadExistente() {
        // GIVEN
        Producto entity = entidadExistente();
        when(productoRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarProducto(7);

        // THEN
        verify(productoRepository).delete(entity);
    }

    @Test
    void crearProducto_DeberiaPropagarErrorDeIntegridad() {
        // GIVEN
        ProductoDTO solicitud = new ProductoDTO();
        solicitud.setNombreProducto("Producto editado");
        DataIntegrityViolationException rechazo = new DataIntegrityViolationException("Restricción de integridad");
        when(productoRepository.save(any(Producto.class))).thenThrow(rechazo);

        // WHEN
        DataIntegrityViolationException error = assertThrows(DataIntegrityViolationException.class,
                () -> service.crearProducto(solicitud));

        // THEN
        assertSame(rechazo, error);
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void actualizarProducto_DeberiaFallarAntesDeGuardarSiNoExiste() {
        // GIVEN
        when(productoRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> service.actualizarProducto(7, new ProductoDTO()));

        // THEN
        assertEquals("Producto no encontrado con ID: 7", error.getMessage());
        verify(productoRepository).findById(7);
        verifyNoMoreInteractions(productoRepository);
    }

    private Producto entidadExistente() {
        Producto entity = new Producto();
        entity.setIdProducto(7);
        entity.setNombreProducto("Producto inicial");
        EstadoArma estadoArma = new EstadoArma();
        estadoArma.setIdEstadoArma(21);
        entity.setEstadoArma(estadoArma);
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setIdSubcategoria(22);
        entity.setSubcategoria(subcategoria);
        return entity;
    }
}
