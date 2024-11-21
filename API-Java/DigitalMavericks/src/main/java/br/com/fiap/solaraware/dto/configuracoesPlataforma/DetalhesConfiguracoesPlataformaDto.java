package br.com.fiap.solaraware.dto.configuracoesPlataforma;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhesConfiguracoesPlataformaDto {
    private int id;
    private String descricao;
    private Double valor;
    private LocalDate dataAtualizacao;
}
