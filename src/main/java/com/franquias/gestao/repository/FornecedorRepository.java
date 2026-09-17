package com.franquias.gestao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	// Busca fornecedores pelo nome
	List<Fornecedor> findByNomeContainingIgnoreCase(String nome);

	// Busca fornecedor pelo CNPJ
	Optional<Fornecedor> findByCnpj(String cnpj);

	// Busca fornecedores pelo status
	List<Fornecedor> findByAtivo(Boolean ativo);
}