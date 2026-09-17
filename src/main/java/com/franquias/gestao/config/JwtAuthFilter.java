package com.franquias.gestao.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.franquias.gestao.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		// Pega o token enviado no cabeçalho da requisição
		String authorization = request.getHeader("Authorization");

		if (authorization != null && authorization.startsWith("Bearer ")) {

			// Remove a palavra Bearer e pega somente o token
			String token = authorization.substring(7);

			try {

				// Pega os dados salvos dentro do token
				String email = jwtService.extrairEmail(token);
				String cargo = jwtService.extrairPerfil(token);

				// Cria a permissão do usuário
				SimpleGrantedAuthority autoridade =
						new SimpleGrantedAuthority("ROLE_" + cargo);

				// Autentica o usuário na requisição
				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(email,null,Collections.singletonList(autoridade));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			} 
			
			catch (Exception e) {

				// Limpa a autenticação caso o token seja inválido
				SecurityContextHolder.clearContext();
			}
		}

		filterChain.doFilter(request, response);
	}
}