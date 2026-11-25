package com.shoparmas.gta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoparmas.gta.DTO.UsuarioDTO;
import com.shoparmas.gta.assembler.LoginAssembler;
import com.shoparmas.gta.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Login", description = "Autenticación de usuarios")
@RestController("LoginControllerV1")
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LoginAssembler assembler;

    @Operation(summary = "Iniciar sesión de usuario")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> login(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO resultado = (usuarioDTO.getIdUsuario() != null)
                    ? usuarioService.obtenerUsuarioPorId(usuarioDTO.getIdUsuario())
                    : usuarioDTO;
            return ResponseEntity.ok(assembler.toModel(resultado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Obtener usuario de login por ID")
    @GetMapping(value = "/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> obtenerUsuarioLogin(@PathVariable Integer idUsuario) {
        try {
            UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(idUsuario);
            return ResponseEntity.ok(assembler.toModel(usuarioDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
