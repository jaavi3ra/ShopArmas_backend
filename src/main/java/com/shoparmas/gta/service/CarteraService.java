package com.shoparmas.gta.service;

import org.springframework.stereotype.Service;

import com.shoparmas.gta.DTO.CarteraDTO;
import com.shoparmas.gta.DTO.UsuarioDTO;
import com.shoparmas.gta.model.Cartera;
import com.shoparmas.gta.model.Usuario;

@Service
public class CarteraService {


private CarteraDTO convertirADTO(Cartera cartera) {
    CarteraDTO carteraDTO = new CarteraDTO();

    carteraDTO.setIdUsuario(cartera.getIdUsuario());
    carteraDTO.setUsuario(convertirUsuarioADTO(cartera.getUsuario()));
    carteraDTO.setSaldo(cartera.getSaldo());
    carteraDTO.setActualizadaEn(cartera.getActualizadaEn());

    return carteraDTO;
}

private UsuarioDTO convertirUsuarioADTO(Usuario usuario) {
    UsuarioDTO usuarioDTO = new UsuarioDTO();

    usuarioDTO.setIdUsuario(usuario.getIdUsuario());
    usuarioDTO.setIdRol(usuario.getRol().getIdRol());
    usuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
    usuarioDTO.setCorreo(usuario.getCorreo());
    usuarioDTO.setSaldo(usuario.getSaldo());
    usuarioDTO.setPuntosRp(usuario.getPuntosRp());
    usuarioDTO.setFechaRegistro(usuario.getFechaRegistro());
    usuarioDTO.setActivo(usuario.getActivo());

    return usuarioDTO;
}
}


