package com.shoparmas.gta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoparmas.gta.DTO.SubcategoriaDTO;
import com.shoparmas.gta.assembler.SubcategoriaAssembler;
import com.shoparmas.gta.service.SubcategoriaService;

import jakarta.validation.Valid;

@RestController("SubcategoriaControllerV1")
@RequestMapping("/api/v1/subcategoria")
public class SubcategoriaController {

    @Autowired
    private SubcategoriaService subcategoriaService;

    @Autowired
    private SubcategoriaAssembler assembler;

    @GetMapping(value = "/{idSubcategoria}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SubcategoriaDTO>> obtenerSubcategoriaPorId(@PathVariable Integer idSubcategoria) {
        try {
            SubcategoriaDTO subcategoriaDTO = subcategoriaService.obtenerSubcategoriaPorId(idSubcategoria);
            return ResponseEntity.ok(assembler.toModel(subcategoriaDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SubcategoriaDTO>> crearSubcategoria(
            @Valid @RequestBody SubcategoriaDTO subcategoriaDTO) {
        try {
            SubcategoriaDTO creada = subcategoriaService.crearSubcategoria(subcategoriaDTO);
            return ResponseEntity.ok(assembler.toModel(creada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/editar-subcategoria/{idSubcategoria}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SubcategoriaDTO>> editarSubcategoria(@PathVariable Integer idSubcategoria,
            @Valid @RequestBody SubcategoriaDTO subcategoriaDTO) {
        try {
            SubcategoriaDTO actualizada = subcategoriaService.actualizarSubcategoria(idSubcategoria, subcategoriaDTO);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/eliminar/{idSubcategoria}")
    public ResponseEntity<Void> eliminarSubcategoria(@PathVariable Integer idSubcategoria) {
        try {
            subcategoriaService.eliminarSubcategoria(idSubcategoria);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
