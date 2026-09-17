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

import com.franquias.gestao.model.Responsavel;
import com.franquias.gestao.model.UnidadeFranqueada;
import com.franquias.gestao.repository.ResponsavelRepository;
import com.franquias.gestao.repository.UnidadeFranqueadaRepository;

@RestController
@RequestMapping("/responsaveis")
public class ResponsavelController {

	@Autowired
	private ResponsavelRepository responsavelRepository;

	@Autowired
	private UnidadeFranqueadaRepository unidadeRepository;

		// Lista todos os responsáveis
	@GetMapping
	public List<Responsavel> listar() {
		return responsavelRepository.findAll();
	}

		// Busca o responsável pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Responsavel responsavel = responsavelRepository.findById(id).orElse(null);

		if (responsavel == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Responsável não encontrado");
		}

		return ResponseEntity.ok(responsavel);
	}

		// Busca responsáveis pelo nome
	@GetMapping("/nome/{nome}")
	public List<Responsavel> buscarPorNome(@PathVariable String nome) {
		return responsavelRepository.findByNomeContainingIgnoreCase(nome);
	}

		// Busca responsáveis pela unidade
	@GetMapping("/unidade/{unidadeId}")
	public List<Responsavel> buscarPorUnidade(@PathVariable Long unidadeId) {
		return responsavelRepository.findByUnidadeId(unidadeId);
	}

		// Busca o responsável pelo CPF
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<?> buscarPorCpf(@PathVariable String cpf) {
		Responsavel responsavel = responsavelRepository.findByCpf(cpf).orElse(null);

		if (responsavel == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Responsável não encontrado");
		}

		return ResponseEntity.ok(responsavel);
	}

		// Cadastra um novo responsável
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Responsavel responsavel) {

		// Verifica se o nome foi informado
		if (responsavel.getNome() == null || responsavel.getNome().isBlank()) {
			return ResponseEntity.badRequest().body("Nome do responsável não informado");
		}

		// Verifica se o CPF foi informado
		if (responsavel.getCpf() == null || responsavel.getCpf().isBlank()) {
			return ResponseEntity.badRequest().body("CPF do responsável não informado");
		}

		// Verifica se o CPF já existe
		if (responsavelRepository.findByCpf(responsavel.getCpf()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um responsável com esse CPF");
		}

		// Verifica se a unidade foi informada
		if (responsavel.getUnidade() == null || responsavel.getUnidade().getId() == null) {

			return ResponseEntity.badRequest().body("Unidade não informada");
		}

		// Busca a unidade
		UnidadeFranqueada unidade = unidadeRepository.findById(responsavel.getUnidade().getId()).orElse(null);

		if (unidade == null) {
			return ResponseEntity.badRequest().body("Unidade não encontrada");
		}

		responsavel.setUnidade(unidade);

		// Salva o responsável
		Responsavel responsavelSalvo = responsavelRepository.save(responsavel);

		return ResponseEntity.status(HttpStatus.CREATED).body("Responsável cadastrado com sucesso. ID: "
					+ responsavelSalvo.getId());
	}

		// Atualiza o responsável
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Responsavel dados) {

		// Busca o responsável
		Responsavel responsavel = responsavelRepository.findById(id).orElse(null);
		if (responsavel == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Responsável não encontrado");
		}

		// Verifica se outro responsável já usa o CPF
		if (dados.getCpf() != null) {

			Responsavel responsavelCpf = responsavelRepository.findByCpf(dados.getCpf()).orElse(null);
			if (responsavelCpf != null && !responsavelCpf.getId().equals(id)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um responsável com esse CPF");
			}
		}

		// Verifica se a unidade foi informada
		if (dados.getUnidade() == null || dados.getUnidade().getId() == null) {
			return ResponseEntity.badRequest().body("Unidade não informada");
		}

		// Busca a unidade
		UnidadeFranqueada unidade = unidadeRepository.findById(dados.getUnidade().getId()).orElse(null);
		if (unidade == null) {
			return ResponseEntity.badRequest().body("Unidade não encontrada");
		}

		responsavel.setNome(dados.getNome());
		responsavel.setCpf(dados.getCpf());
		responsavel.setEmail(dados.getEmail());
		responsavel.setTelefone(dados.getTelefone());
		responsavel.setUnidade(unidade);

		// Salva as alterações
		responsavelRepository.save(responsavel);

		return ResponseEntity.ok("Responsável atualizado com sucesso");
	}

		// Exclui o responsável
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {

		// Verifica se o responsável existe
		if (!responsavelRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Responsável não encontrado");
		}

		responsavelRepository.deleteById(id);
		return ResponseEntity.ok("Responsável excluído com sucesso");
	}
}