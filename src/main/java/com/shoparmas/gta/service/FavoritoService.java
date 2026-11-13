package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.FavoritoDTO;
import com.shoparmas.gta.model.Favorito;
import org.springframework.stereotype.Service;

@Service
public class FavoritoService {

    private FavoritoDTO convertirADTO(Favorito favorito) {
        FavoritoDTO dto = new FavoritoDTO();

        dto.setIdUsuario(favorito.getId().getIdUsuario());
        dto.setIdProducto(favorito.getId().getIdProducto());
        dto.setFechaAgregado(favorito.getFechaAgregado());

        return dto;
    }
}
