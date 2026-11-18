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

import com.shoparmas.gta.DTO.UsuarioDTO;
import com.shoparmas.gta.assembler.RegistroAssembler;
import com.shoparmas.gta.service.UsuarioService;

import jakarta.validation.Valid;

@RestController("RegistroControllerV2")
@RequestMapping("/api/v1/registro")
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RegistroAssembler assembler;

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> registrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(usuarioDTO);
            return ResponseEntity.ok(assembler.toModel(nuevoUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> obtenerRegistroPorId(@PathVariable Integer idUsuario) {
        try {
            UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(idUsuario);
            return ResponseEntity.ok(assembler.toModel(usuarioDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/editar-registro/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> editarRegistro(@PathVariable Integer idUsuario,
            @Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO usuarioActualizado = usuarioService.actualizarUsuario(idUsuario, usuarioDTO);
            return ResponseEntity.ok(assembler.toModel(usuarioActualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
