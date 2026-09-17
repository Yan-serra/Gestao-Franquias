package com.franquias.gestao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franquias.gestao.model.MovimentacaoEstoque;
import com.franquias.gestao.repository.MovimentacaoEstoqueRepository;

@RestController
@RequestMapping("/movimentacoes-estoque")
public class MovimentacaoEstoqueController {

	@Autowired
	private MovimentacaoEstoqueRepository movimentacaoRepository;

		// Lista todas as movimentações
	@GetMapping
	public List<MovimentacaoEstoque> listar() {
		return movimentacaoRepository.findAll();
	}

		// Busca a movimentação pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		MovimentacaoEstoque movimentacao =movimentacaoRepository.findById(id).orElse(null);

		if (movimentacao == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movimentação não encontrada");
		}

		return ResponseEntity.ok(movimentacao);
	}

		// Busca as movimentações de um estoque
	@GetMapping("/estoque/{estoqueId}")
	public List<MovimentacaoEstoque> buscarPorEstoque(@PathVariable Long estoqueId) {
		return movimentacaoRepository.findByEstoqueId(estoqueId);
	}

		// Busca as movimentações pelo tipo
	@GetMapping("/tipo/{tipo}")
	public List<MovimentacaoEstoque> buscarPorTipo(
			@PathVariable String tipo) {
		return movimentacaoRepository.findByTipo(tipo);
	}
}