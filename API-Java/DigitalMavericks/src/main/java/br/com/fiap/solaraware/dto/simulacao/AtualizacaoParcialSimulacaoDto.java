package br.com.fiap.solaraware.dto.simulacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizacaoParcialSimulacaoDto {
    private Integer idUsuario;
    private Double percentEnergiaSolar;
    private Double custoInicial;
    private Integer duracaoAnos;
    private LocalDate dataSimulacao;
}
