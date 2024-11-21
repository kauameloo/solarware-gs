package br.com.fiap.solaraware.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CasoEstudo {
    private int id;
    private String localizacao;
    private String descricao;
    private double emissaoReduzida;
    private double economiaGerada;
    private String beneficiosAmbientais;
}
