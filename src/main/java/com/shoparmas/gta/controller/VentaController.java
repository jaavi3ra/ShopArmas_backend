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

import com.shoparmas.gta.DTO.VentaDTO;
import com.shoparmas.gta.assembler.VentaAssembler;
import com.shoparmas.gta.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Ventas", description = "Gestión de ventas")
@RestController("VentaControllerV1")
@RequestMapping("/api/v1/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaAssembler assembler;

    @Operation(summary = "Obtener venta por ID")
    @GetMapping(value = "/{idVenta}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<VentaDTO>> obtenerVentaPorId(@PathVariable Integer idVenta) {
        try {
            VentaDTO ventaDTO = ventaService.obtenerVentaPorId(idVenta);
            return ResponseEntity.ok(assembler.toModel(ventaDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Crear nueva venta")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<VentaDTO>> crearVenta(@Valid @RequestBody VentaDTO ventaDTO) {
        try {
            VentaDTO creada = ventaService.crearVenta(ventaDTO);
            return ResponseEntity.ok(assembler.toModel(creada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Editar venta existente")
    @PutMapping(value = "/editar-venta/{idVenta}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<VentaDTO>> editarVenta(@PathVariable Integer idVenta,
            @Valid @RequestBody VentaDTO ventaDTO) {
        try {
            VentaDTO actualizada = ventaService.actualizarVenta(idVenta, ventaDTO);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar venta por ID")
    @DeleteMapping(value = "/eliminar/{idVenta}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Integer idVenta) {
        try {
            ventaService.eliminarVenta(idVenta);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
