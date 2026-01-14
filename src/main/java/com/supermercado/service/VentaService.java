package com.supermercado.service;

import com.supermercado.dto.DetalleVentaDTO;
import com.supermercado.dto.VentaDTO;
import com.supermercado.exception.NotFoundExceptionSup;
import com.supermercado.mapper.Mapper;
import com.supermercado.model.DetalleVenta;
import com.supermercado.model.Producto;
import com.supermercado.model.Sucursal;
import com.supermercado.model.Venta;
import com.supermercado.repository.ProductoRepository;
import com.supermercado.repository.SucursalRepository;
import com.supermercado.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService implements IVentaService {
    @Autowired
     private VentaRepository repoVenta;
    @Autowired
    private SucursalRepository repoSuc;
    @Autowired
    private ProductoRepository repoProd;

    @Override
    public List<VentaDTO> traerVentas() {
        List<Venta> ventas = repoVenta.findAll();
        List<VentaDTO> ventasDto = new ArrayList<>();

        VentaDTO dto;
        for(Venta v : ventas){
            dto = Mapper.toDTO(v);
            ventasDto.add(dto);

        }
        return ventasDto;
    }

    @Override
    public VentaDTO crearVenta(VentaDTO ventaDto) {
        //Validaciones
        if( ventaDto == null) throw new RuntimeException("VentaDTO es null");
        if(ventaDto.getIdSucursal() == null) throw new RuntimeException("Debe informar la sucursal");
        if(ventaDto.getDetalle() == null || ventaDto.getDetalle().isEmpty()){
            throw new NotFoundExceptionSup("Debe incluir al menos un producto");
        }

        //Buscamos la sucursal
        Sucursal suc = repoSuc.findById(ventaDto.getIdSucursal()).orElse(null);
        if (suc == null){
            throw new NotFoundExceptionSup("Sucursal no encontrada");
        }

        //Creamos la venta
        Venta vent = new Venta();
        vent.setFecha(ventaDto.getFecha());
        vent.setEstado(ventaDto.getEstado());
        vent.setSucursal(suc);
       // vent.setTotal(ventaDto.getTotal()); no viene calculado

        // ahora los detalles...................
        List<DetalleVenta> detalles = new ArrayList<>();

        //List<DetalleVentaDTO> aux = ventaDto.getDetalle(); lo probare
        double total =0;
        for (DetalleVentaDTO detDTO : ventaDto.getDetalle()){
            //buscamos producto con el dato que tenemos que es el nombre
           // Producto p = repoProd.findByNombre(detDTO.getNombreProd()).orElse(null);
            //le añado idProducto a detalleVentaDTo es mas logico que buscar por nombre
            Producto p = repoProd.findById(detDTO.getIdProducto()).orElse(null);
            if(p == null){ throw  new NotFoundExceptionSup("Producto no encontrado"+ detDTO.getNombreProd());}

            //creamos detalle
            DetalleVenta detalleVent = new DetalleVenta();
            detalleVent.setProd(p);
            detalleVent.setPrecio(detDTO.getPrecio());
            detalleVent.setCantProd(detDTO.getCantProd());
            detalleVent.setVenta(vent);
            //vamos acumulando para total
            total = total + (detDTO.getPrecio() * detDTO.getCantProd());

            detalles.add(detalleVent);
        }
        //añadimos total calculado
        vent.setTotal(total);
        //añadimos detalles a venta
        vent.setDetalle(detalles);
        //.........................................
        //guardamos y devolvemos
        return Mapper.toDTO(repoVenta.save(vent));



    }

    @Override
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDto) {
        //validaciones
        Venta venta = (repoVenta.findById(id)
                .orElseThrow(()->  new NotFoundExceptionSup("No existe venta con ID:" + id)));
        //modificamos lo que consideremos que puede cambiar,
        //en Venta es estado, fecha,. El resto sucursal  se deja
        venta.setEstado(ventaDto.getEstado());
        venta.setFecha(ventaDto.getFecha());

        // *** LIMPIAR DETALLES ANTIGUOS ***
        venta.getDetalle().clear();

        // ahora los detalles...................
        //List<DetalleVenta> detalles = new ArrayList<>(); no se debe crear nueva

        //List<DetalleVentaDTO> aux = ventaDto.getDetalle(); lo probare
        double total =0;
        for (DetalleVentaDTO detDTO : ventaDto.getDetalle()){
            //buscamos producto con el dato que tenemos que es el nombre
            // Producto p = repoProd.findByNombre(detDTO.getNombreProd()).orElse(null);
            //le añado idProducto a detalleVentaDTo es mas logico que buscar por nombre
            Producto p = repoProd.findById(detDTO.getIdProducto()).orElse(null);
            if(p == null){ throw  new NotFoundExceptionSup("Producto no encontrado"+ detDTO.getNombreProd());}

            //creamos detalle
            DetalleVenta detalleVent = new DetalleVenta();
            detalleVent.setProd(p);
            detalleVent.setPrecio(detDTO.getPrecio());
            detalleVent.setCantProd(detDTO.getCantProd());
            detalleVent.setVenta(venta);
            //vamos acumulando para total
            total = total + (detDTO.getPrecio() * detDTO.getCantProd());

            //detalles.add(detalleVent);
            venta.getDetalle().add(detalleVent);
        }
        //añadimos total calculado
        venta.setTotal(total);
        //añadimos detalles a venta, no haria falta porque ya hemos modificado la lista directamente
        //venta.setDetalle(detalles);
        //.........................................
        return Mapper.toDTO(repoVenta.save(venta));


    }



    @Override
    public void eliminarVenta(Long id) {
        Venta v = repoVenta.findById(id).orElse(null);
        if(v ==null){
            throw  new RuntimeException("Venta no encotrada al eliminar con id: "+ id);
        }

        repoVenta.deleteById(id);

    }
}
