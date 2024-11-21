package br.com.fiap.solaraware.dto.parametrosClimaticos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroParametrosClimaticosDto {
    @NotNull(message = "A localização não pode ser nula!")
    @NotBlank(message = "O campo não pode estar vazio!")
    @Size(min = 5, max= 100, message = "O tamanho deve ser de no minimo 5 e  no máximo 100 caracteres!")
    private String localizacao;

    @NotNull(message = "A insolação média não pode ser nula!")
    @Positive(message = "A insolação média reduzida não pode ser zero nem negativa!")
    private Double insolacaoMedia;

    @NotNull(message = "A temperatura média não pode ser nula!")
    @Positive(message = "A temperatura média não pode ser zero nem negativa!")
    private Double temperaturaMedia;

    @NotNull(message = "A data não pode ser nula!")
    @PastOrPresent(message = "A data não pode estar no futuro!")
    private LocalDate dataRegistro;
}
