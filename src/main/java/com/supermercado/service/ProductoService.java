package com.supermercado.service;

import com.supermercado.dto.ProductoDTO;
import com.supermercado.dto.SucursalDTO;
import com.supermercado.exception.BadRequestException;
import com.supermercado.exception.NotFoundExceptionSup;
import com.supermercado.mapper.Mapper;
import com.supermercado.model.Categoria;
import com.supermercado.model.Producto;
import com.supermercado.model.Sucursal;
import com.supermercado.repository.CategoriaRepository;
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
    @Autowired
    private CategoriaRepository categoriaRepository;

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
        //ahora nos llegara id de categoria en lugar del texto

        Categoria cat = categoriaRepository.findById(productoDto.getIdCategoria()).orElseThrow(
                ()-> new NotFoundExceptionSup("Categoría no encontrada al insertar producto")
        );


        

        var prod = Producto.builder()
                .nombre(productoDto.getNombre())
                //.categoria(productoDto.getCategoria())
                .categoria(cat)
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
        //volvemos a buscar la categoría que nos llega
        Categoria cat = categoriaRepository.findById(productoDto.getIdCategoria()).orElseThrow(
                ()-> new NotFoundExceptionSup("Categoría no encontrada al modificar producto")
        );

        prod.setNombre(productoDto.getNombre());
        //prod.setCategoria(productoDto.getCategoria());
        prod.setCategoria(cat);
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
