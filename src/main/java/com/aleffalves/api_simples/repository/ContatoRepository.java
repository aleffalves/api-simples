package com.aleffalves.api_simples.repository;

import com.aleffalves.api_simples.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
}
