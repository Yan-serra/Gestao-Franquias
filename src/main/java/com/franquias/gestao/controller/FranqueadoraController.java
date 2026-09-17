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
import com.franquias.gestao.repository.FranqueadoraRepository;

@RestController
@RequestMapping("/franqueadoras")
public class FranqueadoraController {

	@Autowired
	private FranqueadoraRepository franqueadoraRepository;

	@GetMapping
	public List<Franqueadora> listar() {
		return franqueadoraRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

		Franqueadora franqueadora = franqueadoraRepository.findById(id).orElse(null);

		if (franqueadora == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(franqueadora);
	}

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Franqueadora franqueadora) {

		Franqueadora franqueadoraSalva = franqueadoraRepository.save(franqueadora);

		return ResponseEntity.status(HttpStatus.CREATED).body(franqueadoraSalva);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(
			@PathVariable Long id,
			@RequestBody Franqueadora dados) {

		Franqueadora franqueadora = franqueadoraRepository.findById(id).orElse(null);

		if (franqueadora == null) {
			return ResponseEntity.notFound().build();
		}

		franqueadora.setNome(dados.getNome());
		franqueadora.setCnpj(dados.getCnpj());

		return ResponseEntity.ok(franqueadoraRepository.save(franqueadora));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {

		if (!franqueadoraRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		franqueadoraRepository.deleteById(id);

		return ResponseEntity.ok().build();
	}
}