package com.shoparmas.gta.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoparmas.gta.DTO.CarteraDTO;
import com.shoparmas.gta.DTO.UsuarioDTO;
import com.shoparmas.gta.model.Cartera;
import com.shoparmas.gta.model.Usuario;
import com.shoparmas.gta.repository.CarteraRepository;
import com.shoparmas.gta.repository.UsuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j // anotacion de lombok para log.error()
@Service
public class CarteraService {
    @Autowired
    private CarteraRepository carteraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public CarteraDTO crearCartera(Integer idUsuario) {
        try {
            log.info("Creando cartera para el usuario...", idUsuario);

            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Usuario no encontrado: " + idUsuario));

            if (carteraRepository.existsById(idUsuario)) {
                throw new IllegalStateException("El usuario ya tiene una cartera");
            }

            Cartera cartera = new Cartera();
            cartera.setUsuario(usuario);
            cartera.setSaldo(new BigDecimal("100000.00"));

            Cartera guardada = carteraRepository.saveAndFlush(cartera);
            entityManager.refresh(guardada);

            return convertirADTO(guardada);
        } catch (RuntimeException e) {
            log.error("Error al crear la cartera del usuario... ", idUsuario, e);
            throw e;
        }
    }

    @Transactional
    public CarteraDTO obtenerCarteraId(Integer idUsuario) {
        log.info("Obteniendo cartera del usuario...", idUsuario);

        Cartera cartera = buscarCartera(idUsuario);
        return convertirADTO(cartera);
    }

    @Transactional(readOnly = true)
    public List<CarteraDTO> listarCarteras() {
        log.info("Listando carteras");

        List<CarteraDTO> carterasDTO = new ArrayList<>();

        for (Cartera cartera : carteraRepository.findAll()) {
            carterasDTO.add(convertirADTO(cartera));
        }

        return carterasDTO;
    }

    @Transactional
    public CarteraDTO actualizarSaldo(Integer idUsuario, BigDecimal nuevoSaldo) {
        log.info("Actualizando saldo de la cartera del usuario...", idUsuario);

        if (nuevoSaldo == null || nuevoSaldo.signum() < 0) {
            throw new IllegalArgumentException(
                    "El saldo debe ser mayor o igual a cero");
        }

        Cartera cartera = buscarCartera(idUsuario);
        cartera.setSaldo(nuevoSaldo);

        carteraRepository.flush();
        entityManager.refresh(cartera);

        return convertirADTO(cartera);
    }

    @Transactional
    public void eliminarCartera(Integer idUsuario) {
        log.info("Eliminando cartera del usuario...", idUsuario);

        Cartera cartera = buscarCartera(idUsuario);
        carteraRepository.delete(cartera);
    }

    private Cartera buscarCartera(Integer idUsuario) {
        return carteraRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cartera no encontrada para el usuario: " + idUsuario));
    }


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


