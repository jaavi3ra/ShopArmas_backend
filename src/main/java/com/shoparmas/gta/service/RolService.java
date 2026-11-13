package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.RolDTO;
import com.shoparmas.gta.model.Rol;
import com.shoparmas.gta.repository.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RolService {
    @Autowired 
    private RolRepository rolRepository;

    private Rol buscarRol(Integer idRol) {
        log.info("Buscando rol con ID: {}", idRol);
        return rolRepository.findById(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + idRol));
    }

    private RolDTO convertirADTO(Rol rol) {
        RolDTO dto = new RolDTO();

        dto.setIdRol(rol.getIdRol());
        dto.setNombreRol(rol.getNombreRol());
        dto.setDescripcion(rol.getDescripcion());

        return dto;
    }

    public RolDTO obtenerRolPorId(Integer idRol) {
        Rol rol = buscarRol(idRol);
        return convertirADTO(rol);
    }

    public RolDTO crearRol(RolDTO rolDTO) {
        try {
            log.info("Creando rol: {}", rolDTO.getNombreRol());
            Rol rol = new Rol();
            rol.setNombreRol(rolDTO.getNombreRol());
            rol.setDescripcion(rolDTO.getDescripcion());

            Rol guardado = rolRepository.save(rol);
            return convertirADTO(guardado);
        } catch (RuntimeException e) {
            log.error("Error al crear el rol: {}", rolDTO.getNombreRol(), e);
            throw e;
        }
    }

    public RolDTO actualizarRol(Integer idRol, RolDTO rolDTO) {
        log.info("Actualizando rol con ID: {}", idRol);
        Rol rol = buscarRol(idRol);
        rol.setNombreRol(rolDTO.getNombreRol());
        rol.setDescripcion(rolDTO.getDescripcion());

        Rol actualizado = rolRepository.save(rol);
        return convertirADTO(actualizado);
    }

    public void eliminarRol(Integer idRol) {
        log.info("Eliminando rol con ID: {}", idRol);
        Rol rol = buscarRol(idRol);
        rolRepository.delete(rol);
    }
}
