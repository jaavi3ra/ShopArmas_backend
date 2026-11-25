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

import com.shoparmas.gta.DTO.DireccionDTO;
import com.shoparmas.gta.assembler.DireccionAssembler;
import com.shoparmas.gta.service.DireccionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Direcciones", description = "Gestión de direcciones de envío")
@RestController("DireccionControllerV1")
@RequestMapping("/api/v1/direccion")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private DireccionAssembler assembler;

    @Operation(summary = "Obtener dirección por ID")
    @GetMapping(value = "/{idDireccion}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DireccionDTO>> obtenerDireccionPorId(@PathVariable Integer idDireccion) {
        try {
            DireccionDTO direccionDTO = direccionService.obtenerDireccionPorId(idDireccion);
            return ResponseEntity.ok(assembler.toModel(direccionDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Crear nueva dirección")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DireccionDTO>> crearDireccion(@Valid @RequestBody DireccionDTO direccionDTO) {
        try {
            DireccionDTO creada = direccionService.crearDireccion(direccionDTO);
            return ResponseEntity.ok(assembler.toModel(creada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Editar dirección existente")
    @PutMapping(value = "/editar-direccion/{idDireccion}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DireccionDTO>> editarDireccion(@PathVariable Integer idDireccion,
            @Valid @RequestBody DireccionDTO direccionDTO) {
        try {
            DireccionDTO actualizada = direccionService.actualizarDireccion(idDireccion, direccionDTO);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar dirección por ID")
    @DeleteMapping(value = "/eliminar/{idDireccion}")
    public ResponseEntity<Void> eliminarDireccion(@PathVariable Integer idDireccion) {
        try {
            direccionService.eliminarDireccion(idDireccion);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
