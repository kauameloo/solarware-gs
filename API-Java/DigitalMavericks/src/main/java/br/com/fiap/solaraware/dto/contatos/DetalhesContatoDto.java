package br.com.fiap.solaraware.dto.contatos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhesContatoDto {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String mensagem;
    private LocalDate dataContato;
}
