package br.com.fiap.solaraware.dto.relatorio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroRelatorioDto {
    @NotNull(message = "O ID do usuario não pode ser nulo!")
    private Integer idUsuario;

    @NotNull(message = "O ID da simulação não pode ser nulo!")
    private Integer idSimulacao;

    @NotNull(message = "O tipo do relatorio não pode ser nulo!")
    @NotBlank(message = "O campo não pode estar vazio!")
    @Size(min = 1, max= 20, message = "O tamanho deve ser de no minimo 5 e  no máximo 20 caracteres!")
    private String tipoRelatorio;

    @NotNull(message = "A data não pode ser nula!")
    @PastOrPresent(message = "A data não pode estar no futuro!")
    private LocalDate dataGeracao;
}
