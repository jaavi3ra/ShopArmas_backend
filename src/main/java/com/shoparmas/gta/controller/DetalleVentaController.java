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

import com.shoparmas.gta.DTO.DetalleVentaDTO;
import com.shoparmas.gta.assembler.DetalleVentaAssembler;
import com.shoparmas.gta.service.DetalleVentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Detalles de Venta", description = "Gestión de detalles de venta")
@RestController("DetalleVentaControllerV1")
@RequestMapping("/api/v1/detalle-venta")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    @Autowired
    private DetalleVentaAssembler assembler;

    @Operation(summary = "Obtener detalle de venta por ID")
    @GetMapping(value = "/{idDetalleVenta}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DetalleVentaDTO>> obtenerDetalleVentaPorId(@PathVariable Integer idDetalleVenta) {
        try {
            DetalleVentaDTO detalleVentaDTO = detalleVentaService.obtenerDetalleVentaPorId(idDetalleVenta);
            return ResponseEntity.ok(assembler.toModel(detalleVentaDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Crear nuevo detalle de venta")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DetalleVentaDTO>> crearDetalleVenta(
            @Valid @RequestBody DetalleVentaDTO detalleVentaDTO) {
        try {
            DetalleVentaDTO creado = detalleVentaService.crearDetalleVenta(detalleVentaDTO);
            return ResponseEntity.ok(assembler.toModel(creado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Editar detalle de venta existente")
    @PutMapping(value = "/editar-detalle/{idDetalleVenta}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DetalleVentaDTO>> editarDetalleVenta(@PathVariable Integer idDetalleVenta,
            @Valid @RequestBody DetalleVentaDTO detalleVentaDTO) {
        try {
            DetalleVentaDTO actualizado = detalleVentaService.actualizarDetalleVenta(idDetalleVenta, detalleVentaDTO);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar detalle de venta por ID")
    @DeleteMapping(value = "/eliminar/{idDetalleVenta}")
    public ResponseEntity<Void> eliminarDetalleVenta(@PathVariable Integer idDetalleVenta) {
        try {
            detalleVentaService.eliminarDetalleVenta(idDetalleVenta);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
