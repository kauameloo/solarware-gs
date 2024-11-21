"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Slider } from "@/components/ui/slider";
import ImpactChart from "./ImpactChart";

export default function SimulatorForm() {
  const [percentSolar, setPercentSolar] = useState(50);
  const [years, setYears] = useState(10);
  const [initialCost, setInitialCost] = useState(10000);
  const [results, setResults] = useState<any>(null);

  const handleSimulate = async () => {
    // Aqui você faria a chamada para a API Python para processar os dados
    // const response = await fetch('/api/simulate', {
    //   method: 'POST',
    //   headers: { 'Content-Type': 'application/json' },
    //   body: JSON.stringify({ percentSolar, years, initialCost }),
    // });
    // const data = await response.json();
    // setResults(data);

    // Por enquanto, vamos simular alguns resultados
    const simulatedResults = {
      carbonReduction: percentSolar * years * 0.5,
      financialSavings: percentSolar * years * 100 - initialCost,
      environmentalBenefits: `Equivalente a plantar ${
        percentSolar * years
      } árvores`,
      yearlyData: Array.from({ length: years }, (_, i) => ({
        year: i + 1,
        savings: percentSolar * 100 * (i + 1) - initialCost,
        carbonReduced: percentSolar * 0.5 * (i + 1),
      })),
    };
    setResults(simulatedResults);
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle>Configure sua Simulação</CardTitle>
        <CardDescription>
          Ajuste os parâmetros para ver o impacto da energia solar
        </CardDescription>
      </CardHeader>
      <CardContent className="space-y-4">
        <div>
          <Label htmlFor="percentSolar">
            Porcentagem de uso de energia solar
          </Label>
          <Slider
            id="percentSolar"
            min={0}
            max={100}
            step={1}
            value={[percentSolar]}
            onValueChange={(value) => setPercentSolar(value[0])}
          />
          <span className="text-sm text-gray-500 dark:text-gray-400">
            {percentSolar}%
          </span>
        </div>
        <div>
          <Label htmlFor="years">Número de anos</Label>
          <Input
            id="years"
            type="number"
            value={years}
            onChange={(e) => setYears(Number(e.target.value))}
            min={1}
            max={30}
          />
        </div>
        <div>
          <Label htmlFor="initialCost">Custo inicial (R$)</Label>
          <Input
            id="initialCost"
            type="number"
            value={initialCost}
            onChange={(e) => setInitialCost(Number(e.target.value))}
            min={0}
          />
        </div>
      </CardContent>
      <CardFooter>
        <Button onClick={handleSimulate}>Simular</Button>
      </CardFooter>
      {results && (
        <CardContent>
          <h2 className="text-xl font-semibold mb-4">
            Resultados da Simulação:
          </h2>
          <p>Redução de carbono: {results.carbonReduction.toFixed(2)} kg CO2</p>
          <p>Economia financeira: R$ {results.financialSavings.toFixed(2)}</p>
          <p>Benefícios ambientais: {results.environmentalBenefits}</p>
          <ImpactChart data={results.yearlyData} />
        </CardContent>
      )}
    </Card>
  );
}
