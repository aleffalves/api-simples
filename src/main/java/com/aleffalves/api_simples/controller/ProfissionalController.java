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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar profissional"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),}
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Buscar profissional por id")
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(profissionalService.buscarPorId(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profissional atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Input inválido"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),}
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Atualizar profissional")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @Valid @RequestBody ProfissionalRequestDTO profissionalRequestDTO) {
        profissionalService.atualizar(id, profissionalRequestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Sucesso cadastrado alterado");

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar profissional"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),}
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Deletar profissional")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        profissionalService.deletar(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Sucesso profissional excluído");

    }

}
