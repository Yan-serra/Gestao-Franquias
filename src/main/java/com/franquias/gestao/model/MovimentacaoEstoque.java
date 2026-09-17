package com.franquias.gestao.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class MovimentacaoEstoque {

	// Identificador da movimentação
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Tipo da movimentação
	private String tipo;

	// Quantidade movimentada
	private Integer quantidade;

	// Data e hora da movimentação
	private LocalDateTime dataMovimentacao;

	// Estoque relacionado com a movimentação
	@ManyToOne
	private Estoque estoque;

	public MovimentacaoEstoque() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public LocalDateTime getDataMovimentacao() {
		return dataMovimentacao;
	}

	public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
		this.dataMovimentacao = dataMovimentacao;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
}