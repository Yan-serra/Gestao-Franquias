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

import com.franquias.gestao.model.Perfil;
import com.franquias.gestao.model.Usuario;
import com.franquias.gestao.repository.PerfilRepository;
import com.franquias.gestao.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PerfilRepository perfilRepository;

		// Lista todos os usuários
	@GetMapping
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

		// Busca o usuário pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		if (usuario == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
		}

		return ResponseEntity.ok(usuario);
	}

		// Verifica se o email já existe
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
		if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body("Já existe um usuário com este e-mail");
		}

		if (usuario.getPerfil() == null || usuario.getPerfil().getId() == null) {
			return ResponseEntity.badRequest().body("Perfil não informado");
		}

		// Busca o perfil
		Perfil perfil = perfilRepository.findById(usuario.getPerfil().getId()).orElse(null);
		if (perfil == null) {
			return ResponseEntity.badRequest().body("Perfil não encontrado");
		}

		usuario.setPerfil(perfil);
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso. ID: "
				+ usuarioSalvo.getId());
	}

		// Busca o usuário
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody Usuario usuario) {
		Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
		if (usuarioExistente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
		}

		// Verifica se o email pertence a outro usuário
		if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()
				&& !usuarioExistente.getEmail().equals(usuario.getEmail())) {
			return ResponseEntity.badRequest().body("Este email já está em uso");
		}

		if (usuario.getPerfil() == null || usuario.getPerfil().getId() == null) {
			return ResponseEntity.badRequest().body("Perfil não informado");
		}

		Perfil perfil = perfilRepository.findById(usuario.getPerfil().getId()).orElse(null);
		if (perfil == null) {
			return ResponseEntity.badRequest().body("Perfil não encontrado");
		}

		usuario.setId(id);
		usuario.setPerfil(perfil);
		usuarioRepository.save(usuario);
		return ResponseEntity.ok("Usuário atualizado com sucesso");
	}

		// Verifica se o usuário existe
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		if (!usuarioRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
		}

		usuarioRepository.deleteById(id);
		return ResponseEntity.ok("Usuário excluído com sucesso");
	}
}