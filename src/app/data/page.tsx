"use client";

import { useState, useEffect } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import CaseStudyCard from "@/components/CaseStudyCard";

type CaseStudy = {
  localizacao: string;
  descricao: string;
  emissaoReduzida: number;
  economiaGerada: number;
  beneficiosAmbientais: string;
};

export default function DataPage() {
  const [caseStudies, setCaseStudies] = useState<CaseStudy[]>([]);

  useEffect(() => {
    const fetchCaseStudies = async () => {
      try {
        const response = await fetch("http://localhost:8080/casos-estudo");
        if (!response.ok) {
          throw new Error("Falha ao buscar casos de estudo");
        }
        const data = await response.json();
        setCaseStudies(data);
      } catch (error) {
        console.error("Erro ao buscar casos de estudo:", error);
      }
    };

    fetchCaseStudies();
  }, []);

  // Estes dados viriam de uma API ou banco de dados
  const impactData = {
    energySaved: 1200000, // Em kWh, energia economizada anualmente por uma instalação solar comercial de médio porte
    carbonReduced: 480000, // Em kg de CO₂ evitado por ano com a geração solar
    averageROI: "4-6 anos", // Tempo médio de retorno de investimento em energia solar
    longTermSavings: 150000, // Em R$, economias projetadas em 10 anos
  };

  return (
    <div className="space-y-8">
      <h1 className="text-3xl font-bold mb-6 text-gray-800 dark:text-white">
        Dados e Benefícios da Energia Solar
      </h1>

      <section>
        <h2 className="text-2xl font-semibold mb-4 text-gray-800 dark:text-white">
          Impacto Acumulado
        </h2>
        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4">
          <Card>
            <CardHeader>
              <CardTitle>Energia Economizada</CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-2xl font-bold">
                {impactData.energySaved.toLocaleString()} kWh
              </p>
            </CardContent>
          </Card>
          <Card>
            <CardHeader>
              <CardTitle>Redução de Carbono</CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-2xl font-bold">
                {impactData.carbonReduced.toLocaleString()} kg CO2
              </p>
            </CardContent>
          </Card>
          <Card>
            <CardHeader>
              <CardTitle>Retorno do Investimento</CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-2xl font-bold">{impactData.averageROI}</p>
            </CardContent>
          </Card>
          <Card>
            <CardHeader>
              <CardTitle>Economia a Longo Prazo</CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-2xl font-bold">
                R$ {impactData.longTermSavings.toLocaleString()}
              </p>
            </CardContent>
          </Card>
        </div>
      </section>

      <section>
        <h2 className="text-2xl font-semibold mb-4 text-gray-800 dark:text-white">
          Estudos de Caso
        </h2>
        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
          {caseStudies.map((study, index) => (
            <CaseStudyCard key={index} {...study} />
          ))}
        </div>
      </section>
    </div>
  );
}
