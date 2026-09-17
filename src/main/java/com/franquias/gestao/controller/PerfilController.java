package com.franquias.gestao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		
		Perfil perfil = perfilRepository.findById(id).orElse(null);
		
		if (perfil == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(perfil);
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Perfil perfil) {
		
		Perfil perfilSalvo = perfilRepository.save(perfil);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(perfilSalvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Perfil perfil) {
		
		Perfil perfilExistente = perfilRepository.findById(id).orElse(null);
		
		if (perfilExistente == null) {
			return ResponseEntity.notFound().build();
		}
		
		perfil.setId(id);
		
		return ResponseEntity.ok(perfilRepository.save(perfil));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		
		if (!perfilRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		perfilRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}