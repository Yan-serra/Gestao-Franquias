package com.franquias.gestao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private Integer estoqueMinimo;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private UnidadeFranqueada unidade;

    public Estoque() {
    }

    public Estoque(Long id, Integer quantidade, Integer estoqueMinimo,
                   Produto produto, UnidadeFranqueada unidade) {

        this.id = id;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
        this.produto = produto;
        this.unidade = unidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public UnidadeFranqueada getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeFranqueada unidade) {
        this.unidade = unidade;
    }
}