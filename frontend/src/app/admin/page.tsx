"use client";

import { useState } from "react";
import Link from "next/link";
import { ArrowLeft, Bot, ShieldAlert, Loader2, CheckCircle2 } from "lucide-react";
import { Button } from "@/components/ui/Button";
import { api } from "@/lib/api";

export default function AdminPanel() {
  const [password, setPassword] = useState("");
  const [status, setStatus] = useState<"idle" | "loading" | "success" | "error">("idle");
  const [message, setMessage] = useState("");

  const handleScrape = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!password) return;

    setStatus("loading");
    setMessage("Iniciando extração e leitura por IA...");

    try {
      const response = await api.post("/jobs/scraper/start", null, {
        auth: {
          username: "admin",
          password: password
        }
      });
      
      setStatus("success");
      setMessage(response.data || "Scraper finalizado com sucesso!");
    } catch (error: any) {
      setStatus("error");
      if (error.response?.status === 401) {
        setMessage("Senha incorreta. Acesso negado.");
      } else {
        setMessage("Erro ao executar o scraper. Verifique os logs do servidor.");
      }
    }
  };

  return (
    <div className="w-full max-w-lg mx-auto px-6 py-20 animate-slide-up flex flex-col min-h-screen">
      <Link 
        href="/"
        className="inline-flex items-center text-sm font-medium text-muted-foreground hover:text-primary transition-colors focus-ring rounded-md mb-8 self-start"
      >
        <ArrowLeft className="w-4 h-4 mr-2" />
        Voltar para Home
      </Link>

      <div className="glass-darker rounded-2xl p-8 shadow-xl relative overflow-hidden">
        {/* Decorator */}
        <div className="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-red-500 via-primary to-accent" />
        
        <div className="flex flex-col items-center text-center space-y-4 mb-8">
          <div className="w-16 h-16 rounded-full bg-red-500/10 flex items-center justify-center text-red-500 mb-2">
            <ShieldAlert className="w-8 h-8" />
          </div>
          <h1 className="text-2xl font-bold">Painel de Administração</h1>
          <p className="text-muted-foreground text-sm">
            Área restrita. Informe a credencial administrativa para disparar a varredura automática de editais com Inteligência Artificial.
          </p>
        </div>

        <form onSubmit={handleScrape} className="space-y-6">
          <div className="space-y-2">
            <label htmlFor="admin-pass" className="text-sm font-semibold text-foreground">
              Senha Administrativa
            </label>
            <input
              id="admin-pass"
              type="password"
              required
              placeholder="Digite a senha..."
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full h-12 px-4 rounded-lg bg-background border border-border focus-ring transition-all"
            />
          </div>

          <Button 
            type="submit" 
            className="w-full flex items-center justify-center gap-2 h-12 text-base font-bold bg-foreground text-background hover:bg-foreground/90"
            disabled={status === "loading" || !password}
          >
            {status === "loading" ? (
              <Loader2 className="w-5 h-5 animate-spin" />
            ) : (
              <Bot className="w-5 h-5" />
            )}
            Disparar Web Scraper
          </Button>
        </form>

        {status !== "idle" && (
          <div className={`mt-6 p-4 rounded-lg flex items-start gap-3 animate-fade-in ${
            status === "error" ? "bg-red-500/10 text-red-500 border border-red-500/20" :
            status === "success" ? "bg-green-500/10 text-green-600 dark:text-green-400 border border-green-500/20" :
            "bg-primary/10 text-primary border border-primary/20"
          }`}>
            {status === "success" && <CheckCircle2 className="w-5 h-5 mt-0.5 flex-shrink-0" />}
            {status === "error" && <ShieldAlert className="w-5 h-5 mt-0.5 flex-shrink-0" />}
            {status === "loading" && <Loader2 className="w-5 h-5 mt-0.5 animate-spin flex-shrink-0" />}
            <p className="text-sm font-medium">{message}</p>
          </div>
        )}
      </div>
    </div>
  );
}
