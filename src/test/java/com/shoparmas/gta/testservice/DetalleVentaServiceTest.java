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

import com.shoparmas.gta.DTO.DetalleVentaDTO;
import com.shoparmas.gta.model.DetalleVenta;
import com.shoparmas.gta.model.Producto;
import com.shoparmas.gta.model.Venta;
import com.shoparmas.gta.repository.DetalleVentaRepository;
import com.shoparmas.gta.service.DetalleVentaService;

@ExtendWith(MockitoExtension.class)
class DetalleVentaServiceTest {

    @Mock
    private DetalleVentaRepository detalleVentaRepository;

    @InjectMocks
    private DetalleVentaService service;

    @Test
    void obtenerDetalleVentaPorId_DeberiaMapearEntidad() {
        // GIVEN
        DetalleVenta entity = entidadExistente();
        when(detalleVentaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        DetalleVentaDTO resultado = service.obtenerDetalleVentaPorId(7);

        // THEN
        assertEquals(7, resultado.getIdDetalleVenta());
        assertEquals(2, resultado.getCantidad());
        assertEquals(21, resultado.getIdVenta());
        assertEquals(22, resultado.getIdProducto());
    }

    @Test
    void obtenerDetalleVentaPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(detalleVentaRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerDetalleVentaPorId(7));

        // THEN
        assertEquals("Detalle de venta no encontrado con ID: 7", error.getMessage());
    }

    @Test
    void eliminarDetalleVenta_DeberiaEliminarEntidadExistente() {
        // GIVEN
        DetalleVenta entity = entidadExistente();
        when(detalleVentaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarDetalleVenta(7);

        // THEN
        verify(detalleVentaRepository).delete(entity);
    }

    @Test
    void crearDetalleVenta_DeberiaPropagarErrorDeIntegridad() {
        // GIVEN
        DetalleVentaDTO solicitud = new DetalleVentaDTO();
        solicitud.setCantidad(3);
        DataIntegrityViolationException rechazo = new DataIntegrityViolationException("Restricción de integridad");
        when(detalleVentaRepository.save(any(DetalleVenta.class))).thenThrow(rechazo);

        // WHEN
        DataIntegrityViolationException error = assertThrows(DataIntegrityViolationException.class,
                () -> service.crearDetalleVenta(solicitud));

        // THEN
        assertSame(rechazo, error);
        verify(detalleVentaRepository).save(any(DetalleVenta.class));
    }

    @Test
    void actualizarDetalleVenta_DeberiaGuardarCambios() {
        // GIVEN
        DetalleVenta entity = entidadExistente();
        when(detalleVentaRepository.findById(7)).thenReturn(Optional.of(entity));
        DetalleVentaDTO solicitud = new DetalleVentaDTO();
        solicitud.setCantidad(3);
        when(detalleVentaRepository.save(entity)).thenReturn(entity);

        // WHEN
        DetalleVentaDTO resultado = service.actualizarDetalleVenta(7, solicitud);

        // THEN
        verify(detalleVentaRepository).save(entity);
        assertEquals(7, resultado.getIdDetalleVenta());
        assertEquals(3, resultado.getCantidad());
    }

    private DetalleVenta entidadExistente() {
        DetalleVenta entity = new DetalleVenta();
        entity.setIdDetalleVenta(7);
        entity.setCantidad(2);
        Venta venta = new Venta();
        venta.setIdVenta(21);
        entity.setVenta(venta);
        Producto producto = new Producto();
        producto.setIdProducto(22);
        entity.setProducto(producto);
        return entity;
    }
}
