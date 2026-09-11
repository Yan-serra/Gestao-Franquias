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
	public Produto buscarPorId(@PathVariable Long id) {
		return produtoRepository.findById(id).orElse(null);
	}
	
	@PostMapping
	public Produto cadastrar(@RequestBody Produto produto) {
		Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId()).orElse(null);
		produto.setCategoria(categoria);
		return produtoRepository.save(produto);
	}
	
	@PutMapping("/{id}")
	public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto) {
		produto.setId(id);
		return produtoRepository.save(produto);
	}
	
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Long id) {
		produtoRepository.deleteById(id);
	}
}