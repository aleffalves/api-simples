package com.aleffalves.api_simples.dto;

import com.aleffalves.api_simples.enumeration.CargoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class ProfissionalRequestDTO {

    @Schema(description = "Nome do profissional", example = "João Silva")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Schema(description = "Cargo do profissional", example = "Desenvolvedor")
    @NotNull(message = "O cargo é obrigatório")
    private CargoEnum cargo;

    @Schema(description = "Data de nascimento no formato dd-MM-yyyy", example = "09-12-1999")
    @Past(message = "A data de nascimento deve estar no passado")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date nascimento;
}
