package com.example.demo.dtos;

public class CursoDTO {
    private String nome;
    private AtividadeDTO atividade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AtividadeDTO getAtividade() {
        return atividade;
    }

    public void setAtividade(AtividadeDTO atividade) {
        this.atividade = atividade;
    }

    
}
