package com.supermercado.service;

import com.supermercado.dto.CategoriaDTO;
import com.supermercado.exception.NotFoundExceptionSup;
import com.supermercado.mapper.Mapper;
import com.supermercado.model.Categoria;
import com.supermercado.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService implements  ICategoriaService{

   @Autowired
   private CategoriaRepository repository;

    @Override
    public List<CategoriaDTO> traerCategorias() {
        List<Categoria> categorias = repository.findAll();
        if (categorias.isEmpty()){
            throw  new NotFoundExceptionSup("No existen categorias");
        }
        return categorias.stream()
                .map(Mapper::toDTO).toList();
    }

    @Override
    public CategoriaDTO obtenerCategoriaPorId(Long id) {
        Categoria categoria = repository.findById(id).orElseThrow(
                ()-> new NotFoundExceptionSup("Categoria no encontrada")
        );
        return Mapper.toDTO(categoria);
    }

    @Override
    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        var cat = Categoria.builder()
                .nombre(categoriaDTO.getNombre())
                .descripcion(categoriaDTO.getDescripcion())
                .build();
        return Mapper.toDTO(repository.save(cat));
    }

    @Override
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        //buscar si existe categoria
        Categoria cat = repository.findById(id).orElseThrow(
                ()-> new NotFoundExceptionSup("Categoria a actualizar no existe")
        );

        cat.setNombre(categoriaDTO.getNombre());
        cat.setDescripcion(categoriaDTO.getDescripcion());
        return Mapper.toDTO(repository.save(cat));
    }

    @Override
    public void eliminarCategoria(Long id) {
        //buscar si existe categoria
        Categoria cat = repository.findById(id).orElseThrow(
                ()-> new NotFoundExceptionSup("Categoria a eliminar no existe")
        );
        repository.deleteById(id);

    }
}
