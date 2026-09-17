package com.franquias.gestao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.UnidadeFranqueada;

public interface UnidadeFranqueadaRepository extends JpaRepository<UnidadeFranqueada, Long> {

	// Busca a unidade pelo CNPJ
	Optional<UnidadeFranqueada> findByCnpj(String cnpj);

	// Busca unidades pelo nome
	List<UnidadeFranqueada> findByNomeContainingIgnoreCase(String nome);

	// Busca unidades pela cidade
	List<UnidadeFranqueada> findByCidadeContainingIgnoreCase(String cidade);

	// Busca unidades pelo status
	List<UnidadeFranqueada> findByAtiva(Boolean ativa);
}