package br.com.fiap.solaraware.dto.configuracoesPlataforma;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizacaoConfiguracoesPlataformaDto {
    @NotNull(message = "A descrição não pode ser nula!")
    @NotBlank(message = "O campo não pode estar vazio!")
    @Size(min = 5, max= 200, message = "O tamanho deve ser de no minimo 5 e  no máximo 200 caracteres!")
    private String descricao;

    @NotNull(message = "O valor não pode ser nulo!")
    @Positive(message = "O valor média não pode ser zero nem negativo!")
    private Double valor;

    @NotNull(message = "A data não pode ser nula!")
    @PastOrPresent(message = "A data não pode estar no futuro!")
    private LocalDate dataAtualizacao;
}
