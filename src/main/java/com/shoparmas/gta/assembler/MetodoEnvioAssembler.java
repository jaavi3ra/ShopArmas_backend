package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.MetodoEnvioDTO;
import com.shoparmas.gta.controller.MetodoEnvioController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MetodoEnvioAssembler implements RepresentationModelAssembler<MetodoEnvioDTO, EntityModel<MetodoEnvioDTO>> {

    @Override
    public EntityModel<MetodoEnvioDTO> toModel(MetodoEnvioDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(MetodoEnvioController.class).obtenerMetodoEnvioPorId(entity.getIdMetodoEnvio())).withSelfRel(),
                linkTo(methodOn(MetodoEnvioController.class).editarMetodoEnvio(entity.getIdMetodoEnvio(), entity)).withRel("editar-metodo"),
                linkTo(methodOn(MetodoEnvioController.class).eliminarMetodoEnvio(entity.getIdMetodoEnvio())).withRel("eliminar")
        );
    }
}
