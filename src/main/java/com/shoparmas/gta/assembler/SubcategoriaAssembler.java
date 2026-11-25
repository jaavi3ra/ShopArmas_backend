package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.SubcategoriaDTO;
import com.shoparmas.gta.controller.SubcategoriaController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SubcategoriaAssembler implements RepresentationModelAssembler<SubcategoriaDTO, EntityModel<SubcategoriaDTO>> {

    @Override
    public EntityModel<SubcategoriaDTO> toModel(SubcategoriaDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(SubcategoriaController.class).obtenerSubcategoriaPorId(entity.getIdSubcategoria())).withSelfRel(),
                linkTo(methodOn(SubcategoriaController.class).editarSubcategoria(entity.getIdSubcategoria(), entity)).withRel("editar-subcategoria"),
                linkTo(methodOn(SubcategoriaController.class).eliminarSubcategoria(entity.getIdSubcategoria())).withRel("eliminar")
        );
    }
}
