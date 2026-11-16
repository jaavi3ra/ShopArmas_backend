package com.shoparmas.gta.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.shoparmas.gta.service.VentaService;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerVentaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaService.obtenerVentaPorId(id));
    }

    @PostMapping
    public ResponseEntity<VentaDTO> crearVenta(@RequestBody VentaDTO ventaDTO) {
        return ResponseEntity.ok(ventaService.crearVenta(ventaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> actualizarVenta(@PathVariable Integer id, @RequestBody VentaDTO ventaDTO) {
        return ResponseEntity.ok(ventaService.actualizarVenta(id, ventaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Integer id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
