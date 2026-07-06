"use client";

import { useState } from "react";
import { useConcursos, useRecomendados } from "@/hooks/queries";
import { ConcursoCard } from "@/components/ConcursoCard";
import { Search, Loader2, AlertCircle, Sparkles, LayoutList } from "lucide-react";
import { useAuth } from "@/contexts/AuthContext";
import Link from "next/link";

export default function Home() {
  const { user } = useAuth();
  const [activeTab, setActiveTab] = useState<"todos" | "recomendados">("todos");
  const [page, setPage] = useState(0);
  const pageSize = 9;

  const { data: concursosPage, isLoading, isError } = useConcursos(page, pageSize);
  const { data: recomendados, isLoading: isLoadingRec, isError: isErrorRec } = useRecomendados();
  const [searchTerm, setSearchTerm] = useState("");

  const currentData = activeTab === "todos" ? concursosPage?.content : recomendados;
  const currentLoading = activeTab === "todos" ? isLoading : isLoadingRec;
  const currentError = activeTab === "todos" ? isError : isErrorRec;

  const filteredConcursos = currentData?.filter((c) =>
    c.titulo.toLowerCase().includes(searchTerm.toLowerCase()) ||
    c.orgao.nome.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="w-full flex-1 flex flex-col">
      {/* Hero Section */}
      <section className="relative w-full py-24 px-6 md:px-12 flex flex-col items-center justify-center overflow-hidden animate-fade-in bg-gradient-to-br from-primary/10 via-background to-accent/5 border-b border-border/50">
        {/* Decorator Shapes */}
        <div className="absolute top-0 left-1/4 w-96 h-96 bg-primary/20 rounded-full mix-blend-multiply filter blur-3xl opacity-30 animate-blob" />
        <div className="absolute top-0 right-1/4 w-96 h-96 bg-accent/20 rounded-full mix-blend-multiply filter blur-3xl opacity-30 animate-blob animation-delay-2000" />
        
        <div className="absolute inset-0 bg-[url('https://www.transparenttextures.com/patterns/cubes.png')] opacity-[0.03] mix-blend-overlay pointer-events-none" />
        
        <div className="max-w-4xl mx-auto text-center space-y-6 z-10 relative">
          <span className="px-4 py-1.5 rounded-full bg-primary/10 text-primary text-sm font-bold tracking-wide uppercase inline-block mb-4 shadow-sm">
            Monitoramento Inteligente
          </span>
          <h1 className="text-4xl md:text-6xl font-extrabold tracking-tight text-foreground">
            Editais do <span className="text-primary bg-clip-text">Distrito Federal</span>
          </h1>
          <p className="text-lg md:text-xl text-muted-foreground max-w-2xl mx-auto font-medium">
            Acompanhe os andamentos, editais e disciplinas dos principais concursos de nível médio e técnico. Extração em tempo real impulsionada por IA.
          </p>
          
          <div className="pt-8 w-full max-w-lg mx-auto relative group">
            <label htmlFor="search-concursos" className="sr-only">Pesquisar concursos pelo nome ou órgão</label>
            <div className="relative flex items-center transition-transform duration-300 hover:scale-[1.02]">
              <input
                id="search-concursos"
                type="text"
                placeholder="Pesquisar..."
                className="w-full h-14 pl-6 pr-14 rounded-full bg-card/90 backdrop-blur-md border-2 border-primary/20 focus:border-primary focus-ring shadow-xl text-lg transition-all"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
              <Search className="absolute right-5 w-6 h-6 text-muted-foreground group-focus-within:text-primary transition-colors" aria-hidden="true" />
            </div>
          </div>
        </div>
      </section>

      {/* Content Section */}
      <section className="flex-1 w-full max-w-7xl mx-auto px-6 md:px-12 py-12">
        <div className="flex flex-col md:flex-row items-center justify-between mb-8 gap-4">
          <div className="flex bg-card p-1 rounded-xl shadow-sm border border-border/50">
            <button
              onClick={() => { setActiveTab("todos"); setPage(0); }}
              className={`flex items-center px-4 py-2 rounded-lg text-sm font-medium transition-all ${
                activeTab === "todos"
                  ? "bg-primary text-white shadow-md"
                  : "text-muted-foreground hover:text-foreground hover:bg-secondary/50"
              }`}
            >
              <LayoutList className="w-4 h-4 mr-2" />
              Todos os Concursos
            </button>
            <button
              onClick={() => setActiveTab("recomendados")}
              className={`flex items-center px-4 py-2 rounded-lg text-sm font-medium transition-all ${
                activeTab === "recomendados"
                  ? "bg-primary text-white shadow-md"
                  : "text-muted-foreground hover:text-foreground hover:bg-secondary/50"
              }`}
            >
              <Sparkles className="w-4 h-4 mr-2" />
              Smart Matcher
            </button>
          </div>
          
          <span className="text-sm font-medium text-muted-foreground bg-secondary px-3 py-1 rounded-full">
            {activeTab === "todos" && concursosPage ? concursosPage.totalElements : (filteredConcursos?.length || 0)} encontrados
          </span>
        </div>

        {activeTab === "recomendados" && !user && (
          <div className="w-full py-16 flex flex-col items-center text-center glass rounded-xl animate-fade-in border border-primary/20 bg-primary/5">
            <Sparkles className="w-12 h-12 text-primary mb-4" />
            <h3 className="text-xl font-bold mb-2 text-foreground">Faça login para ver seus Matches</h3>
            <p className="text-muted-foreground max-w-md mb-6">
              O Smart Matcher analisa seu nível de escolaridade e pretensão salarial para encontrar os concursos ideais para você.
            </p>
            <Link href="/login" className="bg-primary hover:bg-primary/90 text-white px-6 py-2 rounded-lg font-medium shadow-md transition-all hover:scale-105">
              Fazer Login
            </Link>
          </div>
        )}

        {(activeTab === "todos" || user) && currentLoading && (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 animate-pulse mb-8">
            {[1, 2, 3, 4, 5, 6].map((i) => (
              <div key={i} className="bg-card rounded-xl p-6 border border-border/50 shadow-sm h-48 flex flex-col justify-between">
                <div>
                  <div className="h-6 bg-muted rounded w-3/4 mb-4"></div>
                  <div className="h-4 bg-muted rounded w-1/2 mb-2"></div>
                  <div className="h-4 bg-muted rounded w-1/3"></div>
                </div>
                <div className="flex justify-between items-center mt-6 pt-4 border-t border-border/50">
                  <div className="h-8 bg-muted rounded w-24"></div>
                  <div className="h-8 bg-muted rounded w-24"></div>
                </div>
              </div>
            ))}
          </div>
        )}

        {(activeTab === "todos" || user) && currentError && (
          <div className="w-full p-6 glass-darker border-red-500/20 bg-red-500/5 text-red-500 rounded-xl flex items-center animate-fade-in">
            <AlertCircle className="w-6 h-6 mr-3 flex-shrink-0" />
            <p className="font-medium">Erro ao carregar os dados. Verifique se o servidor backend está rodando.</p>
          </div>
        )}

        {(activeTab === "todos" || user) && filteredConcursos && filteredConcursos.length > 0 ? (
          <>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 animate-slide-up mb-8">
              {filteredConcursos.map((concurso) => (
                <ConcursoCard
                  key={concurso.id}
                  id={concurso.id}
                  titulo={concurso.titulo}
                  orgao={concurso.orgao.nome}
                  status={concurso.status}
                  dataProva={concurso.dataProva}
                  cargosCount={concurso.cargos?.length}
                />
              ))}
            </div>
            
            {/* Pagination Controls */}
            {activeTab === "todos" && concursosPage && concursosPage.totalPages > 1 && (
              <div className="flex justify-center items-center gap-4 mt-8">
                <button
                  onClick={() => setPage((p) => Math.max(0, p - 1))}
                  disabled={page === 0}
                  className="px-4 py-2 bg-card border border-border/50 rounded-lg disabled:opacity-50 hover:bg-secondary transition-colors"
                >
                  Anterior
                </button>
                <span className="text-sm font-medium text-muted-foreground">
                  Página {page + 1} de {concursosPage.totalPages}
                </span>
                <button
                  onClick={() => setPage((p) => Math.min(concursosPage.totalPages - 1, p + 1))}
                  disabled={page >= concursosPage.totalPages - 1}
                  className="px-4 py-2 bg-card border border-border/50 rounded-lg disabled:opacity-50 hover:bg-secondary transition-colors"
                >
                  Próxima
                </button>
              </div>
            )}
          </>
        ) : (
          (activeTab === "todos" || user) && !currentLoading && !currentError && (
            <div className="w-full py-16 text-center text-muted-foreground glass rounded-xl animate-fade-in flex flex-col items-center justify-center">
              {activeTab === "recomendados" ? (
                <>
                  <div className="w-16 h-16 bg-primary/10 rounded-full flex items-center justify-center mb-4">
                    <Search className="w-8 h-8 text-primary opacity-80" />
                  </div>
                  <h3 className="text-2xl font-bold text-foreground mb-2">Nenhum match perfeito encontrado</h3>
                  <p className="text-lg mb-6 max-w-lg">
                    Infelizmente nenhum concurso atende exatamente ao seu nível de escolaridade e pretensão salarial neste momento.
                  </p>
                  <p className="text-sm">
                    Tente explorar a aba <strong className="text-primary font-semibold">Todos os Concursos</strong> para outras oportunidades.
                  </p>
                </>
              ) : (
                <p className="text-lg font-medium">Nenhum concurso encontrado com esse filtro.</p>
              )}
            </div>
          )
        )}
      </section>
    </div>
  );
}
