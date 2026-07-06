import { Shield } from "lucide-react";

export default function PrivacidadePage() {
  return (
    <div className="w-full max-w-4xl mx-auto px-6 py-16">
      <div className="flex items-center gap-3 mb-8">
        <Shield className="w-8 h-8 text-primary" />
        <h1 className="text-3xl font-bold">Política de Privacidade</h1>
      </div>
      
      <div className="prose prose-slate max-w-none text-muted-foreground">
        <p className="mb-4">
          O Concurseiro DF valoriza a sua privacidade. Esta política descreve como coletamos, usamos e protegemos seus dados.
        </p>
        
        <h2 className="text-xl font-semibold mt-8 mb-4 text-foreground">1. Coleta de Dados</h2>
        <p className="mb-4">
          Coletamos informações básicas como nome, e-mail, nível de escolaridade e pretensão salarial para o funcionamento do Smart Matcher.
        </p>

        <h2 className="text-xl font-semibold mt-8 mb-4 text-foreground">2. Uso dos Dados</h2>
        <p className="mb-4">
          Seus dados são usados exclusivamente para recomendar concursos adequados ao seu perfil e enviar notificações, caso você tenha habilitado essa opção.
        </p>

        <h2 className="text-xl font-semibold mt-8 mb-4 text-foreground">3. Proteção e Segurança</h2>
        <p className="mb-4">
          Sua senha é criptografada e armazenada de forma segura usando BCrypt. Nenhuma informação de senha trafega em texto puro.
        </p>

        <h2 className="text-xl font-semibold mt-8 mb-4 text-foreground">4. Contato</h2>
        <p className="mb-4">
          Se você tiver dúvidas sobre nossa Política de Privacidade, entre em contato através do e-mail suporte@concurseirodf.com.br.
        </p>
      </div>
    </div>
  );
}
