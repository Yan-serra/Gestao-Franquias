package com.franquias.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}