package com.franquias.gestao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	// Busca o usuário pelo email
	Optional<Usuario> findByEmail(String email);
}