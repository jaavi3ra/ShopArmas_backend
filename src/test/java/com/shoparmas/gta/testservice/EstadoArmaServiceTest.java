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

import com.shoparmas.gta.DTO.EstadoArmaDTO;
import com.shoparmas.gta.model.EstadoArma;
import com.shoparmas.gta.repository.EstadoArmaRepository;
import com.shoparmas.gta.service.EstadoArmaService;

@ExtendWith(MockitoExtension.class)
class EstadoArmaServiceTest {

    @Mock
    private EstadoArmaRepository estadoArmaRepository;

    @InjectMocks
    private EstadoArmaService service;

    @Test
    void obtenerEstadoArmaPorId_DeberiaMapearEntidad() {
        // GIVEN
        EstadoArma entity = entidadExistente();
        when(estadoArmaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        EstadoArmaDTO resultado = service.obtenerEstadoArmaPorId(7);

        // THEN
        assertEquals(7, resultado.getIdEstadoArma());
        assertEquals("Disponible", resultado.getNombreEstado());
    }

    @Test
    void obtenerEstadoArmaPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(estadoArmaRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerEstadoArmaPorId(7));

        // THEN
        assertEquals("Estado de arma no encontrado con ID: 7", error.getMessage());
    }

    @Test
    void eliminarEstadoArma_DeberiaEliminarEntidadExistente() {
        // GIVEN
        EstadoArma entity = entidadExistente();
        when(estadoArmaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarEstadoArma(7);

        // THEN
        verify(estadoArmaRepository).delete(entity);
    }

    @Test
    void crearEstadoArma_DeberiaGuardarDatosDeLaSolicitud() {
        // GIVEN
        EstadoArmaDTO solicitud = new EstadoArmaDTO();
        solicitud.setNombreEstado("Agotado");
        when(estadoArmaRepository.save(any(EstadoArma.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        EstadoArmaDTO resultado = service.crearEstadoArma(solicitud);

        // THEN
        ArgumentCaptor<EstadoArma> captor = ArgumentCaptor.forClass(EstadoArma.class);
        verify(estadoArmaRepository).save(captor.capture());
        assertEquals("Agotado", captor.getValue().getNombreEstado());
        assertEquals("Agotado", resultado.getNombreEstado());
    }

    @Test
    void actualizarEstadoArma_DeberiaGuardarCambios() {
        // GIVEN
        EstadoArma entity = entidadExistente();
        when(estadoArmaRepository.findById(7)).thenReturn(Optional.of(entity));
        EstadoArmaDTO solicitud = new EstadoArmaDTO();
        solicitud.setNombreEstado("Agotado");
        when(estadoArmaRepository.save(entity)).thenReturn(entity);

        // WHEN
        EstadoArmaDTO resultado = service.actualizarEstadoArma(7, solicitud);

        // THEN
        verify(estadoArmaRepository).save(entity);
        assertEquals(7, resultado.getIdEstadoArma());
        assertEquals("Agotado", resultado.getNombreEstado());
    }

    private EstadoArma entidadExistente() {
        EstadoArma entity = new EstadoArma();
        entity.setIdEstadoArma(7);
        entity.setNombreEstado("Disponible");
        return entity;
    }
}
