package com.franquias.gestao.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.franquias.gestao.model.Estoque;
import com.franquias.gestao.model.ItemVenda;
import com.franquias.gestao.model.Produto;
import com.franquias.gestao.model.UnidadeFranqueada;
import com.franquias.gestao.model.Venda;
import com.franquias.gestao.repository.EstoqueRepository;
import com.franquias.gestao.repository.ProdutoRepository;
import com.franquias.gestao.repository.UnidadeFranqueadaRepository;
import com.franquias.gestao.repository.VendaRepository;

@RestController
@RequestMapping("/vendas")
public class VendaController {

	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	private UnidadeFranqueadaRepository unidadeFranqueadaRepository;

	@GetMapping
	public List<Venda> listar() {
		return vendaRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

		Venda venda = vendaRepository.findById(id).orElse(null);

		if (venda == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(venda);
	}

	@GetMapping("/unidade/{unidadeId}")
	public List<Venda> buscarPorUnidade(@PathVariable Long unidadeId) {

		return vendaRepository.findByUnidadeId(unidadeId);
	}
	
	@GetMapping("/unidade/{unidadeId}/faturamento")
	public ResponseEntity<?> calcularFaturamento(
			@PathVariable Long unidadeId,
			@RequestParam String inicio,
			@RequestParam String fim) {

		LocalDateTime dataInicio = LocalDateTime.parse(inicio);
		LocalDateTime dataFim = LocalDateTime.parse(fim);

		List<Venda> vendas = vendaRepository.findByUnidadeIdAndDataVendaBetween(
				unidadeId,
				dataInicio,
				dataFim);

		double faturamento = 0.0;

		for (Venda venda : vendas) {
			faturamento += venda.getValorTotal();
		}

		return ResponseEntity.ok(faturamento);
	}

	@GetMapping("/periodo")
	public List<Venda> buscarPorPeriodo(
			@RequestParam String inicio,
			@RequestParam String fim) {

		LocalDateTime dataInicio = LocalDateTime.parse(inicio);
		LocalDateTime dataFim = LocalDateTime.parse(fim);

		return vendaRepository.findByDataVendaBetween(dataInicio, dataFim);
	}

	@GetMapping("/unidade/{unidadeId}/periodo")
	public List<Venda> buscarPorUnidadeEPeriodo(
			@PathVariable Long unidadeId,
			@RequestParam String inicio,
			@RequestParam String fim) {

		LocalDateTime dataInicio = LocalDateTime.parse(inicio);
		LocalDateTime dataFim = LocalDateTime.parse(fim);

		return vendaRepository.findByUnidadeIdAndDataVendaBetween(
				unidadeId,
				dataInicio,
				dataFim);
	}

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Venda venda) {

		if (venda.getItens() == null || venda.getItens().isEmpty()) {
			return ResponseEntity.badRequest()
					.body("A venda deve possuir pelo menos um item");
		}

		if (venda.getUnidade() == null || venda.getUnidade().getId() == null) {
			return ResponseEntity.badRequest()
					.body("Unidade não informada");
		}

		UnidadeFranqueada unidade = unidadeFranqueadaRepository
				.findById(venda.getUnidade().getId())
				.orElse(null);

		if (unidade == null) {
			return ResponseEntity.badRequest()
					.body("Unidade não encontrada");
		}

		if (!unidade.isAtiva()) {
			return ResponseEntity.badRequest()
					.body("Não é possível realizar venda em uma unidade inativa");
		}

		venda.setUnidade(unidade);

		double valorTotal = 0.0;

		List<Estoque> estoquesAlterados = new ArrayList<>();

		for (ItemVenda item : venda.getItens()) {

			if (item.getProduto() == null || item.getProduto().getId() == null) {
				return ResponseEntity.badRequest()
						.body("Produto não informado");
			}

			if (item.getQuantidade() == null || item.getQuantidade() <= 0) {
				return ResponseEntity.badRequest()
						.body("A quantidade do produto deve ser maior que zero");
			}

			Produto produto = produtoRepository
					.findById(item.getProduto().getId())
					.orElse(null);

			if (produto == null) {
				return ResponseEntity.badRequest()
						.body("Produto não encontrado");
			}

			Estoque estoque = estoqueRepository.findAll().stream()
					.filter(e -> e.getProduto().getId().equals(produto.getId()))
					.filter(e -> e.getUnidade().getId().equals(unidade.getId()))
					.findFirst()
					.orElse(null);

			if (estoque == null) {
				return ResponseEntity.badRequest()
						.body("Estoque não encontrado para este produto e unidade");
			}

			if (estoque.getQuantidade() < item.getQuantidade()) {
				return ResponseEntity.badRequest()
						.body("Estoque insuficiente para o produto: " + produto.getNome());
			}

			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPrecoBase());

			double subtotal = item.getQuantidade() * produto.getPrecoBase();

			item.setSubtotal(subtotal);
			valorTotal += subtotal;

			estoque.setQuantidade(
					estoque.getQuantidade() - item.getQuantidade());

			estoquesAlterados.add(estoque);
		}

		for (Estoque estoque : estoquesAlterados) {
			estoqueRepository.save(estoque);
		}

		venda.setValorTotal(valorTotal);
		venda.setDataVenda(LocalDateTime.now());

		return ResponseEntity.ok(vendaRepository.save(venda));
	}
}