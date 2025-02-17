package com.aleffalves.api_simples.mapper;

import com.aleffalves.api_simples.dto.ContatoDTO;
import com.aleffalves.api_simples.model.Contato;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ContatoMapper {

    Contato toEntity(ContatoDTO dto);
    ContatoDTO toDTO(Contato entity);

    List<Contato> toEntity(List<ContatoDTO> dto);
    List<ContatoDTO> toDTO(List<Contato> entity);
}
