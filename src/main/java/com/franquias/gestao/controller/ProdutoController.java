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
import com.franquias.gestao.model.Produto;
import com.franquias.gestao.repository.CategoriaRepository;
import com.franquias.gestao.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Produto> listar(){
		return produtoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		
		Produto produto = produtoRepository.findById(id).orElse(null);
		
		if (produto == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(produto);
	}
	
	@GetMapping("/categoria/{categoriaId}")
	public List<Produto> buscarPorCategoria(@PathVariable Long categoriaId) {

		return produtoRepository.findByCategoriaId(categoriaId);
	}

	@GetMapping("/status/{ativo}")
	public List<Produto> buscarPorStatus(@PathVariable Boolean ativo) {

		return produtoRepository.findByAtivo(ativo);
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Produto produto) {
		
		if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
			return ResponseEntity.badRequest().body("Categoria não informada");
		}
		
		Categoria categoria = categoriaRepository
				.findById(produto.getCategoria().getId())
				.orElse(null);
		
		if (categoria == null) {
			return ResponseEntity.badRequest().body("Categoria não encontrada");
		}
		
		produto.setCategoria(categoria);
		
		return ResponseEntity.ok(produtoRepository.save(produto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
		
		Produto produtoExistente = produtoRepository.findById(id).orElse(null);
		
		if (produtoExistente == null) {
			return ResponseEntity.notFound().build();
		}
		
		if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
			return ResponseEntity.badRequest().body("Categoria não informada");
		}
		
		Categoria categoria = categoriaRepository
				.findById(produto.getCategoria().getId())
				.orElse(null);
		
		if (categoria == null) {
			return ResponseEntity.badRequest().body("Categoria não encontrada");
		}
		
		produto.setId(id);
		produto.setCategoria(categoria);
		
		return ResponseEntity.ok(produtoRepository.save(produto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		
		if (!produtoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		produtoRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}