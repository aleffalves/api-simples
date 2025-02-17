package com.aleffalves.api_simples.controller;

import com.aleffalves.api_simples.dto.ProfissionalRequestDTO;
import com.aleffalves.api_simples.dto.ProfissionalResponseDTO;
import com.aleffalves.api_simples.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Input inválido"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),}
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar profissional")
    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody ProfissionalRequestDTO profissionalRequestDTO) {
        ProfissionalResponseDTO profissionalResponseDTO = profissionalService.create(profissionalRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Profissional com id " + profissionalResponseDTO.getId() + " cadastrado");

    }


}
