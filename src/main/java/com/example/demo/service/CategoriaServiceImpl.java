package com.example.demo.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.models.Categoria;


@Service
public class CategoriaServiceImpl implements ICategoriaService{

	private List<Categoria> categorias=null;
	
	public CategoriaServiceImpl() {
		categorias = new LinkedList<Categoria>();
		try {
			Categoria categoria1=new Categoria();
			categoria1.setId(1);
			categoria1.setNombre("Recursos Humanos");
			categoria1.setDescripcion("Trabajos relacionados area RRHH");
			
			Categoria categoria2=new Categoria();
			categoria2.setId(2);
			categoria2.setNombre("Ventas");
			categoria2.setDescripcion("Trabajos relacionados area Ventas");
			
			Categoria categoria3=new Categoria();
			categoria3.setId(3);
			categoria3.setNombre("Arquitectura");
			categoria3.setDescripcion("Diseño de planos en general y trabajos relacionados.");
			
			Categoria categoria4=new Categoria();
			categoria4.setId(4);
			categoria4.setNombre("Computacion");
			categoria4.setDescripcion("Desarrollo y mantecion de software");
			
			categorias.add(categoria1);
			categorias.add(categoria2);
			categorias.add(categoria3);
			categorias.add(categoria4);
			
			
	    }catch (Exception e) {
		System.out.println("Error:" + e.getMessage());
	    }}
		
	
	@Override
	public void guardar(Categoria categoria) {
		categorias.add(categoria);
		
	}

	@Override
	public List<Categoria> mostrarTodas() {
		// TODO Auto-generated method stub
		return categorias;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		for(Categoria cat:categorias) {
			if(cat.getId()==idCategoria) {
	
		return cat;
	}
		}
		return null;
}


	@Override
	public void eliminar(Integer idCategoria) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

}
