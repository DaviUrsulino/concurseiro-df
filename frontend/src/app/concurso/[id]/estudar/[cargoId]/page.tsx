"use client";

import { use } from "react";
import Link from "next/link";
import { useConcursoById, useProgresso, useUpdateProgresso } from "@/hooks/queries";
import { ArrowLeft, BookOpen, CheckCircle2, Circle, Loader2, Trophy } from "lucide-react";
import { useAuth } from "@/contexts/AuthContext";

export default function DashboardEstudante({ params }: { params: Promise<{ id: string, cargoId: string }> }) {
  const { user } = useAuth();
  const { id, cargoId } = use(params);
  
  const { data: concurso, isLoading } = useConcursoById(id);
  const { data: topicosConcluidos = [], isLoading: loadingProgresso } = useProgresso(cargoId);
  const updateProgresso = useUpdateProgresso();

  if (isLoading || loadingProgresso) {
    return (
      <div className="w-full min-h-[50vh] flex flex-col items-center justify-center animate-fade-in">
        <Loader2 className="w-12 h-12 animate-spin text-primary mb-4" />
        <p className="text-lg font-medium text-muted-foreground">Carregando seu Dashboard...</p>
      </div>
    );
  }

  if (!concurso) return null;

  const cargo = concurso.cargos?.find((c) => c.id === cargoId);
  if (!cargo || !cargo.conteudoProgramatico) return null;

  const disciplinas = JSON.parse(cargo.conteudoProgramatico) as { disciplina: string; topicos: string[] }[];
  
  const totalTopicos = disciplinas.reduce((acc, d) => acc + d.topicos.length, 0);
  const concluidosCount = topicosConcluidos.length;
  const progressPercentage = totalTopicos === 0 ? 0 : Math.round((concluidosCount / totalTopicos) * 100);

  const toggleTopico = (topico: string) => {
    if (!user) return; // Deveria redirecionar para login
    const newTopicos = topicosConcluidos.includes(topico)
      ? topicosConcluidos.filter((t) => t !== topico)
      : [...topicosConcluidos, topico];
    updateProgresso.mutate({ cargoId, topicosConcluidos: newTopicos });
  };

  return (
    <div className="w-full max-w-5xl mx-auto px-6 py-12 animate-slide-up space-y-12">
      <Link 
        href={`/concurso/${id}`}
        className="inline-flex items-center text-sm font-medium text-muted-foreground hover:text-primary transition-colors focus-ring rounded-md"
      >
        <ArrowLeft className="w-4 h-4 mr-2" />
        Voltar para o Edital
      </Link>

      <header className="space-y-6">
        <div className="flex flex-col md:flex-row md:items-end justify-between gap-6">
          <div>
            <h1 className="text-4xl md:text-5xl font-extrabold tracking-tight mb-2 text-foreground">
              Jornada de Estudos
            </h1>
            <p className="text-xl text-muted-foreground font-medium flex items-center gap-2">
              <BookOpen className="w-5 h-5 text-primary" />
              {cargo.nome} - {concurso.orgao.sigla}
            </p>
          </div>

          {/* Premium Progress Bar Widget */}
          <div className="glass p-5 rounded-2xl md:min-w-[300px] border border-primary/20 shadow-lg shadow-primary/5 relative overflow-hidden group">
            <div className="absolute top-0 right-0 w-32 h-32 bg-primary/10 rounded-full blur-2xl -mr-10 -mt-10 transition-transform group-hover:scale-150 duration-700" />
            <div className="flex items-center justify-between mb-3 relative z-10">
              <span className="font-bold text-foreground">Seu Progresso</span>
              <span className="text-2xl font-black text-primary drop-shadow-sm">{progressPercentage}%</span>
            </div>
            <div className="w-full bg-secondary h-3 rounded-full overflow-hidden relative z-10">
              <div 
                className="bg-gradient-to-r from-primary to-accent h-full transition-all duration-1000 ease-out relative"
                style={{ width: `${progressPercentage}%` }}
              >
                <div className="absolute inset-0 bg-white/20 animate-pulse" />
              </div>
            </div>
            <p className="text-xs text-muted-foreground mt-3 font-medium relative z-10 flex items-center gap-1">
              {progressPercentage === 100 ? (
                <span className="text-green-500 flex items-center gap-1"><Trophy className="w-3 h-3"/> Você está pronto!</span>
              ) : (
                `${concluidosCount} de ${totalTopicos} tópicos concluídos`
              )}
            </p>
          </div>
        </div>
      </header>

      <section className="space-y-8">
        {disciplinas.map((disciplina, idx) => {
          const disciplinaTopicos = disciplina.topicos;
          const disciplinaConcluidos = disciplinaTopicos.filter(t => topicosConcluidos.includes(t)).length;
          const isComplete = disciplinaConcluidos === disciplinaTopicos.length;

          return (
            <div key={idx} className="glass rounded-xl overflow-hidden border border-border/50 transition-all hover:border-primary/30">
              <div className={`p-5 flex items-center justify-between border-b transition-colors ${isComplete ? 'bg-primary/5 border-primary/10' : 'bg-card border-border'}`}>
                <h3 className="text-xl font-bold text-foreground">{disciplina.disciplina}</h3>
                <span className="text-sm font-medium px-3 py-1 bg-secondary rounded-full">
                  {disciplinaConcluidos} / {disciplinaTopicos.length}
                </span>
              </div>
              <div className="p-2">
                {disciplinaTopicos.map((topico, tidx) => {
                  const checked = topicosConcluidos.includes(topico);
                  return (
                    <button
                      key={tidx}
                      onClick={() => toggleTopico(topico)}
                      disabled={updateProgresso.isPending}
                      className={`w-full text-left p-4 flex items-start gap-4 hover:bg-secondary/50 transition-all rounded-lg group ${checked ? 'opacity-70' : ''}`}
                    >
                      <div className="mt-0.5 flex-shrink-0 relative">
                        {checked ? (
                          <CheckCircle2 className="w-6 h-6 text-primary drop-shadow-sm transition-transform scale-110" />
                        ) : (
                          <Circle className="w-6 h-6 text-muted-foreground group-hover:text-primary transition-colors" />
                        )}
                        {updateProgresso.isPending && updateProgresso.variables?.topicosConcluidos.includes(topico) !== checked && (
                           <Loader2 className="absolute inset-0 w-6 h-6 text-primary animate-spin bg-background rounded-full" />
                        )}
                      </div>
                      <span className={`text-base font-medium transition-all ${checked ? 'line-through text-muted-foreground' : 'text-foreground'}`}>
                        {topico}
                      </span>
                    </button>
                  );
                })}
              </div>
            </div>
          );
        })}
      </section>

      {/* Decorative background for the page */}
      <div className="fixed inset-0 pointer-events-none -z-10 bg-[radial-gradient(ellipse_at_top_right,_var(--tw-gradient-stops))] from-primary/5 via-background to-background" />
    </div>
  );
}
