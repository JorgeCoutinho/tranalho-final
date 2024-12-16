package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.FotoDTO;
import com.example.demo.model.Foto;
import com.example.demo.service.FotoService;
import com.example.demo.service.AtividadeService;

@RestController
@RequestMapping("/fotos")
public class FotoController {

    @Autowired
    private FotoService fotoService;

    @Autowired
    private AtividadeService atividadeService;

    @GetMapping
    public ResponseEntity<List<Foto>> listar() {
        List<Foto> fotos = fotoService.listar();
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<Foto> buscarPorId(@PathVariable Long id) {
        return fotoService.buscarPorId(id)
                .map(ResponseEntity::ok) 
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Foto> criar(@RequestBody FotoDTO fotoDTO) {
        return atividadeService.buscarPorId(fotoDTO.getIdAtividade())
                .map(atividade -> {
                    Foto novaFoto = new Foto();
                    novaFoto.setUrl(fotoDTO.getUrl());
                    novaFoto.setLegenda(fotoDTO.getLegenda());
                    novaFoto.setAtividade(atividade);

                    Foto fotoSalva = fotoService.salvar(novaFoto);
                    return ResponseEntity.status(HttpStatus.CREATED).body(fotoSalva); 
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); 
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Foto> atualizar(@PathVariable Long id, @RequestBody FotoDTO fotoDTO) {
        if (!fotoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }

        return atividadeService.buscarPorId(fotoDTO.getIdAtividade())
                .map(atividade -> {
                    Foto fotoExistente = fotoService.buscarPorId(id).get();
                    fotoExistente.setUrl(fotoDTO.getUrl());
                    fotoExistente.setLegenda(fotoDTO.getLegenda());
                    fotoExistente.setAtividade(atividade);

                    Foto fotoAtualizada = fotoService.salvar(fotoExistente);
                    return ResponseEntity.ok(fotoAtualizada);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); 
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!fotoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
        fotoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
