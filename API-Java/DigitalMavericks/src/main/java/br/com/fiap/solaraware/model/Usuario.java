package br.com.fiap.solaraware.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataRegistro;

    public Usuario(int id) {
        this.id = id;
    }
}
