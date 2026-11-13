package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.ComunaDTO;
import com.shoparmas.gta.model.Comuna;
import org.springframework.stereotype.Service;

@Service
public class ComunaService {

    private ComunaDTO convertirADTO(Comuna comuna) {
        ComunaDTO dto = new ComunaDTO();

        dto.setIdComuna(comuna.getIdComuna());
        dto.setNombreComuna(comuna.getNombreComuna());
        dto.setIdRegion(comuna.getRegion().getIdRegion());

        return dto;
    }
}
