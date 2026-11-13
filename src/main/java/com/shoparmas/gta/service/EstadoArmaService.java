package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.EstadoArmaDTO;
import com.shoparmas.gta.model.EstadoArma;
import org.springframework.stereotype.Service;

@Service
public class EstadoArmaService {

    private EstadoArmaDTO convertirADTO(EstadoArma estadoArma) {
        EstadoArmaDTO dto = new EstadoArmaDTO();

        dto.setIdEstadoArma(estadoArma.getIdEstadoArma());
        dto.setNombreEstado(estadoArma.getNombreEstado());
        dto.setDescripcion(estadoArma.getDescripcion());

        return dto;
    }
}
