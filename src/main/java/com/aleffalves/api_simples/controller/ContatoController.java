package com.aleffalves.api_simples.controller;

import com.aleffalves.api_simples.dto.ContatoRequestDTO;
import com.aleffalves.api_simples.dto.ContatoResponseDTO;
import com.aleffalves.api_simples.dto.ProfissionalRequestDTO;
import com.aleffalves.api_simples.dto.ProfissionalResponseDTO;
import com.aleffalves.api_simples.service.ContatoService;
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
@RequestMapping("/contatos")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Input inválido"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),}
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar profissional")
    @PostMapping
    public ResponseEntity<String> criar(@Valid @RequestBody ContatoRequestDTO contatoRequestDTO) {
        ContatoResponseDTO contatoResponseDTO = contatoService.criar(contatoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Contato com id " + contatoResponseDTO.getId() + " cadastrado");

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contatos retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar contatos"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")}
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar contatos")
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarProfissionais(@RequestParam(required = false) String q,
                                                                         @RequestParam(required = false) List<String> fields){
        return ResponseEntity.status(HttpStatus.OK).body(contatoService.buscar(q, fields));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar contato"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),}
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Buscar contato por id")
    @GetMapping("/{id}")
    public ResponseEntity<ContatoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(contatoService.buscarPorId(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Input inválido"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),}
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar contato")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @Valid @RequestBody ContatoRequestDTO contatoRequestDTO) {
        contatoService.atualizar(id, contatoRequestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Sucesso cadastrado alterado");

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar contato"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),}
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deletar profissional")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        contatoService.deletar(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Sucesso contato excluído");

    }

}
