package br.com.fiap.solaraware.dto.relatorio;

import br.com.fiap.solaraware.model.Simulacao;
import br.com.fiap.solaraware.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizacaoParcialRelatorioDto {
    private Integer idUsuario;
    private Integer idSimulacao;
    private String tipoRelatorio;
    private LocalDate dataGeracao;
}
