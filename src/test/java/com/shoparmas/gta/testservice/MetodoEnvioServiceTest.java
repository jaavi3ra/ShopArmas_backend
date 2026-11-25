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

import com.shoparmas.gta.DTO.MetodoEnvioDTO;
import com.shoparmas.gta.model.MetodoEnvio;
import com.shoparmas.gta.repository.MetodoEnvioRepository;
import com.shoparmas.gta.service.MetodoEnvioService;

@ExtendWith(MockitoExtension.class)
class MetodoEnvioServiceTest {

    @Mock
    private MetodoEnvioRepository metodoEnvioRepository;

    @InjectMocks
    private MetodoEnvioService service;

    @Test
    void obtenerMetodoEnvioPorId_DeberiaMapearEntidad() {
        // GIVEN
        MetodoEnvio entity = entidadExistente();
        when(metodoEnvioRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        MetodoEnvioDTO resultado = service.obtenerMetodoEnvioPorId(7);

        // THEN
        assertEquals(7, resultado.getIdMetodoEnvio());
        assertEquals("Retiro", resultado.getNombreMetodo());
    }

    @Test
    void obtenerMetodoEnvioPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(metodoEnvioRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerMetodoEnvioPorId(7));

        // THEN
        assertEquals("Método de envío no encontrado con ID: 7", error.getMessage());
    }

    @Test
    void eliminarMetodoEnvio_DeberiaEliminarEntidadExistente() {
        // GIVEN
        MetodoEnvio entity = entidadExistente();
        when(metodoEnvioRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarMetodoEnvio(7);

        // THEN
        verify(metodoEnvioRepository).delete(entity);
    }

    @Test
    void crearMetodoEnvio_DeberiaGuardarDatosDeLaSolicitud() {
        // GIVEN
        MetodoEnvioDTO solicitud = new MetodoEnvioDTO();
        solicitud.setNombreMetodo("Despacho");
        when(metodoEnvioRepository.save(any(MetodoEnvio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        MetodoEnvioDTO resultado = service.crearMetodoEnvio(solicitud);

        // THEN
        ArgumentCaptor<MetodoEnvio> captor = ArgumentCaptor.forClass(MetodoEnvio.class);
        verify(metodoEnvioRepository).save(captor.capture());
        assertEquals("Despacho", captor.getValue().getNombreMetodo());
        assertEquals("Despacho", resultado.getNombreMetodo());
    }

    @Test
    void actualizarMetodoEnvio_DeberiaGuardarCambios() {
        // GIVEN
        MetodoEnvio entity = entidadExistente();
        when(metodoEnvioRepository.findById(7)).thenReturn(Optional.of(entity));
        MetodoEnvioDTO solicitud = new MetodoEnvioDTO();
        solicitud.setNombreMetodo("Despacho");
        when(metodoEnvioRepository.save(entity)).thenReturn(entity);

        // WHEN
        MetodoEnvioDTO resultado = service.actualizarMetodoEnvio(7, solicitud);

        // THEN
        verify(metodoEnvioRepository).save(entity);
        assertEquals(7, resultado.getIdMetodoEnvio());
        assertEquals("Despacho", resultado.getNombreMetodo());
    }

    private MetodoEnvio entidadExistente() {
        MetodoEnvio entity = new MetodoEnvio();
        entity.setIdMetodoEnvio(7);
        entity.setNombreMetodo("Retiro");
        return entity;
    }
}
