package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.models.Categoria;


public interface ICategoriaService {

	void guardar(Categoria categoria);
	List<Categoria>mostrarTodas();
	Categoria buscarPorId(Integer idCategoria);
	void eliminar (Integer idCategoria);
	Page<Categoria>buscarTodas(Pageable page);
}
