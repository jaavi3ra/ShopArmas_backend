package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.SubcategoriaDTO;
import com.shoparmas.gta.model.Subcategoria;
import com.shoparmas.gta.repository.SubcategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubcategoriaService {
    @Autowired 
    private SubcategoriaRepository subcategoriaRepository;

    private Subcategoria buscarSubcategoria(Integer idSubcategoria) {
        return subcategoriaRepository.findById(idSubcategoria)
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada con ID: " + idSubcategoria));
    }

    private SubcategoriaDTO convertirADTO(Subcategoria subcategoria) {
        SubcategoriaDTO dto = new SubcategoriaDTO();

        dto.setIdSubcategoria(subcategoria.getIdSubcategoria());
        dto.setNombreSubcategoria(subcategoria.getNombreSubcategoria());
        dto.setDescripcion(subcategoria.getDescripcion());
        dto.setIdCategoria(subcategoria.getCategoria().getIdCategoria());

        return dto;
    }

    public SubcategoriaDTO obtenerSubcategoriaPorId(Integer idSubcategoria) {
        Subcategoria subcategoria = buscarSubcategoria(idSubcategoria);
        return convertirADTO(subcategoria);
    }
    
    public SubcategoriaDTO crearSubcategoria(SubcategoriaDTO subcategoriaDTO) {
        try {
            Subcategoria subcategoria = new Subcategoria();
            subcategoria.setNombreSubcategoria(subcategoriaDTO.getNombreSubcategoria());
            subcategoria.setDescripcion(subcategoriaDTO.getDescripcion());
            // Asignar la categoría según corresponda
            subcategoria.setCategoria(null);

            Subcategoria guardada = subcategoriaRepository.save(subcategoria);
            return convertirADTO(guardada);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public SubcategoriaDTO actualizarSubcategoria(Integer idSubcategoria, SubcategoriaDTO subcategoriaDTO) {
        Subcategoria subcategoria = buscarSubcategoria(idSubcategoria);
        subcategoria.setNombreSubcategoria(subcategoriaDTO.getNombreSubcategoria());
        subcategoria.setDescripcion(subcategoriaDTO.getDescripcion());
        // Actualizar la categoría según corresponda
        subcategoria.setCategoria(null);

        Subcategoria actualizada = subcategoriaRepository.save(subcategoria);
        return convertirADTO(actualizada);
    }

    public void eliminarSubcategoria(Integer idSubcategoria) {
        Subcategoria subcategoria = buscarSubcategoria(idSubcategoria);
        subcategoriaRepository.delete(subcategoria);
    }
}
