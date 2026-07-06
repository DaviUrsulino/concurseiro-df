import type { Metadata } from "next";
import { Inter, Outfit } from "next/font/google";
import "./globals.css";
import Providers from "./Providers";
import SkipLink from "../components/ui/SkipLink";
import Link from "next/link";
import { BookOpen } from "lucide-react";

import { ThemeToggle } from "@/components/ThemeToggle";

const inter = Inter({
  variable: "--font-inter",
  subsets: ["latin"],
});

const outfit = Outfit({
  variable: "--font-outfit",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Concurseiro DF | Editais e Inteligência Artificial",
  description: "Acompanhe todos os editais de concursos do Distrito Federal com IA.",
};

import { Toaster } from "sonner";

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="pt-BR" suppressHydrationWarning className={`${inter.variable} ${outfit.variable} h-full antialiased`}>
      <body className="min-h-full flex flex-col bg-background text-foreground font-sans selection:bg-primary/30 selection:text-primary">
        <Providers>
          <SkipLink />
          <Toaster position="top-right" richColors />
          
          <header className="sticky top-0 z-40 w-full border-b border-border/40 bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60 shadow-sm">
            <div className="container max-w-7xl mx-auto flex h-16 items-center justify-between px-6 md:px-12">
              <Link href="/" className="flex items-center gap-2 group focus-ring rounded-md">
                <div className="bg-primary/10 p-2 rounded-lg group-hover:bg-primary/20 transition-colors">
                  <BookOpen className="w-6 h-6 text-primary" />
                </div>
                <span className="font-heading font-bold text-xl tracking-tight hidden sm:inline-block">
                  Concurseiro DF
                </span>
              </Link>
              <nav className="flex items-center gap-6 text-sm font-medium">
                <Link href="/" className="transition-colors hover:text-primary focus-ring rounded-sm">Home</Link>
                <Link href="/admin" className="transition-colors hover:text-primary focus-ring rounded-sm text-red-500/80 hover:text-red-500">Admin</Link>
                <ThemeToggle />
              </nav>
            </div>
          </header>

          <main id="main-content" className="flex-1 flex flex-col w-full relative z-0">
            {children}
          </main>
          
          <footer className="border-t border-border/40 py-8 md:py-12 bg-muted/30">
            <div className="container max-w-7xl mx-auto px-6 md:px-12 flex flex-col md:flex-row justify-between items-center gap-6">
              <div className="flex flex-col items-center md:items-start gap-2">
                <span className="font-heading font-bold text-lg">Concurseiro DF</span>
                <p className="text-sm text-muted-foreground text-center md:text-left">
                  Transformando a busca por editais com Inteligência Artificial.
                </p>
              </div>
              <div className="flex flex-col items-center md:items-end gap-2 text-sm text-muted-foreground">
                <div className="flex gap-4 mb-2">
                  <Link href="/termos-de-uso" className="hover:text-primary transition-colors">Termos de Uso</Link>
                  <Link href="/privacidade" className="hover:text-primary transition-colors">Privacidade</Link>
                </div>
                <p>Contato: suporte@concurseirodf.com.br</p>
                <p>v1.0.0 &copy; {new Date().getFullYear()}</p>
              </div>
            </div>
          </footer>
        </Providers>
      </body>
    </html>
  );
}
