package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.totvs.tjf.api.context.stereotype.ApiError;
import com.totvs.tjf.api.context.stereotype.ApiGuideline;
import com.totvs.tjf.api.context.stereotype.ApiGuideline.ApiGuidelineVersion;
import com.totvs.tjf.api.context.v2.request.ApiFieldRequest;
import com.totvs.tjf.api.context.v2.request.ApiPageRequest;
import com.totvs.tjf.api.context.v2.request.ApiSortRequest;
import com.totvs.tjf.api.context.v2.response.ApiCollectionResponse;
import com.totvs.tjf.core.api.jpa.repository.ApiJpaCollectionResult;

import br.com.alura.forum.controller.dto.DetalhesTopicoDTO;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.exception.TopicoNaoEncontradoException;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.controller.repository.CursoRepository;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/api/v1/topicos")
@ApiGuideline(ApiGuidelineVersion.V2)
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public ApiCollectionResponse<Topico> lista(ApiFieldRequest field, ApiPageRequest page, ApiSortRequest sort ,String nomeCurso) {
		ApiJpaCollectionResult<Topico> topicos;
		
		/*TODO como implementar filtro por curso?? */
		/*if (nomeCurso != null) {
			topicos = topicoRepository.findByCursoNome(nomeCurso);
		} else {
			topicos = topicoRepository.findAllProjected(field, page, sort);
		}*/
		
		topicos = topicoRepository.findAllProjected(field, page, sort, null);

		return ApiCollectionResponse.from(topicos);
	}

	@PostMapping
	@Transactional
	public Topico cadastrar(@RequestBody @Valid Topico form, UriComponentsBuilder uriBuilder) {
		return topicoRepository.saveAndFlush(form);
	}


	@GetMapping("/{id}")
	public DetalhesTopicoDTO detalhar(@PathVariable Long id) {
		return new DetalhesTopicoDTO(topicoRepository.findById(id).orElseThrow(() -> {
			throw new TopicoNaoEncontradoException(id);
		}));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {

		Optional<Topico> topico = topicoRepository.findById(id);

		if (topico.isPresent()) {
			Topico topicoAtualizado = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDTO(topicoAtualizado));
		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		
		Optional<Topico> topico = topicoRepository.findById(id);

		if (topico.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
		
	}

}
