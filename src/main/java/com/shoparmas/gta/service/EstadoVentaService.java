package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.EstadoVentaDTO;
import com.shoparmas.gta.model.EstadoVenta;
import org.springframework.stereotype.Service;

@Service
public class EstadoVentaService {

    private EstadoVentaDTO convertirADTO(EstadoVenta estadoVenta) {
        EstadoVentaDTO dto = new EstadoVentaDTO();

        dto.setIdEstadoVenta(estadoVenta.getIdEstadoVenta());
        dto.setNombreEstado(estadoVenta.getNombreEstado());
        dto.setDescripcion(estadoVenta.getDescripcion());

        return dto;
    }
}
