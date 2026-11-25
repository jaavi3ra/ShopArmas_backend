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

import com.shoparmas.gta.DTO.RegionDTO;
import com.shoparmas.gta.assembler.RegionAssembler;
import com.shoparmas.gta.service.RegionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Regiones", description = "Gestión de regiones")
@RestController("RegionControllerV1")
@RequestMapping("/api/v1/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionAssembler assembler;

    @Operation(summary = "Obtener región por ID")
    @GetMapping(value = "/{idRegion}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionDTO>> obtenerRegionPorId(@PathVariable Integer idRegion) {
        try {
            RegionDTO regionDTO = regionService.obtenerRegionPorId(idRegion);
            return ResponseEntity.ok(assembler.toModel(regionDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Crear nueva región")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionDTO>> crearRegion(@Valid @RequestBody RegionDTO regionDTO) {
        try {
            RegionDTO creada = regionService.crearRegion(regionDTO);
            return ResponseEntity.ok(assembler.toModel(creada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Editar región existente")
    @PutMapping(value = "/editar-region/{idRegion}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionDTO>> editarRegion(@PathVariable Integer idRegion,
            @Valid @RequestBody RegionDTO regionDTO) {
        try {
            RegionDTO actualizada = regionService.actualizarRegion(idRegion, regionDTO);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar región por ID")
    @DeleteMapping(value = "/eliminar/{idRegion}")
    public ResponseEntity<Void> eliminarRegion(@PathVariable Integer idRegion) {
        try {
            regionService.eliminarRegion(idRegion);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
