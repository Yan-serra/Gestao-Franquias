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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime dataVenda;
	private Double valorTotal;
	
	@ManyToOne
	private UnidadeFranqueada unidade;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<ItemVenda> itens;
	
	public Venda() {
	}

	public Venda(Long id, LocalDateTime dataVenda, Double valorTotal, UnidadeFranqueada unidade,
			List<ItemVenda> itens) {
		super();
		this.id = id;
		this.dataVenda = dataVenda;
		this.valorTotal = valorTotal;
		this.unidade = unidade;
		this.itens = itens;
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