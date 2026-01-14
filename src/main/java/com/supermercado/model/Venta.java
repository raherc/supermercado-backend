package com.supermercado.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private String estado;
    private Double total;
    @ManyToOne// muchas ventas pertenecen a una sucursal, una sucursal puede tener muchas ventas
    private Sucursal sucursal;

    //una venta tiene muchos detalles de venta, cada detalle pertenece a una venta
    @OneToMany (mappedBy = "venta", cascade = CascadeType.ALL,
                orphanRemoval = true, //elimina los detalles que no pertenecen a ninguna venta
                fetch = FetchType.EAGER)//trae el detalle de venta siempre, LAZY
    private List<DetalleVenta> detalle = new ArrayList<>();

}
