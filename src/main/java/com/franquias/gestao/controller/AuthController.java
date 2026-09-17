package com.franquias.gestao.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franquias.gestao.model.Usuario;
import com.franquias.gestao.repository.UsuarioRepository;
import com.franquias.gestao.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private JwtService jwtService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Usuario usuario) {

		// Busca o usuário pelo email
		Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());

		// Verifica se o usuário existe
		if (usuarioEncontrado.isEmpty()) {
			return ResponseEntity.badRequest().body("E-mail ou senha inválidos");
		}

		Usuario usuarioBanco = usuarioEncontrado.get();

		// Verifica se a senha está correta
		if (!usuarioBanco.getSenha().equals(usuario.getSenha())) {
			return ResponseEntity.badRequest().body("E-mail ou senha inválidos");
		}

		// Verifica se o usuário está ativo
		if (!usuarioBanco.getAtivo()) {
			return ResponseEntity.badRequest().body("Usuário inativo");
		}

		// Gera o token do usuário
		String token = jwtService.gerarToken(usuarioBanco.getEmail(),usuarioBanco.getPerfil().getCargo());

		return ResponseEntity.ok(token);
	}
}