package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.EstadoVentaDTO;
import com.shoparmas.gta.controller.EstadoVentaController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EstadoVentaAssembler implements RepresentationModelAssembler<EstadoVentaDTO, EntityModel<EstadoVentaDTO>> {

    @Override
    public EntityModel<EstadoVentaDTO> toModel(EstadoVentaDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(EstadoVentaController.class).obtenerEstadoVentaPorId(entity.getIdEstadoVenta())).withSelfRel(),
                linkTo(methodOn(EstadoVentaController.class).editarEstado(entity.getIdEstadoVenta(), entity)).withRel("editar-estado"),
                linkTo(methodOn(EstadoVentaController.class).eliminarEstadoVenta(entity.getIdEstadoVenta())).withRel("eliminar")
        );
    }
}
