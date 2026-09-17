package com.franquias.gestao.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.franquias.gestao.model.Royalty;
import com.franquias.gestao.model.UnidadeFranqueada;
import com.franquias.gestao.model.Venda;
import com.franquias.gestao.repository.RoyaltyRepository;
import com.franquias.gestao.repository.UnidadeFranqueadaRepository;
import com.franquias.gestao.repository.VendaRepository;

@RestController
@RequestMapping("/royalties")
public class RoyaltyController {

	@Autowired
	private RoyaltyRepository royaltyRepository;

	@Autowired
	private UnidadeFranqueadaRepository unidadeRepository;

	@Autowired
	private VendaRepository vendaRepository;

		// Lista todos os royalties
	@GetMapping
	public List<Royalty> listar() {
		return royaltyRepository.findAll();
	}

		// Busca o royalty pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Royalty royalty = royaltyRepository.findById(id).orElse(null);

		if (royalty == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Royalty não encontrado");
		}

		return ResponseEntity.ok(royalty);
	}

		// Busca royalties pela unidade
	@GetMapping("/unidade/{unidadeId}")
	public List<Royalty> buscarPorUnidade(@PathVariable Long unidadeId) {
		return royaltyRepository.findByUnidadeId(unidadeId);
	}

		// Busca royalties pelo status
	@GetMapping("/status/{status}")
	public List<Royalty> buscarPorStatus(@PathVariable String status) {
		return royaltyRepository.findByStatus(status);
	}

		// Busca royalties da unidade pelo período
	@GetMapping("/unidade/{unidadeId}/periodo")
	public List<Royalty> buscarPorUnidadeEPeriodo(@PathVariable Long unidadeId,
			@RequestParam String inicio,@RequestParam String fim) {

		LocalDate dataInicio = LocalDate.parse(inicio);
		LocalDate dataFim = LocalDate.parse(fim);

		List<Royalty> royaltiesUnidade = royaltyRepository.findByUnidadeId(unidadeId);
		List<Royalty> resultado = new ArrayList<>();

		for (Royalty royalty : royaltiesUnidade) {
			if (!royalty.getDataInicio().isBefore(dataInicio)&& !royalty.getDataFim().isAfter(dataFim)) {
					resultado.add(royalty);
			}
		}

		return resultado;
	}

		// Calcula o total dos royalties
	@GetMapping("/total")
	public ResponseEntity<?> calcularTotalRoyalties() {
		List<Royalty> royalties = royaltyRepository.findAll();

		double total = 0.0;

		for (Royalty royalty : royalties) {
			if (royalty.getValorDevido() != null) {total += royalty.getValorDevido();
			}
		}

		return ResponseEntity.ok(total);
	}

		// Verifica se a unidade foi informada
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Royalty royalty) {
		if (royalty.getUnidade() == null || royalty.getUnidade().getId() == null) {
			return ResponseEntity.badRequest().body("Unidade não informada");
		}

		if (royalty.getPercentual() == null || royalty.getPercentual() <= 0) {
			return ResponseEntity.badRequest().body("Percentual inválido");
		}

		if (royalty.getDataInicio() == null || royalty.getDataFim() == null) {
			return ResponseEntity.badRequest().body("Período não informado");
		}

		if (royalty.getDataFim().isBefore(royalty.getDataInicio())) {
			return ResponseEntity.badRequest().body("Período inválido");
		}

		// Busca a unidade
		UnidadeFranqueada unidade = unidadeRepository.findById(royalty.getUnidade().getId()).orElse(null);

		if (unidade == null) {
			return ResponseEntity.badRequest().body("Unidade não encontrada");
		}

		LocalDateTime inicio = royalty.getDataInicio().atStartOfDay();
		LocalDateTime fim = royalty.getDataFim().atTime(23, 59, 59);

		// Busca as vendas do período
		List<Venda> vendas = vendaRepository.findByUnidadeIdAndDataVendaBetween(unidade.getId(),inicio,fim);
		double faturamento = 0.0;

		// Calcula o faturamento
		for (Venda venda : vendas) {
			if (venda.getValorTotal() != null) {
				faturamento += venda.getValorTotal();
			}
		}

		// Calcula o valor do royalty
		double valorDevido = faturamento * royalty.getPercentual() / 100;

		royalty.setUnidade(unidade);
		royalty.setFaturamento(faturamento);
		royalty.setValorDevido(valorDevido);

		if (royalty.getValorPago() == null) {
			royalty.setValorPago(0.0);
		}

		if (royalty.getValorPago() >= valorDevido) {
			royalty.setStatus("PAGO");
		} else {
			royalty.setStatus("PENDENTE");
		}

		Royalty royaltySalvo = royaltyRepository.save(royalty);
		return ResponseEntity.status(HttpStatus.CREATED).body("Royalty calculado e cadastrado com sucesso. ID: "
				+ royaltySalvo.getId());
	}

		// Registra o pagamento
	@PutMapping("/{id}/pagar")
	public ResponseEntity<?> registrarPagamento(@PathVariable Long id,@RequestBody Royalty dados) {
		Royalty royalty = royaltyRepository.findById(id).orElse(null);
		if (royalty == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Royalty não encontrado");
		}

		if (dados.getValorPago() == null || dados.getValorPago() < 0) {
			return ResponseEntity.badRequest().body("Valor pago inválido");
		}

		royalty.setValorPago(dados.getValorPago());

		if (royalty.getValorPago() >= royalty.getValorDevido()) {
			royalty.setStatus("PAGO");
		} else {
			royalty.setStatus("PENDENTE");
		}

		royaltyRepository.save(royalty);
		return ResponseEntity.ok("Pagamento do royalty registrado com sucesso");
	}
}