package com.aleffalves.api_simples.profissional;

import com.aleffalves.api_simples.controller.ProfissionalController;
import com.aleffalves.api_simples.dto.ProfissionalRequestDTO;
import com.aleffalves.api_simples.dto.ProfissionalResponseDTO;
import com.aleffalves.api_simples.enumeration.CargoEnum;
import com.aleffalves.api_simples.service.ProfissionalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProfissionalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfissionalService profissionalService;

    @InjectMocks
    private ProfissionalController profissionalController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(profissionalController).build();
    }

    @Test
    @DisplayName("Sucesso ao criar profissional")
    public void testCreateProfissional() throws Exception {
        ProfissionalRequestDTO requestDTO = ProfissionalRequestDTO.builder()
                .nome("João Silva")
                .cargo(CargoEnum.DESENVOLVEDOR)
                .nascimento(new Date())
                .build();

        ProfissionalResponseDTO responseDTO = ProfissionalResponseDTO.builder()
                .id(1L)
                .nome("João Silva")
                .cargo(CargoEnum.DESENVOLVEDOR)
                .nascimento(new Date())
                .build();

        when(profissionalService.criar(any(ProfissionalRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/profissionais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Profissional com id 1 cadastrado"));
    }

    @Test
    @DisplayName("Erro ao criar profissional sem nome")
    public void testCreateProfissionalSemNome() throws Exception {
        ProfissionalRequestDTO requestDTO = ProfissionalRequestDTO.builder()
                .nome(null)
                .cargo(CargoEnum.DESENVOLVEDOR)
                .nascimento(new Date())
                .build();

        mockMvc.perform(post("/profissionais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Erro ao criar profissional sem cargo")
    public void testCreateProfissionalSemCargo() throws Exception {
        ProfissionalRequestDTO requestDTO = ProfissionalRequestDTO.builder()
                .nome("João Silva")
                .cargo(null)
                .nascimento(new Date())
                .build();

        mockMvc.perform(post("/profissionais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Sucesso ao listar profissionais sem filtro")
    public void testListarProfissionaisSemFiltros() throws Exception {
        List<Map<String, Object>> profissionaisMock = List.of(
                Map.of("id", 1, "nome", "João Silva"),
                Map.of("id", 2, "nome", "Maria Santos")
        );

        when(profissionalService.buscar(null, null)).thenReturn(profissionaisMock);

        mockMvc.perform(get("/profissionais"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("João Silva"));
    }

    @Test
    @DisplayName("Sucesso ao listar profissionais com filtro")
    public void testListarProfissionaisComFiltroDeTexto() throws Exception {
        String query = "João";
        List<Map<String, Object>> profissionaisMock = List.of(
                Map.of("id", 1, "nome", "João Silva")
        );

        when(profissionalService.buscar(query, null)).thenReturn(profissionaisMock);

        mockMvc.perform(get("/profissionais").param("q", query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("João Silva"));
    }

    @Test
    @DisplayName("Sucesso ao listar profissionais com campos especificos")
    public void testListarProfissionaisComCamposEspecificos() {
        List<String> fields = List.of("id", "nome");
        List<Map<String, Object>> profissionaisMock = List.of(
                Map.of("id", 1, "nome", "João Silva")
        );

        when(profissionalService.buscar(null, fields)).thenReturn(profissionaisMock);

        ResponseEntity<List<Map<String, Object>>> response = profissionalController.listarProfissionais(null, fields);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profissionaisMock, response.getBody());
    }

    @Test
    @DisplayName("Sucesso ao listar profissionais sem resultados")
    public void testListarProfissionaisSemResultados() throws Exception {
        when(profissionalService.buscar(null, null)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/profissionais"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

}
