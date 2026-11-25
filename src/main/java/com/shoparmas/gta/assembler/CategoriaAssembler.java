package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.CategoriaDTO;
import com.shoparmas.gta.controller.CategoriaController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CategoriaAssembler implements RepresentationModelAssembler<CategoriaDTO, EntityModel<CategoriaDTO>> {

    @Override
    public EntityModel<CategoriaDTO> toModel(CategoriaDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(CategoriaController.class).obtenerCategoriaPorId(entity.getIdCategoria())).withSelfRel(),
                linkTo(methodOn(CategoriaController.class).editarCategoria(entity.getIdCategoria(), entity)).withRel("editar-categoria"),
                linkTo(methodOn(CategoriaController.class).eliminarCategoria(entity.getIdCategoria())).withRel("eliminar")
        );
    }
}
