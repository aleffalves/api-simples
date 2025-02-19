package com.aleffalves.api_simples.contato;

import com.aleffalves.api_simples.dto.ContatoRequestDTO;
import com.aleffalves.api_simples.dto.ContatoResponseDTO;
import com.aleffalves.api_simples.dto.ProfissionalResponseDTO;
import com.aleffalves.api_simples.enumeration.CargoEnum;
import com.aleffalves.api_simples.mapper.ContatoMapper;
import com.aleffalves.api_simples.model.Contato;
import com.aleffalves.api_simples.model.Profissional;
import com.aleffalves.api_simples.repository.ContatoRepository;
import com.aleffalves.api_simples.service.ContatoService;
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
public class ContatoServiceTest {

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ContatoMapper contatoMapper;

    @InjectMocks
    private ContatoService contatoService;
    private ContatoRequestDTO requestDTO;
    private Contato contato;
    private List<Contato> contatos;
    private ContatoResponseDTO responseDTO;
    private List<ContatoResponseDTO> responseDTOs;

    @BeforeEach
    void setUp() {
        requestDTO = ContatoRequestDTO.builder()
                .nome("Fixo")
                .contato("(31) 93333-48312")
                .profissional(1L)
                .build();

        contato = Contato.builder()
                .id(1L)
                .nome("Fixo")
                .contato("(31) 93333-48312")
                .createdDate(new Date())
                .profissional(new Profissional())
                .build();

        contatos = Arrays.asList(contato, contato);

        responseDTO = ContatoResponseDTO.builder()
                .id(1L)
                .nome("Fixo")
                .contato("(31) 93333-48312")
                .build();

        responseDTOs = Arrays.asList( responseDTO, responseDTO);

    }

