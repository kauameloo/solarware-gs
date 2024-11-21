package br.com.fiap.solaraware.dto.contatos;

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
public class CadastroContatosDto {
    @NotNull(message = "O nome não pode ser nulo!")
    @NotBlank(message = "O campo não pode estar vazio!")
    @Size(min = 5, max= 100, message = "O tamanho deve ser de no minimo 5 e  no máximo 100 caracteres!")
    private String nome;

    @NotNull(message = "O email não pode ser nulo!")
    @NotBlank(message = "O campo não pode estar vazio!")
    @Size(min = 5, max= 100, message = "O tamanho deve ser de no minimo 5 e  no máximo 100 caracteres!")
    private String email;

    @NotNull(message = "O telefone não pode ser nulo!")
    @NotBlank (message = "O campo não pode estar vazio!")
    @Size(min = 14, max= 15, message = "Telefone inválido! O formato deve ser (11) 12345-6789")
    private String telefone;

    @NotNull(message = "A mensagem não pode ser nula!")
    @NotBlank(message = "O campo não pode estar vazio!")
    @Size(min = 5, max= 500, message = "O tamanho deve ser de no minimo 5 e  no máximo 500 caracteres!")
    private String mensagem;

    @NotNull(message = "A data não pode ser nula!")
    @PastOrPresent(message = "A data não pode estar no futuro!")
    private LocalDate dataContato;
}
