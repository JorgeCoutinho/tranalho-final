package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade,Long>{

   List<Atividade> findByData(String data);

   List<Atividade> findByCategoriaId(Long idCategoria);

   List<Atividade> findByCursoId(Long idCurso);

   List<Atividade> findByDataAndCategoriaIdAndCursoId(String data, Long idCategoria, Long idCurso);

   List<Atividade> findByDataAndCategoriaId(String data, Long idCategoria);

   List<Atividade> findByDataAndCursoId(String data, Long idCurso);

   List<Atividade> findByCategoriaIdAndCursoId(Long idCategoria, Long idCurso);
}
