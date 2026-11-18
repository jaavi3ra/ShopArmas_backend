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

import com.shoparmas.gta.DTO.RolDTO;
import com.shoparmas.gta.assembler.RolAssembler;
import com.shoparmas.gta.service.RolService;

import jakarta.validation.Valid;

@RestController("RolControllerV1")
@RequestMapping("/api/v1/rol")
public class RolController {

    @Autowired
    private RolService rolService;

    @Autowired
    private RolAssembler assembler;

    @GetMapping(value = "/{idRol}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RolDTO>> obtenerRolPorId(@PathVariable Integer idRol) {
        try {
            RolDTO rolDTO = rolService.obtenerRolPorId(idRol);
            return ResponseEntity.ok(assembler.toModel(rolDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RolDTO>> crearRol(@Valid @RequestBody RolDTO rolDTO) {
        try {
            RolDTO creado = rolService.crearRol(rolDTO);
            return ResponseEntity.ok(assembler.toModel(creado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/editar-rol/{idRol}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RolDTO>> actualizarRol(@PathVariable Integer idRol,
            @Valid @RequestBody RolDTO rolDTO) {
        try {
            RolDTO actualizado = rolService.actualizarRol(idRol, rolDTO);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/eliminar/{idRol}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Integer idRol) {
        try {
            rolService.eliminarRol(idRol);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
