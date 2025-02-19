package com.aleffalves.api_simples.specification;

import com.aleffalves.api_simples.model.Contato;
import org.springframework.data.jpa.domain.Specification;

public class ContatoSpecification {

    public static Specification<Contato> comTexto(String q) {
        return (root, query, criteriaBuilder) -> {
            if (q == null || q.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%%" + q.toLowerCase() + "%%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("contato")), "%%" + q.toLowerCase() + "%%")
            );
        };
    }

}
