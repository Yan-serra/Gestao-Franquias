package com.franquias.gestao.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UnidadeFranqueada {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	private String nome;
	private String cnpj;
	private String cidade;
	private String estado;
	private String endereco;
	private String telefone;
	private LocalDate dateInicio;
	private boolean ativa;

	@ManyToOne
	private Franqueadora franqueadora;
	
	public UnidadeFranqueada() {
		
	}
	
	public Long getId(){
		return id;
	}
	
	public void setid(Long id) {
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
		return dateInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dateInicio = dataInicio;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}
	
	public  Franqueadora getFranqueadora(){
		return franqueadora;
	}
	
	public void setFranqueadora(Franqueadora franqueadora) {
		this.franqueadora = franqueadora;
	}
}
