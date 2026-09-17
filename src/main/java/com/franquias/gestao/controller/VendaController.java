package com.franquias.gestao.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.franquias.gestao.model.MovimentacaoEstoque;
import com.franquias.gestao.model.Produto;
import com.franquias.gestao.model.UnidadeFranqueada;
import com.franquias.gestao.model.Venda;
import com.franquias.gestao.repository.EstoqueRepository;
import com.franquias.gestao.repository.MovimentacaoEstoqueRepository;
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

	@Autowired
	private MovimentacaoEstoqueRepository movimentacaoRepository;

	// Lista todas as vendas
	@GetMapping
	public List<Venda> listar() {
		return vendaRepository.findAll();
	}

	// Busca a venda pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Venda venda = vendaRepository.findById(id).orElse(null);

		if (venda == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada");
		}

		return ResponseEntity.ok(venda);
	}

	// Busca vendas pela unidade
	@GetMapping("/unidade/{unidadeId}")
	public List<Venda> buscarPorUnidade(@PathVariable Long unidadeId) {
		return vendaRepository.findByUnidadeId(unidadeId);
	}

	// Busca vendas pelo período
	@GetMapping("/periodo")
	public List<Venda> buscarPorPeriodo(@RequestParam String inicio, @RequestParam String fim) {

		LocalDateTime dataInicio = LocalDateTime.parse(inicio);
		LocalDateTime dataFim = LocalDateTime.parse(fim);
		return vendaRepository.findByDataVendaBetween(dataInicio, dataFim);
	}

	// Busca vendas da unidade pelo período
	@GetMapping("/unidade/{unidadeId}/periodo")
	public List<Venda> buscarPorUnidadeEPeriodo(@PathVariable Long unidadeId, @RequestParam String inicio, 
			@RequestParam String fim) {

		LocalDateTime dataInicio = LocalDateTime.parse(inicio);
		LocalDateTime dataFim = LocalDateTime.parse(fim);
		return vendaRepository.findByUnidadeIdAndDataVendaBetween(unidadeId,dataInicio, dataFim);
	}

	// Calcula o faturamento da unidade
	@GetMapping("/unidade/{unidadeId}/faturamento")
	public ResponseEntity<?> calcularFaturamento(@PathVariable Long unidadeId, @RequestParam String inicio,
			@RequestParam String fim) {

		LocalDateTime dataInicio = LocalDateTime.parse(inicio);
		LocalDateTime dataFim = LocalDateTime.parse(fim);
		List<Venda> vendas = vendaRepository.findByUnidadeIdAndDataVendaBetween(unidadeId, dataInicio, dataFim);

		double faturamento = 0.0;

		for (Venda venda : vendas) {
			faturamento += venda.getValorTotal();
		}

		return ResponseEntity.ok(faturamento);
	}

	// Faz o ranking das unidades
	@GetMapping("/ranking-unidades")
	public List<String> rankingUnidades() {

		List<Venda> vendas = vendaRepository.findAll();
		List<String> nomesUnidades = new ArrayList<>();
		List<Double> faturamentos = new ArrayList<>();

		for (Venda venda : vendas) {
			String nomeUnidade = venda.getUnidade().getNome();
			int posicao = nomesUnidades.indexOf(nomeUnidade);

			if (posicao == -1) {
				nomesUnidades.add(nomeUnidade);
				faturamentos.add(venda.getValorTotal());

			} else {
				double novoTotal =faturamentos.get(posicao) + venda.getValorTotal();
				faturamentos.set(posicao, novoTotal);
			}
		}

		// Organiza do maior para o menor
		for (int i = 0; i < faturamentos.size(); i++) {

			for (int j = i + 1;
					j < faturamentos.size();
					j++) {

				if (faturamentos.get(j)
						> faturamentos.get(i)) {

					double faturamentoTemp =
							faturamentos.get(i);

					faturamentos.set(
							i,
							faturamentos.get(j));

					faturamentos.set(
							j,
							faturamentoTemp);

					String nomeTemp =
							nomesUnidades.get(i);

					nomesUnidades.set(
							i,
							nomesUnidades.get(j));

					nomesUnidades.set(
							j,
							nomeTemp);
				}
			}
		}

		List<String> ranking = new ArrayList<>();

		for (int i = 0; i < nomesUnidades.size(); i++) {

			ranking.add(
					(i + 1)
					+ "º - "
					+ nomesUnidades.get(i)
					+ " - Faturamento: "
					+ faturamentos.get(i));
		}

		return ranking;
	}

	// Calcula os produtos mais vendidos
	@GetMapping("/produtos-mais-vendidos")
	public List<String> produtosMaisVendidos() {

		List<Venda> vendas = vendaRepository.findAll();
		List<String> nomesProdutos = new ArrayList<>();
		List<Integer> quantidades = new ArrayList<>();

		for (Venda venda : vendas) {

			for (ItemVenda item : venda.getItens()) {

				String nomeProduto = item.getProduto().getNome();

				int posicao = nomesProdutos.indexOf(nomeProduto);

				if (posicao == -1) {

					nomesProdutos.add(nomeProduto);
					quantidades.add( item.getQuantidade());

				} else {
					int novaQuantidade = quantidades.get(posicao) + item.getQuantidade();
					quantidades.set(posicao, novaQuantidade);
				}
			}
		}

		// Organiza do mais vendido para o menos vendido
		for (int i = 0; i < quantidades.size(); i++) {

			for (int j = i + 1;
					j < quantidades.size();
					j++) {

				if (quantidades.get(j)
						> quantidades.get(i)) {

					int quantidadeTemp =
							quantidades.get(i);

					quantidades.set(
							i,
							quantidades.get(j));

					quantidades.set(
							j,
							quantidadeTemp);

					String nomeTemp =
							nomesProdutos.get(i);

					nomesProdutos.set(
							i,
							nomesProdutos.get(j));

					nomesProdutos.set(
							j,
							nomeTemp);
				}
			}
		}

		List<String> resultado = new ArrayList<>();

		for (int i = 0; i < nomesProdutos.size(); i++) {

			resultado.add(
					(i + 1)
					+ "º - "
					+ nomesProdutos.get(i)
					+ " - Quantidade vendida: "
					+ quantidades.get(i));
		}

		return resultado;
	}

	// Cadastra uma nova venda
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Venda venda) {

		// Verifica se existem itens
		if (venda.getItens() == null || venda.getItens().isEmpty()) {

			return ResponseEntity.badRequest().body("A venda deve possuir pelo menos um item");
		}

		// Verifica se a unidade foi informada
		if (venda.getUnidade() == null || venda.getUnidade().getId() == null) {

			return ResponseEntity.badRequest().body("Unidade não informada");
		}

		// Busca a unidade
		UnidadeFranqueada unidade =unidadeFranqueadaRepository.findById(venda.getUnidade().getId()).orElse(null);

		if (unidade == null) {
			return ResponseEntity.badRequest().body("Unidade não encontrada");
		}

		// Verifica se a unidade está ativa
		if (!unidade.isAtiva()) {
			return ResponseEntity.badRequest().body("Não é possível realizar venda em uma unidade inativa");
		}

		venda.setUnidade(unidade);
		double valorTotal = 0.0;

		List<Estoque> estoquesAlterados = new ArrayList<>();
		List<MovimentacaoEstoque> movimentacoes =new ArrayList<>();

		// Verifica os itens da venda
		for (ItemVenda item : venda.getItens()) {

			if (item.getProduto() == null || item.getProduto().getId() == null) {

				return ResponseEntity.badRequest().body("Produto não informado");
			}

			if (item.getQuantidade() == null || item.getQuantidade() <= 0) {

				return ResponseEntity.badRequest().body("A quantidade do produto deve ser maior que zero");
			}

			// Busca o produto
			Produto produto = produtoRepository.findById(item.getProduto().getId()).orElse(null);

			if (produto == null) {
				return ResponseEntity.badRequest().body("Produto não encontrado");
			}

			// Busca o estoque do produto na unidade
			Estoque estoque = estoqueRepository.findAll().stream().filter(e -> e.getProduto().getId().equals(produto
					.getId())).filter(e -> e.getUnidade().getId().equals(unidade.getId())).findFirst().orElse(null);

			if (estoque == null) {
				return ResponseEntity.badRequest().body("Estoque não encontrado para este produto e unidade");
			}

			// Verifica o estoque disponível
			if (estoque.getQuantidade()< item.getQuantidade()) {

				return ResponseEntity.badRequest().body("Estoque insuficiente para o produto: "
								+ produto.getNome());
			}

			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPrecoBase());

			// Calcula o subtotal
			double subtotal = item.getQuantidade()* produto.getPrecoBase();

			item.setSubtotal(subtotal);
			valorTotal += subtotal;

			// Atualiza a quantidade do estoque
			estoque.setQuantidade(estoque.getQuantidade()- item.getQuantidade());
			estoquesAlterados.add(estoque);

			// Prepara a movimentação da venda
			MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();

			movimentacao.setTipo("VENDA");
			movimentacao.setQuantidade(
					item.getQuantidade());
			movimentacao.setDataMovimentacao(
					LocalDateTime.now());
			movimentacao.setEstoque(estoque);
			movimentacoes.add(movimentacao);
		}

		// Atualiza os estoques
		for (Estoque estoque : estoquesAlterados) {
			estoqueRepository.save(estoque);
		}

		venda.setValorTotal(valorTotal);
		venda.setDataVenda(LocalDateTime.now());

		// Salva a venda
		Venda vendaSalva =
				vendaRepository.save(venda);

		// Salva o histórico da movimentação
		for (MovimentacaoEstoque movimentacao: movimentacoes) {

			movimentacaoRepository.save(movimentacao);
		}

		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Venda realizada com sucesso. ID: "+ vendaSalva.getId()+ " - Valor total: "+ vendaSalva
						.getValorTotal());
	}
}