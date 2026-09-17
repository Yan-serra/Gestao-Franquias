package com.franquias.gestao.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Venda {

	// Identificador da venda
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Data e hora da venda
	private LocalDateTime dataVenda;

	// Valor total da venda
	private Double valorTotal;

	// Unidade onde a venda foi realizada
	@ManyToOne
	private UnidadeFranqueada unidade;

	// Itens que fazem parte da venda
	@OneToMany(cascade = CascadeType.ALL)
	private List<ItemVenda> itens;

	public Venda() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDateTime dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public UnidadeFranqueada getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeFranqueada unidade) {
		this.unidade = unidade;
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}
}