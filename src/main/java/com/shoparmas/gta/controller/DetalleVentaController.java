
package com.shoparmas.gta.controller;

import com.shoparmas.gta.DTO.DetalleVentaDTO;
import com.shoparmas.gta.service.DetalleVentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detalles-venta")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaDTO> obtenerDetalleVentaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(detalleVentaService.obtenerDetalleVentaPorId(id));
    }

    @PostMapping
    public ResponseEntity<DetalleVentaDTO> crearDetalleVenta(@RequestBody DetalleVentaDTO detalleVentaDTO) {
        return ResponseEntity.status(201).body(detalleVentaService.crearDetalleVenta(detalleVentaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleVentaDTO> actualizarDetalleVenta(
            @PathVariable Integer id,
            @RequestBody DetalleVentaDTO detalleVentaDTO) {

        return ResponseEntity.ok(detalleVentaService.actualizarDetalleVenta(id, detalleVentaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalleVenta(@PathVariable Integer id) {
        detalleVentaService.eliminarDetalleVenta(id);
        return ResponseEntity.noContent().build();
    }
}
