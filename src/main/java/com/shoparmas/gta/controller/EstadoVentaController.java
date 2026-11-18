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

import com.shoparmas.gta.DTO.EstadoVentaDTO;
import com.shoparmas.gta.assembler.EstadoVentaAssembler;
import com.shoparmas.gta.service.EstadoVentaService;

import jakarta.validation.Valid;

@RestController("EstadoVentaControllerV2")
@RequestMapping("/api/v1/estado-venta")
public class EstadoVentaController {

    @Autowired
    private EstadoVentaService estadoVentaService;

    @Autowired
    private EstadoVentaAssembler assembler;

    @GetMapping(value = "/{idEstadoVenta}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EstadoVentaDTO>> obtenerEstadoVentaPorId(@PathVariable Integer idEstadoVenta) {
        try {
            EstadoVentaDTO estadoDTO = estadoVentaService.obtenerEstadoVentaPorId(idEstadoVenta);
            return ResponseEntity.ok(assembler.toModel(estadoDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EstadoVentaDTO>> crearEstadoVenta(@Valid @RequestBody EstadoVentaDTO estadoVentaDTO) {
        try {
            EstadoVentaDTO nuevoEstado = estadoVentaService.crearEstadoVenta(estadoVentaDTO);
            return ResponseEntity.ok(assembler.toModel(nuevoEstado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/editar-estado/{idEstadoVenta}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EstadoVentaDTO>> editarEstado(@PathVariable Integer idEstadoVenta,
            @Valid @RequestBody EstadoVentaDTO estadoVentaDTO) {
        try {
            EstadoVentaDTO estadoDTO = estadoVentaService.actualizarEstadoVenta(idEstadoVenta, estadoVentaDTO);
            return ResponseEntity.ok(assembler.toModel(estadoDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/eliminar/{idEstadoVenta}")
    public ResponseEntity<Void> eliminarEstadoVenta(@PathVariable Integer idEstadoVenta) {
        try {
            estadoVentaService.eliminarEstadoVenta(idEstadoVenta);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
