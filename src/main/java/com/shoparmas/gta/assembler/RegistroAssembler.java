package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.UsuarioDTO;
import com.shoparmas.gta.controller.RegistroController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RegistroAssembler implements RepresentationModelAssembler<UsuarioDTO, EntityModel<UsuarioDTO>> {

    @Override
    public EntityModel<UsuarioDTO> toModel(UsuarioDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(RegistroController.class).obtenerRegistroPorId(entity.getIdUsuario())).withSelfRel(),
                linkTo(methodOn(RegistroController.class).registrarUsuario(null)).withRel("registro"),
                linkTo(methodOn(RegistroController.class).editarRegistro(entity.getIdUsuario(), entity)).withRel("editar-registro")
        );
    }
}
