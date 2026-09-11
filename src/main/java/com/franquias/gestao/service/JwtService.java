package com.franquias.gestao.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String chaveSecreta =
            "minha-chave-secreta-gestao-franquias-2026";

    private SecretKey getChave() {
        return Keys.hmacShaKeyFor(chaveSecreta.getBytes());
    }

    public String gerarToken(String email, String perfil) {

        long tempoExpiracao = 1000 * 60 * 60;

        return Jwts.builder().subject(email).claim("perfil", perfil).issuedAt(new Date())
        		.expiration(new Date(System.currentTimeMillis() + tempoExpiracao)).signWith(getChave()).compact();
    }
    
    public String extrairEmail(String token) {
    	return Jwts.parser().verifyWith(getChave()).build().parseSignedClaims(token)
    			.getPayload().getSubject();
    }
    
    public String extrairPerfil(String token) {
    	return Jwts.parser().verifyWith(getChave()).build().parseSignedClaims(token)
    			.getPayload().get("perfil",String.class); 
    }
    
    
}