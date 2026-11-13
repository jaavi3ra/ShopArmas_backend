package com.shoparmas.gta.service;

import com.shoparmas.gta.DTO.VentaDTO;
import com.shoparmas.gta.model.Venta;
import com.shoparmas.gta.repository.VentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VentaService {
    @Autowired 
    private VentaRepository ventaRepository;
    @Autowired 
    private UsuarioService usuarioService;
    @Autowired 
    private DireccionService direccionService;
    @Autowired 
    private EstadoVentaService estadoVentaService;
    @Autowired 
    private MetodoEnvioService metodoEnvioService;

    private Venta buscarVenta(Integer idVenta) {
        log.info("Buscando venta con ID: {}", idVenta);
        return ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + idVenta));
    }

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

    public VentaDTO obtenerVentaPorId(Integer idVenta) {
        Venta venta = buscarVenta(idVenta);
        return convertirADTO(venta);
    }

    public VentaDTO crearVenta(VentaDTO ventaDTO) {
        try {
            log.info("Creando venta para el usuario con ID: {}", ventaDTO.getIdUsuario());
            Venta venta = new Venta();

            venta.setUsuario(usuarioService.buscarUsuario(ventaDTO.getIdUsuario()));
            venta.setDireccion(direccionService.buscarDireccion(ventaDTO.getIdDireccion()));
            venta.setEstadoVenta(estadoVentaService.buscarEstadoVenta(ventaDTO.getIdEstadoVenta()));
            venta.setMetodoEnvio(metodoEnvioService.buscarMetodoEnvio(ventaDTO.getIdMetodoEnvio()));
            venta.setFechaVenta(ventaDTO.getFechaVenta());
            venta.setSubtotal(ventaDTO.getSubtotal());
            venta.setCostoEnvio(ventaDTO.getCostoEnvio());
            venta.setTotal(ventaDTO.getTotal());

            Venta guardada = ventaRepository.save(venta);
            log.info("Venta creada con ID: {}", guardada.getIdVenta());
            return convertirADTO(guardada);
        } catch (RuntimeException e) {
            log.error("Error al crear la venta: {}", ventaDTO.getIdUsuario(), e);
            throw e;
        }
    }

    public VentaDTO actualizarVenta(Integer idVenta, VentaDTO ventaDTO) {
        log.info("Actualizando venta con ID: {}", idVenta);
        Venta venta = buscarVenta(idVenta);

        venta.setUsuario(usuarioService.buscarUsuario(ventaDTO.getIdUsuario()));
        venta.setDireccion(direccionService.buscarDireccion(ventaDTO.getIdDireccion()));
        venta.setEstadoVenta(estadoVentaService.buscarEstadoVenta(ventaDTO.getIdEstadoVenta()));
        venta.setMetodoEnvio(metodoEnvioService.buscarMetodoEnvio(ventaDTO.getIdMetodoEnvio()));
        venta.setFechaVenta(ventaDTO.getFechaVenta());
        venta.setSubtotal(ventaDTO.getSubtotal());
        venta.setCostoEnvio(ventaDTO.getCostoEnvio());
        venta.setTotal(ventaDTO.getTotal());

        Venta actualizada = ventaRepository.save(venta);
        log.info("Venta actualizada con ID: {}", actualizada.getIdVenta());
        return convertirADTO(actualizada);
    }

    public void eliminarVenta(Integer idVenta) {
        log.info("Eliminando venta con ID: {}", idVenta);
        Venta venta = buscarVenta(idVenta);
        ventaRepository.delete(venta);
        log.info("Venta eliminada con ID: {}", idVenta);
    }
}
