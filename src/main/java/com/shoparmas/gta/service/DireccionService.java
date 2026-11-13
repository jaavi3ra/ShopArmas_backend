package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.DireccionDTO;
import com.shoparmas.gta.model.Direccion;
import com.shoparmas.gta.repository.DireccionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DireccionService {
    @Autowired 
    private DireccionRepository direccionRepository;

    private Direccion buscarDireccion(Integer idDireccion) {
        log.info("Buscando dirección con ID: {}", idDireccion);
        return direccionRepository.findById(idDireccion)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada con ID: " + idDireccion));
    }

    private DireccionDTO convertirADTO(Direccion direccion) {
        DireccionDTO dto = new DireccionDTO();

        dto.setIdDireccion(direccion.getIdDireccion());
        dto.setIdUsuario(direccion.getUsuario().getIdUsuario());
        dto.setIdComuna(direccion.getComuna().getIdComuna());
        dto.setCalle(direccion.getCalle());
        dto.setNumero(direccion.getNumero());
        dto.setDepartamento(direccion.getDepartamento());
        dto.setCodigoPostal(direccion.getCodigoPostal());
        dto.setReferencia(direccion.getReferencia());

        return dto;
    }

    public DireccionDTO obtenerDireccionPorId(Integer idDireccion) {
        Direccion direccion = buscarDireccion(idDireccion);
        return convertirADTO(direccion);
    }
    public DireccionDTO crearDireccion(DireccionDTO direccionDTO) {
        try {
            log.info("Creando dirección para el usuario con ID: {}", direccionDTO.getIdUsuario());
            Direccion direccion = new Direccion();
            direccion.setCalle(direccionDTO.getCalle());
            direccion.setNumero(direccionDTO.getNumero());
            direccion.setDepartamento(direccionDTO.getDepartamento());
            direccion.setCodigoPostal(direccionDTO.getCodigoPostal());
            direccion.setReferencia(direccionDTO.getReferencia());

            Direccion guardada = direccionRepository.save(direccion);
            return convertirADTO(guardada);
        } catch (RuntimeException e) {
            log.error("Error al crear la dirección para el usuario con ID: {}", direccionDTO.getIdUsuario(), e);
            throw e;
        }
    }
    public DireccionDTO actualizarDireccion(Integer idDireccion, DireccionDTO direccionDTO) {
        log.info("Actualizando dirección con ID: {}", idDireccion);
        Direccion direccion = buscarDireccion(idDireccion);
        direccion.setCalle(direccionDTO.getCalle());
        direccion.setNumero(direccionDTO.getNumero());
        direccion.setDepartamento(direccionDTO.getDepartamento());
        direccion.setCodigoPostal(direccionDTO.getCodigoPostal());
        direccion.setReferencia(direccionDTO.getReferencia());

        Direccion guardada = direccionRepository.save(direccion);
        return convertirADTO(guardada);
    }
    public void eliminarDireccion(Integer idDireccion) {
        log.info("Eliminando dirección con ID: {}", idDireccion);
        Direccion direccion = buscarDireccion(idDireccion);
        direccionRepository.delete(direccion);
    }
}
