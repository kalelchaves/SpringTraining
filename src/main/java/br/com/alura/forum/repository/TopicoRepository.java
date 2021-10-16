package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.totvs.tjf.api.jpa.repository.ApiJpaRepository;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.modelo.Topico;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long>, ApiJpaRepository<Topico>{

	List<Topico> findByCursoNome(String nomeCurso);

}
