package com.franquias.gestao.service;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	// Chave usada para gerar e validar o token
	
	private String chaveSecreta = "minha-chave-secreta-gestao-franquias-2026";

	// Tempo de validade do token
	private long tempoExpiracao = 1000 * 60 * 60 * 10;

	// Converte a chave em um formato usado pelo JWT
	private Key getChave() {
		return Keys.hmacShaKeyFor(chaveSecreta.getBytes());
	}

	// Gera o token do usuário
	public String gerarToken(String email, String perfil) {

		return Jwts.builder().subject(email).claim("perfil", perfil).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + tempoExpiracao)).signWith(getChave()).compact();
	}

	// Pega os dados salvos dentro do token
	private Claims extrairClaims(String token) {
		return Jwts.parser().verifyWith((javax.crypto.SecretKey) getChave()).build().parseSignedClaims(token).getPayload();
	}

	// Pega o email salvo no token
	public String extrairEmail(String token) {
		return extrairClaims(token).getSubject();
	}

	// Pega o perfil salvo no token
	public String extrairPerfil(String token) {
		return extrairClaims(token).get("perfil", String.class);
	}
}