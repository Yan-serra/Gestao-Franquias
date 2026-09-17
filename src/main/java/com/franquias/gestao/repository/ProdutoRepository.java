package com.franquias.gestao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	// Busca produtos pelo nome
	List<Produto> findByNomeContainingIgnoreCase(String nome);

	// Busca produtos pela categoria
	List<Produto> findByCategoriaId(Long categoriaId);

	// Busca produtos pelo status
	List<Produto> findByAtivo(Boolean ativo);

	// Busca produtos pelo fornecedor
	List<Produto> findByFornecedorId(Long fornecedorId);
}