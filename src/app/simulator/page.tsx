import SimulatorForm from "@/components/SimulatorForm";

export default function SimulatorPage() {
  return (
    <div className="space-y-8">
      <h1 className="text-3xl font-bold mb-6 text-gray-800 dark:text-white">
        Simulador de Impacto da Energia Solar
      </h1>
      <SimulatorForm />
    </div>
  );
}
