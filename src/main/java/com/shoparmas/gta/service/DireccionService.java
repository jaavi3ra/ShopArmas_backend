package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.DireccionDTO;
import com.shoparmas.gta.model.Direccion;
import org.springframework.stereotype.Service;

@Service
public class DireccionService {

    private DireccionDTO convertirADTO(Direccion direccion) {
        DireccionDTO dto = new DireccionDTO();

        dto.setIdDireccion(direccion.getIdDireccion());
        dto.setIdUsuario(direccion.getUsuario().getIdUsuario());
        dto.setIdComuna(direccion.getComuna().getIdComuna());
        dto.setCalle(direccion.getCalle());
        dto.setNumero(direccion.getNumero());
        dto.setDepartamento(direccion.getDepartamento());
        dto.setCodigoPostal(direccion.getCodigoPostal());
        dto.setReferencia(direccion.getReferencia());

        return dto;
    }
}
