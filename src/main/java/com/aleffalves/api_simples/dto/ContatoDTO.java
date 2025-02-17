package com.aleffalves.api_simples.dto;
import lombok.Data;

import java.util.Date;

@Data
public class ContatoDTO {

    private Long id;
    private String nome;
    private String contato;
    private Date createdDate;
    private ProfissionalResponseDTO profissional;

}
