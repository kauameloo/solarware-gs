package br.com.fiap.solaraware.dto.simulacao;

import br.com.fiap.solaraware.model.Usuario;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizacaoSimulacaoDto {
    @NotNull(message = "O ID do usuario não pode ser nulo!")
    private Integer idUsuario;

    @NotNull(message = "O percentual não pode ser nulo!")
    @Positive(message = "O percentual não pode ser zero nem negativo!")
    private Double percentEnergiaSolar;

    @NotNull(message = "O custo não pode ser nulo!")
    @Positive(message = "O custo não pode ser zero nem negativo!")
    private Double custoInicial;

    @NotNull(message = "A duração não pode ser nula!")
    @PositiveOrZero(message = "A duração não pode ser negativa!")
    private Integer duracaoAnos;

    @NotNull(message = "A data não pode ser nula!")
    @PastOrPresent(message = "A data não pode estar no futuro!")
    private LocalDate dataSimulacao;
}
