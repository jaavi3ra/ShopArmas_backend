package com.shoparmas.gta.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shoparmas.gta.DTO.FavoritoDTO;
import com.shoparmas.gta.model.Favorito;
import com.shoparmas.gta.model.FavoritoID;
import com.shoparmas.gta.repository.FavoritoRepository;
import com.shoparmas.gta.service.FavoritoService;

@ExtendWith(MockitoExtension.class)
class FavoritoServiceTest {

    @Mock
    private FavoritoRepository favoritoRepository;

    @InjectMocks
    private FavoritoService service;

    @Test
    void crearFavorito_DeberiaGuardarClaveCompuestaYFecha() {
        // GIVEN
        LocalDateTime fecha = LocalDateTime.of(2026, 1, 15, 12, 0);
        FavoritoDTO solicitud = new FavoritoDTO();
        solicitud.setIdUsuario(7);
        solicitud.setIdProducto(21);
        solicitud.setFechaAgregado(fecha);
        when(favoritoRepository.save(any(Favorito.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        FavoritoDTO resultado = service.crearFavorito(solicitud);

        // THEN
        ArgumentCaptor<Favorito> captor = ArgumentCaptor.forClass(Favorito.class);
        verify(favoritoRepository).save(captor.capture());
        assertEquals(new FavoritoID(7, 21), captor.getValue().getId());
        assertEquals(7, resultado.getIdUsuario());
        assertEquals(21, resultado.getIdProducto());
        assertEquals(fecha, resultado.getFechaAgregado());
    }

    @Test
    void obtenerFavoritoPorId_DeberiaMapearClaveCompuesta() {
        // GIVEN
        Favorito favorito = favoritoExistente();
        when(favoritoRepository.findByIdUsuarioAndIdProducto(7, 21)).thenReturn(Optional.of(favorito));

        // WHEN
        FavoritoDTO resultado = service.obtenerFavoritoPorId(7, 21);

        // THEN
        assertEquals(7, resultado.getIdUsuario());
        assertEquals(21, resultado.getIdProducto());
    }

    @Test
    void obtenerFavoritoPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(favoritoRepository.findByIdUsuarioAndIdProducto(7, 21)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerFavoritoPorId(7, 21));

        // THEN
        assertEquals("Favorito no encontrado con ID de usuario: 7 y ID de producto: 21", error.getMessage());
    }

    @Test
    void eliminarFavorito_DeberiaEliminarLaCombinacionSolicitada() {
        // GIVEN
        Favorito favorito = favoritoExistente();
        when(favoritoRepository.findByIdUsuarioAndIdProducto(7, 21)).thenReturn(Optional.of(favorito));

        // WHEN
        service.eliminarFavorito(7, 21);

        // THEN
        verify(favoritoRepository).delete(favorito);
    }

    private Favorito favoritoExistente() {
        Favorito favorito = new Favorito();
        favorito.setId(new FavoritoID(7, 21));
        return favorito;
    }
}
