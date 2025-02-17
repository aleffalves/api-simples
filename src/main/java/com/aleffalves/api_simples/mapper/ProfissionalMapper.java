package com.aleffalves.api_simples.mapper;

import com.aleffalves.api_simples.dto.ProfissionalRequestDTO;
import com.aleffalves.api_simples.dto.ProfissionalResponseDTO;
import com.aleffalves.api_simples.model.Profissional;
import org.mapstruct.Mapper;

@Mapper
public interface ProfissionalMapper {

    Profissional toRequestEntity(ProfissionalRequestDTO dto);
    ProfissionalRequestDTO toRequestDTO(Profissional entity);

    Profissional toResponseEntity(ProfissionalResponseDTO dto);
    ProfissionalResponseDTO toResponseDTO(Profissional entity);

}
