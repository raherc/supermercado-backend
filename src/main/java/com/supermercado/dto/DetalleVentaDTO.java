package com.supermercado.dto;

import lombok.*;
import lombok.Builder;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaDTO {
    private Long id;
    private String nombreProd;
    private Long idProducto;
    private Integer cantProd;
    private Double precio;
    private Double subtotal;
}
