package br.com.fiap.solaraware.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Relatorio {
    private int id;
    private Usuario usuario;
    private Simulacao simulacao;
    private String tipoRelatorio;
    private LocalDate dataGeracao;
}
