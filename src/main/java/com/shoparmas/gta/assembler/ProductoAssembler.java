package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.ProductoDTO;
import com.shoparmas.gta.controller.ProductoController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoAssembler implements RepresentationModelAssembler<ProductoDTO, EntityModel<ProductoDTO>> {

    @Override
    public EntityModel<ProductoDTO> toModel(ProductoDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ProductoController.class).obtenerProductoPorId(entity.getIdProducto())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).editarProducto(entity.getIdProducto(), entity)).withRel("editar-producto"),
                linkTo(methodOn(ProductoController.class).eliminarProducto(entity.getIdProducto())).withRel("eliminar")
        );
    }
}
