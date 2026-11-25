package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.CarteraDTO;
import com.shoparmas.gta.controller.CarteraController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CarteraAssembler implements RepresentationModelAssembler<CarteraDTO, EntityModel<CarteraDTO>> {

    @Override
    public EntityModel<CarteraDTO> toModel(CarteraDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(CarteraController.class).obtenerCarteraPorId(entity.getIdUsuario())).withSelfRel(),
                linkTo(methodOn(CarteraController.class).listarCarteras()).withRel("carteras"),
                linkTo(methodOn(CarteraController.class).actualizarSaldo(entity.getIdUsuario(), entity)).withRel("editar-saldo"),
                linkTo(methodOn(CarteraController.class).eliminarCartera(entity.getIdUsuario())).withRel("eliminar")
        );
    }
}
