package com.shoparmas.gta.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shoparmas.gta.DTO.EstadoVentaDTO;
import com.shoparmas.gta.model.EstadoVenta;
import com.shoparmas.gta.repository.EstadoVentaRepository;
import com.shoparmas.gta.service.EstadoVentaService;

@ExtendWith(MockitoExtension.class)
class EstadoVentaServiceTest {

    @Mock
    private EstadoVentaRepository estadoVentaRepository;

    @InjectMocks
    private EstadoVentaService service;

    @Test
    void obtenerEstadoVentaPorId_DeberiaMapearEntidad() {
        // GIVEN
        EstadoVenta entity = entidadExistente();
        when(estadoVentaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        EstadoVentaDTO resultado = service.obtenerEstadoVentaPorId(7);

        // THEN
        assertEquals(7, resultado.getIdEstadoVenta());
        assertEquals("Pendiente", resultado.getNombreEstado());
    }

    @Test
    void obtenerEstadoVentaPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(estadoVentaRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerEstadoVentaPorId(7));

        // THEN
        assertEquals("Estado de venta no encontrado con ID: 7", error.getMessage());
    }

    @Test
    void eliminarEstadoVenta_DeberiaEliminarEntidadExistente() {
        // GIVEN
        EstadoVenta entity = entidadExistente();
        when(estadoVentaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarEstadoVenta(7);

        // THEN
        verify(estadoVentaRepository).delete(entity);
    }

    @Test
    void crearEstadoVenta_DeberiaGuardarDatosDeLaSolicitud() {
        // GIVEN
        EstadoVentaDTO solicitud = new EstadoVentaDTO();
        solicitud.setNombreEstado("Entregada");
        when(estadoVentaRepository.save(any(EstadoVenta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        EstadoVentaDTO resultado = service.crearEstadoVenta(solicitud);

        // THEN
        ArgumentCaptor<EstadoVenta> captor = ArgumentCaptor.forClass(EstadoVenta.class);
        verify(estadoVentaRepository).save(captor.capture());
        assertEquals("Entregada", captor.getValue().getNombreEstado());
        assertEquals("Entregada", resultado.getNombreEstado());
    }

    @Test
    void actualizarEstadoVenta_DeberiaGuardarCambios() {
        // GIVEN
        EstadoVenta entity = entidadExistente();
        when(estadoVentaRepository.findById(7)).thenReturn(Optional.of(entity));
        EstadoVentaDTO solicitud = new EstadoVentaDTO();
        solicitud.setNombreEstado("Entregada");
        when(estadoVentaRepository.save(entity)).thenReturn(entity);

        // WHEN
        EstadoVentaDTO resultado = service.actualizarEstadoVenta(7, solicitud);

        // THEN
        verify(estadoVentaRepository).save(entity);
        assertEquals(7, resultado.getIdEstadoVenta());
        assertEquals("Entregada", resultado.getNombreEstado());
    }

    private EstadoVenta entidadExistente() {
        EstadoVenta entity = new EstadoVenta();
        entity.setIdEstadoVenta(7);
        entity.setNombreEstado("Pendiente");
        return entity;
    }
}
