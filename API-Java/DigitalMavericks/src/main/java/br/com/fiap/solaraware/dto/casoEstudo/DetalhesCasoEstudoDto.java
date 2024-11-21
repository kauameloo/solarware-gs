package br.com.fiap.solaraware.dto.casoEstudo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhesCasoEstudoDto {
    private int id;
    private String localizacao;
    private String descricao;
    private Double emissaoReduzida;
    private Double economiaGerada;
    private String beneficiosAmbientais;
}
