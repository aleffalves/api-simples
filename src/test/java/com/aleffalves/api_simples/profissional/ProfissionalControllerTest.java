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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        when(profissionalService.create(any(ProfissionalRequestDTO.class))).thenReturn(responseDTO);

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

}
