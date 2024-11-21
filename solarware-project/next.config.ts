import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: 'http://localhost:8080/:path*', // Substitua pela URL do seu backend Java
      },
    ]
  },
};

export default nextConfig;
