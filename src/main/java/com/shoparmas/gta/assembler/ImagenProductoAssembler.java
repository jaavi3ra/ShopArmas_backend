package com.shoparmas.gta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.shoparmas.gta.DTO.ImagenProductoDTO;
import com.shoparmas.gta.controller.ImagenProductoController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ImagenProductoAssembler implements RepresentationModelAssembler<ImagenProductoDTO, EntityModel<ImagenProductoDTO>> {

    @Override
    public EntityModel<ImagenProductoDTO> toModel(ImagenProductoDTO entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ImagenProductoController.class).obtenerImagenProductoPorId(entity.getIdImagen())).withSelfRel(),
                linkTo(methodOn(ImagenProductoController.class).editarImagenProducto(entity.getIdImagen(), entity)).withRel("editar-imagen"),
                linkTo(methodOn(ImagenProductoController.class).eliminarImagenProducto(entity.getIdImagen())).withRel("eliminar")
        );
    }
}
