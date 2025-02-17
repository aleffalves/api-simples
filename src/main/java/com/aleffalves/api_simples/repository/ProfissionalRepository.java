package com.aleffalves.api_simples.repository;

import com.aleffalves.api_simples.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long>, JpaSpecificationExecutor<Profissional> {
}
