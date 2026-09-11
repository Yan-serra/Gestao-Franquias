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
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,
    		FilterChain filterChain)throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {

            String token = authorization.substring(7);
            String email = jwtService.extrairEmail(token);
            String cargo = jwtService.extrairPerfil(token);
            
            System.out.println("Email do token: " + email);
            System.out.println("Cargo do Token: " + cargo);
            System.out.println("AUTORIDADE: Role_" + cargo);

            if (email != null && cargo != null) {

                SimpleGrantedAuthority autoridade =
                        new SimpleGrantedAuthority("ROLE_" + cargo);

                UsernamePasswordAuthenticationToken authentication =  new 
                		UsernamePasswordAuthenticationToken(email,null,Collections.singletonList(autoridade));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}