package com.franquias.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque,Long>{

}
