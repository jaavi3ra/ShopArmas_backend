package com.shoparmas.gta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Entity
@Table(name = "EstadoArma")
public class EstadoArma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_arma")
    private Integer idEstadoArma;

    @NotNull(message = "El nombre es obligatorio")
    @Size(max = 50)
    @Column(name = "nombre_estado", nullable = false, unique = true, length = 50)
    private String nombreEstado;

    @Size(max = 255)
    @Column(name = "descripcion", length = 255)
    private String descripcion;
}
