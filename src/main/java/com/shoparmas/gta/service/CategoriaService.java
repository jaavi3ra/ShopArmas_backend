package com.shoparmas.gta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoparmas.gta.DTO.CategoriaDTO;
import com.shoparmas.gta.model.Categoria;
import com.shoparmas.gta.repository.CategoriaRepository;

@Service 
public class CategoriaService {

    @Autowired 
    private CategoriaRepository categoriaRepository;
    
    private CategoriaDTO convertirADTO(Categoria categoria) {
    CategoriaDTO categoriaDTO = new CategoriaDTO();

    categoriaDTO.setIdCategoria(categoria.getIdCategoria());
    categoriaDTO.setNombreCategoria(categoria.getNombreCategoria());
    categoriaDTO.setDescripcion(categoria.getDescripcion());

    return categoriaDTO;
}

    private Categoria buscarCategoria(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + idCategoria));
    }

    public CategoriaDTO obtenerCategoriaPorId(Integer idCategoria) {
        Categoria categoria = buscarCategoria(idCategoria);
        return convertirADTO(categoria);
    }

    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(categoriaDTO.getNombreCategoria());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        Categoria guardada = categoriaRepository.save(categoria);
        return convertirADTO(guardada);
    }

    public CategoriaDTO actualizarCategoria(Integer idCategoria, CategoriaDTO categoriaDTO) {
        Categoria categoria = buscarCategoria(idCategoria);
        categoria.setNombreCategoria(categoriaDTO.getNombreCategoria());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        Categoria actualizada = categoriaRepository.save(categoria);
        return convertirADTO(actualizada);
    }

    public void eliminarCategoria(Integer idCategoria) {
        Categoria categoria = buscarCategoria(idCategoria);
        categoriaRepository.delete(categoria);
    }
}

