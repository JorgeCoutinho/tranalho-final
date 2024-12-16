package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dtos.CursoDTO;
import com.example.demo.model.Curso;
import com.example.demo.service.CursoService;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        List<Curso> cursos = cursoService.listar();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Curso> criar(@RequestBody CursoDTO cursoDTO) {
        Curso curso = new Curso();
        curso.setNome(cursoDTO.getNome());

        Curso novoCurso = cursoService.salvar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCurso);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Curso> atualizar(@PathVariable Long id, @RequestBody CursoDTO cursoDTO) {
        return cursoService.buscarPorId(id).map(cursoExistente -> {
            cursoExistente.setNome(cursoDTO.getNome());

            Curso cursoAtualizado = cursoService.salvar(cursoExistente);
            return ResponseEntity.ok(cursoAtualizado);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!cursoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        cursoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
