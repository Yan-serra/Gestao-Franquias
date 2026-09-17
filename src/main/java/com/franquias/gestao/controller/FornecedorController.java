package com.franquias.gestao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franquias.gestao.model.Fornecedor;
import com.franquias.gestao.repository.FornecedorRepository;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@GetMapping
	public List<Fornecedor> listar() {
		return fornecedorRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

		Fornecedor fornecedor = fornecedorRepository.findById(id).orElse(null);

		if (fornecedor == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(fornecedor);
	}

	@GetMapping("/nome/{nome}")
	public List<Fornecedor> buscarPorNome(@PathVariable String nome) {

		return fornecedorRepository.findByNomeContainingIgnoreCase(nome);
	}

	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<?> buscarPorCnpj(@PathVariable String cnpj) {

		Fornecedor fornecedor = fornecedorRepository.findByCnpj(cnpj).orElse(null);

		if (fornecedor == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(fornecedor);
	}

	@GetMapping("/status/{ativo}")
	public List<Fornecedor> buscarPorStatus(@PathVariable Boolean ativo) {

		return fornecedorRepository.findByAtivo(ativo);
	}

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Fornecedor fornecedor) {

		return ResponseEntity.ok(fornecedorRepository.save(fornecedor));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Fornecedor fornecedor) {

		Fornecedor fornecedorExistente = fornecedorRepository.findById(id).orElse(null);

		if (fornecedorExistente == null) {
			return ResponseEntity.notFound().build();
		}

		fornecedor.setId(id);

		return ResponseEntity.ok(fornecedorRepository.save(fornecedor));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {

		if (!fornecedorRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		fornecedorRepository.deleteById(id);

		return ResponseEntity.ok().build();
	}
}