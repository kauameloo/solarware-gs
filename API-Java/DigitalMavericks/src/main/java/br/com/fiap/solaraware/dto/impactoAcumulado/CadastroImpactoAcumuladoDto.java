package br.com.fiap.solaraware.dto.impactoAcumulado;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroImpactoAcumuladoDto {
    @NotNull(message = "A data não pode ser nula!")
    @PastOrPresent(message = "A data não pode estar no futuro!")
    private LocalDate dataRegistro;

    @NotNull(message = "A emissão total reduzida não pode ser nula!")
    @Positive(message = "A emissão total reduzida não pode ser zero nem negativa!")
    private Double emissaoReduzidaTotal;

    @NotNull(message = "A economia total não pode ser nula!")
    @Positive(message = "A economia total não pode ser zero nem negativa!")
    private Double economiaTotal;

    @NotNull(message = "Os benefícios ambientais não pode ser nula!")
    @NotBlank(message = "O campo não pode estar vazio!")
    @Size(min = 5, max= 100, message = "O tamanho deve ser de no minimo 5 e  no máximo 500 caracteres!")
    private String beneficioAmbiental;
}
