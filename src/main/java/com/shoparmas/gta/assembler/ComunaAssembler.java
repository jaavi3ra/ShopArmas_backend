package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.ComunaDTO;
import com.shoparmas.gta.controller.ComunaController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ComunaAssembler implements RepresentationModelAssembler<ComunaDTO, EntityModel<ComunaDTO>> {

    @Override
    public EntityModel<ComunaDTO> toModel(ComunaDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ComunaController.class).obtenerComunaPorId(entity.getIdComuna())).withSelfRel(),
                linkTo(methodOn(ComunaController.class).editarComuna(entity.getIdComuna(), entity)).withRel("editar-comuna"),
                linkTo(methodOn(ComunaController.class).eliminarComuna(entity.getIdComuna())).withRel("eliminar")
        );
    }
}
