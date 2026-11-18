package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.RolDTO;
import com.shoparmas.gta.controller.RolController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RolAssembler implements RepresentationModelAssembler<RolDTO, EntityModel<RolDTO>> {

    @Override
    public EntityModel<RolDTO> toModel(RolDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(RolController.class).obtenerRolPorId(entity.getIdRol())).withSelfRel(),
                linkTo(methodOn(RolController.class).actualizarRol(entity.getIdRol(), entity)).withRel("editar-rol"),
                linkTo(methodOn(RolController.class).eliminarRol(entity.getIdRol())).withRel("eliminar")
        );
    }
}
