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
		if(unidadeRepository.findByCnpj(unidade.getCnpj()).isPresent()){
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Já existe um unidade com esse CNPJ!");
		}
		
		if(franqueadoraRepository.findById(franqueadoraId).isPresent()) {
			Franqueadora franqueadora = franqueadoraRepository.findById(franqueadoraId).get();
			unidade.setFranqueadora(franqueadora);
			unidade.setAtiva(true);
			UnidadeFranqueada unidadeSalva = unidadeRepository.save(unidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(unidadeSalva);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("Franqueadora não encontrada.");
	}
	
	@GetMapping
	public List<UnidadeFranqueada> listar(){
		return unidadeRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public UnidadeFranqueada buscarporId(@PathVariable Long id) {
		if(unidadeRepository.findById(id).isPresent()) {
			return unidadeRepository.findById(id).get();
		}
		return null;
	}
	
	@PutMapping("/{id}")
	public UnidadeFranqueada atualizar(
			@PathVariable Long id,
			@RequestBody UnidadeFranqueada dados) {
		if(unidadeRepository.findById(id).isPresent()) {
			
			UnidadeFranqueada unidade = unidadeRepository.findById(id).get();
			
			unidade.setNome(dados.getNome());
			unidade.setCnpj(dados.getCnpj());
			unidade.setCidade(dados.getCidade());
			unidade.setEstado(dados.getEstado());
			unidade.setEndereco(dados.getEndereco());
			unidade.setTelefone(dados.getTelefone());
			unidade.setDataInicio(dados.getDataInicio());
			unidade.setAtiva(dados.isAtiva());
			
			return unidadeRepository.save(unidade);
		}
		return null;
	}
	
	@DeleteMapping("/{id}")
	public UnidadeFranqueada inativar(@PathVariable Long id) {
		if(unidadeRepository.findById(id).isPresent()) {
			UnidadeFranqueada unidade = unidadeRepository.findById(id).get();
			unidade.setAtiva(false);
			return unidadeRepository.save(unidade);
		}
		return null;
	}
}