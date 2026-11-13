package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.EstadoVentaDTO;
import com.shoparmas.gta.model.EstadoVenta;
import com.shoparmas.gta.repository.EstadoVentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadoVentaService {
    @Autowired 
    private EstadoVentaRepository estadoVentaRepository;

    private EstadoVenta buscarEstadoVenta(Integer idEstadoVenta) {
        log.info("Buscando estado de venta con ID: {}", idEstadoVenta);
        return estadoVentaRepository.findById(idEstadoVenta)
                .orElseThrow(() -> new RuntimeException("Estado de venta no encontrado con ID: " + idEstadoVenta));
    }

    private EstadoVentaDTO convertirADTO(EstadoVenta estadoVenta) {
        EstadoVentaDTO dto = new EstadoVentaDTO();

        dto.setIdEstadoVenta(estadoVenta.getIdEstadoVenta());
        dto.setNombreEstado(estadoVenta.getNombreEstado());
        dto.setDescripcion(estadoVenta.getDescripcion());

        return dto;
    }

    public EstadoVentaDTO obtenerEstadoVentaPorId(Integer idEstadoVenta) {
        EstadoVenta estadoVenta = buscarEstadoVenta(idEstadoVenta);
        return convertirADTO(estadoVenta);
    }

    public EstadoVentaDTO crearEstadoVenta(EstadoVentaDTO estadoVentaDTO) {
        try {
            log.info("Creando estado de venta con nombre: {}", estadoVentaDTO.getNombreEstado());
            EstadoVenta estadoVenta = new EstadoVenta();
            estadoVenta.setNombreEstado(estadoVentaDTO.getNombreEstado());
            estadoVenta.setDescripcion(estadoVentaDTO.getDescripcion());
            EstadoVenta guardada = estadoVentaRepository.save(estadoVenta);
            return convertirADTO(guardada);
        } catch (RuntimeException e) {
            log.error("Error al crear el estado de venta con nombre: {}", estadoVentaDTO.getNombreEstado(), e);
            throw e;
        }
    }

    public EstadoVentaDTO actualizarEstadoVenta(Integer idEstadoVenta, EstadoVentaDTO estadoVentaDTO) {
        log.info("Actualizando estado de venta con ID: {}", idEstadoVenta);
        EstadoVenta estadoVenta = buscarEstadoVenta(idEstadoVenta);
        estadoVenta.setNombreEstado(estadoVentaDTO.getNombreEstado());
        estadoVenta.setDescripcion(estadoVentaDTO.getDescripcion());
        EstadoVenta guardada = estadoVentaRepository.save(estadoVenta);
        return convertirADTO(guardada);
    }

    public void eliminarEstadoVenta(Integer idEstadoVenta) {
        log.info("Eliminando estado de venta con ID: {}", idEstadoVenta);
        EstadoVenta estadoVenta = buscarEstadoVenta(idEstadoVenta);
        estadoVentaRepository.delete(estadoVenta);
    }
}
