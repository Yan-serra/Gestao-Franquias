package com.franquias.gestao.controller; 
 
import java.util.List; 
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
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
	private FranqueadoraRepository franqueadoraRepository; 
 
		// Lista todas as franqueadoras 
	@GetMapping 
	public List<Franqueadora> listar() { 
		return franqueadoraRepository.findAll(); 
	} 
 
		// Busca a franqueadora pelo ID 
	@GetMapping("/{id}") 
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) { 
		Franqueadora franqueadora = franqueadoraRepository.findById(id).orElse(null); 
		if (franqueadora == null) { 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Franqueadora não encontrada"); 
		} 
 
		return ResponseEntity.ok(franqueadora); 
	} 
 
		// Salva a franqueadora 
	@PostMapping 
	public ResponseEntity<?> cadastrar(@RequestBody Franqueadora franqueadora) { 
		
		if (franqueadoraRepository.existsByCnpj(franqueadora.getCnpj())) { 
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CNPJ já cadastrado"); 
		} 
		
		Franqueadora franqueadoraSalva = franqueadoraRepository.save(franqueadora); 
		return ResponseEntity.status(HttpStatus.CREATED).body("Franqueadora cadastrada com sucesso. ID: " 
				+ franqueadoraSalva.getId()); 
	} 
 
		// Atualiza a franqueadora 
	@PutMapping("/{id}") 
	public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody Franqueadora dados) { 
		Franqueadora franqueadora = franqueadoraRepository.findById(id).orElse(null); 
		if (franqueadora == null) { 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Franqueadora não encontrada"); 
		} 
 
		franqueadora.setNome(dados.getNome()); 
		franqueadora.setCnpj(dados.getCnpj()); 
		franqueadoraRepository.save(franqueadora); 
		return ResponseEntity.ok("Franqueadora atualizada com sucesso"); 
	} 
 
		// Verifica se a franqueadora existe 
	@DeleteMapping("/{id}") 
	public ResponseEntity<?> excluir(@PathVariable Long id) { 
		if (!franqueadoraRepository.existsById(id)) { 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Franqueadora não encontrada"); 
		} 
 
		// Exclui a franqueadora 
		franqueadoraRepository.deleteById(id); 
		return ResponseEntity.ok("Franqueadora excluída com sucesso"); 
	} 
}