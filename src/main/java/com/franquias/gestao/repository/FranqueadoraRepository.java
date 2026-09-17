package com.franquias.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Franqueadora;

public interface FranqueadoraRepository extends JpaRepository<Franqueadora, Long> {

	// Usa os métodos padrão do JpaRepository
}