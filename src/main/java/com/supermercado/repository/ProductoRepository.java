package com.supermercado.repository;

import com.supermercado.dto.ProductoDTO;
import com.supermercado.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    //Buscar por nombre, al final no se usa porque se añada id al dto,
    //Optional<Producto> findByNombre(String nombre);
}

