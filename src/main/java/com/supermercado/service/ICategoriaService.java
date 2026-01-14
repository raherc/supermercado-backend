package com.supermercado.service;

import com.supermercado.dto.CategoriaDTO;
import com.supermercado.model.Categoria;

import java.util.List;

public interface ICategoriaService {

    List<CategoriaDTO> traerCategorias();

    CategoriaDTO obtenerCategoriaPorId(Long id);
    CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO);
    CategoriaDTO actualizarCategoria (Long id, CategoriaDTO categoriaDTO);

    void eliminarCategoria (Long id);
}
