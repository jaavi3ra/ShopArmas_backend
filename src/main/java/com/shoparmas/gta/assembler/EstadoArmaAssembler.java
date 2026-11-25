package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.EstadoArmaDTO;
import com.shoparmas.gta.controller.EstadoArmaController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EstadoArmaAssembler implements RepresentationModelAssembler<EstadoArmaDTO, EntityModel<EstadoArmaDTO>> {

    @Override
    public EntityModel<EstadoArmaDTO> toModel(EstadoArmaDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(EstadoArmaController.class).obtenerEstadoArmaPorId(entity.getIdEstadoArma())).withSelfRel(),
                linkTo(methodOn(EstadoArmaController.class).editarEstadoArma(entity.getIdEstadoArma(), entity)).withRel("editar-estado"),
                linkTo(methodOn(EstadoArmaController.class).eliminarEstadoArma(entity.getIdEstadoArma())).withRel("eliminar")
        );
    }
}
