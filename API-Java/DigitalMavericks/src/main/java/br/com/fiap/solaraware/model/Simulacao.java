package br.com.fiap.solaraware.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Simulacao {
    private int id;
    private Usuario usuario;
    private double percentEnergiaSolar;
    private double custoInicial;
    private int duracaoAnos;
    private LocalDate dataSimulacao;

    public Simulacao(int id) {
        this.id = id;
    }

    //Métodos
    public double calcularEconomiaAcumulada() {
        double economiaAnual = this.percentEnergiaSolar * this.custoInicial * 0.10; // Exemplo de taxa de economia anual de 10%
        return economiaAnual * this.duracaoAnos;
    }

    public double estimarReducaoEmissoes() {
        double fatorEmissao = 0.25; // Exemplo: 0.25 kg CO₂ por unidade monetária economizada
        return calcularEconomiaAcumulada() * fatorEmissao;
    }
}
