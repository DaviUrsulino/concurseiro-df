import Link from "next/link";
import { SearchX, ArrowLeft } from "lucide-react";

export default function NotFound() {
  return (
    <div className="flex-1 w-full flex flex-col items-center justify-center p-6 text-center animate-fade-in bg-background">
      <div className="bg-primary/10 p-6 rounded-full mb-6">
        <SearchX className="w-16 h-16 text-primary" />
      </div>
      <h1 className="text-4xl font-extrabold text-foreground mb-4">Página não encontrada</h1>
      <p className="text-lg text-muted-foreground max-w-md mb-8">
        Desculpe, não conseguimos encontrar a página que você está procurando. Talvez ela tenha sido movida ou excluída.
      </p>
      <Link 
        href="/"
        className="flex items-center gap-2 bg-primary hover:bg-primary/90 text-primary-foreground px-6 py-3 rounded-lg font-medium shadow-md transition-all hover:scale-105"
      >
        <ArrowLeft className="w-5 h-5" />
        Voltar para o Início
      </Link>
    </div>
  );
}
