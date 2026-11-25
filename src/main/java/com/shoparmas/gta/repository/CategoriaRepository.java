package com.shoparmas.gta.repository;

import org.springframework.stereotype.Repository;
import com.shoparmas.gta.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository 
public interface CategoriaRepository  extends JpaRepository<Categoria, Integer> {
    
}
