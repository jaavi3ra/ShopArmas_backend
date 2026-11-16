
package com.shoparmas.gta.controller;

import com.shoparmas.gta.DTO.ImagenProductoDTO;
import com.shoparmas.gta.service.ImagenProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/imagenes-producto")
public class ImagenProductoController {

    @Autowired
    private ImagenProductoService imagenProductoService;

    @GetMapping("/{id}")
    public ResponseEntity<ImagenProductoDTO> obtenerImagenProductoPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(imagenProductoService.obtenerImagenProductoPorId(id));
    }

    @PostMapping
    public ResponseEntity<ImagenProductoDTO> crearImagenProducto(@RequestBody ImagenProductoDTO imagenProductoDTO) {
        return ResponseEntity.status(201).body(imagenProductoService.crearImagenProducto(imagenProductoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImagenProductoDTO> actualizarImagenProducto(
            @PathVariable Integer id,
            @RequestBody ImagenProductoDTO imagenProductoDTO) {

        return ResponseEntity.ok(imagenProductoService.actualizarImagenProducto(id, imagenProductoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarImagenProducto(@PathVariable Integer id) {
        imagenProductoService.eliminarImagenProducto(id);
        return ResponseEntity.noContent().build();
    }
}
