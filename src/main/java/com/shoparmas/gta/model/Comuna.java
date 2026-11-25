package com.shoparmas.gta.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Entity
@Table(
    name = "Comuna",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_comuna_region",
        columnNames = {"nombre_comuna", "id_region"}
    )
)
public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comuna")
    private Integer idComuna;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre_comuna", nullable = false, length = 100)
    private String nombreComuna;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_region", nullable = false)
    private Region region;
}