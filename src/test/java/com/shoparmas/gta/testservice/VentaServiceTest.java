package com.shoparmas.gta.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shoparmas.gta.DTO.VentaDTO;
import com.shoparmas.gta.model.Direccion;
import com.shoparmas.gta.model.EstadoVenta;
import com.shoparmas.gta.model.MetodoEnvio;
import com.shoparmas.gta.model.Usuario;
import com.shoparmas.gta.model.Venta;
import com.shoparmas.gta.repository.VentaRepository;
import com.shoparmas.gta.service.DireccionService;
import com.shoparmas.gta.service.EstadoVentaService;
import com.shoparmas.gta.service.MetodoEnvioService;
import com.shoparmas.gta.service.UsuarioService;
import com.shoparmas.gta.service.VentaService;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private DireccionService direccionService;

    @Mock
    private EstadoVentaService estadoVentaService;

    @Mock
    private MetodoEnvioService metodoEnvioService;

    @InjectMocks
    private VentaService service;

    @Test
    void obtenerVentaPorId_DeberiaMapearEntidad() {
        // GIVEN
        Venta entity = entidadExistente();
        when(ventaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        VentaDTO resultado = service.obtenerVentaPorId(7);

        // THEN
        assertEquals(7, resultado.getIdVenta());
        assertEquals(new BigDecimal("150.00"), resultado.getTotal());
        assertEquals(21, resultado.getIdUsuario());
        assertEquals(22, resultado.getIdDireccion());
        assertEquals(23, resultado.getIdEstadoVenta());
        assertEquals(24, resultado.getIdMetodoEnvio());
    }

    @Test
    void obtenerVentaPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(ventaRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerVentaPorId(7));

        // THEN
        assertEquals("Venta no encontrada con ID: 7", error.getMessage());
    }

    @Test
    void eliminarVenta_DeberiaEliminarEntidadExistente() {
        // GIVEN
        Venta entity = entidadExistente();
        when(ventaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarVenta(7);

        // THEN
        verify(ventaRepository).delete(entity);
    }

    @Test
    void crearVenta_DeberiaGuardarDatosDeLaSolicitud() {
        // GIVEN
        VentaDTO solicitud = new VentaDTO();
        solicitud.setTotal(new BigDecimal("250.00"));
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(31);
        solicitud.setIdUsuario(31);
        when(usuarioService.buscarUsuario(31)).thenReturn(usuario);
        Direccion direccion = new Direccion();
        direccion.setIdDireccion(32);
        solicitud.setIdDireccion(32);
        when(direccionService.buscarDireccion(32)).thenReturn(direccion);
        EstadoVenta estadoVenta = new EstadoVenta();
        estadoVenta.setIdEstadoVenta(33);
        solicitud.setIdEstadoVenta(33);
        when(estadoVentaService.buscarEstadoVenta(33)).thenReturn(estadoVenta);
        MetodoEnvio metodoEnvio = new MetodoEnvio();
        metodoEnvio.setIdMetodoEnvio(34);
        solicitud.setIdMetodoEnvio(34);
        when(metodoEnvioService.buscarMetodoEnvio(34)).thenReturn(metodoEnvio);
        when(ventaRepository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        VentaDTO resultado = service.crearVenta(solicitud);

        // THEN
        ArgumentCaptor<Venta> captor = ArgumentCaptor.forClass(Venta.class);
        verify(ventaRepository).save(captor.capture());
        assertEquals(new BigDecimal("250.00"), captor.getValue().getTotal());
        assertEquals(new BigDecimal("250.00"), resultado.getTotal());
        assertEquals(31, resultado.getIdUsuario());
        assertEquals(32, resultado.getIdDireccion());
        assertEquals(33, resultado.getIdEstadoVenta());
        assertEquals(34, resultado.getIdMetodoEnvio());
    }

    @Test
    void actualizarVenta_DeberiaGuardarCambios() {
        // GIVEN
        Venta entity = entidadExistente();
        when(ventaRepository.findById(7)).thenReturn(Optional.of(entity));
        VentaDTO solicitud = new VentaDTO();
        solicitud.setTotal(new BigDecimal("250.00"));
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(31);
        solicitud.setIdUsuario(31);
        when(usuarioService.buscarUsuario(31)).thenReturn(usuario);
        Direccion direccion = new Direccion();
        direccion.setIdDireccion(32);
        solicitud.setIdDireccion(32);
        when(direccionService.buscarDireccion(32)).thenReturn(direccion);
        EstadoVenta estadoVenta = new EstadoVenta();
        estadoVenta.setIdEstadoVenta(33);
        solicitud.setIdEstadoVenta(33);
        when(estadoVentaService.buscarEstadoVenta(33)).thenReturn(estadoVenta);
        MetodoEnvio metodoEnvio = new MetodoEnvio();
        metodoEnvio.setIdMetodoEnvio(34);
        solicitud.setIdMetodoEnvio(34);
        when(metodoEnvioService.buscarMetodoEnvio(34)).thenReturn(metodoEnvio);
        when(ventaRepository.save(entity)).thenReturn(entity);

        // WHEN
        VentaDTO resultado = service.actualizarVenta(7, solicitud);

        // THEN
        verify(ventaRepository).save(entity);
        assertEquals(7, resultado.getIdVenta());
        assertEquals(new BigDecimal("250.00"), resultado.getTotal());
        assertEquals(31, resultado.getIdUsuario());
        assertEquals(32, resultado.getIdDireccion());
        assertEquals(33, resultado.getIdEstadoVenta());
        assertEquals(34, resultado.getIdMetodoEnvio());
    }

    private Venta entidadExistente() {
        Venta entity = new Venta();
        entity.setIdVenta(7);
        entity.setTotal(new BigDecimal("150.00"));
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(21);
        entity.setUsuario(usuario);
        Direccion direccion = new Direccion();
        direccion.setIdDireccion(22);
        entity.setDireccion(direccion);
        EstadoVenta estadoVenta = new EstadoVenta();
        estadoVenta.setIdEstadoVenta(23);
        entity.setEstadoVenta(estadoVenta);
        MetodoEnvio metodoEnvio = new MetodoEnvio();
        metodoEnvio.setIdMetodoEnvio(24);
        entity.setMetodoEnvio(metodoEnvio);
        return entity;
    }
}
