package com.franquias.gestao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.UnidadeFranqueada;

public interface UnidadeFranqueadaRepository extends JpaRepository<UnidadeFranqueada, Long> {

	Optional<UnidadeFranqueada> findByCnpj(String cnpj);

	List<UnidadeFranqueada> findByNomeContainingIgnoreCase(String nome);

	List<UnidadeFranqueada> findByCidadeContainingIgnoreCase(String cidade);

	List<UnidadeFranqueada> findByAtiva(Boolean ativa);

}