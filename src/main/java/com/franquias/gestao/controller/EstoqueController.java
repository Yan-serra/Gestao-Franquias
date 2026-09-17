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

import com.franquias.gestao.model.Estoque;
import com.franquias.gestao.model.Produto;
import com.franquias.gestao.model.UnidadeFranqueada;
import com.franquias.gestao.repository.EstoqueRepository;
import com.franquias.gestao.repository.ProdutoRepository;
import com.franquias.gestao.repository.UnidadeFranqueadaRepository;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private UnidadeFranqueadaRepository unidadeFranqueadaRepository;

	@GetMapping
	public List<Estoque> listar() {
		return estoqueRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

		Estoque estoque = estoqueRepository.findById(id).orElse(null);

		if (estoque == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(estoque);
	}

	@GetMapping("/abaixo-minimo")
	public List<Estoque> listarAbaixoDoMinimo() {

		return estoqueRepository.buscarEstoqueAbaixoDoMinimo();
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Estoque estoque) {

		if (estoque.getProduto() == null || estoque.getProduto().getId() == null) {
			return ResponseEntity.badRequest().body("Produto não informado");
		}

		if (estoque.getUnidade() == null || estoque.getUnidade().getId() == null) {
			return ResponseEntity.badRequest().body("Unidade não informada");
		}

		if (estoque.getQuantidade() < 0) {
			return ResponseEntity.badRequest().body("O estoque não pode ficar negativo");
		}

		Produto produto = produtoRepository
				.findById(estoque.getProduto().getId())
				.orElse(null);

		if (produto == null) {
			return ResponseEntity.badRequest().body("Produto não encontrado");
		}

		UnidadeFranqueada unidade = unidadeFranqueadaRepository
				.findById(estoque.getUnidade().getId())
				.orElse(null);

		if (unidade == null) {
			return ResponseEntity.badRequest().body("Unidade não encontrada");
		}

		estoque.setProduto(produto);
		estoque.setUnidade(unidade);

		return ResponseEntity.ok(estoqueRepository.save(estoque));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Estoque estoque) {

		Estoque estoqueExistente = estoqueRepository.findById(id).orElse(null);

		if (estoqueExistente == null) {
			return ResponseEntity.notFound().build();
		}

		if (estoque.getQuantidade() < 0) {
			return ResponseEntity.badRequest().body("O estoque não pode ficar negativo");
		}

		estoque.setId(id);

		return ResponseEntity.ok(estoqueRepository.save(estoque));
	}

	@PutMapping("/{id}/entrada/{quantidade}")
	public ResponseEntity<?> entrada(@PathVariable Long id, @PathVariable Integer quantidade) {

		Estoque estoque = estoqueRepository.findById(id).orElse(null);

		if (estoque == null) {
			return ResponseEntity.notFound().build();
		}

		if (quantidade <= 0) {
			return ResponseEntity.badRequest().body("A quantidade deve ser maior que zero");
		}

		estoque.setQuantidade(estoque.getQuantidade() + quantidade);

		return ResponseEntity.ok(estoqueRepository.save(estoque));
	}

	@PutMapping("/{id}/saida/{quantidade}")
	public ResponseEntity<?> saida(@PathVariable Long id, @PathVariable Integer quantidade) {

		Estoque estoque = estoqueRepository.findById(id).orElse(null);

		if (estoque == null) {
			return ResponseEntity.notFound().build();
		}

		if (quantidade <= 0) {
			return ResponseEntity.badRequest().body("A quantidade deve ser maior que zero");
		}

		if (estoque.getQuantidade() - quantidade < 0) {
			return ResponseEntity.badRequest().body("Estoque insuficiente");
		}

		estoque.setQuantidade(estoque.getQuantidade() - quantidade);

		return ResponseEntity.ok(estoqueRepository.save(estoque));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {

		if (!estoqueRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		estoqueRepository.deleteById(id);

		return ResponseEntity.ok().build();
	}
}