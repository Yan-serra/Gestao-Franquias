package com.franquias.gestao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Responsavel;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {

	// Busca o responsável pelo CPF
	Optional<Responsavel> findByCpf(String cpf);

	// Busca responsáveis pelo nome
	List<Responsavel> findByNomeContainingIgnoreCase(String nome);

	// Busca responsáveis pela unidade
	List<Responsavel> findByUnidadeId(Long unidadeId);
}