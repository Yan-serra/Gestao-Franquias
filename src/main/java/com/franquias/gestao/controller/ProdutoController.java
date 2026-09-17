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
import com.franquias.gestao.model.Fornecedor;
import com.franquias.gestao.model.Produto;
import com.franquias.gestao.repository.CategoriaRepository;
import com.franquias.gestao.repository.FornecedorRepository;
import com.franquias.gestao.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

		// Lista todos os produtos
	@GetMapping
	public List<Produto> listar() {
		return produtoRepository.findAll();
	}

		// Busca o produto pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Produto produto = produtoRepository.findById(id).orElse(null);
		if (produto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
		}

		return ResponseEntity.ok(produto);
	}

		// Busca produtos pelo nome
	@GetMapping("/nome/{nome}")
	public List<Produto> buscarPorNome(@PathVariable String nome) {
		return produtoRepository.findByNomeContainingIgnoreCase(nome);
	}

		// Busca produtos pela categoria
	@GetMapping("/categoria/{categoriaId}")
	public List<Produto> buscarPorCategoria(@PathVariable Long categoriaId) {
		return produtoRepository.findByCategoriaId(categoriaId);
	}

		// Busca produtos pelo status
	@GetMapping("/status/{ativo}")
	public List<Produto> buscarPorStatus(@PathVariable Boolean ativo) {
		return produtoRepository.findByAtivo(ativo);
	}

		// Busca produtos pelo fornecedor
	@GetMapping("/fornecedor/{fornecedorId}")
	public List<Produto> buscarPorFornecedor(@PathVariable Long fornecedorId) {
		return produtoRepository.findByFornecedorId(fornecedorId);
	}

		// Verifica se a categoria foi informada
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Produto produto) {
		if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
			return ResponseEntity.badRequest().body("Categoria não informada");
		}

		// Busca a categoria
		Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId()).orElse(null);
		if (categoria == null) {
			return ResponseEntity.badRequest().body("Categoria não encontrada");
		}

		// Verifica se o fornecedor foi informado
		if (produto.getFornecedor() == null || produto.getFornecedor().getId() == null) {
			return ResponseEntity.badRequest().body("Fornecedor não informado");
		}

		// Busca o fornecedor
		Fornecedor fornecedor = fornecedorRepository.findById(produto.getFornecedor().getId()).orElse(null);
		if (fornecedor == null) {
			return ResponseEntity.badRequest().body("Fornecedor não encontrado");
		}

		produto.setCategoria(categoria);
		produto.setFornecedor(fornecedor);

		// Salva o produto
		Produto produtoSalvo = produtoRepository.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso. ID: "
				+ produtoSalvo.getId());
	}

		// Busca o produto
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody Produto produto) {
		Produto produtoExistente = produtoRepository.findById(id).orElse(null);
		if (produtoExistente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
		}

		if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
			return ResponseEntity.badRequest().body("Categoria não informada");
		}

		Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId()).orElse(null);

		if (categoria == null) {
			return ResponseEntity.badRequest().body("Categoria não encontrada");
		}

		if (produto.getFornecedor() == null || produto.getFornecedor().getId() == null) {
			return ResponseEntity.badRequest().body("Fornecedor não informado");
		}

		Fornecedor fornecedor = fornecedorRepository.findById(produto.getFornecedor().getId()).orElse(null);

		if (fornecedor == null) {
			return ResponseEntity.badRequest().body("Fornecedor não encontrado");
		}

		produto.setId(id);
		produto.setCategoria(categoria);
		produto.setFornecedor(fornecedor);

		// Atualiza o produto
		produtoRepository.save(produto);
		return ResponseEntity.ok("Produto atualizado com sucesso");
	}

		// Verifica se o produto existe
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		if (!produtoRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
		}

		// Exclui o produto
		produtoRepository.deleteById(id);
		return ResponseEntity.ok("Produto excluído com sucesso");
	}
}