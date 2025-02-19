package com.aleffalves.api_simples.mapper;

import com.aleffalves.api_simples.dto.ContatoRequestDTO;
import com.aleffalves.api_simples.dto.ContatoResponseDTO;
import com.aleffalves.api_simples.model.Contato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ContatoMapper {

    Contato toResponseEntity(ContatoResponseDTO dto);
    ContatoResponseDTO toResponseDTO(Contato entity);

    @Mapping(source = "profissional" , target = "profissional.id")
    Contato toRequestEntity(ContatoRequestDTO dto);
    @Mapping(source = "profissional.id" , target = "profissional")
    ContatoRequestDTO toRequestDTO(Contato entity);

    List<Contato> toResponseEntity(List<ContatoResponseDTO> dto);
    List<ContatoResponseDTO> toResponseDTO(List<Contato> entity);
}
