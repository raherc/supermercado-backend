package com.supermercado.controller;

import com.supermercado.dto.SucursalDTO;
import com.supermercado.service.ISucursalService;
import com.supermercado.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@CrossOrigin(origins = "http://localhost:4201")
public class SucursalController {

    @Autowired
    private ISucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> traerSucursales(){
        return ResponseEntity.ok(sucursalService.traerSucursales());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> obtenerSucursalPorId(@PathVariable Long id){
        return ResponseEntity.ok(sucursalService.obtenerSucursalPorId(id));
    }


    @PostMapping
    public ResponseEntity<SucursalDTO> crearSucursal (@RequestBody SucursalDTO sucDto){
        SucursalDTO creada =sucursalService.crearSucursal(sucDto);
        //pongo otro ejemplo
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("{id}").buildAndExpand(creada.getId()).toUri();
//        return ResponseEntity.created(location).build(creada);

        return ResponseEntity.created(URI.create("/api/productos/"+creada.getId())).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> actualizarSucursal (@PathVariable Long id,@RequestBody SucursalDTO sucDTO){
        return ResponseEntity.ok( sucursalService.actualizaSucursal(id, sucDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity borrarSucusal(@PathVariable Long id){
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();

    }
}
