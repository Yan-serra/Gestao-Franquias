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

import com.franquias.gestao.model.Estoque;
import com.franquias.gestao.model.Produto;
import com.franquias.gestao.model.UnidadeFranqueada;
import com.franquias.gestao.repository.EstoqueRepository;
import com.franquias.gestao.repository.ProdutoRepository;
import com.franquias.gestao.repository.UnidadeFranqueadaRepository;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    @Autowired
    private EstoqueRepository estoqueRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UnidadeFranqueadaRepository unidadeFranqueadaRepository;

    @GetMapping
    public List<Estoque> listar() {
        return estoqueRepository.findAll();
    }

    @GetMapping("/{id}")
    public Estoque buscarPorId(@PathVariable Long id) {
        return estoqueRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Estoque cadastrar(@RequestBody Estoque estoque) {

        Produto produto = produtoRepository
                .findById(estoque.getProduto().getId())
                .orElse(null);

        UnidadeFranqueada unidade = unidadeFranqueadaRepository
                .findById(estoque.getUnidade().getId())
                .orElse(null);

        estoque.setProduto(produto);
        estoque.setUnidade(unidade);

        return estoqueRepository.save(estoque);
    }

    @PutMapping("/{id}")
    public Estoque atualizar(@PathVariable Long id, @RequestBody Estoque estoque) {

        if (estoque.getQuantidade() < 0) {
            throw new RuntimeException("O estoque não pode ficar negativo");
        }

        estoque.setId(id);

        return estoqueRepository.save(estoque);
    }

    @PutMapping("/{id}/entrada/{quantidade}")
    public Estoque entrada(@PathVariable Long id, @PathVariable Integer quantidade) {

        Estoque estoque = estoqueRepository.findById(id).orElse(null);

        if (estoque == null) {
            return null;
        }

        estoque.setQuantidade(estoque.getQuantidade() + quantidade);

        return estoqueRepository.save(estoque);
    }

    @PutMapping("/{id}/saida/{quantidade}")
    public ResponseEntity<?> saida(@PathVariable Long id, @PathVariable Integer quantidade) {

        Estoque estoque = estoqueRepository.findById(id).orElse(null);

        if (estoque == null) {
            return ResponseEntity.notFound().build();
        }

        if (estoque.getQuantidade() - quantidade < 0) {
            return ResponseEntity
                    .badRequest()
                    .body("Estoque insuficiente");
        }

        estoque.setQuantidade(estoque.getQuantidade() - quantidade);

        return ResponseEntity.ok(estoqueRepository.save(estoque));
    }
    
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        estoqueRepository.deleteById(id);
    }
}