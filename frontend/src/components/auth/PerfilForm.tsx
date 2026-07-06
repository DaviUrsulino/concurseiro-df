"use client";

import { useState, useEffect } from "react";
import { api } from "@/lib/api";
import { useRouter } from "next/navigation";
import { toast } from "sonner";

export default function PerfilForm() {
  const router = useRouter();
  
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState(""); // Apenas leitura
  const [nivelEscolaridade, setNivelEscolaridade] = useState("");
  const [pretensaoSalarial, setPretensaoSalarial] = useState("");
  const [novaSenha, setNovaSenha] = useState("");
  
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    const fetchPerfil = async () => {
      try {
        const response = await api.get("/usuarios/me");
        const data = response.data;
        setNome(data.nome);
        setEmail(data.email);
        setNivelEscolaridade(data.nivelEscolaridade || "");
        setPretensaoSalarial(data.pretensaoSalarial || "");
      } catch (err: any) {
        if (err.response?.status === 403 || err.response?.status === 401) {
          router.push("/login");
        } else {
          toast.error("Erro ao carregar perfil.");
        }
      } finally {
        setLoading(false);
      }
    };
    fetchPerfil();
  }, [router]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSaving(true);

    try {
      await api.put("/usuarios/me", {
        nome,
        nivelEscolaridade,
        pretensaoSalarial: pretensaoSalarial ? parseFloat(pretensaoSalarial.toString()) : null,
        novaSenha
      });
      toast.success("Perfil atualizado com sucesso!");
      setNovaSenha(""); // Limpa o campo de senha
    } catch (err: any) {
      toast.error(err.response?.data?.message || "Erro ao salvar perfil.");
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return <div className="text-center py-4 text-muted-foreground animate-pulse">Carregando perfil...</div>;
  }

  return (
    <form className="space-y-6" onSubmit={handleSubmit}>
      <div>
        <label htmlFor="email" className="block text-sm font-medium text-foreground">
          E-mail
        </label>
        <div className="mt-1">
          <input
            id="email"
            type="email"
            disabled
            value={email}
            className="appearance-none block w-full px-3 py-2 border border-input rounded-md shadow-sm bg-muted text-muted-foreground sm:text-sm"
          />
        </div>
      </div>

      <div>
        <label htmlFor="nome" className="block text-sm font-medium text-foreground">
          Nome Completo
        </label>
        <div className="mt-1">
          <input
            id="nome"
            type="text"
            required
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            className="appearance-none block w-full px-3 py-2 border border-input rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary sm:text-sm bg-background"
          />
        </div>
      </div>

      <div>
        <label htmlFor="nivelEscolaridade" className="block text-sm font-medium text-foreground">
          Nível de Escolaridade (Para o Smart Matcher)
        </label>
        <div className="mt-1">
          <select
            id="nivelEscolaridade"
            value={nivelEscolaridade}
            onChange={(e) => setNivelEscolaridade(e.target.value)}
            className="appearance-none block w-full px-3 py-2 border border-input rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary sm:text-sm bg-background"
          >
            <option value="">Selecione</option>
            <option value="Médio">Ensino Médio</option>
            <option value="Superior">Ensino Superior</option>
          </select>
        </div>
      </div>

      <div>
        <label htmlFor="pretensaoSalarial" className="block text-sm font-medium text-foreground">
          Pretensão Salarial (Para o Smart Matcher)
        </label>
        <div className="mt-1">
          <input
            id="pretensaoSalarial"
            type="number"
            min="0"
            step="0.01"
            value={pretensaoSalarial}
            onChange={(e) => setPretensaoSalarial(e.target.value)}
            className="appearance-none block w-full px-3 py-2 border border-input rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary sm:text-sm bg-background"
          />
        </div>
      </div>

      <div>
        <label htmlFor="novaSenha" className="block text-sm font-medium text-foreground">
          Nova Senha (deixe em branco para não alterar)
        </label>
        <div className="mt-1">
          <input
            id="novaSenha"
            type="password"
            value={novaSenha}
            onChange={(e) => setNovaSenha(e.target.value)}
            className="appearance-none block w-full px-3 py-2 border border-input rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary sm:text-sm bg-background"
          />
        </div>
      </div>

      <div>
        <button
          type="submit"
          disabled={saving}
          className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-primary-foreground bg-primary hover:bg-primary/90 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary disabled:opacity-50 transition-colors"
        >
          {saving ? "Salvando..." : "Salvar Alterações"}
        </button>
      </div>
    </form>
  );
}
