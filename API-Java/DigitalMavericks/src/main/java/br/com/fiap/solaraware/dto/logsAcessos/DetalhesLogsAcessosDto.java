package br.com.fiap.solaraware.dto.logsAcessos;

import br.com.fiap.solaraware.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhesLogsAcessosDto {
    private int id;
    private Usuario usuario;
    private LocalDate dataAcesso;
    private String acaoRealizada;
}
