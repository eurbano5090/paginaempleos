package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.models.Vacante;

@Service
public class VacanteServiceImpl implements IVacanteService {

	private List<Vacante>lista=null;
	
	public VacanteServiceImpl() {
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyyy");
		lista = new LinkedList<Vacante>();
		try {
			Vacante vacante1=new Vacante();
			vacante1.setId(1);
			vacante1.setNombre("Ingeniero Civil");
			vacante1.setDescripcion("Solicitamos Ing. Civil para diseñar puente peatonal");
			vacante1.setFecha(sdf.parse("08-02-2023"));
			vacante1.setSalario(8500.0);
			vacante1.setDestacado(1);
			vacante1.setImagen("empresa1.png");
			
			Vacante vacante2=new Vacante();
			vacante2.setId(2);
			vacante2.setNombre("Desarrollador Software");
			vacante2.setDescripcion("Solicitamos desarrollador Junior Java");
			vacante2.setFecha(sdf.parse("09-02-2023"));
			vacante2.setSalario(11500.0);
			vacante2.setDestacado(1);
			vacante2.setImagen("empresa2.png");
			
			Vacante vacante3=new Vacante();
			vacante3.setId(3);
			vacante3.setNombre("Jefe de Local");
			vacante3.setDescripcion("Solicitamos Ing./tecnico adm. para local de marca muy conocida");
			vacante3.setFecha(sdf.parse("08-02-2023"));
			vacante3.setSalario(9500.0);
			vacante3.setDestacado(0);
			vacante3.setImagen("empresa3.png");
			
			Vacante vacante4=new Vacante();
			vacante4.setId(4);
			vacante4.setNombre("Ingeniero Software");
			vacante4.setDescripcion("Solicitamos Ing./tec. o carrera afin para desarrollar pagina web de pagos");
			vacante4.setFecha(sdf.parse("08-02-2023"));
			vacante4.setSalario(8500.0);
			vacante4.setDestacado(0);
			vacante4.setImagen("empresa1.png");
			
			Vacante vacante5=new Vacante();
			vacante5.setId(5);
			vacante5.setNombre("devops");
			vacante5.setDescripcion("Importante empresa solicita Ing/tec. o carrera afin ,para software de marketplace");
			vacante5.setFecha(sdf.parse("08-02-2023"));
			vacante5.setSalario(10500.0);
			vacante5.setDestacado(1);
			vacante5.setImagen("empresa4.png");
			
			lista.add(vacante1);
			lista.add(vacante2);
			lista.add(vacante3);
			lista.add(vacante4);
			lista.add(vacante5);
			
		}catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}
		
	}
	
	
	@Override
	public List<Vacante> findAll() {
		
		return lista;
	}


	@Override
	public Vacante buscarPorId(Integer idVacante) {
		for(Vacante v:lista) {
			if(v.getId()==idVacante) {
				return v;
			}
		}
		return null;
	}


	@Override
	public void guardar(Vacante vacante) {
		lista.add(vacante);
		
	}


	@Override
	public List<Vacante> buscarDestacadas() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void eliminar(Integer idVacante) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

}
