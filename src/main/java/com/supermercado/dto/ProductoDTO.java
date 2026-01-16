package com.supermercado.dto;

import lombok.*;
import lombok.Builder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private Long idCategoria;
    private Double precio;
    private int cantidad;



}
