package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.models.Vacante;

public interface IVacanteService {

	List<Vacante> findAll();
	Vacante buscarPorId(Integer idVacante);
	public void guardar(Vacante vacante);
	List<Vacante>buscarDestacadas();
	void eliminar (Integer idVacante);
	List<Vacante>buscarByExample(Example<Vacante> example);
	Page<Vacante>buscarTodas(Pageable page);
}
