export interface Filme {
  id: string;
  titulo: string;
  genero: string;
  duracaoMinutos: number;
  idioma: string;
  classificacaoEtaria: string;
  dataInicioExibicao: string;
  dataFimExibicao: string;
  sinopse: string;
  notaMediaAvaliacao: number;
  removidoDaProgramacao: boolean;
}

export interface Sessao {
  id: string;
  filmeId: string;
  salaId: string;
  dataHora: string;
  preco: number;
  assentosDisponiveis: number;
  totalAssentos: number;
}

export interface Ingresso {
  id: string;
  sessaoId: string;
  clienteId: string;
  assento: string;
  valor: number;
  status: 'RESERVADO' | 'CONFIRMADO' | 'CANCELADO';
  dataCompra: string;
}

export interface Cliente {
  id: string;
  nome: string;
  email: string;
  cpf: string;
  dataNascimento: string;
  creditos: number;
}

export interface Avaliacao {
  id: string;
  filmeId: string;
  clienteId: string;
  nota: number;
  comentario: string;
  dataAvaliacao: string;
  statusVisibilidade: 'PENDENTE_MODERACAO' | 'APROVADA' | 'REPROVADA_OFENSIVA';
}

export interface Promocao {
  id: string;
  codigo: string;
  desconto: number;
  dataInicio: string;
  dataFim: string;
  tipo: 'PORCENTAGEM' | 'VALOR_FIXO';
  valorMinimo: number;
} 