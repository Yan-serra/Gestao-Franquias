package com.franquias.gestao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.MovimentacaoEstoque;

public interface MovimentacaoEstoqueRepository
		extends JpaRepository<MovimentacaoEstoque, Long> {

	// Busca movimentações pelo estoque
	List<MovimentacaoEstoque> findByEstoqueId(Long estoqueId);

	// Busca movimentações pelo tipo
	List<MovimentacaoEstoque> findByTipo(String tipo);
}