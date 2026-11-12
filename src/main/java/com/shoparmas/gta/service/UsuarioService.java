package com.shoparmas.gta.service;

import org.springframework.stereotype.Service;

import com.shoparmas.gta.DTO.UsuarioDTO;
import com.shoparmas.gta.model.Usuario;


@Service 
public class UsuarioService {

private UsuarioDTO convertirADTO(Usuario usuario) {
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
