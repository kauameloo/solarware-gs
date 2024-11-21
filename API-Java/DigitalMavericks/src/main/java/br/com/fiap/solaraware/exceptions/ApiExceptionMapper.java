package br.com.fiap.solaraware.exceptions;

import br.com.fiap.solaraware.dto.erro.MensagemDeErroDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        //Validar o tipo de exception que será tratada
        if (e instanceof IdNaoEncontradoException){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new MensagemDeErroDto(e.getMessage()))
                    .build();

        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new MensagemDeErroDto(e.getMessage()))
                .build();
    }
}