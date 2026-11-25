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

import com.shoparmas.gta.DTO.MetodoEnvioDTO;
import com.shoparmas.gta.assembler.MetodoEnvioAssembler;
import com.shoparmas.gta.service.MetodoEnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Métodos de Envío", description = "Gestión de métodos de envío")
@RestController("MetodoEnvioControllerV1")
@RequestMapping("/api/v1/metodo-envio")
public class MetodoEnvioController {

    @Autowired
    private MetodoEnvioService metodoEnvioService;

    @Autowired
    private MetodoEnvioAssembler assembler;

    @Operation(summary = "Obtener método de envío por ID")
    @GetMapping(value = "/{idMetodoEnvio}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoEnvioDTO>> obtenerMetodoEnvioPorId(@PathVariable Integer idMetodoEnvio) {
        try {
            MetodoEnvioDTO metodoEnvioDTO = metodoEnvioService.obtenerMetodoEnvioPorId(idMetodoEnvio);
            return ResponseEntity.ok(assembler.toModel(metodoEnvioDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Crear nuevo método de envío")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoEnvioDTO>> crearMetodoEnvio(
            @Valid @RequestBody MetodoEnvioDTO metodoEnvioDTO) {
        try {
            MetodoEnvioDTO creado = metodoEnvioService.crearMetodoEnvio(metodoEnvioDTO);
            return ResponseEntity.ok(assembler.toModel(creado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Editar método de envío existente")
    @PutMapping(value = "/editar-metodo/{idMetodoEnvio}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoEnvioDTO>> editarMetodoEnvio(@PathVariable Integer idMetodoEnvio,
            @Valid @RequestBody MetodoEnvioDTO metodoEnvioDTO) {
        try {
            MetodoEnvioDTO actualizado = metodoEnvioService.actualizarMetodoEnvio(idMetodoEnvio, metodoEnvioDTO);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar método de envío por ID")
    @DeleteMapping(value = "/eliminar/{idMetodoEnvio}")
    public ResponseEntity<Void> eliminarMetodoEnvio(@PathVariable Integer idMetodoEnvio) {
        try {
            metodoEnvioService.eliminarMetodoEnvio(idMetodoEnvio);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
