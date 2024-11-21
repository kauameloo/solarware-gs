package br.com.fiap.solaraware.dto.casoEstudo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizacaoCasoEstudoDto {
    @NotNull(message = "A localização não pode ser nula!")
    @NotBlank(message = "O campo não pode estar vazio!")
    @Size(min = 5, max= 100, message = "O tamanho deve ser de no minimo 5 e  no máximo 100 caracteres!")
    private String localizacao;

    @NotNull(message = "A descrição não pode ser nula!")
    @NotBlank(message = "O campo não pode estar vazio!")
    @Size(min = 5, max= 100, message = "O tamanho deve ser de no minimo 5 e  no máximo 500 caracteres!")
    private String descricao;

    @NotNull(message = "A emissão reduzida não pode ser nula!")
    @Positive(message = "A emissão reduzida não pode ser zero nem negativa!")
    private Double emissaoReduzida;

    @NotNull(message = "A economia gerada não pode ser nula!")
    @Positive(message = "A economia gerada não pode ser zero nem negativa!")
    private Double economiaGerada;

    @NotNull(message = "Os benefícios ambientais não pode ser nula!")
    @NotBlank(message = "O campo não pode estar vazio!")
    @Size(min = 5, max= 100, message = "O tamanho deve ser de no minimo 5 e  no máximo 500 caracteres!")
    private String beneficiosAmbientais;
}
