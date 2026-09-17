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

import com.franquias.gestao.model.Categoria;
import com.franquias.gestao.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listar(){
		
		// Lista todas as categorias
		return categoriaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		
		// Busca a categoria pelo ID
		Categoria categoria = categoriaRepository.findById(id).orElse(null);
		
		// Verifica se a categoria existe
		if (categoria == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada");
		}
		
		return ResponseEntity.ok(categoria);
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Categoria categoria) {
		
		// Salva a nova categoria
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Categoria cadastrada com sucesso. ID: " 
				+ categoriaSalva.getId());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(
			@PathVariable Long id,
			@RequestBody Categoria categoria) {
		
		// Verifica se a categoria existe
		Categoria categoriaExistente = categoriaRepository.findById(id).orElse(null);
		
		if (categoriaExistente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada");
		}
		
		categoria.setId(id);
		
		// Atualiza a categoria
		categoriaRepository.save(categoria);
		return ResponseEntity.ok("Categoria atualizada com sucesso");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		
		// Verifica se a categoria existe
		if (!categoriaRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada");
		}
		
		// Exclui a categoria
		categoriaRepository.deleteById(id);
		
		return ResponseEntity.ok("Categoria excluída com sucesso");
	}
}