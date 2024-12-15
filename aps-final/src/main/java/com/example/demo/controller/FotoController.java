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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Foto;
import com.example.demo.service.FotoService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/fotos")
public class FotoController {
    @Autowired
    private FotoService fotoService;

    // Listar todos os fotoes
    @GetMapping
    public ResponseEntity<List<Foto>> listar() {
        List<Foto> fotoes = fotoService.listar();
        return ResponseEntity.ok(fotoes); // Retorna 200 OK
    }

    // Buscar foto por ID -
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<Foto> buscarPorId(@PathVariable Long id) {
        return fotoService.buscarPorId(id)
                .map(ResponseEntity::ok) // Retorna 200 OK se encontrado
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Retorna 404 Not Found se não encontrado
    }

    // Criar um novo foto
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Foto> criar(@RequestBody Foto foto) {
        Foto novoFoto = fotoService.salvar(foto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFoto); // Retorna 201 Created
    }

    // Atualizar um foto existente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Foto> atualizar(@PathVariable Long id, @RequestBody Foto foto) {
        if (!fotoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 Not Found se o foto não existir
        }
        foto.setId(id);
        Foto fotoAtualizado = fotoService.salvar(foto);
        return ResponseEntity.ok(fotoAtualizado); // Retorna 200 OK se atualizado com sucesso
    }

    // Deletar um foto por ID > localhost:8080/fotos/1
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!fotoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 Not Found se o foto não existir
        }
        fotoService.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content se deletado com sucesso
    }
}
