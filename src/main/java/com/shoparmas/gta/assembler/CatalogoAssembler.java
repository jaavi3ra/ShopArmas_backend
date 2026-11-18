package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.ProductoDTO;
import com.shoparmas.gta.controller.CatalogoController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CatalogoAssembler implements RepresentationModelAssembler<ProductoDTO, EntityModel<ProductoDTO>> {

    @Override
    public EntityModel<ProductoDTO> toModel(ProductoDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(CatalogoController.class).obtenerProductoCatalogo(entity.getIdProducto())).withSelfRel(),
                linkTo(methodOn(CatalogoController.class).editarProductoCatalogo(entity.getIdProducto(), entity)).withRel("editar-catalogo")
        );
    }
}
