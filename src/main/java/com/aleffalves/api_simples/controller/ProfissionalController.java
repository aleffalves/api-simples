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

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> criar(@Valid @RequestBody ProfissionalRequestDTO profissionalRequestDTO) {
        ProfissionalResponseDTO profissionalResponseDTO = profissionalService.criar(profissionalRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Profissional com id " + profissionalResponseDTO.getId() + " cadastrado");

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissionais retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar profissionais"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),}
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Listar profissionais")
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarProfissionais(@RequestParam(required = false) String q,
                                                                         @RequestParam(required = false) List<String> fields){
        return ResponseEntity.status(HttpStatus.OK).body(profissionalService.buscar(q, fields));
    }



}
