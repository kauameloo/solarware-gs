package br.com.fiap.solaraware.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoSimulacao {
    private int id;
    private Simulacao simulacao;
    private double emissaoReduzida;
    private double economiaFinanceira;
    private String beneficioAmbiental;
    private double custoTotalProjetado;

}
