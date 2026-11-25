package com.shoparmas.gta.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shoparmas.gta.DTO.ComunaDTO;
import com.shoparmas.gta.model.Comuna;
import com.shoparmas.gta.model.Region;
import com.shoparmas.gta.repository.ComunaRepository;
import com.shoparmas.gta.repository.RegionRepository;
import com.shoparmas.gta.service.ComunaService;

@ExtendWith(MockitoExtension.class)
class ComunaServiceTest {

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private ComunaService service;

    @Test
    void obtenerComunaPorId_DeberiaMapearEntidad() {
        // GIVEN
        Comuna entity = entidadExistente();
        when(comunaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        ComunaDTO resultado = service.obtenerComunaPorId(7);

        // THEN
        assertEquals(7, resultado.getIdComuna());
        assertEquals("Santiago", resultado.getNombreComuna());
        assertEquals(21, resultado.getIdRegion());
    }

    @Test
    void obtenerComunaPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(comunaRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerComunaPorId(7));

        // THEN
        assertEquals("Comuna no encontrada con ID: 7", error.getMessage());
    }

    @Test
    void eliminarComuna_DeberiaEliminarEntidadExistente() {
        // GIVEN
        Comuna entity = entidadExistente();
        when(comunaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarComuna(7);

        // THEN
        verify(comunaRepository).delete(entity);
    }

    @Test
    void crearComuna_DeberiaFallarSiRegionNoExiste() {
        // GIVEN
        ComunaDTO solicitud = new ComunaDTO();
        solicitud.setIdRegion(31);
        when(regionRepository.findById(31)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.crearComuna(solicitud));

        // THEN
        assertEquals("Región no encontrada con ID: 31", error.getMessage());
        verifyNoInteractions(comunaRepository);
    }

    @Test
    void crearComuna_DeberiaGuardarDatosDeLaSolicitud() {
        // GIVEN
        ComunaDTO solicitud = new ComunaDTO();
        solicitud.setNombreComuna("Providencia");
        Region region = new Region();
        region.setIdRegion(31);
        solicitud.setIdRegion(31);
        when(regionRepository.findById(31)).thenReturn(Optional.of(region));
        when(comunaRepository.save(any(Comuna.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        ComunaDTO resultado = service.crearComuna(solicitud);

        // THEN
        ArgumentCaptor<Comuna> captor = ArgumentCaptor.forClass(Comuna.class);
        verify(comunaRepository).save(captor.capture());
        assertEquals("Providencia", captor.getValue().getNombreComuna());
        assertEquals("Providencia", resultado.getNombreComuna());
        assertEquals(31, resultado.getIdRegion());
    }

    @Test
    void actualizarComuna_DeberiaGuardarCambios() {
        // GIVEN
        Comuna entity = entidadExistente();
        when(comunaRepository.findById(7)).thenReturn(Optional.of(entity));
        ComunaDTO solicitud = new ComunaDTO();
        solicitud.setNombreComuna("Providencia");
        Region region = new Region();
        region.setIdRegion(31);
        solicitud.setIdRegion(31);
        when(regionRepository.findById(31)).thenReturn(Optional.of(region));
        when(comunaRepository.save(entity)).thenReturn(entity);

        // WHEN
        ComunaDTO resultado = service.actualizarComuna(7, solicitud);

        // THEN
        verify(comunaRepository).save(entity);
        assertEquals(7, resultado.getIdComuna());
        assertEquals("Providencia", resultado.getNombreComuna());
        assertEquals(31, resultado.getIdRegion());
    }

    private Comuna entidadExistente() {
        Comuna entity = new Comuna();
        entity.setIdComuna(7);
        entity.setNombreComuna("Santiago");
        Region region = new Region();
        region.setIdRegion(21);
        entity.setRegion(region);
        return entity;
    }
}
