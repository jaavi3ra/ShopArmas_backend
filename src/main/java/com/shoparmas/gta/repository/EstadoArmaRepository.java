package com.shoparmas.gta.repository;

import org.springframework.stereotype.Repository;
import com.shoparmas.gta.model.EstadoArma;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EstadoArmaRepository extends JpaRepository<EstadoArma, Integer> {
    
}
