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

import com.shoparmas.gta.DTO.CategoriaDTO;
import com.shoparmas.gta.assembler.CategoriaAssembler;
import com.shoparmas.gta.service.CategoriaService;

import jakarta.validation.Valid;

@RestController("CategoriaControllerV2")
@RequestMapping("/api/v2/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaAssembler assembler;

    @GetMapping(value = "/{idCategoria}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaDTO>> obtenerCategoriaPorId(@PathVariable Integer idCategoria) {
        try {
            CategoriaDTO categoriaDTO = categoriaService.obtenerCategoriaPorId(idCategoria);
            return ResponseEntity.ok(assembler.toModel(categoriaDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaDTO>> crearCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        try {
            CategoriaDTO creada = categoriaService.crearCategoria(categoriaDTO);
            return ResponseEntity.ok(assembler.toModel(creada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/editar-categoria/{idCategoria}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaDTO>> editarCategoria(@PathVariable Integer idCategoria,
            @Valid @RequestBody CategoriaDTO categoriaDTO) {
        try {
            CategoriaDTO actualizada = categoriaService.actualizarCategoria(idCategoria, categoriaDTO);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/eliminar/{idCategoria}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer idCategoria) {
        try {
            categoriaService.eliminarCategoria(idCategoria);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
