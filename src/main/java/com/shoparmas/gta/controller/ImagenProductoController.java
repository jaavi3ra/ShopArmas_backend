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

import com.shoparmas.gta.DTO.ImagenProductoDTO;
import com.shoparmas.gta.assembler.ImagenProductoAssembler;
import com.shoparmas.gta.service.ImagenProductoService;

import jakarta.validation.Valid;

@RestController("ImagenProductoControllerV1")
@RequestMapping("/api/v1/imagen-producto")
public class ImagenProductoController {

    @Autowired
    private ImagenProductoService imagenProductoService;

    @Autowired
    private ImagenProductoAssembler assembler;

    @GetMapping(value = "/{idImagen}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ImagenProductoDTO>> obtenerImagenProductoPorId(@PathVariable Integer idImagen) {
        try {
            ImagenProductoDTO imagenProductoDTO = imagenProductoService.obtenerImagenProductoPorId(idImagen);
            return ResponseEntity.ok(assembler.toModel(imagenProductoDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ImagenProductoDTO>> crearImagenProducto(
            @Valid @RequestBody ImagenProductoDTO imagenProductoDTO) {
        try {
            ImagenProductoDTO creada = imagenProductoService.crearImagenProducto(imagenProductoDTO);
            return ResponseEntity.ok(assembler.toModel(creada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/editar-imagen/{idImagen}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ImagenProductoDTO>> editarImagenProducto(@PathVariable Integer idImagen,
            @Valid @RequestBody ImagenProductoDTO imagenProductoDTO) {
        try {
            ImagenProductoDTO actualizada = imagenProductoService.actualizarImagenProducto(idImagen, imagenProductoDTO);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/eliminar/{idImagen}")
    public ResponseEntity<Void> eliminarImagenProducto(@PathVariable Integer idImagen) {
        try {
            imagenProductoService.eliminarImagenProducto(idImagen);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
