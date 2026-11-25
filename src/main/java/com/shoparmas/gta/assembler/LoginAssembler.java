package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.UsuarioDTO;
import com.shoparmas.gta.controller.LoginController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class LoginAssembler implements RepresentationModelAssembler<UsuarioDTO, EntityModel<UsuarioDTO>> {

    @Override
    public EntityModel<UsuarioDTO> toModel(UsuarioDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(LoginController.class).obtenerUsuarioLogin(entity.getIdUsuario())).withSelfRel(),
                linkTo(methodOn(LoginController.class).login(entity)).withRel("login")
        );
    }
}
