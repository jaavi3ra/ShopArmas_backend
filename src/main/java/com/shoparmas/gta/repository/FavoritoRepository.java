package com.shoparmas.gta.repository;

import org.springframework.stereotype.Repository;
import com.shoparmas.gta.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {
    
}
