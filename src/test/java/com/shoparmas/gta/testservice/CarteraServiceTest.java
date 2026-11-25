package com.shoparmas.gta.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shoparmas.gta.DTO.CarteraDTO;
import com.shoparmas.gta.model.Cartera;
import com.shoparmas.gta.model.Rol;
import com.shoparmas.gta.model.Usuario;
import com.shoparmas.gta.repository.CarteraRepository;
import com.shoparmas.gta.repository.UsuarioRepository;
import com.shoparmas.gta.service.CarteraService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class CarteraServiceTest {

    @Mock
    private CarteraRepository carteraRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private CarteraService service;

    @Test
    void crearCartera_DeberiaGuardarSaldoInicial() {
        // GIVEN
        Usuario usuario = usuarioExistente();
        when(usuarioRepository.findById(7)).thenReturn(Optional.of(usuario));
        when(carteraRepository.existsById(7)).thenReturn(false);
        when(carteraRepository.saveAndFlush(any(Cartera.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        CarteraDTO resultado = service.crearCartera(7);

        // THEN
        ArgumentCaptor<Cartera> captor = ArgumentCaptor.forClass(Cartera.class);
        verify(carteraRepository).saveAndFlush(captor.capture());
        verify(entityManager).refresh(captor.getValue());
        assertSame(usuario, captor.getValue().getUsuario());
        assertEquals(new BigDecimal("100000.00"), resultado.getSaldo());
        assertEquals(7, resultado.getUsuario().getIdUsuario());
    }

    @Test
    void crearCartera_DeberiaRechazarUsuarioInexistente() {
        // GIVEN
        when(usuarioRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> service.crearCartera(7));

        // THEN
        assertEquals("Usuario no encontrado: 7", error.getMessage());
        verifyNoInteractions(carteraRepository, entityManager);
    }

    @Test
    void crearCartera_DeberiaRechazarCarteraDuplicada() {
        // GIVEN
        when(usuarioRepository.findById(7)).thenReturn(Optional.of(usuarioExistente()));
        when(carteraRepository.existsById(7)).thenReturn(true);

        // WHEN
        IllegalStateException error = assertThrows(IllegalStateException.class, () -> service.crearCartera(7));

        // THEN
        assertEquals("El usuario ya tiene una cartera", error.getMessage());
        verify(carteraRepository).existsById(7);
        verifyNoMoreInteractions(carteraRepository);
        verifyNoInteractions(entityManager);
    }

    @Test
    void obtenerCarteraId_DeberiaMapearCarteraYUsuario() {
        // GIVEN
        when(carteraRepository.findById(7)).thenReturn(Optional.of(carteraExistente()));

        // WHEN
        CarteraDTO resultado = service.obtenerCarteraId(7);

        // THEN
        assertEquals(7, resultado.getIdUsuario());
        assertEquals(3, resultado.getUsuario().getIdRol());
    }

    @Test
    void obtenerCarteraId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(carteraRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> service.obtenerCarteraId(7));

        // THEN
        assertEquals("Cartera no encontrada para el usuario: 7", error.getMessage());
    }

    @Test
    void listarCarteras_DeberiaMapearCarterasEncontradas() {
        // GIVEN
        when(carteraRepository.findAll()).thenReturn(List.of(carteraExistente()));

        // WHEN
        List<CarteraDTO> resultado = service.listarCarteras();

        // THEN
        assertEquals(1, resultado.size());
        assertEquals(7, resultado.getFirst().getIdUsuario());
    }

    @Test
    void listarCarteras_DeberiaDevolverListaVacia() {
        // GIVEN
        when(carteraRepository.findAll()).thenReturn(List.of());

        // WHEN
        List<CarteraDTO> resultado = service.listarCarteras();

        // THEN
        assertTrue(resultado.isEmpty());
    }

    @Test
    void actualizarSaldo_DeberiaAceptarSaldoCero() {
        // GIVEN
        Cartera cartera = carteraExistente();
        when(carteraRepository.findById(7)).thenReturn(Optional.of(cartera));

        // WHEN
        CarteraDTO resultado = service.actualizarSaldo(7, BigDecimal.ZERO);

        // THEN
        assertEquals(BigDecimal.ZERO, resultado.getSaldo());
        verify(carteraRepository).flush();
        verify(entityManager).refresh(cartera);
    }

    @Test
    void actualizarSaldo_DeberiaRechazarSaldoNegativo() {
        // GIVEN
        BigDecimal saldo = new BigDecimal("-0.01");

        // WHEN
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> service.actualizarSaldo(7, saldo));

        // THEN
        assertEquals("El saldo debe ser mayor o igual a cero", error.getMessage());
        verifyNoInteractions(carteraRepository, entityManager);
    }

    @Test
    void actualizarSaldo_DeberiaRechazarSaldoNulo() {
        // GIVEN
        BigDecimal saldo = null;

        // WHEN
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> service.actualizarSaldo(7, saldo));

        // THEN
        assertEquals("El saldo debe ser mayor o igual a cero", error.getMessage());
        verifyNoInteractions(carteraRepository, entityManager);
    }

    @Test
    void eliminarCartera_DeberiaEliminarCarteraExistente() {
        // GIVEN
        Cartera cartera = carteraExistente();
        when(carteraRepository.findById(7)).thenReturn(Optional.of(cartera));

        // WHEN
        service.eliminarCartera(7);

        // THEN
        verify(carteraRepository).delete(cartera);
    }

    private Cartera carteraExistente() {
        Cartera cartera = new Cartera();
        cartera.setIdUsuario(7);
        cartera.setUsuario(usuarioExistente());
        return cartera;
    }

    private Usuario usuarioExistente() {
        Rol rol = new Rol();
        rol.setIdRol(3);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(7);
        usuario.setRol(rol);
        return usuario;
    }
}
