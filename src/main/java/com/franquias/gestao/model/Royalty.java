package com.franquias.gestao.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Royalty {

	// Identificador do royalty
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Percentual usado no cálculo
	private Double percentual;

	// Faturamento usado como base
	private Double faturamento;

	// Valor que deve ser pago
	private Double valorDevido;

	// Valor que já foi pago
	private Double valorPago;

	// Situação do pagamento
	private String status;

	// Início do período
	private LocalDate dataInicio;

	// Final do período
	private LocalDate dataFim;

	// Unidade responsável pelo royalty
	@ManyToOne
	private UnidadeFranqueada unidade;

	public Royalty() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPercentual() {
		return percentual;
	}

	public void setPercentual(Double percentual) {
		this.percentual = percentual;
	}

	public Double getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(Double faturamento) {
		this.faturamento = faturamento;
	}

	public Double getValorDevido() {
		return valorDevido;
	}

	public void setValorDevido(Double valorDevido) {
		this.valorDevido = valorDevido;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public UnidadeFranqueada getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeFranqueada unidade) {
		this.unidade = unidade;
	}
}