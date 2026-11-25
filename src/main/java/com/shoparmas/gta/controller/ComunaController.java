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

import com.shoparmas.gta.DTO.ComunaDTO;
import com.shoparmas.gta.assembler.ComunaAssembler;
import com.shoparmas.gta.service.ComunaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Comunas", description = "Gestión de comunas")
@RestController("ComunaControllerV1")
@RequestMapping("/api/v1/comuna")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private ComunaAssembler assembler;

    @Operation(summary = "Obtener comuna por ID")
    @GetMapping(value = "/{idComuna}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ComunaDTO>> obtenerComunaPorId(@PathVariable Integer idComuna) {
        try {
            ComunaDTO comunaDTO = comunaService.obtenerComunaPorId(idComuna);
            return ResponseEntity.ok(assembler.toModel(comunaDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Crear nueva comuna")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ComunaDTO>> crearComuna(@Valid @RequestBody ComunaDTO comunaDTO) {
        try {
            ComunaDTO creada = comunaService.crearComuna(comunaDTO);
            return ResponseEntity.ok(assembler.toModel(creada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Editar comuna existente")
    @PutMapping(value = "/editar-comuna/{idComuna}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ComunaDTO>> editarComuna(@PathVariable Integer idComuna,
            @Valid @RequestBody ComunaDTO comunaDTO) {
        try {
            ComunaDTO actualizada = comunaService.actualizarComuna(idComuna, comunaDTO);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar comuna por ID")
    @DeleteMapping(value = "/eliminar/{idComuna}")
    public ResponseEntity<Void> eliminarComuna(@PathVariable Integer idComuna) {
        try {
            comunaService.eliminarComuna(idComuna);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
