package br.com.fiap.solaraware.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImpactoAcumulado {
    private int id;
    private LocalDate dataRegistro;
    private double emissaoReduzidaTotal;
    private double economiaTotal;
    private String beneficioAmbiental;
}
