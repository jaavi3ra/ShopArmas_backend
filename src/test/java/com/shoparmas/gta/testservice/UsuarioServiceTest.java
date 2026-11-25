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

import com.shoparmas.gta.DTO.UsuarioDTO;
import com.shoparmas.gta.model.Rol;
import com.shoparmas.gta.model.Usuario;
import com.shoparmas.gta.repository.UsuarioRepository;
import com.shoparmas.gta.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService service;

    @Test
    void obtenerUsuarioPorId_DeberiaMapearEntidad() {
        // GIVEN
        Usuario entity = entidadExistente();
        when(usuarioRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        UsuarioDTO resultado = service.obtenerUsuarioPorId(7);

        // THEN
        assertEquals(7, resultado.getIdUsuario());
        assertEquals("jugador1", resultado.getNombreUsuario());
        assertEquals(21, resultado.getIdRol());
    }

    @Test
    void obtenerUsuarioPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(usuarioRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerUsuarioPorId(7));

        // THEN
        assertEquals("Usuario no encontrado con ID: 7", error.getMessage());
    }

    @Test
    void eliminarUsuario_DeberiaEliminarEntidadExistente() {
        // GIVEN
        Usuario entity = entidadExistente();
        when(usuarioRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarUsuario(7);

        // THEN
        verify(usuarioRepository).delete(entity);
    }

    @Test
    void crearUsuario_DeberiaPropagarErrorDeIntegridad() {
        // GIVEN
        UsuarioDTO solicitud = new UsuarioDTO();
        solicitud.setNombreUsuario("jugador2");
        DataIntegrityViolationException rechazo = new DataIntegrityViolationException("Restricción de integridad");
        when(usuarioRepository.save(any(Usuario.class))).thenThrow(rechazo);

        // WHEN
        DataIntegrityViolationException error = assertThrows(DataIntegrityViolationException.class,
                () -> service.crearUsuario(solicitud));

        // THEN
        assertSame(rechazo, error);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void actualizarUsuario_DeberiaGuardarCambios() {
        // GIVEN
        Usuario entity = entidadExistente();
        when(usuarioRepository.findById(7)).thenReturn(Optional.of(entity));
        UsuarioDTO solicitud = new UsuarioDTO();
        solicitud.setNombreUsuario("jugador2");
        when(usuarioRepository.save(entity)).thenReturn(entity);

        // WHEN
        UsuarioDTO resultado = service.actualizarUsuario(7, solicitud);

        // THEN
        verify(usuarioRepository).save(entity);
        assertEquals(7, resultado.getIdUsuario());
        assertEquals("jugador2", resultado.getNombreUsuario());
    }

    private Usuario entidadExistente() {
        Usuario entity = new Usuario();
        entity.setIdUsuario(7);
        entity.setNombreUsuario("jugador1");
        Rol rol = new Rol();
        rol.setIdRol(21);
        entity.setRol(rol);
        return entity;
    }
}
