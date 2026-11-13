package com.shoparmas.gta.service;

import org.springframework.stereotype.Service;

import com.shoparmas.gta.DTO.CategoriaDTO;
import com.shoparmas.gta.model.Categoria;

@Service 
public class CategoriaService {
    
    private CategoriaDTO convertirADTO(Categoria categoria) {
    CategoriaDTO categoriaDTO = new CategoriaDTO();

    categoriaDTO.setIdCategoria(categoria.getIdCategoria());
    categoriaDTO.setNombreCategoria(categoria.getNombreCategoria());
    categoriaDTO.setDescripcion(categoria.getDescripcion());

    return categoriaDTO;
}
}
