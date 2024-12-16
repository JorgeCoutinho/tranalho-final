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


    public List<Atividade> listarAtividadesFiltradas(String data, Long idCategoria, Long idCurso) {
        if (data != null && idCategoria != null && idCurso != null) {
            return atividadeRepository.findByDataAndCategoriaIdAndCursoId(data, idCategoria, idCurso);
        } else if (data != null && idCategoria != null) {
            return atividadeRepository.findByDataAndCategoriaId(data, idCategoria);
        } else if (data != null && idCurso != null) {
            return atividadeRepository.findByDataAndCursoId(data, idCurso);
        } else if (idCategoria != null && idCurso != null) {
            return atividadeRepository.findByCategoriaIdAndCursoId(idCategoria, idCurso);
        } else if (data != null) {
            return atividadeRepository.findByData(data);
        } else if (idCategoria != null) {
            return atividadeRepository.findByCategoriaId(idCategoria);
        } else if (idCurso != null) {
            return atividadeRepository.findByCursoId(idCurso);
        } else {
            return atividadeRepository.findAll();
        }
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
