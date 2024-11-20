import Link from "next/link";
import { Button } from "@/components/ui/button";
import EmissionsComparison from "@/components/EmissionsComparison";

export default function HomePage() {
  return (
    <div className="space-y-12">
      <section className="text-center">
        <h1 className="text-4xl font-bold mb-4 text-gray-800 dark:text-white">
          Bem-vindo ao SolarWare
        </h1>
        <p className="text-xl mb-8 text-gray-600 dark:text-gray-300">
          Descubra o poder da energia solar para um futuro sustentável
        </p>
        <Button
          asChild
          size="lg"
          className="bg-green-600 hover:bg-green-700 text-white"
        >
          <Link href="/simulator">Experimente nosso simulador</Link>
        </Button>
      </section>

      <section>
        <h2 className="text-2xl font-semibold mb-4 text-gray-800 dark:text-white">
          Comparação de Emissões e Custos
        </h2>
        <EmissionsComparison />
      </section>
    </div>
  );
}
