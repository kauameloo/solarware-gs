package br.com.fiap.solaraware.dto.erro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidacaoDeErroDto {
    private String mensagem;
    private List<CamposDeErroDto> campos;
}
