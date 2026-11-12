package com.shoparmas.gta.repository;

import org.springframework.stereotype.Repository;
import com.shoparmas.gta.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RolRepository  extends JpaRepository<Rol, Integer>     {
    
}
