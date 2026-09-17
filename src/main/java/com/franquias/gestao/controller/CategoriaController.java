package com.franquias.gestao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franquias.gestao.model.Categoria;
import com.franquias.gestao.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		
		Categoria categoria = categoriaRepository.findById(id).orElse(null);
		
		if (categoria == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(categoria);
	}
	
	@PostMapping
	public Categoria cadastrar(@RequestBody Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
		
		Categoria categoriaExistente = categoriaRepository.findById(id).orElse(null);
		
		if (categoriaExistente == null) {
			return ResponseEntity.notFound().build();
		}
		
		categoria.setId(id);
		
		return ResponseEntity.ok(categoriaRepository.save(categoria));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		
		if (!categoriaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		categoriaRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}