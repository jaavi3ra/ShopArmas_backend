package com.shoparmas.gta.repository;

import org.springframework.stereotype.Repository;
import com.shoparmas.gta.model.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {
    
}
