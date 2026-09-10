package com.franquias.gestao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Já existe um usuário com este e-mail.");    
        }
        
        Perfil perfil = perfilRepository.findById(usuario.getPerfil().getId()).orElse(null);
        if (perfil == null) {
            return ResponseEntity.badRequest().body("Perfil não encontrado.");
        }

        usuario.setPerfil(perfil);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody Usuario usuario) {
        Usuario usuarioExistente =
                usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()
                && !usuarioExistente.getEmail().equals(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("Este email já está em uso!");
        }

        Perfil perfil = perfilRepository.findById(usuario.getPerfil().getId()).orElse(null);

        if (perfil == null) {
            return ResponseEntity.badRequest().body("Perfil não encontrado.");
        }

        usuario.setId(id);
        usuario.setPerfil(perfil);
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}