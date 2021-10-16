package br.com.alura.forum.controller.exception;

import org.springframework.http.HttpStatus;

import com.totvs.tjf.api.context.stereotype.ApiError;
import com.totvs.tjf.api.context.stereotype.ApiErrorParameter;

@ApiError(status = HttpStatus.NOT_FOUND, value = "TopicoNaoEncontradoException")
public class TopicoNaoEncontradoException extends RuntimeException {
	
	  @ApiErrorParameter
	  private final Long id;

	  public TopicoNaoEncontradoException(Long id) {
	    this.id = id;
	  }

	  public Long getId() {
	    return id;
	  }

}
