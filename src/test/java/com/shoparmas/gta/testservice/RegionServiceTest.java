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

import com.shoparmas.gta.DTO.RegionDTO;
import com.shoparmas.gta.model.Region;
import com.shoparmas.gta.repository.RegionRepository;
import com.shoparmas.gta.service.RegionService;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService service;

    @Test
    void obtenerRegionPorId_DeberiaMapearEntidad() {
        // GIVEN
        Region entity = entidadExistente();
        when(regionRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        RegionDTO resultado = service.obtenerRegionPorId(7);

        // THEN
        assertEquals(7, resultado.getIdRegion());
        assertEquals("Valparaíso", resultado.getNombreRegion());
    }

    @Test
    void obtenerRegionPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(regionRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerRegionPorId(7));

        // THEN
        assertEquals("Región no encontrada con ID: 7", error.getMessage());
    }

    @Test
    void eliminarRegion_DeberiaEliminarEntidadExistente() {
        // GIVEN
        Region entity = entidadExistente();
        when(regionRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarRegion(7);

        // THEN
        verify(regionRepository).delete(entity);
    }

    @Test
    void crearRegion_DeberiaGuardarDatosDeLaSolicitud() {
        // GIVEN
        RegionDTO solicitud = new RegionDTO();
        solicitud.setNombreRegion("Metropolitana");
        when(regionRepository.save(any(Region.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        RegionDTO resultado = service.crearRegion(solicitud);

        // THEN
        ArgumentCaptor<Region> captor = ArgumentCaptor.forClass(Region.class);
        verify(regionRepository).save(captor.capture());
        assertEquals("Metropolitana", captor.getValue().getNombreRegion());
        assertEquals("Metropolitana", resultado.getNombreRegion());
    }

    @Test
    void actualizarRegion_DeberiaGuardarCambios() {
        // GIVEN
        Region entity = entidadExistente();
        when(regionRepository.findById(7)).thenReturn(Optional.of(entity));
        RegionDTO solicitud = new RegionDTO();
        solicitud.setNombreRegion("Metropolitana");
        when(regionRepository.save(entity)).thenReturn(entity);

        // WHEN
        RegionDTO resultado = service.actualizarRegion(7, solicitud);

        // THEN
        verify(regionRepository).save(entity);
        assertEquals(7, resultado.getIdRegion());
        assertEquals("Metropolitana", resultado.getNombreRegion());
    }

    private Region entidadExistente() {
        Region entity = new Region();
        entity.setIdRegion(7);
        entity.setNombreRegion("Valparaíso");
        return entity;
    }
}
