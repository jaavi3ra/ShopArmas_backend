package com.shoparmas.gta.repository;

import org.springframework.stereotype.Repository;
import com.shoparmas.gta.model.ImagenProducto;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, Integer> {
    
}
