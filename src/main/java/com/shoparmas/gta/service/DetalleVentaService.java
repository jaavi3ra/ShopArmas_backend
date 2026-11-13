package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.DetalleVentaDTO;
import com.shoparmas.gta.model.DetalleVenta;
import org.springframework.stereotype.Service;

@Service
public class DetalleVentaService {

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
}
