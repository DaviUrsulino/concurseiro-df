import { useQuery } from '@tanstack/react-query';
import { api } from '../lib/api';

export interface Concurso {
  id: string;
  orgao: { nome: string; sigla: string };
  banca?: { nome: string; siteOficial: string };
  titulo: string;
  status: "ABERTO" | "EM_ANDAMENTO" | "FINALIZADO";
  linkPaginaOficial?: string;
  dataProva?: string;
  cargos?: Cargo[];
}

export interface Cargo {
  id: string;
  nome: string;
  nivel: string;
  salario?: number;
  vagasImediatas?: number;
  vagasCadastroReserva?: number;
}

export interface Andamento {
  id: string;
  titulo: string;
  descricao?: string;
  linkDocumento?: string;
  dataPublicacao: string;
  extraidoPorIa?: boolean;
}

export function useConcursos() {
  return useQuery({
    queryKey: ['concursos'],
    queryFn: async () => {
      const { data } = await api.get<Concurso[]>('/concursos');
      return data;
    },
  });
}

export function useConcursoById(id: string) {
  return useQuery({
    queryKey: ['concurso', id],
    queryFn: async () => {
      const { data } = await api.get<Concurso>(`/concursos/${id}`);
      return data;
    },
    enabled: !!id,
  });
}

export function useAndamentos(concursoId: string) {
  return useQuery({
    queryKey: ['andamentos', concursoId],
    queryFn: async () => {
      const { data } = await api.get<Andamento[]>(`/concursos/${concursoId}/andamentos`);
      return data;
    },
    enabled: !!concursoId,
  });
}
