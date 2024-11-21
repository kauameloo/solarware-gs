package br.com.fiap.solaraware.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfiguracoesPlataforma {
    private int id;
    private String descricao;
    private double valor;
    private LocalDate dataAtualizacao;
}
