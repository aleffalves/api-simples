package com.aleffalves.api_simples.mapper;

import com.aleffalves.api_simples.dto.ProfissionalRequestDTO;
import com.aleffalves.api_simples.dto.ProfissionalResponseDTO;
import com.aleffalves.api_simples.model.Profissional;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {ContatoMapper.class})
public interface ProfissionalMapper {

    Profissional toRequestEntity(ProfissionalRequestDTO dto);
    ProfissionalRequestDTO toRequestDTO(Profissional entity);

    Profissional toResponseEntity(ProfissionalResponseDTO dto);
    ProfissionalResponseDTO toResponseDTO(Profissional entity);

    List<ProfissionalResponseDTO> toResponseDTO(List<Profissional> entity);

}
