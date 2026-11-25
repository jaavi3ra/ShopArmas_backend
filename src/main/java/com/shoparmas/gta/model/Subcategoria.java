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
@Table(
    name = "Subcategoria",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_subcategoria_categoria",
        columnNames = {"nombre_subcategoria", "id_categoria"}
    )
)
public class Subcategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subcategoria")
    private Integer idSubcategoria;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre_subcategoria", nullable = false, length = 100)
    private String nombreSubcategoria;

    @Size(max = 255)
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
}