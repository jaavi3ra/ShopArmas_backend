package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.MetodoEnvioDTO;
import com.shoparmas.gta.model.MetodoEnvio;
import org.springframework.stereotype.Service;

@Service
public class MetodoEnvioService {

    private MetodoEnvioDTO convertirADTO(MetodoEnvio metodoEnvio) {
        MetodoEnvioDTO dto = new MetodoEnvioDTO();

        dto.setIdMetodoEnvio(metodoEnvio.getIdMetodoEnvio());
        dto.setNombreMetodo(metodoEnvio.getNombreMetodo());
        dto.setDescripcion(metodoEnvio.getDescripcion());
        dto.setCosto(metodoEnvio.getCosto());
        dto.setActivo(metodoEnvio.getActivo());

        return dto;
    }
}
