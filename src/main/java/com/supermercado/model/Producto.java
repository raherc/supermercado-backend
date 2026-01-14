package com.supermercado.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    //se cambiará para tener referencia a categoria
    private String categoria;
    // Relación unidireccional
//    @ManyToOne
//    @JoinColumn(name = "id_categoria", nullable = false)
//    private Categoria categoria;
    private int cantidad;


}
