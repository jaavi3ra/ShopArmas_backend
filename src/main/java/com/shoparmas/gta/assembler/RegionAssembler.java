package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.RegionDTO;
import com.shoparmas.gta.controller.RegionController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RegionAssembler implements RepresentationModelAssembler<RegionDTO, EntityModel<RegionDTO>> {

    @Override
    public EntityModel<RegionDTO> toModel(RegionDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(RegionController.class).obtenerRegionPorId(entity.getIdRegion())).withSelfRel(),
                linkTo(methodOn(RegionController.class).editarRegion(entity.getIdRegion(), entity)).withRel("editar-region"),
                linkTo(methodOn(RegionController.class).eliminarRegion(entity.getIdRegion())).withRel("eliminar")
        );
    }
}
