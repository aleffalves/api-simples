package com.aleffalves.api_simples.service;

import com.aleffalves.api_simples.dto.ProfissionalRequestDTO;
import com.aleffalves.api_simples.dto.ProfissionalResponseDTO;
import com.aleffalves.api_simples.mapper.ProfissionalMapper;
import com.aleffalves.api_simples.model.Profissional;
import com.aleffalves.api_simples.repository.ProfissionalRepository;
import com.aleffalves.api_simples.specification.ProfissionalSpecification;
import com.aleffalves.api_simples.utils.FilterDtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ProfissionalMapper profissionalMapper;

    public ProfissionalResponseDTO criar(ProfissionalRequestDTO profissionalRequestDTO) {
        try {
            Profissional profissional = profissionalRepository.save(profissionalMapper.toRequestEntity(profissionalRequestDTO));
            return profissionalMapper.toResponseDTO(profissional);
        }catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar profissional: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> buscar(String q, List<String> fields) {
        try {
            Specification<Profissional> spec = ProfissionalSpecification.comTexto(q);
            List<Profissional> all = profissionalRepository.findAll(spec);
            List<ProfissionalResponseDTO> responseDTOs = profissionalMapper.toResponseDTO(all);

            return responseDTOs.stream()
                    .map(response -> FilterDtoUtils.filterFields(response, fields))
                    .toList();

        }catch (Exception e){
            throw new RuntimeException("Erro ao buscar profissionais: " + e.getMessage(), e);
        }
    }
}
