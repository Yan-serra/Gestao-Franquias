package com.franquias.gestao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.ChamadoSuporte;

public interface ChamadoSuporteRepository extends JpaRepository<ChamadoSuporte, Long> {

	// Busca chamados pelo status
	List<ChamadoSuporte> findByStatus(String status);

	// Busca chamados pela prioridade
	List<ChamadoSuporte> findByPrioridade(String prioridade);

	// Busca chamados pela unidade
	List<ChamadoSuporte> findByUnidadeId(Long unidadeId);

	// Busca chamados pelo status e prioridade
	List<ChamadoSuporte> findByStatusAndPrioridade(String status, String prioridade);
}