package com.example.demo.servicedb;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.models.Vacante;
import com.example.demo.repository.VacanteRepository;
import com.example.demo.service.IVacanteService;

@Service
@Primary
public class VacanteServiceJpa implements IVacanteService {
	
	@Autowired
	private VacanteRepository vacaRepo;

	@Override
	public List<Vacante> findAll() {
		// TODO Auto-generated method stub
		return vacaRepo.findAll();
	}

	@Override
	public Vacante buscarPorId(Integer idVacante) {
		Optional<Vacante> optional=vacaRepo.findById(idVacante);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	
	}

	@Override
	public void guardar(Vacante vacante) {
		vacaRepo.save(vacante);

	}

	@Override
	public List<Vacante> buscarDestacadas() {
		
		return vacaRepo.findByDestacadoAndEstatusOrderByIdDesc(1,"Aprobada");
	}

	@Override
	public void eliminar(Integer idVacante) {
		vacaRepo.deleteById(idVacante);
		
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		return vacaRepo.findAll(example);
		
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		//
		return vacaRepo.findAll(page);
	}

}
