package br.com.fiap.solaraware.dto.resulatadoSimulacao;

import br.com.fiap.solaraware.model.Simulacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroResultadoSimulacaoDto {
    @NotNull(message = "O ID do usuario não pode ser nulo!")
    private Integer idSimulacao;

    @NotNull(message = "A emissão não pode ser nula!")
    @Positive(message = "A emissão não pode ser zero nem negativa!")
    private Double emissaoReduzida;

    @NotNull(message = "A economia não pode ser nula!")
    @Positive(message = "A economia não pode ser zero nem negativa!")
    private Double economiaFinanceira;

    @NotNull(message = "O benefício não pode ser nulo!")
    @NotBlank(message = "O campo não pode estar vazio!")
    private String beneficioAmbiental;

    @NotNull(message = "O custo não pode ser nulo!")
    @Positive(message = "O custo não pode ser zero nem negativo!")
    private Double custoTotalProjetado;
}
