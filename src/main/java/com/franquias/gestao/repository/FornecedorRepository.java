package com.franquias.gestao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	List<Fornecedor> findByNomeContainingIgnoreCase(String nome);

	Optional<Fornecedor> findByCnpj(String cnpj);

	List<Fornecedor> findByAtivo(Boolean ativo);

}