package br.com.fiap.solaraware.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParametrosClimaticos {
    private int id;
    private String localizacao;
    private double insolacaoMedia;
    private double temperaturaMedia;
    private LocalDate dataRegistro;
}
