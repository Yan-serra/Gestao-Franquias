package com.franquias.gestao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())

			// Não salva sessão, pois usamos JWT
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.authorizeHttpRequests(auth -> auth

					// Login e Swagger podem ser acessados sem token
					.requestMatchers(
							"/auth/login",
							"/swagger-ui/**",
							"/swagger-ui.html",
							"/v3/api-docs/**")
						.permitAll()

					// Somente ADMIN pode acessar usuários e perfis
					.requestMatchers(
							"/usuarios/**",
							"/perfis/**")
						.hasAuthority("ROLE_ADMIN")

					// ADMIN e GERENTE acessam os módulos principais
					.requestMatchers(
							"/produtos/**",
							"/categorias/**",
							"/fornecedores/**",
							"/estoques/**",
							"/vendas/**",
							"/royalties/**",
							"/chamados/**",
							"/responsaveis/**",
							"/movimentacoes-estoque/**")
						.hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE")

					// Outras rotas precisam estar autenticadas
					.anyRequest()
						.authenticated()
			)

			// Verifica o JWT
			.addFilterBefore(
					jwtAuthFilter,
					UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}