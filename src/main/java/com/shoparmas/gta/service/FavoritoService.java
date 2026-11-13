package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.FavoritoDTO;
import com.shoparmas.gta.model.Favorito;
import com.shoparmas.gta.model.FavoritoID;
import com.shoparmas.gta.repository.FavoritoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FavoritoService {
    @Autowired
    private FavoritoRepository favoritoRepository;

    private Favorito buscarFavorito(Integer idUsuario, Integer idProducto) {
        log.info("Buscando favorito con ID de usuario: {} y ID de producto: {}", idUsuario, idProducto);
        return favoritoRepository.findByIdUsuarioAndIdProducto(idUsuario, idProducto)
                .orElseThrow(() -> new RuntimeException("Favorito no encontrado con ID de usuario: " + idUsuario + " y ID de producto: " + idProducto));
    }

    private FavoritoDTO convertirADTO(Favorito favorito) {
        FavoritoDTO dto = new FavoritoDTO();

        dto.setIdUsuario(favorito.getId().getIdUsuario());
        dto.setIdProducto(favorito.getId().getIdProducto());
        dto.setFechaAgregado(favorito.getFechaAgregado());

        return dto;
    }


    public FavoritoDTO obtenerFavoritoPorId(Integer idUsuario, Integer idProducto) {
        Favorito favorito = buscarFavorito(idUsuario, idProducto);
        return convertirADTO(favorito);
    }

    public FavoritoDTO crearFavorito(FavoritoDTO favoritoDTO) {
        try {
            log.info("Creando favorito para el usuario con ID: {} y producto con ID: {}", favoritoDTO.getIdUsuario(), favoritoDTO.getIdProducto());
            Favorito favorito = new Favorito();
            favorito.setId(new FavoritoID(favoritoDTO.getIdUsuario(), favoritoDTO.getIdProducto()));
            favorito.setFechaAgregado(favoritoDTO.getFechaAgregado());

            Favorito guardado = favoritoRepository.save(favorito);
            return convertirADTO(guardado);
        } catch (RuntimeException e) {
            log.error("Error al crear el favorito para el usuario con ID: {} y producto con ID: {}", favoritoDTO.getIdUsuario(), favoritoDTO.getIdProducto(), e);
            throw e;
        }
    }

    public void eliminarFavorito(Integer idUsuario, Integer idProducto) {
        log.info("Eliminando favorito con ID de usuario: {} y ID de producto: {}", idUsuario, idProducto);
        Favorito favorito = buscarFavorito(idUsuario, idProducto);
        favoritoRepository.delete(favorito);
    }
}