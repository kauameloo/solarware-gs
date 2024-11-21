package br.com.fiap.solaraware.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contatos {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String mensagem;
    private LocalDate dataContato;
}
