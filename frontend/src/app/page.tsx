"use client";

import { useState } from "react";
import { useConcursos } from "@/hooks/queries";
import { ConcursoCard } from "@/components/ConcursoCard";
import { Search, Loader2, AlertCircle } from "lucide-react";

export default function Home() {
  const { data: concursos, isLoading, isError } = useConcursos();
  const [searchTerm, setSearchTerm] = useState("");

  const filteredConcursos = concursos?.filter((c) =>
    c.titulo.toLowerCase().includes(searchTerm.toLowerCase()) ||
    c.orgao.nome.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="w-full flex-1 flex flex-col">
      {/* Hero Section */}
      <section className="relative w-full py-20 px-6 md:px-12 flex flex-col items-center justify-center overflow-hidden animate-fade-in bg-gradient-to-b from-primary/10 to-background border-b border-border/50">
        <div className="absolute inset-0 bg-[url('https://www.transparenttextures.com/patterns/cubes.png')] opacity-5 mix-blend-overlay pointer-events-none" />
        
        <div className="max-w-4xl mx-auto text-center space-y-6 z-10">
          <h1 className="text-4xl md:text-6xl font-extrabold tracking-tight text-foreground">
            Editais do <span className="text-primary bg-clip-text">Distrito Federal</span>
          </h1>
          <p className="text-lg md:text-xl text-muted-foreground max-w-2xl mx-auto font-medium">
            Acompanhe os andamentos, editais e disciplinas dos principais concursos de nível médio e técnico. Extração em tempo real impulsionada por IA.
          </p>
          
          <div className="pt-8 w-full max-w-lg mx-auto relative group">
            <label htmlFor="search-concursos" className="sr-only">Pesquisar concursos pelo nome ou órgão</label>
            <div className="relative flex items-center transition-transform duration-300 hover:scale-[1.02]">
              <Search className="absolute left-4 w-5 h-5 text-muted-foreground group-focus-within:text-primary transition-colors" aria-hidden="true" />
              <input
                id="search-concursos"
                type="text"
                placeholder="Ex: SEDF, Novacap, TCDF..."
                className="w-full h-14 pl-12 pr-4 rounded-full bg-card/80 backdrop-blur-md border-2 border-border/50 focus-ring shadow-lg text-lg transition-all"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
          </div>
        </div>
      </section>

      {/* Content Section */}
      <section className="flex-1 w-full max-w-7xl mx-auto px-6 md:px-12 py-12">
        <div className="flex items-center justify-between mb-8">
          <h2 className="text-2xl font-bold tracking-tight">Concursos Recentes</h2>
          <span className="text-sm font-medium text-muted-foreground bg-secondary px-3 py-1 rounded-full">
            {filteredConcursos?.length || 0} encontrados
          </span>
        </div>

        {isLoading && (
          <div className="w-full h-64 flex flex-col items-center justify-center text-muted-foreground animate-fade-in">
            <Loader2 className="w-10 h-10 animate-spin text-primary mb-4" />
            <p className="font-medium">Carregando concursos...</p>
          </div>
        )}

        {isError && (
          <div className="w-full p-6 glass-darker border-red-500/20 bg-red-500/5 text-red-500 rounded-xl flex items-center animate-fade-in">
            <AlertCircle className="w-6 h-6 mr-3 flex-shrink-0" />
            <p className="font-medium">Erro ao carregar os dados. Verifique se o servidor backend está rodando na porta 8080.</p>
          </div>
        )}

        {filteredConcursos && filteredConcursos.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 animate-slide-up">
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
        ) : (
          !isLoading && !isError && (
            <div className="w-full py-16 text-center text-muted-foreground glass rounded-xl animate-fade-in">
              <p className="text-lg font-medium">Nenhum concurso encontrado com esse filtro.</p>
            </div>
          )
        )}
      </section>
    </div>
  );
}
