package br.com.fiap.solaraware.exceptions;

import br.com.fiap.solaraware.dto.erro.CamposDeErroDto;
import br.com.fiap.solaraware.dto.erro.ValidacaoDeErroDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;

@Provider
public class ApiValidationMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<CamposDeErroDto> errosCampos = new ArrayList<>();
        //Percorrer cada erro encontrado nos campos
        for(ConstraintViolation<?> c : e.getConstraintViolations()){
            CamposDeErroDto camposDeErroDto = new CamposDeErroDto();
            camposDeErroDto.setCampo(c.getPropertyPath().toString());
            camposDeErroDto.setMensagem(c.getMessage());
            errosCampos.add(camposDeErroDto);
        }
        //Retornar o Bad Request com os erros
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ValidacaoDeErroDto("Dados inválidos", errosCampos))
                .build();
    }
}
