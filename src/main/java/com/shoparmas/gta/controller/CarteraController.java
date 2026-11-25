package com.shoparmas.gta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.shoparmas.gta.DTO.CarteraDTO;
import com.shoparmas.gta.assembler.CarteraAssembler;
import com.shoparmas.gta.service.CarteraService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController("CarteraControllerV1")
@RequestMapping("/api/v1/cartera")
public class CarteraController {

    @Autowired
    private CarteraService carteraService;

    @Autowired
    private CarteraAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<CarteraDTO>>> listarCarteras() {
        try {
            List<CarteraDTO> carteras = carteraService.listarCarteras();
            CollectionModel<EntityModel<CarteraDTO>> collectionModel = assembler.toCollectionModel(carteras)
                    .add(linkTo(methodOn(CarteraController.class).listarCarteras()).withSelfRel());
            return ResponseEntity.ok(collectionModel);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CarteraDTO>> obtenerCarteraPorId(@PathVariable Integer idUsuario) {
        try {
            CarteraDTO carteraDTO = carteraService.obtenerCarteraId(idUsuario);
            return ResponseEntity.ok(assembler.toModel(carteraDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CarteraDTO>> crearCartera(@PathVariable Integer idUsuario) {
        try {
            CarteraDTO creada = carteraService.crearCartera(idUsuario);
            return ResponseEntity.ok(assembler.toModel(creada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/editar-saldo/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CarteraDTO>> actualizarSaldo(@PathVariable Integer idUsuario,
            @Valid @RequestBody CarteraDTO carteraDTO) {
        try {
            CarteraDTO actualizada = carteraService.actualizarSaldo(idUsuario, carteraDTO.getSaldo());
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/eliminar/{idUsuario}")
    public ResponseEntity<Void> eliminarCartera(@PathVariable Integer idUsuario) {
        try {
            carteraService.eliminarCartera(idUsuario);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
