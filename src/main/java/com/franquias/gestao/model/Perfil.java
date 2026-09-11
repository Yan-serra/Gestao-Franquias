package com.franquias.gestao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Perfil {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cargo;
	
	public Perfil() {
		
	}
	
	public Perfil(Long id, String nome) {
		this.id = id;
		this.setNome(nome);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getCargo() {
		return cargo;
	}

	public void setNome(String cargo) {
		this.cargo = cargo;
	}
}
