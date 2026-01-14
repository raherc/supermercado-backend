package com.supermercado.service;

import com.supermercado.dto.SucursalDTO;

import java.util.List;

public interface ISucursalService {
    List<SucursalDTO> traerSucursales();
    SucursalDTO obtenerSucursalPorId(Long id);
    SucursalDTO crearSucursal(SucursalDTO sucursalDto);
    SucursalDTO actualizaSucursal(Long id, SucursalDTO sucursalDto);
    void eliminarSucursal(Long id);
}
