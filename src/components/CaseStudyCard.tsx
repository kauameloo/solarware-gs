import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";

type CaseStudyCardProps = {
  localizacao: string;
  descricao: string;
  emissaoReduzida: number;
  economiaGerada: number;
  beneficiosAmbientais: string;
};

export default function CaseStudyCard({
  localizacao,
  descricao,
  emissaoReduzida,
  economiaGerada,
  beneficiosAmbientais,
}: CaseStudyCardProps) {
  return (
    <Card>
      <CardHeader>
        <CardTitle>{localizacao}</CardTitle>
      </CardHeader>
      <CardContent>
        <p className="mb-2">{descricao}</p>
        <p className="mb-2">
          Emissão Reduzida: {emissaoReduzida.toFixed(2)} kg CO2
        </p>
        <p className="mb-2">Economia Gerada: R$ {economiaGerada.toFixed(2)}</p>
        <p className="font-semibold">{beneficiosAmbientais}</p>
      </CardContent>
    </Card>
  );
}
