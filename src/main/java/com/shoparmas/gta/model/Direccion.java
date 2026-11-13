package com.shoparmas.gta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Direccion")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Integer idDireccion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_comuna", nullable = false)
    private Comuna comuna;

    @NotNull
    @Size(max = 150)
    @Column(name = "calle", nullable = false, length = 150)
    private String calle;

    @NotNull
    @Size(max = 20)
    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    @Size(max = 50)
    @Column(name = "departamento", length = 50)
    private String departamento;

    @Size(max = 15)
    @Column(name = "codigo_postal", length = 15)
    private String codigoPostal;

    @Size(max = 255)
    @Column(name = "referencia", length = 255)
    private String referencia;
}