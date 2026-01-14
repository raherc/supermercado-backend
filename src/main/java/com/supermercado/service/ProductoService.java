package com.supermercado.service;

import com.supermercado.dto.ProductoDTO;
import com.supermercado.dto.SucursalDTO;
import com.supermercado.exception.BadRequestException;
import com.supermercado.exception.NotFoundExceptionSup;
import com.supermercado.mapper.Mapper;
import com.supermercado.model.Producto;
import com.supermercado.model.Sucursal;
import com.supermercado.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService  implements  IProductoService{
    @Autowired
    private ProductoRepository repository;

    @Override
    public List<ProductoDTO> traerProductos() {
        List<Producto> productos = repository.findAll();
        //debemos por lo menos controlar que haya algun producto
        if(productos.isEmpty()){
            throw  new NotFoundExceptionSup("No hay productos disponibles");
        }
        return productos.stream()
                .map(Mapper::toDTO).toList();
    }

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        Producto prod = repository.findById(id).orElseThrow(
                ()-> new NotFoundExceptionSup("Producto no encontrado")
        );
        return Mapper.toDTO(prod);
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDto) {
        var prod = Producto.builder()
                .nombre(productoDto.getNombre())
                .categoria(productoDto.getCategoria())
                .precio(productoDto.getPrecio())
                .cantidad(productoDto.getCantidad())
                .build();

        return Mapper.toDTO(repository.save(prod));
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDto) {
        //buscar si existe
        Producto prod = repository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionSup("Producto no encontrado para actualizar"));

        prod.setNombre(productoDto.getNombre());
        prod.setCategoria(productoDto.getCategoria());
        prod.setCantidad(productoDto.getCantidad());
        prod.setPrecio(productoDto.getPrecio());

        return Mapper.toDTO(repository.save(prod));
    }

    @Override
    public void eliminarProducto(Long id) {
        if( !repository.existsById(id)){
            throw new NotFoundExceptionSup("Producto a eliminar no encontrado");
        }
        try {
            repository.deleteById(id);
        }catch (BadRequestException e) {
            throw new BadRequestException("Error al borrar producto,puede estar asociado"+e.getMessage());
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("No se puede borrar producto con Id: "+id+" esta asociado a ventas");
        }catch (Exception e){
            throw new NotFoundExceptionSup("Error al eliminar"+e.getMessage());

        }

    }
}
