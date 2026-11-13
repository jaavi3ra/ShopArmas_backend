package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.RegionDTO;
import com.shoparmas.gta.model.Region;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    private RegionDTO convertirADTO(Region region) {
        RegionDTO dto = new RegionDTO();

        dto.setIdRegion(region.getIdRegion());
        dto.setNombreRegion(region.getNombreRegion());

        return dto;
    }
}
