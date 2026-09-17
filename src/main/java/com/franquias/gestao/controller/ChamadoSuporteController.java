package com.franquias.gestao.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RestController;

import com.franquias.gestao.model.ChamadoSuporte;
import com.franquias.gestao.model.UnidadeFranqueada;
import com.franquias.gestao.repository.ChamadoSuporteRepository;
import com.franquias.gestao.repository.UnidadeFranqueadaRepository;

@RestController
@RequestMapping("/chamados")
public class ChamadoSuporteController {

	@Autowired
	private ChamadoSuporteRepository chamadoRepository;

	@Autowired
	private UnidadeFranqueadaRepository unidadeRepository;

	@GetMapping
	public List<ChamadoSuporte> listar() {

		// Lista todos os chamados
		return chamadoRepository.findAll();
	}
	
	 	// Busca o chamado pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		ChamadoSuporte chamado = chamadoRepository.findById(id).orElse(null);

		if (chamado == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chamado não encontrado");
		}

		return ResponseEntity.ok(chamado);
	}
	
	 	// Busca chamados pelo status
	@GetMapping("/status/{status}")
	public List<ChamadoSuporte> buscarPorStatus(@PathVariable String status) {
		return chamadoRepository.findByStatus(status);
	}
	
	 	// Busca chamados pela prioridade
	@GetMapping("/prioridade/{prioridade}")
	public List<ChamadoSuporte> buscarPorPrioridade(@PathVariable String prioridade) {
		return chamadoRepository.findByPrioridade(prioridade);
	}

		// Busca chamados pela unidade
	@GetMapping("/unidade/{unidadeId}")
	public List<ChamadoSuporte> buscarPorUnidade(@PathVariable Long unidadeId) {
		return chamadoRepository.findByUnidadeId(unidadeId);
	}
	
	 	// Busca por status e prioridade
	@GetMapping("/status/{status}/prioridade/{prioridade}")
	public List<ChamadoSuporte> buscarPorStatusEPrioridade(@PathVariable String status, @PathVariable String prioridade) {
		return chamadoRepository.findByStatusAndPrioridade(status, prioridade);
	}
	
		// Verifica se a unidade foi informada
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody ChamadoSuporte chamado) {
		if (chamado.getUnidade() == null || chamado.getUnidade().getId() == null) {
			return ResponseEntity.badRequest().body("Unidade não informada");
		}

		// Verifica os campos obrigatórios
		if (chamado.getCategoria() == null || chamado.getCategoria().isBlank()) {
			return ResponseEntity.badRequest().body("Categoria não informada");
		}

		if (chamado.getPrioridade() == null || chamado.getPrioridade().isBlank()) {
			return ResponseEntity.badRequest().body("Prioridade não informada");
		}

		if (chamado.getDescricao() == null || chamado.getDescricao().isBlank()) {
			return ResponseEntity.badRequest().body("Descrição não informada");
		}

		// Busca a unidade
		UnidadeFranqueada unidade = unidadeRepository.findById(chamado.getUnidade().getId()).orElse(null);

		if (unidade == null) {
			return ResponseEntity.badRequest().body("Unidade não encontrada");
		}

		// Prepara o chamado
		chamado.setUnidade(unidade);
		chamado.setStatus("ABERTO");
		chamado.setDataAbertura(LocalDateTime.now());
		chamado.setDataFechamento(null);
		ChamadoSuporte chamadoSalvo = chamadoRepository.save(chamado);
		return ResponseEntity.status(HttpStatus.CREATED).body("Chamado aberto com sucesso. ID: " + chamadoSalvo.getId());
	}

	 	//Atualiza pelo Id
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody ChamadoSuporte dados) {

		// Busca o chamado
		ChamadoSuporte chamado = chamadoRepository.findById(id).orElse(null);
		if (chamado == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chamado não encontrado");
		}

		// Não permite alterar chamado fechado
		if ("FECHADO".equalsIgnoreCase(chamado.getStatus())) {
			return ResponseEntity.badRequest().body("Chamado fechado não pode ser alterado");
		}

		chamado.setCategoria(dados.getCategoria());
		chamado.setPrioridade(dados.getPrioridade());
		chamado.setDescricao(dados.getDescricao());
		chamadoRepository.save(chamado);
		return ResponseEntity.ok("Chamado atualizado com sucesso");
	}

	  	//Busca o chamado pelo Id e seu status
	@PutMapping("/{id}/andamento")
	public ResponseEntity<?> colocarEmAndamento(@PathVariable Long id) {

		// Busca o chamado
		ChamadoSuporte chamado = chamadoRepository.findById(id).orElse(null);
		if (chamado == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chamado não encontrado");
		}

		if ("FECHADO".equalsIgnoreCase(chamado.getStatus())) {
			return ResponseEntity.badRequest().body("Chamado já está fechado");
		}

		// Altera o status
		chamado.setStatus("EM_ANDAMENTO");
		chamadoRepository.save(chamado);
		return ResponseEntity.ok("Chamado colocado em andamento com sucesso");
	}

	@PutMapping("/{id}/fechar")
	public ResponseEntity<?> fechar(@PathVariable Long id) {

		// Busca o chamado
		ChamadoSuporte chamado = chamadoRepository.findById(id).orElse(null);

		if (chamado == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chamado não encontrado");
		}

		if ("FECHADO".equalsIgnoreCase(chamado.getStatus())) {
			return ResponseEntity.badRequest().body("Chamado já está fechado");
		}

		// Fecha o chamado
		chamado.setStatus("FECHADO");
		chamado.setDataFechamento(LocalDateTime.now());
		chamadoRepository.save(chamado);
		return ResponseEntity.ok("Chamado fechado com sucesso");
	}
}