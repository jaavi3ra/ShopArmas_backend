package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.VentaDTO;
import com.shoparmas.gta.model.Venta;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    private VentaDTO convertirADTO(Venta venta) {
        VentaDTO dto = new VentaDTO();

        dto.setIdVenta(venta.getIdVenta());
        dto.setIdUsuario(venta.getUsuario().getIdUsuario());
        dto.setIdDireccion(venta.getDireccion() == null ? null : venta.getDireccion().getIdDireccion());
        dto.setIdEstadoVenta(venta.getEstadoVenta().getIdEstadoVenta());
        dto.setIdMetodoEnvio(venta.getMetodoEnvio().getIdMetodoEnvio());
        dto.setFechaVenta(venta.getFechaVenta());
        dto.setSubtotal(venta.getSubtotal());
        dto.setCostoEnvio(venta.getCostoEnvio());
        dto.setTotal(venta.getTotal());

        return dto;
    }
}
