package com.franquias.gestao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByCategoriaId(Long categoriaId);

	List<Produto> findByAtivo(Boolean ativo);

}