package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.ComunaDTO;
import com.shoparmas.gta.model.Comuna;
import com.shoparmas.gta.model.Region;
import com.shoparmas.gta.repository.ComunaRepository;
import com.shoparmas.gta.repository.RegionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service
public class ComunaService {
    @Autowired 
    private ComunaRepository comunaRepository;
    @Autowired 
    private RegionRepository regionRepository;

    private ComunaDTO convertirADTO(Comuna comuna) {
        ComunaDTO dto = new ComunaDTO();

        dto.setIdComuna(comuna.getIdComuna());
        dto.setNombreComuna(comuna.getNombreComuna());
        dto.setIdRegion(comuna.getRegion().getIdRegion());

        return dto;
    }

    private Comuna buscarComuna(Integer idComuna) {
        log.info("Buscando comuna con ID: {}", idComuna);
        return comunaRepository.findById(idComuna)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada con ID: " + idComuna));
    }
    private Region buscarRegion(Integer idRegion) {
        log.info("Buscando región con ID: {}", idRegion);
        return regionRepository.findById(idRegion)
                .orElseThrow(() -> new RuntimeException("Región no encontrada con ID: " + idRegion));
    }

    public ComunaDTO obtenerComunaPorId(Integer idComuna) {
        log.info("Obteniendo comuna por ID: {}", idComuna);
        Comuna comuna = buscarComuna(idComuna);
        return convertirADTO(comuna);
    }

    public ComunaDTO crearComuna(ComunaDTO comunaDTO) {
        try {

            Region region = buscarRegion(comunaDTO.getIdRegion());
            log.info("Creando comuna: {}", comunaDTO.getNombreComuna());
            Comuna comuna = new Comuna();
            comuna.setNombreComuna(comunaDTO.getNombreComuna());
            comuna.setRegion(region);

            Comuna guardada = comunaRepository.save(comuna);
            return convertirADTO(guardada);
        } catch (RuntimeException e) {
            log.error("Error al crear la comuna: {}", comunaDTO.getNombreComuna(), e);
            throw e;
        }
    }

    public ComunaDTO actualizarComuna(Integer idComuna, ComunaDTO comunaDTO) {
        log.info("Actualizando comuna con ID: {}", idComuna);
        Comuna comuna = buscarComuna(idComuna);
        Region region = buscarRegion(comunaDTO.getIdRegion());
        comuna.setNombreComuna(comunaDTO.getNombreComuna());
        comuna.setRegion(region);

        Comuna guardada = comunaRepository.save(comuna);
        return convertirADTO(guardada);
    }

    public void eliminarComuna(Integer idComuna) {
        log.info("Eliminando comuna con ID: {}", idComuna);
        Comuna comuna = buscarComuna(idComuna);
        comunaRepository.delete(comuna);
    }
    
}
