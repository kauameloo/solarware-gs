package br.com.fiap.solaraware.dto.resulatadoSimulacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizacaoParcialResultadoSimulacaoDto {
    private Integer idSimulacao;
    private Double emissaoReduzida;
    private Double economiaFinanceira;
    private String beneficioAmbiental;
    private Double custoTotalProjetado;
}
