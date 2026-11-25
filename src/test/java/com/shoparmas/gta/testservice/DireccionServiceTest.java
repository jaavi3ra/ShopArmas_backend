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

import com.shoparmas.gta.DTO.DireccionDTO;
import com.shoparmas.gta.model.Comuna;
import com.shoparmas.gta.model.Direccion;
import com.shoparmas.gta.model.Usuario;
import com.shoparmas.gta.repository.DireccionRepository;
import com.shoparmas.gta.service.DireccionService;

@ExtendWith(MockitoExtension.class)
class DireccionServiceTest {

    @Mock
    private DireccionRepository direccionRepository;

    @InjectMocks
    private DireccionService service;

    @Test
    void obtenerDireccionPorId_DeberiaMapearEntidad() {
        // GIVEN
        Direccion entity = entidadExistente();
        when(direccionRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        DireccionDTO resultado = service.obtenerDireccionPorId(7);

        // THEN
        assertEquals(7, resultado.getIdDireccion());
        assertEquals("Los Pinos", resultado.getCalle());
        assertEquals(21, resultado.getIdUsuario());
        assertEquals(22, resultado.getIdComuna());
    }

    @Test
    void obtenerDireccionPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(direccionRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerDireccionPorId(7));

        // THEN
        assertEquals("Dirección no encontrada con ID: 7", error.getMessage());
    }

    @Test
    void eliminarDireccion_DeberiaEliminarEntidadExistente() {
        // GIVEN
        Direccion entity = entidadExistente();
        when(direccionRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarDireccion(7);

        // THEN
        verify(direccionRepository).delete(entity);
    }

    @Test
    void crearDireccion_DeberiaPropagarErrorDeIntegridad() {
        // GIVEN
        DireccionDTO solicitud = new DireccionDTO();
        solicitud.setCalle("Las Acacias");
        DataIntegrityViolationException rechazo = new DataIntegrityViolationException("Restricción de integridad");
        when(direccionRepository.save(any(Direccion.class))).thenThrow(rechazo);

        // WHEN
        DataIntegrityViolationException error = assertThrows(DataIntegrityViolationException.class,
                () -> service.crearDireccion(solicitud));

        // THEN
        assertSame(rechazo, error);
        verify(direccionRepository).save(any(Direccion.class));
    }

    @Test
    void actualizarDireccion_DeberiaGuardarCambios() {
        // GIVEN
        Direccion entity = entidadExistente();
        when(direccionRepository.findById(7)).thenReturn(Optional.of(entity));
        DireccionDTO solicitud = new DireccionDTO();
        solicitud.setCalle("Las Acacias");
        when(direccionRepository.save(entity)).thenReturn(entity);

        // WHEN
        DireccionDTO resultado = service.actualizarDireccion(7, solicitud);

        // THEN
        verify(direccionRepository).save(entity);
        assertEquals(7, resultado.getIdDireccion());
        assertEquals("Las Acacias", resultado.getCalle());
    }

    private Direccion entidadExistente() {
        Direccion entity = new Direccion();
        entity.setIdDireccion(7);
        entity.setCalle("Los Pinos");
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(21);
        entity.setUsuario(usuario);
        Comuna comuna = new Comuna();
        comuna.setIdComuna(22);
        entity.setComuna(comuna);
        return entity;
    }
}
