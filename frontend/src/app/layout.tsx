import type { Metadata } from "next";
import { Inter, Outfit } from "next/font/google";
import "./globals.css";
import Providers from "./Providers";
import SkipLink from "../components/ui/SkipLink";

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

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="pt-BR" className={`${inter.variable} ${outfit.variable} h-full antialiased`}>
      <body className="min-h-full flex flex-col bg-background text-foreground font-sans selection:bg-primary/30 selection:text-primary">
        <Providers>
          <SkipLink />
          {/* Header will go here */}
          <main id="main-content" className="flex-1 flex flex-col w-full relative z-0">
            {children}
          </main>
          {/* Footer will go here */}
        </Providers>
      </body>
    </html>
  );
}
