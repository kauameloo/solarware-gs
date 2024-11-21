package br.com.fiap.solaraware.dto.resulatadoSimulacao;

import br.com.fiap.solaraware.model.Simulacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhesResultadoSimulacaoDto {
    private int id;
    private Simulacao simulacao;
    private double emissaoReduzida;
    private double economiaFinanceira;
    private String beneficioAmbiental;
    private double custoTotalProjetado;
}
