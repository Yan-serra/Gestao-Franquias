package com.franquias.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.franquias.gestao.model.Franquia;

public interface FranquiaRepository extends JpaRepository<Franquia, Long> {

}