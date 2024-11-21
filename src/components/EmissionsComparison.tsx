"use client";

import { useState } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Slider } from "@/components/ui/slider";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";

export default function EmissionsComparison() {
  const [solarPercentage, setSolarPercentage] = useState(50);

  // Simulação de dados - isso seria substituído por cálculos reais baseados na API
  const data = [
    {
      name: "Emissões de CO2",
      solar: 100 - solarPercentage,
      fossil: 200,
    },
    {
      name: "Custos a Longo Prazo",
      solar: 150,
      fossil: 300 - solarPercentage,
    },
  ];

  return (
    <Card>
      <CardHeader>
        <CardTitle>
          Comparação Interativa: Energia Solar vs. Combustíveis Fósseis
        </CardTitle>
        <CardDescription>
          Ajuste a porcentagem de uso de energia solar para ver o impacto
        </CardDescription>
      </CardHeader>
      <CardContent>
        <div className="mb-6">
          <label
            htmlFor="solar-percentage"
            className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2"
          >
            Porcentagem de Energia Solar: {solarPercentage}%
          </label>
          <Slider
            id="solar-percentage"
            min={0}
            max={100}
            step={1}
            value={[solarPercentage]}
            onValueChange={(value) => setSolarPercentage(value[0])}
          />
        </div>
        <ResponsiveContainer width="100%" height={300}>
          <BarChart data={data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="solar" fill="#4ade80" name="Energia Solar" />
            <Bar dataKey="fossil" fill="#f87171" name="Combustíveis Fósseis" />
          </BarChart>
        </ResponsiveContainer>
      </CardContent>
    </Card>
  );
}
