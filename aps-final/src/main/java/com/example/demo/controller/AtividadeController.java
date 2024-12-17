package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dtos.AtividadeDTO;
import com.example.demo.model.Atividade;
import com.example.demo.service.AtividadeService;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @GetMapping
    public ResponseEntity<List<Atividade>> listarAtividadesFiltradas(
            @RequestParam(required = false) String data,
            @RequestParam(required = false) Long idCategoria,
            @RequestParam(required = false) Long idCurso
    ) {
        
        List<Atividade> atividades = atividadeService.listarAtividadesFiltradas(data, idCategoria, idCurso);
        return ResponseEntity.ok(atividades);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<Atividade> buscarPorId(@PathVariable Long id) {
        return atividadeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Atividade> criar(@RequestBody AtividadeDTO atividadeDTO) {
        Atividade atividade = new Atividade();
        atividade.setNome(atividadeDTO.getNome());
        atividade.setObjetivo(atividadeDTO.getObjetivo());
        atividade.setPublicoAlvo(atividadeDTO.getPublicoAlvo());
        atividade.setPublicada(atividadeDTO.isPublicada());
        atividade.setData(atividadeDTO.getData());

        Atividade novaAtividade = atividadeService.salvar(atividade);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAtividade);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIA')")
    public ResponseEntity<Atividade> atualizar(@PathVariable Long id, @RequestBody AtividadeDTO atividadeDTO) {
        return atividadeService.buscarPorId(id).map(atividadeExistente -> {
            atividadeExistente.setNome(atividadeDTO.getNome());
            atividadeExistente.setObjetivo(atividadeDTO.getObjetivo());
            atividadeExistente.setPublicoAlvo(atividadeDTO.getPublicoAlvo());
            atividadeExistente.setPublicada(atividadeDTO.isPublicada());
            atividadeExistente.setData(atividadeDTO.getData());

            Atividade atividadeAtualizada = atividadeService.salvar(atividadeExistente);
            return ResponseEntity.ok(atividadeAtualizada);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!atividadeService.buscarPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        atividadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
