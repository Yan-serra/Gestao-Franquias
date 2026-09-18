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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.franquias.gestao.model.Franqueadora;
import com.franquias.gestao.model.UnidadeFranqueada;
import com.franquias.gestao.repository.FranqueadoraRepository;
import com.franquias.gestao.repository.UnidadeFranqueadaRepository;

@RestController
@RequestMapping("/unidades")
public class UnidadeFranqueadaController {

	@Autowired
	private UnidadeFranqueadaRepository unidadeRepository;

	@Autowired
	private FranqueadoraRepository franqueadoraRepository;

		// Cadastra uma nova unidade
	@PostMapping("/{franqueadoraId}")
	public ResponseEntity<?> cadastrar(@PathVariable Long franqueadoraId,@RequestBody UnidadeFranqueada unidade) {

		// Verifica se o CNPJ já existe
		if (unidadeRepository.findByCnpj(unidade.getCnpj()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe uma unidade com esse CNPJ");
		}

		// Busca a franqueadora
		Franqueadora franqueadora = franqueadoraRepository.findById(franqueadoraId).orElse(null);
		if (franqueadora == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Franqueadora não encontrada");
		}

		unidade.setFranqueadora(franqueadora);
		unidade.setAtiva(true);
		UnidadeFranqueada unidadeSalva = unidadeRepository.save(unidade);
		return ResponseEntity.status(HttpStatus.CREATED).body("Unidade cadastrada com sucesso. ID: "
				+ unidadeSalva.getId());
	}

		// Lista todas as unidades
	@GetMapping
	public List<UnidadeFranqueada> listar() {
		return unidadeRepository.findAll();
	}

		// Busca a unidade pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		UnidadeFranqueada unidade = unidadeRepository.findById(id).orElse(null);
		if (unidade == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unidade não encontrada");
		}

		return ResponseEntity.ok(unidade);
	}

		// Busca unidades pelo nome
	@GetMapping("/nome/{nome}")
	public List<UnidadeFranqueada> buscarPorNome(@PathVariable String nome) {
		return unidadeRepository.findByNomeContainingIgnoreCase(nome);
	}

		// Busca unidades pela cidade
	@GetMapping("/cidade/{cidade}")
	public List<UnidadeFranqueada> buscarPorCidade(@PathVariable String cidade) {
		return unidadeRepository.findByCidadeContainingIgnoreCase(cidade);
	}

		// Busca a unidade pelo CNPJ
	@GetMapping("/cnpj")
	public ResponseEntity<?> buscarPorCnpj(@RequestParam String cnpj) {
		UnidadeFranqueada unidade = unidadeRepository.findByCnpj(cnpj).orElse(null);

		if (unidade == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unidade não encontrada");
		}

		return ResponseEntity.ok(unidade);
	}

		// Busca unidades pelo status
	@GetMapping("/status/{ativa}")
	public List<UnidadeFranqueada> buscarPorStatus(@PathVariable Boolean ativa) {
		return unidadeRepository.findByAtiva(ativa);
	}

		// Atualiza a unidade
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody UnidadeFranqueada dados) {
		UnidadeFranqueada unidade = unidadeRepository.findById(id).orElse(null);

		if (unidade == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unidade não encontrada");
		}

		// Verifica se o CNPJ pertence a outra unidade
		UnidadeFranqueada unidadeComMesmoCnpj = unidadeRepository.findByCnpj(dados.getCnpj()).orElse(null);

		if (unidadeComMesmoCnpj != null && !unidadeComMesmoCnpj.getId().equals(id)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe uma unidade com esse CNPJ");
		}

		unidade.setNome(dados.getNome());
		unidade.setCnpj(dados.getCnpj());
		unidade.setCidade(dados.getCidade());
		unidade.setEstado(dados.getEstado());
		unidade.setEndereco(dados.getEndereco());
		unidade.setTelefone(dados.getTelefone());
		unidade.setDataInicio(dados.getDataInicio());
		unidade.setAtiva(dados.isAtiva());

		unidadeRepository.save(unidade);
		return ResponseEntity.ok("Unidade atualizada com sucesso");
	}

		// Inativa a unidade
	@DeleteMapping("/{id}")
	public ResponseEntity<?> inativar(@PathVariable Long id) {
		UnidadeFranqueada unidade = unidadeRepository.findById(id).orElse(null);

		if (unidade == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unidade não encontrada");
		}

		unidade.setAtiva(false);
		unidadeRepository.save(unidade);
		return ResponseEntity.ok("Unidade inativada com sucesso");
	}
}