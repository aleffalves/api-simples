package com.aleffalves.api_simples.dto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class ContatoResponseDTO {

    private Long id;
    private String nome;
    private String contato;

}
