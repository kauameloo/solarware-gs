package br.com.fiap.solaraware.dto.logsAcessos;

import br.com.fiap.solaraware.model.Usuario;
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
public class CadastroLogsAcessosDto {
    @NotNull(message = "O ID do usuario não pode ser nulo!")
    private Integer idUsuario;

    @NotNull(message = "A data não pode ser nula!")
    @PastOrPresent(message = "A data não pode estar no futuro!")
    private LocalDate dataAcesso;

    @NotNull(message = "A ação realizada não pode ser nula!")
    @NotBlank(message = "O campo não pode estar vazio!")
    @Size(min = 5, max= 200, message = "O tamanho deve ser de no minimo 5 e  no máximo 200 caracteres!")
    private String acaoRealizada;
}
