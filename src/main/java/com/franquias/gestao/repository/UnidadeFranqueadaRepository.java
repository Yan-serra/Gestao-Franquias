package com.franquias.gestao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.UnidadeFranqueada;

public interface UnidadeFranqueadaRepository  extends JpaRepository<UnidadeFranqueada, Long>{

	Optional<UnidadeFranqueada>findByCnpj(String cnpj);
}
