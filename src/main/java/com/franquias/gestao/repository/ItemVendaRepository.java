package com.franquias.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.ItemVenda;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

	// Usa os métodos padrão do JpaRepository
}