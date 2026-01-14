package com.supermercado.dto;

import lombok.*;
import lombok.Builder;


import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {
    //datos de la venta
    private Long id;
    private LocalDate fecha;
    private String estado;

    //datos de la sucursal
    private Long idSucursal;
    //lista detalle
    private List<DetalleVentaDTO> detalle;

    //total
    private Double total;
}
