package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.EstadoArmaDTO;
import com.shoparmas.gta.model.EstadoArma;
import com.shoparmas.gta.repository.EstadoArmaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadoArmaService {
    @Autowired 
    private EstadoArmaRepository estadoArmaRepository;

    private EstadoArma buscarEstadoArma(Integer idEstadoArma) {
        log.info("Buscando estado de arma con ID: {}", idEstadoArma);
        return estadoArmaRepository.findById(idEstadoArma)
                .orElseThrow(() -> new RuntimeException("Estado de arma no encontrado con ID: " + idEstadoArma));
    }

    private EstadoArmaDTO convertirADTO(EstadoArma estadoArma) {
        EstadoArmaDTO dto = new EstadoArmaDTO();

        dto.setIdEstadoArma(estadoArma.getIdEstadoArma());
        dto.setNombreEstado(estadoArma.getNombreEstado());
        dto.setDescripcion(estadoArma.getDescripcion());

        return dto;
    }

    public EstadoArmaDTO obtenerEstadoArmaPorId(Integer idEstadoArma) {
        EstadoArma estadoArma = buscarEstadoArma(idEstadoArma);
        return convertirADTO(estadoArma);
    }
    public EstadoArmaDTO crearEstadoArma(EstadoArmaDTO estadoArmaDTO) {
        try {
            log.info("Creando estado de arma con nombre: {}", estadoArmaDTO.getNombreEstado());
            EstadoArma estadoArma = new EstadoArma();
            estadoArma.setNombreEstado(estadoArmaDTO.getNombreEstado());
            estadoArma.setDescripcion(estadoArmaDTO.getDescripcion());
            EstadoArma guardada = estadoArmaRepository.save(estadoArma);
            return convertirADTO(guardada);
        } catch (RuntimeException e) {
            log.error("Error al crear el estado de arma con nombre: {}", estadoArmaDTO.getNombreEstado(), e);
            throw e;
        }
    }

    public EstadoArmaDTO actualizarEstadoArma(Integer idEstadoArma, EstadoArmaDTO estadoArmaDTO) {
        log.info("Actualizando estado de arma con ID: {}", idEstadoArma);
        EstadoArma estadoArma = buscarEstadoArma(idEstadoArma);
        estadoArma.setNombreEstado(estadoArmaDTO.getNombreEstado());
        estadoArma.setDescripcion(estadoArmaDTO.getDescripcion());
        EstadoArma guardada = estadoArmaRepository.save(estadoArma);
        return convertirADTO(guardada);
    }

    public void eliminarEstadoArma(Integer idEstadoArma) {
        log.info("Eliminando estado de arma con ID: {}", idEstadoArma);
        EstadoArma estadoArma = buscarEstadoArma(idEstadoArma);
        estadoArmaRepository.delete(estadoArma);
    }
}