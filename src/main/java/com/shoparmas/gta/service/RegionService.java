package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.RegionDTO;
import com.shoparmas.gta.model.Region;
import com.shoparmas.gta.repository.RegionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service
public class RegionService {
    @Autowired 
    private RegionRepository regionRepository;

    private RegionDTO convertirADTO(Region region) {
        RegionDTO dto = new RegionDTO();

        dto.setIdRegion(region.getIdRegion());
        dto.setNombreRegion(region.getNombreRegion());

        return dto;
    }

    private Region buscarRegion(Integer idRegion) {
        log.info("Buscando región con ID: {}", idRegion);
        return regionRepository.findById(idRegion)
                .orElseThrow(() -> new RuntimeException("Región no encontrada con ID: " + idRegion));
    }

    public RegionDTO obtenerRegionPorId(Integer idRegion) {
        log.info("Obteniendo región por ID: {}", idRegion);
        Region region = buscarRegion(idRegion);
        return convertirADTO(region);
    }
    public RegionDTO crearRegion(RegionDTO regionDTO) {
        try {
            log.info("Creando región: {}", regionDTO.getNombreRegion());
            Region region = new Region();
            region.setNombreRegion(regionDTO.getNombreRegion());

            Region guardada = regionRepository.save(region);
            return convertirADTO(guardada);
        } catch (RuntimeException e) {
            log.error("Error al crear la región: {}", regionDTO.getNombreRegion(), e);
            throw e;
        }
    }

    public RegionDTO actualizarRegion(Integer idRegion, RegionDTO regionDTO) {
        log.info("Actualizando región con ID: {}", idRegion);
        Region region = buscarRegion(idRegion);
        region.setNombreRegion(regionDTO.getNombreRegion());

        Region guardada = regionRepository.save(region);
        return convertirADTO(guardada);
    }
    public void eliminarRegion(Integer idRegion) {
        log.info("Eliminando región con ID: {}", idRegion);
        Region region = buscarRegion(idRegion);
        regionRepository.delete(region);
    }
}
