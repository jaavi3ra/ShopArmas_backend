package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.FavoritoDTO;
import com.shoparmas.gta.controller.FavoritoController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class FavoritoAssembler implements RepresentationModelAssembler<FavoritoDTO, EntityModel<FavoritoDTO>> {

    @Override
    public EntityModel<FavoritoDTO> toModel(FavoritoDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(FavoritoController.class).obtenerFavorito(entity.getIdUsuario(), entity.getIdProducto())).withSelfRel(),
                linkTo(methodOn(FavoritoController.class).eliminarFavorito(entity.getIdUsuario(), entity.getIdProducto())).withRel("eliminar")
        );
    }
}
