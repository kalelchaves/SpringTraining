package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.totvs.tjf.api.jpa.repository.ApiJpaRepository;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>, ApiJpaRepository<TopicoDTO>{

	List<Topico> findByCursoNome(String nomeCurso);

}
