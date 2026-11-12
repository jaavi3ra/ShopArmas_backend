package com.shoparmas.gta.repository;

import org.springframework.stereotype.Repository;
import com.shoparmas.gta.model.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Integer>    {
    
}
