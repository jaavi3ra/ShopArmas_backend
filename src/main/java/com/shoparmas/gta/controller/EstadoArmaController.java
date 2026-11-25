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

import com.shoparmas.gta.DTO.EstadoArmaDTO;
import com.shoparmas.gta.assembler.EstadoArmaAssembler;
import com.shoparmas.gta.service.EstadoArmaService;

import jakarta.validation.Valid;

@RestController("EstadoArmaControllerV1")
@RequestMapping("/api/v1/estado-arma")
public class EstadoArmaController {

    @Autowired
    private EstadoArmaService estadoArmaService;

    @Autowired
    private EstadoArmaAssembler assembler;

    @GetMapping(value = "/{idEstadoArma}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EstadoArmaDTO>> obtenerEstadoArmaPorId(@PathVariable Integer idEstadoArma) {
        try {
            EstadoArmaDTO estadoArmaDTO = estadoArmaService.obtenerEstadoArmaPorId(idEstadoArma);
            return ResponseEntity.ok(assembler.toModel(estadoArmaDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EstadoArmaDTO>> crearEstadoArma(@Valid @RequestBody EstadoArmaDTO estadoArmaDTO) {
        try {
            EstadoArmaDTO creado = estadoArmaService.crearEstadoArma(estadoArmaDTO);
            return ResponseEntity.ok(assembler.toModel(creado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/editar-estado/{idEstadoArma}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EstadoArmaDTO>> editarEstadoArma(@PathVariable Integer idEstadoArma,
            @Valid @RequestBody EstadoArmaDTO estadoArmaDTO) {
        try {
            EstadoArmaDTO actualizado = estadoArmaService.actualizarEstadoArma(idEstadoArma, estadoArmaDTO);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/eliminar/{idEstadoArma}")
    public ResponseEntity<Void> eliminarEstadoArma(@PathVariable Integer idEstadoArma) {
        try {
            estadoArmaService.eliminarEstadoArma(idEstadoArma);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
