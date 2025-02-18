package com.aleffalves.api_simples.service;

import com.aleffalves.api_simples.dto.ContatoRequestDTO;
import com.aleffalves.api_simples.dto.ContatoResponseDTO;
import com.aleffalves.api_simples.dto.ProfissionalResponseDTO;
import com.aleffalves.api_simples.mapper.ContatoMapper;
import com.aleffalves.api_simples.model.Contato;
import com.aleffalves.api_simples.model.Profissional;
import com.aleffalves.api_simples.repository.ContatoRepository;
import com.aleffalves.api_simples.specification.ContatoSpecification;
import com.aleffalves.api_simples.specification.ProfissionalSpecification;
import com.aleffalves.api_simples.utils.FilterDtoUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ContatoMapper contatoMapper;

    public ContatoResponseDTO criar(@Valid ContatoRequestDTO contatoRequestDTO) {
        try {
            Contato profissional = contatoRepository.save(contatoMapper.toRequestEntity(contatoRequestDTO));
            return contatoMapper.toResponseDTO(profissional);
        }catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar contato: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> buscar(String q, List<String> fields) {
        try {
            Specification<Contato> spec = ContatoSpecification.comTexto(q);
            List<Contato> all = contatoRepository.findAll(spec);
            List<ContatoResponseDTO> responseDTOs = contatoMapper.toResponseDTO(all);

            return responseDTOs.stream()
                    .map(response -> FilterDtoUtils.filterFields(response, fields))
                    .toList();

        }catch (Exception e){
            throw new RuntimeException("Erro ao buscar contatos: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public ContatoResponseDTO buscarPorId(Long id) {
        try {
            Optional<Contato> contatoOptional = contatoRepository.findById(id);
            if(contatoOptional.isPresent()){
                Contato contato = contatoOptional.get();
                return contatoMapper.toResponseDTO(contato);
            } else {
                throw new RuntimeException("Contato não encontrado com o ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar contato: " + e.getMessage(), e);
        }
    }

    public ContatoResponseDTO atualizar(Long id, @Valid ContatoRequestDTO contatoRequestDTO) {
        try {
            Optional<Contato> contatoOptional = contatoRepository.findById(id);

            if (contatoOptional.isPresent()) {
                Contato contato = contatoOptional.get();

                contato.setNome(contatoRequestDTO.getNome());
                contato.setContato(contatoRequestDTO.getContato());

                return contatoMapper.toResponseDTO(contatoRepository.save(contato));
            } else {
                throw new RuntimeException("Contato não encontrado com o ID: " + id);
            }
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar contato: " + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        try {
            Optional<Contato> contatoOptional = contatoRepository.findById(id);

            if (contatoOptional.isPresent()) {
                contatoRepository.delete(contatoOptional.get());
            } else {
                throw new RuntimeException("Contato não encontrado com o ID: " + id);
            }
        }catch (Exception e){
            throw new RuntimeException("Erro ao deletar contato: " + e.getMessage(), e);
        }
    }
}
