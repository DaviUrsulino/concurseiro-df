import { FileText } from "lucide-react";

export default function TermosDeUsoPage() {
  return (
    <div className="w-full max-w-4xl mx-auto px-6 py-16">
      <div className="flex items-center gap-3 mb-8">
        <FileText className="w-8 h-8 text-primary" />
        <h1 className="text-3xl font-bold">Termos de Uso</h1>
      </div>
      
      <div className="prose prose-slate max-w-none text-muted-foreground">
        <p className="mb-4">
          Bem-vindo ao Concurseiro DF. Ao acessar ou usar nosso serviço, você concorda com estes Termos de Uso.
        </p>
        
        <h2 className="text-xl font-semibold mt-8 mb-4 text-foreground">1. Uso do Serviço</h2>
        <p className="mb-4">
          Nosso serviço fornece informações sobre concursos públicos no Distrito Federal extraídas por Inteligência Artificial.
          As informações são fornecidas "como estão" e devem sempre ser verificadas nos editais oficiais.
        </p>

        <h2 className="text-xl font-semibold mt-8 mb-4 text-foreground">2. Contas de Usuário</h2>
        <p className="mb-4">
          Para utilizar o Smart Matcher, você precisa criar uma conta. Você é responsável por manter a confidencialidade
          da sua senha e por todas as atividades que ocorram sob sua conta.
        </p>

        <h2 className="text-xl font-semibold mt-8 mb-4 text-foreground">3. Isenção de Responsabilidade</h2>
        <p className="mb-4">
          Não garantimos a precisão absoluta das informações extraídas por IA. O usuário final deve sempre validar
          as informações junto às bancas organizadoras oficiais.
        </p>
      </div>
    </div>
  );
}
