package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.UsuarioDTO;
import com.shoparmas.gta.controller.UsuarioController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<UsuarioDTO, EntityModel<UsuarioDTO>> {

    @Override
    public EntityModel<UsuarioDTO> toModel(UsuarioDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(entity.getIdUsuario())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).editarUsuario(entity.getIdUsuario(), entity)).withRel("editar-usuario"),
                linkTo(methodOn(UsuarioController.class).eliminarUsuario(entity.getIdUsuario())).withRel("eliminar")
        );
    }
}
