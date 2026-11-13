package com.shoparmas.gta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoparmas.gta.DTO.CategoriaDTO;
import com.shoparmas.gta.model.Categoria;
import com.shoparmas.gta.repository.CategoriaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
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
        log.info("Buscando categoría con ID: {}", idCategoria);
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + idCategoria));
    }

    public CategoriaDTO obtenerCategoriaPorId(Integer idCategoria) {
        log.info("Obteniendo categoría por ID: {}", idCategoria);
        Categoria categoria = buscarCategoria(idCategoria);
        return convertirADTO(categoria);
    }

    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        try {
            log.info("Creando categoría: {}", categoriaDTO.getNombreCategoria());
            Categoria categoria = new Categoria();
            categoria.setNombreCategoria(categoriaDTO.getNombreCategoria());
            categoria.setDescripcion(categoriaDTO.getDescripcion());

            Categoria guardada = categoriaRepository.save(categoria);
            return convertirADTO(guardada);
        } catch (RuntimeException e) {
            log.error("Error al crear la categoría: {}", categoriaDTO.getNombreCategoria(), e);
            throw e;
        }
        
    }

    public CategoriaDTO actualizarCategoria(Integer idCategoria, CategoriaDTO categoriaDTO) {
        log.info("Actualizando categoría con ID: {}", idCategoria);
        Categoria categoria = buscarCategoria(idCategoria);
        categoria.setNombreCategoria(categoriaDTO.getNombreCategoria());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        Categoria actualizada = categoriaRepository.save(categoria);
        return convertirADTO(actualizada);
    }

    public void eliminarCategoria(Integer idCategoria) {
        log.info("Eliminando categoría con ID: {}", idCategoria);
        Categoria categoria = buscarCategoria(idCategoria);
        categoriaRepository.delete(categoria);
    }
}

