package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.RolDTO;
import com.shoparmas.gta.model.Rol;
import org.springframework.stereotype.Service;

@Service
public class RolService {

    private RolDTO convertirADTO(Rol rol) {
        RolDTO dto = new RolDTO();

        dto.setIdRol(rol.getIdRol());
        dto.setNombreRol(rol.getNombreRol());
        dto.setDescripcion(rol.getDescripcion());

        return dto;
    }
}
