package com.franquias.gestao.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

	List<Venda> findByUnidadeId(Long unidadeId);

	List<Venda> findByDataVendaBetween(
			LocalDateTime inicio,
			LocalDateTime fim);

	List<Venda> findByUnidadeIdAndDataVendaBetween(
			Long unidadeId,
			LocalDateTime inicio,
			LocalDateTime fim);

}