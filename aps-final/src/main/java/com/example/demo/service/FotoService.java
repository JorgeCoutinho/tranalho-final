package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Foto;
import com.example.demo.repository.FotoRepository;

@Service
public class FotoService {
   @Autowired
    private FotoRepository fotoRepository;

    public List<Foto> listar() {
        return fotoRepository.findAll();
    }

    public Optional<Foto> buscarPorId(Long id) {
        return fotoRepository.findById(id);
    }

    public List<Foto> buscarPorIds(List<Long> ids) {
        return fotoRepository.findAllById(ids);
    }

    public Foto salvar(Foto foto) {
        return fotoRepository.save(foto);
    }

    public void deletar(Long id) {
        fotoRepository.deleteById(id);
    }
}
