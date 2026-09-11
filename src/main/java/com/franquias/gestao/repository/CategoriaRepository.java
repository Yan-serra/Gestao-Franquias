package com.franquias.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franquias.gestao.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}