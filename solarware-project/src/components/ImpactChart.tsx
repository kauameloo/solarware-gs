"use client";

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";

type ImpactChartProps = {
  data: Array<{
    year: number;
    savings: number;
    carbonReduced: number;
  }>;
};

export default function ImpactChart({ data }: ImpactChartProps) {
  return (
    <div className="mt-8 h-[400px]">
      <ResponsiveContainer width="100%" height="100%">
        <LineChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="year" />
          <YAxis yAxisId="left" />
          <YAxis yAxisId="right" orientation="right" />
          <Tooltip />
          <Legend />
          <Line
            yAxisId="left"
            type="monotone"
            dataKey="savings"
            stroke="#4ade80"
            name="Economia (R$)"
          />
          <Line
            yAxisId="right"
            type="monotone"
            dataKey="carbonReduced"
            stroke="#60a5fa"
            name="CO2 Reduzido (kg)"
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}
