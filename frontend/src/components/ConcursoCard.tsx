import Link from "next/link";
import { Badge } from "./Badge";
import { Building2, Calendar, FileText } from "lucide-react";

export interface ConcursoCardProps {
  id: string;
  titulo: string;
  orgao: string;
  status: "ABERTO" | "EM_ANDAMENTO" | "FINALIZADO";
  dataProva?: string;
  cargosCount?: number;
}

export function ConcursoCard({
  id,
  titulo,
  orgao,
  status,
  dataProva,
  cargosCount = 0,
}: ConcursoCardProps) {
  
  const statusMap = {
    ABERTO: "aberto",
    EM_ANDAMENTO: "andamento",
    FINALIZADO: "finalizado",
  } as const;

  const displayStatus = status.replace("_", " ");

  return (
    <Link 
      href={`/concurso/${id}`}
      className="block group focus-ring rounded-xl outline-none"
      aria-label={`Ver detalhes do concurso ${titulo} do órgão ${orgao}`}
    >
      <article className="glass hover-lift rounded-xl p-6 h-full flex flex-col justify-between gap-4 relative overflow-hidden">
        {/* Decorator para o hover */}
        <div className="absolute inset-0 bg-gradient-to-br from-primary/5 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-500 pointer-events-none" />
        
        <div className="space-y-3 z-10">
          <div className="flex justify-between items-start gap-2">
            <Badge variant={statusMap[status]}>{displayStatus}</Badge>
          </div>
          
          <div>
            <h3 className="text-xl font-bold leading-tight group-hover:text-primary transition-colors line-clamp-2" title={titulo}>
              {titulo}
            </h3>
            <div className="flex items-center text-sm text-muted-foreground mt-2 font-medium">
              <Building2 className="w-4 h-4 mr-1.5" aria-hidden="true" />
              <span className="truncate">{orgao}</span>
            </div>
          </div>
        </div>

        <div className="pt-4 border-t border-border flex items-center justify-between text-sm text-muted-foreground z-10">
          {dataProva ? (
            <div className="flex items-center" title="Data da Prova">
              <Calendar className="w-4 h-4 mr-1.5 text-primary" aria-hidden="true" />
              <span>{new Date(dataProva).toLocaleDateString('pt-BR')}</span>
            </div>
          ) : (
            <div className="flex items-center" title="Data a definir">
              <Calendar className="w-4 h-4 mr-1.5 text-muted-foreground/50" aria-hidden="true" />
              <span>A definir</span>
            </div>
          )}
          
          <div className="flex items-center" title={`${cargosCount} Cargos disponíveis`}>
            <FileText className="w-4 h-4 mr-1.5 text-primary" aria-hidden="true" />
            <span>{cargosCount} {cargosCount === 1 ? 'Cargo' : 'Cargos'}</span>
          </div>
        </div>
      </article>
    </Link>
  );
}
