package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.VentaDTO;
import com.shoparmas.gta.controller.VentaController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class VentaAssembler implements RepresentationModelAssembler<VentaDTO, EntityModel<VentaDTO>> {

    @Override
    public EntityModel<VentaDTO> toModel(VentaDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(VentaController.class).obtenerVentaPorId(entity.getIdVenta())).withSelfRel(),
                linkTo(methodOn(VentaController.class).editarVenta(entity.getIdVenta(), entity)).withRel("editar-venta"),
                linkTo(methodOn(VentaController.class).eliminarVenta(entity.getIdVenta())).withRel("eliminar")
        );
    }
}
