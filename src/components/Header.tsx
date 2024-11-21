"use client";

import Link from "next/link";
import { useState } from "react";
import { Sun, Moon, Menu, X } from "lucide-react";
import { Button } from "@/components/ui/button";

export default function Header() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isDarkMode, setIsDarkMode] = useState(false);

  const toggleDarkMode = () => {
    setIsDarkMode(!isDarkMode);
    document.documentElement.classList.toggle("dark");
  };

  return (
    <header className="bg-white dark:bg-gray-800 shadow-md">
      <div className="container mx-auto px-4 py-4">
        <div className="flex items-center justify-between">
          <Link
            href="/"
            className="text-2xl font-bold text-green-600 dark:text-green-400"
          >
            SolarWare
          </Link>
          <div className="hidden md:flex space-x-4">
            <NavLinks />
          </div>
          <div className="flex items-center space-x-2">
            <Button variant="ghost" size="icon" onClick={toggleDarkMode}>
              {isDarkMode ? (
                <Sun className="h-5 w-5" />
              ) : (
                <Moon className="h-5 w-5" />
              )}
            </Button>
            <Button
              variant="ghost"
              size="icon"
              className="md:hidden"
              onClick={() => setIsMenuOpen(!isMenuOpen)}
            >
              {isMenuOpen ? (
                <X className="h-5 w-5" />
              ) : (
                <Menu className="h-5 w-5" />
              )}
            </Button>
          </div>
        </div>
        {isMenuOpen && (
          <div className="mt-4 md:hidden">
            <NavLinks />
          </div>
        )}
      </div>
    </header>
  );
}

function NavLinks() {
  return (
    <>
      <Link
        href="/"
        className="text-gray-700 hover:text-green-600 dark:text-gray-300 dark:hover:text-green-400"
      >
        Home
      </Link>
      <Link
        href="/simulator"
        className="text-gray-700 hover:text-green-600 dark:text-gray-300 dark:hover:text-green-400"
      >
        Simulador
      </Link>
      <Link
        href="/data"
        className="text-gray-700 hover:text-green-600 dark:text-gray-300 dark:hover:text-green-400"
      >
        Dados e Benefícios
      </Link>
      <Link
        href="/contact"
        className="text-gray-700 hover:text-green-600 dark:text-gray-300 dark:hover:text-green-400"
      >
        Contato
      </Link>
      <Link
        href="/team"
        className="text-gray-700 hover:text-green-600 dark:text-gray-300 dark:hover:text-green-400"
      >
        Equipe
      </Link>
    </>
  );
}
