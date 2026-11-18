package com.shoparmas.gta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoparmas.gta.DTO.ProductoDTO;
import com.shoparmas.gta.assembler.CatalogoAssembler;
import com.shoparmas.gta.service.ProductoService;

import jakarta.validation.Valid;

@RestController("CatalogoControllerV2")
@RequestMapping("/api/v1/catalogo")
public class CatalogoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CatalogoAssembler assembler;

    @GetMapping(value = "/{idProducto}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoDTO>> obtenerProductoCatalogo(@PathVariable Integer idProducto) {
        try {
            ProductoDTO productoDTO = productoService.obtenerProductoPorId(idProducto);
            return ResponseEntity.ok(assembler.toModel(productoDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoDTO>> agregarProductoCatalogo(@Valid @RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO guardado = productoService.crearProducto(productoDTO);
            return ResponseEntity.ok(assembler.toModel(guardado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/editar-catalogo/{idProducto}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoDTO>> editarProductoCatalogo(@PathVariable Integer idProducto,
            @Valid @RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO actualizado = productoService.actualizarProducto(idProducto, productoDTO);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
