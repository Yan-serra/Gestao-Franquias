package com.franquias.gestao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franquias.gestao.model.Franqueadora;
import com.franquias.gestao.repository.FranqueadoraRepository;

@RestController
@RequestMapping("/franqueadoras")
public class FranqueadoraController {

	@Autowired 
	private FranqueadoraRepository franqueadorarepository;
	
	@PostMapping
	public Franqueadora cadastrar(@RequestBody Franqueadora franqueadora) {
		return franqueadorarepository.save(franqueadora);
	}
	
	@GetMapping
	public List<Franqueadora> listar(){
		return franqueadorarepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Franqueadora buscarPorId(@PathVariable Long id) {
		if(franqueadorarepository.findById(id).isPresent()) {
			return franqueadorarepository.findById(id).get();
		}
		return null; 
	}
	
	@PutMapping("/{id}")
	public Franqueadora atualizar(@PathVariable Long id, @RequestBody Franqueadora dados) {
		if(franqueadorarepository.findById(id).isPresent()) {
			Franqueadora franqueadora = franqueadorarepository.findById(id).get();
			franqueadora.setNome(dados.getNome());
			franqueadora.setCnpj(dados.getCnpj());
			
			return franqueadorarepository.save(franqueadora);
		}
		return null;
	}
	
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable Long id) {
		franqueadorarepository.deleteById(id);
	}
	
}

