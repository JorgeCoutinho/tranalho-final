package com.example.demo.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Atividade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private String objetivo;
	private String publicoAlvo; // Corrigido o nome
	private boolean publicada;
	private String data;

	@JsonIgnoreProperties("atividades") // Ignorar a lista de atividades no Curso para evitar ciclo
	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;

	@JsonIgnoreProperties("atividades") // Ignorar a lista de atividades na Categoria para evitar ciclo
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@JsonIgnoreProperties("atividade") // Ignorar o campo "atividade" em Foto para evitar ciclo
	@OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL)
	private List<Foto> fotos;

	public Atividade() {}

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getPublicoAlvo() { // Corrigido o nome do método
		return publicoAlvo;
	}

	public void setPublicoAlvo(String publicoAlvo) {
		this.publicoAlvo = publicoAlvo;
	}

	public boolean isPublicada() {
		return publicada;
	}

	public void setPublicada(boolean publicada) {
		this.publicada = publicada;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}
}
