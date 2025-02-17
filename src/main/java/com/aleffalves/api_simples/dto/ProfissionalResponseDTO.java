package com.aleffalves.api_simples.dto;
import com.aleffalves.api_simples.enumeration.CargoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
public class ProfissionalResponseDTO {

    @Schema(description = "Id do profissional", example = "1")
    private Long id;

    @Schema(description = "Nome do profissional", example = "João Silva")
    private String nome;

    @Schema(description = "Cargo do profissional", example = "Desenvolvedor")
    private CargoEnum cargo;

    @Schema(description = "Data de nascimento no formato dd-MM-yyyy", example = "09-12-1999")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date nascimento;

    @Schema(description = "Data de criação", example = "2025-02-16T17:54:36.973Z")
    private Date createdDate;

    @Schema(description = "Lista de contatos")
    private List<ContatoDTO> contatos;

}
