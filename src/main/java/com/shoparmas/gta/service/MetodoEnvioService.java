package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.MetodoEnvioDTO;
import com.shoparmas.gta.model.MetodoEnvio;
import com.shoparmas.gta.repository.MetodoEnvioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MetodoEnvioService {
    @Autowired 
    private MetodoEnvioRepository metodoEnvioRepository;

    public MetodoEnvio buscarMetodoEnvio(Integer idMetodoEnvio) {
        log.info("Buscando método de envío con ID: {}", idMetodoEnvio);
        return metodoEnvioRepository.findById(idMetodoEnvio)
                .orElseThrow(() -> new RuntimeException("Método de envío no encontrado con ID: " + idMetodoEnvio));
    }

    private MetodoEnvioDTO convertirADTO(MetodoEnvio metodoEnvio) {
        MetodoEnvioDTO dto = new MetodoEnvioDTO();

        dto.setIdMetodoEnvio(metodoEnvio.getIdMetodoEnvio());
        dto.setNombreMetodo(metodoEnvio.getNombreMetodo());
        dto.setDescripcion(metodoEnvio.getDescripcion());
        dto.setCosto(metodoEnvio.getCosto());
        dto.setActivo(metodoEnvio.getActivo());

        return dto;
    }

    public MetodoEnvioDTO obtenerMetodoEnvioPorId(Integer idMetodoEnvio) {
        MetodoEnvio metodoEnvio = buscarMetodoEnvio(idMetodoEnvio);
        return convertirADTO(metodoEnvio);
    }

    public MetodoEnvioDTO crearMetodoEnvio(MetodoEnvioDTO metodoEnvioDTO) {
        try {
            log.info("Creando método de envío: {}", metodoEnvioDTO.getNombreMetodo());
            MetodoEnvio metodoEnvio = new MetodoEnvio();
            metodoEnvio.setNombreMetodo(metodoEnvioDTO.getNombreMetodo());
            metodoEnvio.setDescripcion(metodoEnvioDTO.getDescripcion());
            metodoEnvio.setCosto(metodoEnvioDTO.getCosto());
            metodoEnvio.setActivo(metodoEnvioDTO.getActivo());

            MetodoEnvio guardado = metodoEnvioRepository.save(metodoEnvio);
            return convertirADTO(guardado);
        } catch (RuntimeException e) {
            log.error("Error al crear el método de envío: {}", metodoEnvioDTO.getNombreMetodo(), e);
            throw e;
        }
    }

    public MetodoEnvioDTO actualizarMetodoEnvio(Integer idMetodoEnvio, MetodoEnvioDTO metodoEnvioDTO) {
        log.info("Actualizando método de envío con ID: {}", idMetodoEnvio);
        MetodoEnvio metodoEnvio = buscarMetodoEnvio(idMetodoEnvio);
        metodoEnvio.setNombreMetodo(metodoEnvioDTO.getNombreMetodo());
        metodoEnvio.setDescripcion(metodoEnvioDTO.getDescripcion());
        metodoEnvio.setCosto(metodoEnvioDTO.getCosto());
        metodoEnvio.setActivo(metodoEnvioDTO.getActivo());

        MetodoEnvio actualizado = metodoEnvioRepository.save(metodoEnvio);
        return convertirADTO(actualizado);
    }

    public void eliminarMetodoEnvio(Integer idMetodoEnvio) {
        log.info("Eliminando método de envío con ID: {}", idMetodoEnvio);
        MetodoEnvio metodoEnvio = buscarMetodoEnvio(idMetodoEnvio);
        metodoEnvioRepository.delete(metodoEnvio);
    }
}
