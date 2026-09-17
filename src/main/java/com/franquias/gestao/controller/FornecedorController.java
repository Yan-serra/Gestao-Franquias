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

import com.franquias.gestao.model.Fornecedor;
import com.franquias.gestao.repository.FornecedorRepository;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

	@Autowired
	private FornecedorRepository fornecedorRepository;

		// Lista todos os fornecedores
	@GetMapping
	public List<Fornecedor> listar() {
		return fornecedorRepository.findAll();
	}

		// Busca o fornecedor pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Fornecedor fornecedor = fornecedorRepository.findById(id).orElse(null);

		if (fornecedor == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
		}

		return ResponseEntity.ok(fornecedor);
	}

		// Busca fornecedores pelo nome
	@GetMapping("/nome/{nome}")
	public List<Fornecedor> buscarPorNome(@PathVariable String nome) {
		return fornecedorRepository.findByNomeContainingIgnoreCase(nome);
	}

		// Busca o fornecedor pelo CNPJ
	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<?> buscarPorCnpj(@PathVariable String cnpj) {
		Fornecedor fornecedor = fornecedorRepository.findByCnpj(cnpj).orElse(null);

		if (fornecedor == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
		}

		return ResponseEntity.ok(fornecedor);
	}

		// Busca fornecedores pelo status
	@GetMapping("/status/{ativo}")
	public List<Fornecedor> buscarPorStatus(@PathVariable Boolean ativo) {
		return fornecedorRepository.findByAtivo(ativo);
	}

		// Salva o fornecedor
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Fornecedor fornecedor) {
		Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);

		return ResponseEntity.status(HttpStatus.CREATED).body("Fornecedor cadastrado com sucesso. ID: "
				+ fornecedorSalvo.getId());
	}

		// Atualiza o fornecedor
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody Fornecedor fornecedor) {
		Fornecedor fornecedorExistente = fornecedorRepository.findById(id).orElse(null);

		if (fornecedorExistente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
		}

		fornecedor.setId(id);
		fornecedorRepository.save(fornecedor);

		return ResponseEntity.ok("Fornecedor atualizado com sucesso");
	}

		// Verifica se o fornecedor existe
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		if (!fornecedorRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado");
		}

		// Exclui o fornecedor
		fornecedorRepository.deleteById(id);

		return ResponseEntity.ok("Fornecedor excluído com sucesso");
	}
}