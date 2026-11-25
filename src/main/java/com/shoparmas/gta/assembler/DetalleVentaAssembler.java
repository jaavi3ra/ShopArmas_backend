package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.DetalleVentaDTO;
import com.shoparmas.gta.controller.DetalleVentaController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DetalleVentaAssembler implements RepresentationModelAssembler<DetalleVentaDTO, EntityModel<DetalleVentaDTO>> {

    @Override
    public EntityModel<DetalleVentaDTO> toModel(DetalleVentaDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(DetalleVentaController.class).obtenerDetalleVentaPorId(entity.getIdDetalleVenta())).withSelfRel(),
                linkTo(methodOn(DetalleVentaController.class).editarDetalleVenta(entity.getIdDetalleVenta(), entity)).withRel("editar-detalle"),
                linkTo(methodOn(DetalleVentaController.class).eliminarDetalleVenta(entity.getIdDetalleVenta())).withRel("eliminar")
        );
    }
}
