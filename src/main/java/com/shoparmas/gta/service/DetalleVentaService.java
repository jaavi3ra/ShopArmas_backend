package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.DetalleVentaDTO;
import com.shoparmas.gta.model.DetalleVenta;

import org.springframework.beans.factory.annotation.Autowired;
import com.shoparmas.gta.repository.DetalleVentaRepository;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    private DetalleVenta buscarDetalleVenta(Integer idDetalleVenta) {
        log.info("Buscando detalle de venta con ID: {}", idDetalleVenta);
        return detalleVentaRepository.findById(idDetalleVenta)
                .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado con ID: " + idDetalleVenta));
    }
    private DetalleVentaDTO convertirADTO(DetalleVenta detalleVenta) {
        DetalleVentaDTO dto = new DetalleVentaDTO();

        dto.setIdDetalleVenta(detalleVenta.getIdDetalleVenta());
        dto.setIdVenta(detalleVenta.getVenta().getIdVenta());
        dto.setIdProducto(detalleVenta.getProducto().getIdProducto());
        dto.setCantidad(detalleVenta.getCantidad());
        dto.setPrecioUnitario(detalleVenta.getPrecioUnitario());
        dto.setSubtotal(detalleVenta.getSubtotal());

        return dto;
    }

    public DetalleVentaDTO obtenerDetalleVentaPorId(Integer idDetalleVenta) {
        DetalleVenta detalleVenta = buscarDetalleVenta(idDetalleVenta);
        return convertirADTO(detalleVenta);
    }

    public DetalleVentaDTO crearDetalleVenta(DetalleVentaDTO detalleVentaDTO) {
        try {
            log.info("Creando detalle de venta para la venta con ID: {}", detalleVentaDTO.getIdVenta());
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setCantidad(detalleVentaDTO.getCantidad());
            detalleVenta.setPrecioUnitario(detalleVentaDTO.getPrecioUnitario());
            detalleVenta.setSubtotal(detalleVentaDTO.getSubtotal());

            DetalleVenta guardado = detalleVentaRepository.save(detalleVenta);
            return convertirADTO(guardado);
        } catch (RuntimeException e) {
            log.error("Error al crear el detalle de venta para la venta con ID: {}", detalleVentaDTO.getIdVenta(), e);
            throw e;
        }
       
    }

    public DetalleVentaDTO actualizarDetalleVenta(Integer idDetalleVenta, DetalleVentaDTO detalleVentaDTO) {
        log.info("Actualizando detalle de venta con ID: {}", idDetalleVenta);
        DetalleVenta detalleVenta = buscarDetalleVenta(idDetalleVenta);
        detalleVenta.setCantidad(detalleVentaDTO.getCantidad());
        detalleVenta.setPrecioUnitario(detalleVentaDTO.getPrecioUnitario());
        detalleVenta.setSubtotal(detalleVentaDTO.getSubtotal());

        DetalleVenta guardado = detalleVentaRepository.save(detalleVenta);
        return convertirADTO(guardado);
    }
    public void eliminarDetalleVenta(Integer idDetalleVenta) {
        log.info("Eliminando detalle de venta con ID: {}", idDetalleVenta);
        DetalleVenta detalleVenta = buscarDetalleVenta(idDetalleVenta);
        detalleVentaRepository.delete(detalleVenta);
    }
}
