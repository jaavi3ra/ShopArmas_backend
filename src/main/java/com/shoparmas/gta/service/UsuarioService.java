package com.shoparmas.gta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoparmas.gta.DTO.UsuarioDTO;
import com.shoparmas.gta.model.Usuario;
import com.shoparmas.gta.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service 
public class UsuarioService {

    @Autowired 
    private UsuarioRepository usuarioRepository;

    public Usuario buscarUsuario(Integer idUsuario) {
        log.info("Buscando usuario con ID: {}", idUsuario);
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));
    }

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
    

    public UsuarioDTO obtenerUsuarioPorId(Integer idUsuario) {
        Usuario usuario = buscarUsuario(idUsuario);
        return convertirADTO(usuario);
    }
    
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        try {
            log.info("Creando usuario con correo: {}", usuarioDTO.getCorreo());
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
            usuario.setCorreo(usuarioDTO.getCorreo());
            usuario.setSaldo(usuarioDTO.getSaldo());
            usuario.setPuntosRp(usuarioDTO.getPuntosRp());
            usuario.setFechaRegistro(usuarioDTO.getFechaRegistro());
            usuario.setActivo(usuarioDTO.getActivo());

            Usuario guardado = usuarioRepository.save(usuario);
            return convertirADTO(guardado);
        } catch (RuntimeException e) {
            log.error("Error al crear el usuario con correo: {}", usuarioDTO.getCorreo(), e);
            throw e;
        }
    }

    public UsuarioDTO actualizarUsuario(Integer idUsuario, UsuarioDTO usuarioDTO) {
        log.info("Actualizando usuario con ID: {}", idUsuario);
        Usuario usuario = buscarUsuario(idUsuario);
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setSaldo(usuarioDTO.getSaldo());
        usuario.setPuntosRp(usuarioDTO.getPuntosRp());
        usuario.setFechaRegistro(usuarioDTO.getFechaRegistro());
        usuario.setActivo(usuarioDTO.getActivo());

        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirADTO(actualizado);
    }
    public void eliminarUsuario(Integer idUsuario) {
        log.info("Eliminando usuario con ID: {}", idUsuario);
        Usuario usuario = buscarUsuario(idUsuario);
        usuarioRepository.delete(usuario);
    }
}
