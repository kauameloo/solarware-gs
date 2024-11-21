package br.com.fiap.solaraware.dto.logsAcessos;

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
public class AtualizacaoParcialLogsAcessosDto {
    private Integer idUsuario;
    private LocalDate dataAcesso;
    private String acaoRealizada;
}
