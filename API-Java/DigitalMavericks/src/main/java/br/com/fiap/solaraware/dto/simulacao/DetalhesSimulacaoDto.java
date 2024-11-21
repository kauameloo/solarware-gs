package br.com.fiap.solaraware.dto.simulacao;

import br.com.fiap.solaraware.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhesSimulacaoDto {
    private int id;
    private Usuario usuario;
    private Double percentEnergiaSolar;
    private Double custoInicial;
    private Integer duracaoAnos;
    private LocalDate dataSimulacao;
}
