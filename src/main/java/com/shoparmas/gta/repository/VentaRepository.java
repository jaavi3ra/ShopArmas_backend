package com.shoparmas.gta.repository;

import org.springframework.stereotype.Repository;
import com.shoparmas.gta.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    
}
