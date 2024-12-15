package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Atividade;
import com.example.demo.repository.AtividadeRepository;

@Service
public class AtividadeService {
   @Autowired
    private AtividadeRepository atividadeRepository;

    public List<Atividade> listar() {
        return atividadeRepository.findAll();
    }

    public Optional<Atividade> buscarPorId(Long id) {
        return atividadeRepository.findById(id);
    }

    public List<Atividade> buscarPorIds(List<Long> ids) {
        return atividadeRepository.findAllById(ids);
    }

    public Atividade salvar(Atividade atividade) {
        return atividadeRepository.save(atividade);
    }

    public void deletar(Long id) {
        atividadeRepository.deleteById(id);
    }
}
