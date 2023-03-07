package com.example.demo.servicedb;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.IUsuarioService;

@Service
public class UsuarioServiceJpa implements IUsuarioService {

	@Autowired
	private UsuarioRepository usuRepo;
	
	@Override
	public void guardar(Usuario usuario) {
		usuRepo.save(usuario);

	}

	@Override
	public void eliminar(Integer idUsuario) {
		usuRepo.deleteById(idUsuario);

	}

	@Override
	public List<Usuario> buscarTodos() {
		// TODO Auto-generated method stub
		return usuRepo.findAll();
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		
		return usuRepo.findByUsername(username);
	}

	@Override
	public List<Usuario> buscarRegistrados() {
		
		return usuRepo.findByFechaRegistroNotNull();
	}

	@Override
	public  Usuario buscarPorId(Integer idUsuario) {
		Optional <Usuario> optional= usuRepo.findById(idUsuario);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public int bloquear(int idUsuario) {
		int rows = usuRepo.lock(idUsuario);
		return rows;
	}

	@Override
	public int activar(int idUsuario) {
		int rows=usuRepo.unlock(idUsuario);
		return rows;
	}

}