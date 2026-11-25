package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.DireccionDTO;
import com.shoparmas.gta.controller.DireccionController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DireccionAssembler implements RepresentationModelAssembler<DireccionDTO, EntityModel<DireccionDTO>> {

    @Override
    public EntityModel<DireccionDTO> toModel(DireccionDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(DireccionController.class).obtenerDireccionPorId(entity.getIdDireccion())).withSelfRel(),
                linkTo(methodOn(DireccionController.class).editarDireccion(entity.getIdDireccion(), entity)).withRel("editar-direccion"),
                linkTo(methodOn(DireccionController.class).eliminarDireccion(entity.getIdDireccion())).withRel("eliminar")
        );
    }
}
