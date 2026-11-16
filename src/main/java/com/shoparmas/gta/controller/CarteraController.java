
package com.shoparmas.gta.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shoparmas.gta.DTO.CarteraDTO;
import com.shoparmas.gta.service.CarteraService;

@RestController
@RequestMapping("/api/carteras")
public class CarteraController {

    @Autowired
    private CarteraService carteraService;

    @PostMapping("/{idUsuario}")
    public ResponseEntity<CarteraDTO> crearCartera(@PathVariable Integer idUsuario) {
        return ResponseEntity.status(201).body(carteraService.crearCartera(idUsuario));
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<CarteraDTO> obtenerCarteraId(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(carteraService.obtenerCarteraId(idUsuario));
    }

    @GetMapping
    public ResponseEntity<List<CarteraDTO>> listarCarteras() {
        return ResponseEntity.ok(carteraService.listarCarteras());
    }

    @PutMapping("/{idUsuario}/saldo")
    public ResponseEntity<CarteraDTO> actualizarSaldo(
            @PathVariable Integer idUsuario,
            @RequestBody BigDecimal nuevoSaldo) {
        return ResponseEntity.ok(carteraService.actualizarSaldo(idUsuario, nuevoSaldo));
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> eliminarCartera(@PathVariable Integer idUsuario) {
        carteraService.eliminarCartera(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
