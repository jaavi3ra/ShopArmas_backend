package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.SubcategoriaDTO;
import com.shoparmas.gta.model.Subcategoria;
import org.springframework.stereotype.Service;

@Service
public class SubcategoriaService {

    private SubcategoriaDTO convertirADTO(Subcategoria subcategoria) {
        SubcategoriaDTO dto = new SubcategoriaDTO();

        dto.setIdSubcategoria(subcategoria.getIdSubcategoria());
        dto.setNombreSubcategoria(subcategoria.getNombreSubcategoria());
        dto.setDescripcion(subcategoria.getDescripcion());
        dto.setIdCategoria(subcategoria.getCategoria().getIdCategoria());

        return dto;
    }
}
