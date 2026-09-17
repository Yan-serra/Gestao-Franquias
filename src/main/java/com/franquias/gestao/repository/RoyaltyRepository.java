package com.franquias.gestao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Royalty;

public interface RoyaltyRepository extends JpaRepository<Royalty, Long> {

	// Busca royalties pela unidade
	List<Royalty> findByUnidadeId(Long unidadeId);

	// Busca royalties pelo status
	List<Royalty> findByStatus(String status);
}