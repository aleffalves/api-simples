package com.aleffalves.api_simples.specification;

import com.aleffalves.api_simples.model.Profissional;
import org.springframework.data.jpa.domain.Specification;

public class ProfissionalSpecification {

    public static Specification<Profissional> comTexto(String q) {
        return (root, query, criteriaBuilder) -> {
            if (q == null || q.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%%" + q.toLowerCase() + "%%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("cargo")), "%%" + q.toLowerCase() + "%%")
            );
        };
    }
}
