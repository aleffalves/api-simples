package com.aleffalves.api_simples.service;

import com.aleffalves.api_simples.dto.ProfissionalRequestDTO;
import com.aleffalves.api_simples.dto.ProfissionalResponseDTO;
import com.aleffalves.api_simples.mapper.ProfissionalMapper;
import com.aleffalves.api_simples.model.Profissional;
import com.aleffalves.api_simples.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ProfissionalMapper profissionalMapper;

    public ProfissionalResponseDTO create(ProfissionalRequestDTO profissionalRequestDTO) {
        try {
            Profissional profissional = profissionalRepository.save(profissionalMapper.toRequestEntity(profissionalRequestDTO));
            return profissionalMapper.toResponseDTO(profissional);
        }catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar profissional: " + e.getMessage());
        }
    }
}
