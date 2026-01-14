package com.supermercado.service;

import com.supermercado.dto.SucursalDTO;
import com.supermercado.exception.NotFoundExceptionSup;
import com.supermercado.mapper.Mapper;
import com.supermercado.model.Sucursal;
import com.supermercado.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService  implements ISucursalService{

    @Autowired
    private SucursalRepository repository;

    @Override
    public List<SucursalDTO> traerSucursales() {
        return repository.findAll().stream().map(Mapper::toDTO).toList();

    }

    @Override
    public SucursalDTO obtenerSucursalPorId(Long id) {
        Sucursal suc = repository.findById(id).orElseThrow(
                ()-> new NotFoundExceptionSup("Sucursal no encontrada")
        );
        return Mapper.toDTO(suc);
    }

    @Override
    public SucursalDTO crearSucursal(SucursalDTO sucursalDto) {
        var sucur = Sucursal.builder()
                .nombre(sucursalDto.getNombre())
                .direccion(sucursalDto.getDireccion())
                .build();

        return Mapper.toDTO(repository.save(sucur));
    }

    @Override
    public SucursalDTO actualizaSucursal(Long id, SucursalDTO sucursalDto) {
        Sucursal suc = repository.findById(id)
                .orElseThrow(()-> new NotFoundExceptionSup("Sucursal a actualizar no encontrada"));
        System.out.println("nombre que llega:"+sucursalDto.getNombre());
        System.out.println("direccion que llega:"+sucursalDto.getDireccion());

        suc.setNombre(sucursalDto.getNombre());
        suc.setDireccion(sucursalDto.getDireccion());
        return Mapper.toDTO(repository.save(suc));
    }

    @Override
    public void eliminarSucursal(Long id) {

        if( !repository.existsById(id)){
            throw new NotFoundExceptionSup("Sucursal a eliminar no encontrada");
        }

        repository.deleteById(id);
    }
}
