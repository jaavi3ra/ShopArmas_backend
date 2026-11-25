package com.shoparmas.gta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoparmas.gta.DTO.FavoritoDTO;
import com.shoparmas.gta.assembler.FavoritoAssembler;
import com.shoparmas.gta.service.FavoritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Favoritos", description = "Gestión de productos favoritos por usuario")
@RestController("FavoritoControllerV1")
@RequestMapping("/api/v1/favorito")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private FavoritoAssembler assembler;

    @Operation(summary = "Obtener favorito por usuario y producto")
    @GetMapping(value = "/usuario/{idUsuario}/producto/{idProducto}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<FavoritoDTO>> obtenerFavorito(@PathVariable Integer idUsuario,
            @PathVariable Integer idProducto) {
        try {
            FavoritoDTO favoritoDTO = favoritoService.obtenerFavoritoPorId(idUsuario, idProducto);
            return ResponseEntity.ok(assembler.toModel(favoritoDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Agregar producto a favoritos")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<FavoritoDTO>> agregarFavorito(@Valid @RequestBody FavoritoDTO favoritoDTO) {
        try {
            FavoritoDTO creado = favoritoService.crearFavorito(favoritoDTO);
            return ResponseEntity.ok(assembler.toModel(creado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar producto de favoritos")
    @DeleteMapping(value = "/usuario/{idUsuario}/producto/{idProducto}")
    public ResponseEntity<Void> eliminarFavorito(@PathVariable Integer idUsuario,
            @PathVariable Integer idProducto) {
        try {
            favoritoService.eliminarFavorito(idUsuario, idProducto);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
