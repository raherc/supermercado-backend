package com.supermercado.repository;

import com.supermercado.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucursalRepository  extends JpaRepository<Sucursal, Long> {
}
