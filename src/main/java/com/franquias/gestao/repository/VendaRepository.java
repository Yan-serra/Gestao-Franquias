package com.franquias.gestao.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

	// Busca vendas pela unidade
	List<Venda> findByUnidadeId(Long unidadeId);

	// Busca vendas pelo período
	List<Venda> findByDataVendaBetween(LocalDateTime inicio, LocalDateTime fim);

	// Busca vendas da unidade pelo período
	List<Venda> findByUnidadeIdAndDataVendaBetween(
			Long unidadeId,
			LocalDateTime inicio,
			LocalDateTime fim);
}