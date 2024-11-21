package br.com.fiap.solaraware.dto.parametrosClimaticos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhesParametrosClimaticosDto {
    private int id;
    private String localizacao;
    private Double insolacaoMedia;
    private Double temperaturaMedia;
    private LocalDate dataRegistro;
}
