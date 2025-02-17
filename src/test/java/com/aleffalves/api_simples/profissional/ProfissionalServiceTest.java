package com.aleffalves.api_simples.profissional;

import com.aleffalves.api_simples.dto.ProfissionalRequestDTO;
import com.aleffalves.api_simples.dto.ProfissionalResponseDTO;
import com.aleffalves.api_simples.enumeration.CargoEnum;
import com.aleffalves.api_simples.mapper.ProfissionalMapper;
import com.aleffalves.api_simples.model.Profissional;
import com.aleffalves.api_simples.repository.ProfissionalRepository;
import com.aleffalves.api_simples.service.ProfissionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfissionalServiceTest {

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private ProfissionalMapper profissionalMapper;

    @InjectMocks
    private ProfissionalService profissionalService;

    private ProfissionalRequestDTO requestDTO;
    private Profissional profissional;
    private List<Profissional> profissionais;
    private ProfissionalResponseDTO responseDTO;
    private List<ProfissionalResponseDTO> responseDTOs;

    @BeforeEach
    void setUp() {
        requestDTO = ProfissionalRequestDTO.builder()
                .nome("João")
                .cargo(CargoEnum.DESENVOLVEDOR)
                .nascimento(new Date())
                .build();

        profissional = Profissional.builder()
                .id(1L)
                .nome("João Silva")
                .cargo(CargoEnum.DESIGNER)
                .nascimento(new Date())
                .createdDate(new Date())
                .excluido(false)
                .build();

        profissionais = Arrays.asList(profissional, profissional);

        responseDTO = ProfissionalResponseDTO.builder()
                .id(1L)
                .nome("João Silva")
                .cargo(CargoEnum.DESIGNER)
                .nascimento(new Date())
                .createdDate(new Date())
                .build();

        responseDTOs = Arrays.asList( responseDTO, responseDTO);

    }

    @Test
    void testCriarProfissional_Sucesso() {
        when(profissionalMapper.toRequestEntity(requestDTO)).thenReturn(profissional);
        when(profissionalRepository.save(profissional)).thenReturn(profissional);
        when(profissionalMapper.toResponseDTO(profissional)).thenReturn(responseDTO);

        ProfissionalResponseDTO resultado = profissionalService.criar(requestDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals(CargoEnum.DESIGNER, resultado.getCargo());

        verify(profissionalMapper).toRequestEntity(requestDTO);
        verify(profissionalRepository).save(profissional);
        verify(profissionalMapper).toResponseDTO(profissional);
    }

    @Test
    void testCriarProfissional_ErroAoSalvar() {
        // Configuração dos mocks para simular uma exceção
        when(profissionalMapper.toRequestEntity(requestDTO)).thenReturn(profissional);
        when(profissionalRepository.save(profissional)).thenThrow(new RuntimeException("Erro ao salvar no banco de dados"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profissionalService.criar(requestDTO);
        });

        assertEquals("Erro ao cadastrar profissional: Erro ao salvar no banco de dados", exception.getMessage());

        verify(profissionalMapper).toRequestEntity(requestDTO);
        verify(profissionalRepository).save(profissional);
        verify(profissionalMapper, never()).toResponseDTO(profissional);
    }

    @Test
    void testBuscarProfissionais_Sucesso() {

        List<String> fields = Arrays.asList("nome", "cargo");

        when(profissionalRepository.findAll(any(Specification.class))).thenReturn(profissionais);
        when(profissionalMapper.toResponseDTO(profissionais)).thenReturn(responseDTOs);

        List<Map<String, Object>> resultado = profissionalService.buscar("João", fields);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        Map<String, Object> primeiroResultado = resultado.get(0);
        assertEquals(CargoEnum.DESIGNER, primeiroResultado.get("cargo"));
        assertEquals("João Silva", primeiroResultado.get("nome"));
        assertNull(primeiroResultado.get("nascimento"));

        verify(profissionalRepository).findAll(any(Specification.class));
        verify(profissionalMapper).toResponseDTO(profissionais);
    }

    @Test
    void testBuscarProfissionais_ErroAoBuscar() {

        List<String> fields = Arrays.asList("nome", "cargo");

        when(profissionalRepository.findAll(any(Specification.class))).thenThrow(new RuntimeException("Erro ao buscar no banco de dados"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profissionalService.buscar("João", fields);
        });

        assertEquals("Erro ao buscar profissionais: Erro ao buscar no banco de dados", exception.getMessage());

        verify(profissionalRepository).findAll(any(Specification.class));
        verify(profissionalMapper, never()).toResponseDTO(profissionais);
    }


    @Test
    void testBuscarPorId_Sucesso() {
        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));
        when(profissionalMapper.toResponseDTO(profissional)).thenReturn(responseDTO);

        ProfissionalResponseDTO resultado = profissionalService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals(CargoEnum.DESIGNER, resultado.getCargo());

        verify(profissionalRepository).findById(1L);
        verify(profissionalMapper).toResponseDTO(profissional);
    }

    @Test
    void testBuscarPorId_ProfissionalNaoEncontrado() {
        when(profissionalRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profissionalService.buscarPorId(1L);
        });

        assertEquals("Erro ao buscar profissional: Profissional não encontrado com o ID: 1", exception.getMessage());

        verify(profissionalRepository).findById(1L);
        verify(profissionalMapper, never()).toResponseDTO(profissional);
    }

    @Test
    void testBuscarPorId_ErroAoBuscar() {
        when(profissionalRepository.findById(1L)).thenThrow(new RuntimeException("Erro ao buscar no banco de dados"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profissionalService.buscarPorId(1L);
        });

        assertEquals("Erro ao buscar profissional: Erro ao buscar no banco de dados", exception.getMessage());

        verify(profissionalRepository).findById(1L);
        verify(profissionalMapper, never()).toResponseDTO(profissional);
    }

    @Test
    void testAtualizar_Sucesso() {
        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));
        when(profissionalRepository.save(profissional)).thenReturn(profissional);
        when(profissionalMapper.toResponseDTO(profissional)).thenReturn(responseDTO);

        ProfissionalResponseDTO resultado = profissionalService.atualizar(1L, requestDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals(CargoEnum.DESIGNER, resultado.getCargo());

        verify(profissionalRepository).findById(1L);
        verify(profissionalRepository).save(profissional);
        verify(profissionalMapper).toResponseDTO(profissional);
    }

    @Test
    void testAtualizar_ProfissionalNaoEncontrado() {
        when(profissionalRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profissionalService.atualizar(1L, requestDTO);
        });

        assertEquals("Erro ao atualizar profissional: Profissional não encontrado com o ID: 1", exception.getMessage());

        verify(profissionalRepository).findById(1L);
        verify(profissionalRepository, never()).save(any());
        verify(profissionalMapper, never()).toResponseDTO(profissional);
    }

    @Test
    void testAtualizar_ErroAoAtualizar() {
        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));
        when(profissionalRepository.save(profissional)).thenThrow(new RuntimeException("Erro ao salvar no banco de dados"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profissionalService.atualizar(1L, requestDTO);
        });

        assertEquals("Erro ao atualizar profissional: Erro ao salvar no banco de dados", exception.getMessage());

        verify(profissionalRepository).findById(1L);
        verify(profissionalRepository).save(profissional);
        verify(profissionalMapper, never()).toResponseDTO(profissional);
    }

    @Test
    void testDeletar_Sucesso() {
        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));
        when(profissionalRepository.save(profissional)).thenReturn(profissional);

        profissionalService.deletar(1L);

        assertTrue(profissional.getExcluido());

        verify(profissionalRepository).findById(1L);
        verify(profissionalRepository).save(profissional);
    }

    @Test
    void testDeletar_ProfissionalNaoEncontrado() {
        when(profissionalRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profissionalService.deletar(1L);
        });

        assertEquals("Erro ao deletar profissional: Profissional não encontrado com o ID: 1", exception.getMessage());

        verify(profissionalRepository).findById(1L);
        verify(profissionalRepository, never()).save(any());
    }

    @Test
    void testDeletar_ErroAoDeletar() {
        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));
        when(profissionalRepository.save(profissional)).thenThrow(new RuntimeException("Erro ao salvar no banco de dados"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profissionalService.deletar(1L);
        });

        assertEquals("Erro ao deletar profissional: Erro ao salvar no banco de dados", exception.getMessage());

        verify(profissionalRepository).findById(1L);
        verify(profissionalRepository).save(profissional);
    }
}
