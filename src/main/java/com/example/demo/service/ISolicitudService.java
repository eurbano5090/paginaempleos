package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.models.Solicitud;

public interface ISolicitudService {

	void guardar(Solicitud solicitud);
	void eliminar(Integer idSolicitud);
	List<Solicitud>mostrarTodas();
	Solicitud buscarPorId(Integer idSolicitud);
	Page<Solicitud> buscarTodas(Pageable page);
}
