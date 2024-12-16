package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dtos.CategoriaDTO;
import com.example.demo.model.Categoria;
import com.example.demo.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        List<Categoria> categorias = categoriaService.listar();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categoria> criar(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());

        Categoria novaCategoria = categoriaService.salvar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.buscarPorId(id).map(categoriaExistente -> {
            categoriaExistente.setNomeCategoria(categoriaDTO.getNomeCategoria());

            Categoria categoriaAtualizada = categoriaService.salvar(categoriaExistente);
            return ResponseEntity.ok(categoriaAtualizada);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!categoriaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
