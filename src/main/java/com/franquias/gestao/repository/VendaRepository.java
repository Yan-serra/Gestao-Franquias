package com.franquias.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.franquias.gestao.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

}