package com.example.demo.servicedb;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.models.Solicitud;
import com.example.demo.repository.SolicitudRepository;
import com.example.demo.service.ISolicitudService;

@Service
public class SolicitudServiceJpa implements ISolicitudService{

	@Autowired
	private SolicitudRepository soliRepo;
	
	@Override
	public void guardar(Solicitud solicitud) {
		soliRepo.save(solicitud);
		
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		soliRepo.deleteById(idSolicitud);
	}

	@Override
	public List<Solicitud> mostrarTodas() {
		
		return soliRepo.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		Optional<Solicitud> opt=soliRepo.findById(idSolicitud);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		
		return soliRepo.findAll(page);
	}

}
