package com.franquias.gestao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Responsavel {

	// Identificador do responsável
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Nome do responsável
	private String nome;

	// CPF do responsável
	private String cpf;

	// Email para contato
	private String email;

	// Telefone para contato
	private String telefone;

	// Unidade pela qual o responsável é responsável
	@ManyToOne
	private UnidadeFranqueada unidade;

	public Responsavel() {
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public UnidadeFranqueada getUnidade() {
		return unidade;
	}
	
	public void setUnidade(UnidadeFranqueada unidade) {
		this.unidade = unidade;
	}
}