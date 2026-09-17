package com.franquias.gestao.controller;

import java.time.LocalDateTime;
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

import com.franquias.gestao.model.Estoque;
import com.franquias.gestao.model.MovimentacaoEstoque;
import com.franquias.gestao.model.Produto;
import com.franquias.gestao.model.UnidadeFranqueada;
import com.franquias.gestao.repository.EstoqueRepository;
import com.franquias.gestao.repository.MovimentacaoEstoqueRepository;
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

	@Autowired
	private MovimentacaoEstoqueRepository movimentacaoRepository;

	// Lista todos os estoques
	@GetMapping
	public List<Estoque> listar() {
		return estoqueRepository.findAll();
	}

	// Busca o estoque pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Estoque estoque = estoqueRepository.findById(id).orElse(null);

		if (estoque == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estoque não encontrado");
		}

		return ResponseEntity.ok(estoque);
	}

	// Lista os estoques abaixo do mínimo
	@GetMapping("/abaixo-minimo")
	public List<Estoque> listarAbaixoDoMinimo() {
		return estoqueRepository.buscarEstoqueAbaixoDoMinimo();
	}

	// Cadastra um novo estoque
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Estoque estoque) {

		// Verifica se o produto foi informado
		if (estoque.getProduto() == null || estoque.getProduto().getId() == null) {

			return ResponseEntity.badRequest() .body("Produto não informado");
		}

		// Verifica se a unidade foi informada
		if (estoque.getUnidade() == null || estoque.getUnidade().getId() == null) {

			return ResponseEntity.badRequest().body("Unidade não informada");
		}

		// Não permite quantidade negativa
		if (estoque.getQuantidade() == null || estoque.getQuantidade() < 0) {
			return ResponseEntity.badRequest().body("O estoque não pode ficar negativo");
		}

		// Busca o produto
		Produto produto = produtoRepository.findById(estoque.getProduto().getId()).orElse(null);

		if (produto == null) {
			return ResponseEntity.badRequest().body("Produto não encontrado");
		}

		// Busca a unidade
		UnidadeFranqueada unidade = unidadeFranqueadaRepository.findById(estoque.getUnidade().getId()).orElse(null);

		if (unidade == null) {
			return ResponseEntity.badRequest().body("Unidade não encontrada");
		}

		estoque.setProduto(produto);
		estoque.setUnidade(unidade);

		// Salva o estoque
		Estoque estoqueSalvo = estoqueRepository.save(estoque);

		return ResponseEntity.status(HttpStatus.CREATED).body("Estoque cadastrado com sucesso. ID: "
				+ estoqueSalvo.getId());
	}

	// Atualiza o estoque
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody Estoque estoque) {

		Estoque estoqueExistente =
				estoqueRepository.findById(id).orElse(null);

		if (estoqueExistente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estoque não encontrado");
		}

		// Não permite quantidade negativa
		if (estoque.getQuantidade() == null || estoque.getQuantidade() < 0) {

			return ResponseEntity.badRequest().body("O estoque não pode ficar negativo");
		}

		estoque.setId(id);
		estoqueRepository.save(estoque);
		return ResponseEntity.ok("Estoque atualizado com sucesso");
	}

	// Registra entrada de estoque
	@PutMapping("/{id}/entrada/{quantidade}")
	public ResponseEntity<?> entrada(@PathVariable Long id, @PathVariable Integer quantidade) {

		Estoque estoque = estoqueRepository.findById(id).orElse(null);

		if (estoque == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estoque não encontrado");
		}

		if (quantidade == null || quantidade <= 0) {
			return ResponseEntity.badRequest().body("A quantidade deve ser maior que zero");
		}

		// Adiciona a quantidade no estoque
		estoque.setQuantidade(estoque.getQuantidade() + quantidade);
		estoqueRepository.save(estoque);

		// Registra a movimentação
		MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
		movimentacao.setTipo("ENTRADA");
		movimentacao.setQuantidade(quantidade);
		movimentacao.setDataMovimentacao(LocalDateTime.now());
		movimentacao.setEstoque(estoque);

		movimentacaoRepository.save(movimentacao);

		return ResponseEntity.ok("Entrada de estoque realizada com sucesso");
	}

	// Registra saída de estoque
	@PutMapping("/{id}/saida/{quantidade}")
	public ResponseEntity<?> saida(@PathVariable Long id, @PathVariable Integer quantidade) {
		Estoque estoque = estoqueRepository.findById(id).orElse(null);
		if (estoque == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estoque não encontrado");
		}

		if (quantidade == null || quantidade <= 0) {
			return ResponseEntity.badRequest().body("A quantidade deve ser maior que zero");
		}

		// Verifica se existe quantidade suficiente
		if (estoque.getQuantidade() - quantidade < 0) {
			return ResponseEntity.badRequest().body("Estoque insuficiente");
		}

		// Retira a quantidade do estoque
		estoque.setQuantidade(estoque.getQuantidade() - quantidade);
		estoqueRepository.save(estoque);

		// Registra a movimentação
		MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
		movimentacao.setTipo("SAIDA");
		movimentacao.setQuantidade(quantidade);
		movimentacao.setDataMovimentacao(LocalDateTime.now());
		movimentacao.setEstoque(estoque);

		movimentacaoRepository.save(movimentacao);

		return ResponseEntity.ok("Saída de estoque realizada com sucesso");
	}

	// Exclui o estoque
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {

		if (!estoqueRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estoque não encontrado");
		}

		estoqueRepository.deleteById(id);
		return ResponseEntity.ok("Estoque excluído com sucesso");
	}
}