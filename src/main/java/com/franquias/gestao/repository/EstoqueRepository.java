package com.franquias.gestao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.franquias.gestao.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

	@Query("SELECT e FROM Estoque e WHERE e.quantidade < e.estoqueMinimo")
	List<Estoque> buscarEstoqueAbaixoDoMinimo();

}