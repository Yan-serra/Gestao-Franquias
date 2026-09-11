package com.franquias.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

}