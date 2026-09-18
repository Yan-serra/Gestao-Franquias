package com.franquias.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Franqueadora;

public interface FranqueadoraRepository extends JpaRepository<Franqueadora, Long> {

	// Verifica se já existe uma franqueadora com o mesmo CNPJ
	boolean existsByCnpj(String cnpj);

}