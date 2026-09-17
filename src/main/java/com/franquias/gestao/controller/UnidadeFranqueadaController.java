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
		
	@PostMapping("/{franqueadoraId}")
	public ResponseEntity<Object> cadastrar(
			@PathVariable Long franqueadoraId,
			@RequestBody UnidadeFranqueada unidade) {
		
		if (unidadeRepository.findByCnpj(unidade.getCnpj()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Já existe uma unidade com esse CNPJ!");
		}
		
		Franqueadora franqueadora = franqueadoraRepository
				.findById(franqueadoraId)
				.orElse(null);
		
		if (franqueadora == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Franqueadora não encontrada.");
		}
		
		unidade.setFranqueadora(franqueadora);
		unidade.setAtiva(true);
		
		UnidadeFranqueada unidadeSalva = unidadeRepository.save(unidade);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(unidadeSalva);
	}
	
	@GetMapping
	public List<UnidadeFranqueada> listar() {
		return unidadeRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		
		UnidadeFranqueada unidade = unidadeRepository.findById(id).orElse(null);
		
		if (unidade == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(unidade);
	}
	
	@GetMapping("/nome/{nome}")
	public List<UnidadeFranqueada> buscarPorNome(@PathVariable String nome) {
		
		return unidadeRepository.findByNomeContainingIgnoreCase(nome);
	}
	
	@GetMapping("/cidade/{cidade}")
	public List<UnidadeFranqueada> buscarPorCidade(@PathVariable String cidade) {
		
		return unidadeRepository.findByCidadeContainingIgnoreCase(cidade);
	}
	
	@GetMapping("/cnpj")
	public ResponseEntity<?> buscarPorCnpj(@RequestParam String cnpj) {
		
		UnidadeFranqueada unidade = unidadeRepository.findByCnpj(cnpj).orElse(null);
		
		if (unidade == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(unidade);
	}
	
	@GetMapping("/status/{ativa}")
	public List<UnidadeFranqueada> buscarPorStatus(@PathVariable Boolean ativa) {
		
		return unidadeRepository.findByAtiva(ativa);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(
			@PathVariable Long id,
			@RequestBody UnidadeFranqueada dados) {
		
		UnidadeFranqueada unidade = unidadeRepository.findById(id).orElse(null);
		
		if (unidade == null) {
			return ResponseEntity.notFound().build();
		}
		
		unidade.setNome(dados.getNome());
		unidade.setCnpj(dados.getCnpj());
		unidade.setCidade(dados.getCidade());
		unidade.setEstado(dados.getEstado());
		unidade.setEndereco(dados.getEndereco());
		unidade.setTelefone(dados.getTelefone());
		unidade.setDataInicio(dados.getDataInicio());
		unidade.setAtiva(dados.isAtiva());
		
		return ResponseEntity.ok(unidadeRepository.save(unidade));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> inativar(@PathVariable Long id) {
		
		UnidadeFranqueada unidade = unidadeRepository.findById(id).orElse(null);
		
		if (unidade == null) {
			return ResponseEntity.notFound().build();
		}
		
		unidade.setAtiva(false);
		
		return ResponseEntity.ok(unidadeRepository.save(unidade));
	}
}