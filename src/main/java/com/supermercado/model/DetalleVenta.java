package com.supermercado.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //muchos detalles pueden estar asociados a una venta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="venta_id")
    private Venta venta;

    // muchos detalles venta estan asociados a un producto, un producto puede estar en muchos detalles
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="prod_id")
    private Producto prod;
    private Integer cantProd;
    private Double precio;
}
