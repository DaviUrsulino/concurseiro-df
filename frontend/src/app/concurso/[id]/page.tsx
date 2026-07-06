"use client";

import { use } from "react";
import Link from "next/link";
import { useConcursoById, useAndamentos } from "@/hooks/queries";
import { Badge } from "@/components/ui/Badge";
import { ArrowLeft, Building2, Globe, Calendar, Briefcase, FileText, Bot, Loader2, AlertCircle, Download } from "lucide-react";
import { generateStudyGuidePDF } from "@/lib/pdfGenerator";

export default function ConcursoDetails({ params }: { params: Promise<{ id: string }> }) {
  const { id } = use(params);
  
  const { data: concurso, isLoading: loadingConcurso, isError: errorConcurso } = useConcursoById(id);
  const { data: andamentos, isLoading: loadingAndamentos } = useAndamentos(id);

  if (loadingConcurso) {
    return (
      <div className="w-full min-h-[50vh] flex flex-col items-center justify-center animate-fade-in">
        <Loader2 className="w-12 h-12 animate-spin text-primary mb-4" />
        <p className="text-lg font-medium text-muted-foreground">Buscando edital...</p>
      </div>
    );
  }

  if (errorConcurso || !concurso) {
    return (
      <div className="w-full max-w-3xl mx-auto mt-12 p-6 glass-darker border-red-500/20 bg-red-500/5 text-red-500 rounded-xl flex items-center animate-fade-in">
        <AlertCircle className="w-6 h-6 mr-3 flex-shrink-0" />
        <p className="font-medium">Erro ao carregar o concurso. Verifique a conexão.</p>
      </div>
    );
  }

  const statusMap = {
    ABERTO: "aberto",
    EM_ANDAMENTO: "andamento",
    FINALIZADO: "finalizado",
  } as const;

  return (
    <div className="w-full max-w-5xl mx-auto px-6 py-12 animate-slide-up space-y-12">
      <Link 
        href="/"
        className="inline-flex items-center text-sm font-medium text-muted-foreground hover:text-primary transition-colors focus-ring rounded-md"
      >
        <ArrowLeft className="w-4 h-4 mr-2" />
        Voltar para Home
      </Link>

      {/* Header do Concurso */}
      <header className="space-y-6">
        <div className="flex items-center gap-4">
          <Badge variant={statusMap[concurso.status]}>{concurso.status.replace("_", " ")}</Badge>
          <div className="flex items-center text-sm text-muted-foreground font-medium">
            <Calendar className="w-4 h-4 mr-1.5" />
            Prova: {concurso.dataProva ? new Date(concurso.dataProva).toLocaleDateString('pt-BR') : 'A definir'}
          </div>
        </div>
        
        <h1 className="text-4xl md:text-5xl font-extrabold tracking-tight">
          {concurso.titulo}
        </h1>
        
        <div className="flex flex-wrap items-center gap-6 text-muted-foreground">
          <div className="flex items-center font-medium">
            <Building2 className="w-5 h-5 mr-2 text-primary" />
            <span className="text-foreground">{concurso.orgao.nome} ({concurso.orgao.sigla})</span>
          </div>
          {concurso.banca && (
            <div className="flex items-center font-medium">
              <Globe className="w-5 h-5 mr-2 text-primary" />
              <span>Banca: <a href={concurso.banca.siteOficial} target="_blank" rel="noreferrer" className="text-primary hover:underline">{concurso.banca.nome}</a></span>
            </div>
          )}
        </div>
      </header>

      {/* Cargos */}
      <section className="space-y-6">
        <h2 className="text-2xl font-bold flex items-center border-b border-border pb-2">
          <Briefcase className="w-6 h-6 mr-3 text-primary" />
          Cargos e Vagas
        </h2>
        
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {concurso.cargos?.map((cargo) => (
            <article key={cargo.id} className="glass rounded-xl p-5 hover-lift">
              <h3 className="text-lg font-bold">{cargo.nome}</h3>
              <p className="text-sm text-muted-foreground mb-4">{cargo.nivel}</p>
              
              <div className="flex justify-between items-end">
                <div className="space-y-1">
                  <p className="text-sm"><span className="font-semibold text-foreground">Imediatas:</span> {cargo.vagasImediatas || 0}</p>
                  <p className="text-sm"><span className="font-semibold text-foreground">CR:</span> {cargo.vagasCadastroReserva || 0}</p>
                </div>
                {cargo.salario && (
                  <p className="text-lg font-bold text-primary">
                    {new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(cargo.salario)}
                  </p>
                )}
              </div>
              
              {cargo.conteudoProgramatico && (
                <div className="mt-4 pt-4 border-t border-border">
                  <Link 
                    href={`/concurso/${concurso.id}/estudar/${cargo.id}`}
                    className="w-full py-2 px-4 rounded-lg bg-primary/10 text-primary font-bold hover:bg-primary hover:text-primary-foreground transition-colors flex items-center justify-center gap-2 focus-ring"
                  >
                    <Briefcase className="w-4 h-4" />
                    Acessar Dashboard de Estudos
                  </Link>
                </div>
              )}
            </article>
          ))}
          {(!concurso.cargos || concurso.cargos.length === 0) && (
            <p className="text-muted-foreground italic">Nenhum cargo cadastrado no sistema ainda.</p>
          )}
        </div>
      </section>

      {/* Andamentos e Arquivos (Timeline) */}
      <section className="space-y-6">
        <h2 className="text-2xl font-bold flex items-center border-b border-border pb-2">
          <FileText className="w-6 h-6 mr-3 text-primary" />
          Andamentos do Edital
        </h2>

        {loadingAndamentos ? (
          <div className="flex items-center text-muted-foreground">
            <Loader2 className="w-5 h-5 animate-spin mr-2" />
            Carregando andamentos...
          </div>
        ) : andamentos && andamentos.length > 0 ? (
          <div className="space-y-4 border-l-2 border-primary/20 ml-3 pl-6 relative">
            {andamentos.map((andamento) => (
              <div key={andamento.id} className="relative">
                {/* Timeline dot */}
                <div className="absolute -left-[31px] top-1.5 w-4 h-4 rounded-full bg-background border-2 border-primary shadow-[0_0_0_4px_var(--background)]" />
                
                <article className="glass rounded-xl p-5">
                  <div className="flex items-start justify-between mb-2">
                    <h3 className="text-lg font-bold">{andamento.titulo}</h3>
                    <span className="text-xs font-semibold text-muted-foreground bg-secondary px-2 py-1 rounded-md">
                      {new Date(andamento.dataPublicacao).toLocaleDateString('pt-BR')}
                    </span>
                  </div>
                  
                  {andamento.descricao && <p className="text-muted-foreground text-sm mb-4">{andamento.descricao}</p>}
                  
                  <div className="flex items-center gap-4">
                    {andamento.linkDocumento && (
                      <a 
                        href={andamento.linkDocumento} 
                        target="_blank" 
                        rel="noreferrer"
                        className="text-sm font-bold text-primary hover:underline inline-flex items-center focus-ring rounded-sm"
                      >
                        Visualizar Arquivo PDF
                      </a>
                    )}
                    {andamento.extraidoPorIa && (
                      <Badge variant="default" className="gap-1 bg-blue-100 text-blue-800 border border-blue-200 dark:bg-blue-900/50 dark:text-blue-100 hover:bg-blue-200">
                        <Bot className="w-3 h-3" />
                        Lido por IA
                      </Badge>
                    )}
                  </div>
                </article>
              </div>
            ))}
          </div>
        ) : (
          <p className="text-muted-foreground italic glass p-6 rounded-xl text-center">Nenhum andamento ou documento publicado ainda.</p>
        )}
      </section>

    </div>
  );
}
