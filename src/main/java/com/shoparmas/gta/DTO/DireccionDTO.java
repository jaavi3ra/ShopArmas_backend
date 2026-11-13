package com.shoparmas.gta.DTO;

import lombok.Data;

@Data
public class DireccionDTO {

    private Integer idDireccion;
    private Integer idUsuario;
    private Integer idComuna;
    private String calle;
    private String numero;
    private String departamento;
    private String codigoPostal;
    private String referencia;
}
