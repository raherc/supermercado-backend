package com.supermercado.mapper;

import com.supermercado.dto.*;
import com.supermercado.model.*;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Builder
public class Mapper {

    //Mapeo de producto a DTO
    public static ProductoDTO toDTO (Producto p){
        if(p == null ) return null;

        return ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .categoria(p.getCategoria().getNombre())
                .idCategoria(p.getCategoria().getId())
                .precio(p.getPrecio())
                .cantidad(p.getCantidad())
                .build();
    }

    //Mapeo de Sucursal
    public static SucursalDTO toDTO(Sucursal s){
        if(s == null) return null;
        return  SucursalDTO.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .direccion(s.getDireccion())
                .build();

    }

    //Mapeo de Categoria
    public static CategoriaDTO toDTO(Categoria c){
        if (c == null ) return null;
        return CategoriaDTO.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .descripcion(c.getDescripcion())
                .build();
    }

    //Mapeo de venta
    public static VentaDTO toDTO (Venta v){
        if(v == null) return null;

        double miTotal = v.getDetalle().stream()
                .mapToDouble(det -> det.getCantProd() * det.getPrecio())
                .sum();

        return VentaDTO.builder()
                .id(v.getId())
                .fecha(v.getFecha())
                .estado(v.getEstado())
                .idSucursal(v.getSucursal().getId())
                .detalle( v.getDetalle().stream().map(Mapper::toDTO).toList())
                .total(miTotal)
                .build();

    }

    //Mapedo detalle venta

    public static DetalleVentaDTO toDTO (DetalleVenta det){
        if(det == null) return null;
        return DetalleVentaDTO.builder()
                .id(det.getId())
                .nombreProd(det.getProd().getNombre())
                .idProducto(det.getProd().getId())
                .cantProd(det.getCantProd())
                .precio(det.getPrecio())
                .subtotal(det.getCantProd()*det.getPrecio())
                .build();
    }


}
/*
public class Mapper {

    // -----------------------------
    // Producto → DTO
    // -----------------------------
    public static ProductoDTO toDTO(Producto p){
        if(p == null) return null;

        return ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .categoria(p.getCategoria())
                .precio(p.getPrecio())
                .cantidad(p.getCantidad())
                .build();
    }

    // -----------------------------
    // Sucursal → DTO
    // -----------------------------
    public static SucursalDTO toDTO(Sucursal s){
        if(s == null) return null;

        return SucursalDTO.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .direccion(s.getDireccion())
                .build();
    }


    // -----------------------------
    // Venta → DTO
    // -----------------------------
    public static VentaDTO toDTO(Venta v){
        if(v == null) return null;

        // Convertir detalles
        List<DetalleVentaDTO> detallesDTO = new ArrayList<>();

        if (v.getDetalle() != null) {
            detallesDTO = v.getDetalle()
                    .stream()
                    .map(Mapper::toDTO)
                    .toList();
        }

        // Calcular total automáticamente
        double total = detallesDTO.stream()
                .mapToDouble(DetalleVentaDTO::getSubtotal)
                .sum();

        return VentaDTO.builder()
                .id(v.getId())
                .fecha(v.getFecha())
                .estado(v.getEstado())
                .idSucursal(v.getSucursal() != null ? v.getSucursal().getId() : null)
                .detalle(detallesDTO)
                .total(total)
                .build();
    }


    // -----------------------------
    // DetalleVenta → DTO
    // -----------------------------
    public static DetalleVentaDTO toDTO(DetalleVenta det){
        if(det == null) return null;

        String nombreProd = det.getProd() != null ? det.getProd().getNombre() : null;

        double subtotal = det.getCantProd() * det.getPrecio();

        return DetalleVentaDTO.builder()
                .id(det.getId())
                .nombreProd(nombreProd)
                .cantProd(det.getCantProd())
                .precio(det.getPrecio())
                .subtotal(subtotal)
                .build();
    }
}

 */