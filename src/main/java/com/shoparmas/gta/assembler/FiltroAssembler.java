package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.ProductoDTO;
import com.shoparmas.gta.controller.FiltroController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class FiltroAssembler implements RepresentationModelAssembler<ProductoDTO, EntityModel<ProductoDTO>> {

    @Override
    public EntityModel<ProductoDTO> toModel(ProductoDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(FiltroController.class).filtrarPorProducto(entity.getIdProducto())).withSelfRel(),
                linkTo(methodOn(FiltroController.class).editarFiltroProducto(entity.getIdProducto(), entity)).withRel("editar-filtro")
        );
    }
}
