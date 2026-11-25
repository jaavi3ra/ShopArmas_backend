package com.shoparmas.gta.repository;

import org.springframework.stereotype.Repository;
import com.shoparmas.gta.model.Favorito;
import com.shoparmas.gta.model.FavoritoID;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, FavoritoID> {
    default Optional<Favorito> findByIdUsuarioAndIdProducto(Integer idUsuario, Integer idProducto) {
        return findById(new FavoritoID(idUsuario, idProducto));
    }
}
