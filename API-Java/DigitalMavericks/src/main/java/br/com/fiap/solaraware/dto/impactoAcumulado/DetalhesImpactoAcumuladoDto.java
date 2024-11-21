package br.com.fiap.solaraware.dto.impactoAcumulado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhesImpactoAcumuladoDto {
    private int id;
    private LocalDate dataRegistro;
    private Double emissaoReduzidaTotal;
    private Double economiaTotal;
    private String beneficioAmbiental;
}
