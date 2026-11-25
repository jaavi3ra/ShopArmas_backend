package com.shoparmas.gta.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Health", description = "Verificación de disponibilidad del servicio y base de datos")
@RestController
public class HealthController {

    private static final int DB_TIMEOUT_SECONDS = 2;

    @Autowired
    private DataSource dataSource;

    @Operation(summary = "Verificar disponibilidad de la aplicación y conexión a PostgreSQL")
    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> health() {
        if (isDatabaseAvailable()) {
            return ResponseEntity.ok(Map.of("status", "UP"));
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("status", "DOWN"));
    }

    private boolean isDatabaseAvailable() {
        try {
            return CompletableFuture.supplyAsync(() -> {
                try (Connection connection = dataSource.getConnection();
                     PreparedStatement statement = connection.prepareStatement("SELECT 1")) {
                    statement.setQueryTimeout(DB_TIMEOUT_SECONDS);
                    try (ResultSet rs = statement.executeQuery()) {
                        return rs.next();
                    }
                } catch (Exception e) {
                    return false;
                }
            }).get(DB_TIMEOUT_SECONDS + 1L, TimeUnit.SECONDS);
        } catch (Exception e) {
            return false;
        }
    }
}
