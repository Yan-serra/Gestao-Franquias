package com.franquias.gestao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franquias.gestao.model.Perfil;
import com.franquias.gestao.repository.PerfilRepository;

@RestController
@RequestMapping("/perfis")
public class PerfilController {
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@GetMapping
	public List<Perfil> listar(){
		return perfilRepository.findAll();
	}
	
	@PostMapping
	public Perfil cadastrar(@RequestBody Perfil perfil) {
		return perfilRepository.save(perfil);
	}
	
	@PutMapping("/{id}")
	public Perfil atualizar(@PathVariable Long id, @RequestBody Perfil perfil) {
		perfil.setId(id);
		return perfilRepository.save(perfil);
	}
	
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Long id) {
		perfilRepository.deleteById(id);
	}
}
