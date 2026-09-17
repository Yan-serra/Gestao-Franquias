package com.franquias.gestao.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UnidadeFranqueada {

	// Identificador da unidade
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Nome da unidade
	private String nome;

	// CNPJ da unidade
	private String cnpj;

	// Cidade da unidade
	private String cidade;

	// Estado da unidade
	private String estado;

	// Endereço da unidade
	private String endereco;

	// Telefone da unidade
	private String telefone;

	// Data de início da unidade
	private LocalDate dataInicio;

	// Indica se a unidade está ativa
	private boolean ativa;

	// Liga a unidade com uma franqueadora
	@ManyToOne
	private Franqueadora franqueadora;

	public UnidadeFranqueada() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	public Franqueadora getFranqueadora() {
		return franqueadora;
	}

	public void setFranqueadora(Franqueadora franqueadora) {
		this.franqueadora = franqueadora;
	}
}