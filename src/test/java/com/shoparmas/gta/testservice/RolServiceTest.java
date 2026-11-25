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

import com.shoparmas.gta.DTO.RolDTO;
import com.shoparmas.gta.model.Rol;
import com.shoparmas.gta.repository.RolRepository;
import com.shoparmas.gta.service.RolService;

@ExtendWith(MockitoExtension.class)
class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService service;

    @Test
    void obtenerRolPorId_DeberiaMapearEntidad() {
        // GIVEN
        Rol entity = entidadExistente();
        when(rolRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        RolDTO resultado = service.obtenerRolPorId(7);

        // THEN
        assertEquals(7, resultado.getIdRol());
        assertEquals("Cliente", resultado.getNombreRol());
    }

    @Test
    void obtenerRolPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(rolRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerRolPorId(7));

        // THEN
        assertEquals("Rol no encontrado con ID: 7", error.getMessage());
    }

    @Test
    void eliminarRol_DeberiaEliminarEntidadExistente() {
        // GIVEN
        Rol entity = entidadExistente();
        when(rolRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarRol(7);

        // THEN
        verify(rolRepository).delete(entity);
    }

    @Test
    void crearRol_DeberiaGuardarDatosDeLaSolicitud() {
        // GIVEN
        RolDTO solicitud = new RolDTO();
        solicitud.setNombreRol("Moderador");
        when(rolRepository.save(any(Rol.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        RolDTO resultado = service.crearRol(solicitud);

        // THEN
        ArgumentCaptor<Rol> captor = ArgumentCaptor.forClass(Rol.class);
        verify(rolRepository).save(captor.capture());
        assertEquals("Moderador", captor.getValue().getNombreRol());
        assertEquals("Moderador", resultado.getNombreRol());
    }

    @Test
    void actualizarRol_DeberiaGuardarCambios() {
        // GIVEN
        Rol entity = entidadExistente();
        when(rolRepository.findById(7)).thenReturn(Optional.of(entity));
        RolDTO solicitud = new RolDTO();
        solicitud.setNombreRol("Moderador");
        when(rolRepository.save(entity)).thenReturn(entity);

        // WHEN
        RolDTO resultado = service.actualizarRol(7, solicitud);

        // THEN
        verify(rolRepository).save(entity);
        assertEquals(7, resultado.getIdRol());
        assertEquals("Moderador", resultado.getNombreRol());
    }

    private Rol entidadExistente() {
        Rol entity = new Rol();
        entity.setIdRol(7);
        entity.setNombreRol("Cliente");
        return entity;
    }
}
