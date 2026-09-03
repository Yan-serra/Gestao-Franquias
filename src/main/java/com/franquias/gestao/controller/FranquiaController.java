package com.franquias.gestao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franquias.gestao.model.Franquia;
import com.franquias.gestao.repository.FranquiaRepository;

@RestController
@RequestMapping("/franquias")

public class FranquiaController{
	
	@Autowired
	private FranquiaRepository franquiaRepository;
	
	@PostMapping
	public Franquia cadastrar(@RequestBody Franquia franquia) {
		return franquiaRepository.save(franquia);
	}
	
	@GetMapping
	public List<Franquia> listar(){
		return franquiaRepository.findAll();
	}
}
