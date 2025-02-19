package com.aleffalves.api_simples.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContatoRequestDTO {
    @Schema(description = "Tipo do contato", example = "Celular")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Schema(description = "Numero do contato", example = "(31) 933334-8312")
    @NotBlank(message = "O contato é obrigatório")
    private String contato;

    @Schema(description = "Id do profissional", example = "1")
    @NotNull(message = "O profissionalId é obrigatório")
    private Long profissional;
}