    @Test
    void testCriarContato_Sucesso() {
        when(contatoMapper.toRequestEntity(requestDTO)).thenReturn(contato);
        when(contatoRepository.save(contato)).thenReturn(contato);
        when(contatoMapper.toResponseDTO(contato)).thenReturn(responseDTO);

        ContatoResponseDTO resultado = contatoService.criar(requestDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Fixo", resultado.getNome());
        assertEquals("(31) 93333-48312", resultado.getContato());

        verify(contatoMapper).toRequestEntity(requestDTO);
        verify(contatoRepository).save(contato);
        verify(contatoMapper).toResponseDTO(contato);
    }

    @Test
    void testCriarContato_ErroAoSalvar() {
        when(contatoMapper.toRequestEntity(requestDTO)).thenReturn(contato);
        when(contatoRepository.save(contato)).thenThrow(new RuntimeException("Erro ao salvar no banco de dados"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatoService.criar(requestDTO);
        });

        assertEquals("Erro ao cadastrar contato: Erro ao salvar no banco de dados", exception.getMessage());

        verify(contatoMapper).toRequestEntity(requestDTO);
        verify(contatoRepository).save(contato);
        verify(contatoMapper, never()).toResponseDTO(contato);
    }

    @Test
    void testBuscarContatos_Sucesso() {

        List<String> fields = Arrays.asList("nome", "contato");

        when(contatoRepository.findAll(any(Specification.class))).thenReturn(contatos);
        when(contatoMapper.toResponseDTO(contatos)).thenReturn(responseDTOs);

        List<Map<String, Object>> resultado = contatoService.buscar("Fixo", fields);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        Map<String, Object> primeiroResultado = resultado.get(0);
        assertEquals("Fixo", primeiroResultado.get("nome"));
        assertEquals("(31) 93333-48312", primeiroResultado.get("contato"));
        assertNull(primeiroResultado.get("createdDate"));

        verify(contatoRepository).findAll(any(Specification.class));
        verify(contatoMapper).toResponseDTO(contatos);
    }

    @Test
    void testBuscarContatos_ErroAoBuscar() {

        List<String> fields = Arrays.asList("nome", "contato");

        when(contatoRepository.findAll(any(Specification.class))).thenThrow(new RuntimeException("Erro ao buscar no banco de dados"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatoService.buscar("Fixo", fields);
        });

        assertEquals("Erro ao buscar contatos: Erro ao buscar no banco de dados", exception.getMessage());

        verify(contatoRepository).findAll(any(Specification.class));
        verify(contatoMapper, never()).toResponseDTO(contatos);
    }

    @Test
    void testBuscarPorId_Sucesso() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));
        when(contatoMapper.toResponseDTO(contato)).thenReturn(responseDTO);

        ContatoResponseDTO resultado = contatoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Fixo", resultado.getNome());
        assertEquals("(31) 93333-48312", resultado.getContato());

        verify(contatoRepository).findById(1L);
        verify(contatoMapper).toResponseDTO(contato);
    }

    @Test
    void testBuscarPorId_ContatoNaoEncontrado() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatoService.buscarPorId(1L);
        });

        assertEquals("Erro ao buscar contato: Contato não encontrado com o ID: 1", exception.getMessage());

        verify(contatoRepository).findById(1L);
        verify(contatoMapper, never()).toResponseDTO(contato);
    }

    @Test
    void testBuscarPorId_ErroAoBuscar() {
        when(contatoRepository.findById(1L)).thenThrow(new RuntimeException("Erro ao buscar no banco de dados"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatoService.buscarPorId(1L);
        });

        assertEquals("Erro ao buscar contato: Erro ao buscar no banco de dados", exception.getMessage());

        verify(contatoRepository).findById(1L);
        verify(contatoMapper, never()).toResponseDTO(contato);
    }

    @Test
    void testAtualizar_Sucesso() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));
        when(contatoRepository.save(contato)).thenReturn(contato);
        when(contatoMapper.toResponseDTO(contato)).thenReturn(responseDTO);

        ContatoResponseDTO resultado = contatoService.atualizar(1L, requestDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Fixo", resultado.getNome());
        assertEquals("(31) 93333-48312", resultado.getContato());

        verify(contatoRepository).findById(1L);
        verify(contatoRepository).save(contato);
        verify(contatoMapper).toResponseDTO(contato);
    }

    @Test
    void testAtualizar_ContatoNaoEncontrado() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatoService.atualizar(1L, requestDTO);
        });

        assertEquals("Erro ao atualizar contato: Contato não encontrado com o ID: 1", exception.getMessage());

        verify(contatoRepository).findById(1L);
        verify(contatoRepository, never()).save(any());
        verify(contatoMapper, never()).toResponseDTO(contato);
    }

    @Test
    void testAtualizar_ErroAoAtualizar() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));
        when(contatoRepository.save(contato)).thenThrow(new RuntimeException("Erro ao salvar no banco de dados"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatoService.atualizar(1L, requestDTO);
        });

        assertEquals("Erro ao atualizar contato: Erro ao salvar no banco de dados", exception.getMessage());

        verify(contatoRepository).findById(1L);
        verify(contatoRepository).save(contato);
        verify(contatoMapper, never()).toResponseDTO(contato);
    }

    @Test
    void testDeletar_Sucesso() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));
        doNothing().when(contatoRepository).delete(contato);

        contatoService.deletar(1L);

        verify(contatoRepository).findById(1L);
        verify(contatoRepository).delete(contato);
    }

    @Test
    void testDeletar_ContatoNaoEncontrado() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatoService.deletar(1L);
        });

        assertEquals("Erro ao deletar contato: Contato não encontrado com o ID: 1", exception.getMessage());

        verify(contatoRepository).findById(1L);
        verify(contatoRepository, never()).delete(contato);
    }

    @Test
    void testDeletar_ErroAoDeletar() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));
        doThrow(new RuntimeException("Erro ao deletar no banco de dados")).when(contatoRepository).delete(contato);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatoService.deletar(1L);
        });

        assertEquals("Erro ao deletar contato: Erro ao deletar no banco de dados", exception.getMessage());

        verify(contatoRepository).findById(1L);
        verify(contatoRepository).delete(contato);
    }

}
