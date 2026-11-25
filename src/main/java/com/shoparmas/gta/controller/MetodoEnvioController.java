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

import jakarta.validation.Valid;

@RestController("MetodoEnvioControllerV1")
@RequestMapping("/api/v1/metodo-envio")
public class MetodoEnvioController {

    @Autowired
    private MetodoEnvioService metodoEnvioService;

    @Autowired
    private MetodoEnvioAssembler assembler;

    @GetMapping(value = "/{idMetodoEnvio}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MetodoEnvioDTO>> obtenerMetodoEnvioPorId(@PathVariable Integer idMetodoEnvio) {
        try {
            MetodoEnvioDTO metodoEnvioDTO = metodoEnvioService.obtenerMetodoEnvioPorId(idMetodoEnvio);
            return ResponseEntity.ok(assembler.toModel(metodoEnvioDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

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
