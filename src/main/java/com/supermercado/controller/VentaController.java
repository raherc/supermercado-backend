package com.supermercado.controller;

import com.supermercado.dto.VentaDTO;
import com.supermercado.model.Venta;
import com.supermercado.service.IVentaService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private IVentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>>  traerVentas(){
       return  ResponseEntity.ok(ventaService.traerVentas());

    }

    @PostMapping
    public ResponseEntity<VentaDTO> crearVenta (@RequestBody VentaDTO ventaDTO){
        VentaDTO venDto = ventaService.crearVenta(ventaDTO);
        return ResponseEntity.created(URI.create("/api/ventas/" +ventaDTO.getId())).body(venDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> actualizarVenta(@PathVariable Long id,@RequestBody VentaDTO venDTO){
        return  ResponseEntity.ok(ventaService.actualizarVenta(id,venDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminerVenta(@PathVariable Long id){
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();

    }
}
