package br.com.fiap.solaraware.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhesUsuarioDto {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataRegistro;
}
