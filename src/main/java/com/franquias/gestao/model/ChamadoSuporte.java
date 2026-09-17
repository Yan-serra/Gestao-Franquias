package com.franquias.gestao.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ChamadoSuporte {

	// Identificador do chamado
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Categoria do chamado
	private String categoria;

	// Nível de prioridade
	private String prioridade;

	// Descrição do problema
	private String descricao;

	// Situação atual do chamado
	private String status;

	// Data de abertura do chamado
	private LocalDateTime dataAbertura;

	// Data de fechamento do chamado
	private LocalDateTime dataFechamento;

	// Unidade que abriu o chamado
	@ManyToOne
	private UnidadeFranqueada unidade;

	public ChamadoSuporte() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDateTime getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDateTime dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public UnidadeFranqueada getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeFranqueada unidade) {
		this.unidade = unidade;
	}
}