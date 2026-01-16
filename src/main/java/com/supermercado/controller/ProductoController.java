package com.supermercado.controller;

import com.supermercado.dto.ProductoDTO;
import com.supermercado.dto.SucursalDTO;
import com.supermercado.model.Producto;
import com.supermercado.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> traerProductos(){
        return ResponseEntity.ok(productoService.traerProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorId(@PathVariable Long id){
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO proDto){

        ProductoDTO creado =productoService.crearProducto(proDto);
        //es el standar de devolución de spring devolver ruta del elemento creado
        return ResponseEntity.created(URI.create("/api/productos" + creado.getId())).body(creado);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO prodDto){

        return ResponseEntity.ok(productoService.actualizarProducto(id,prodDto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity borrarProducto(@PathVariable Long id){
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();

    }
}
