import React from 'react';
import {
  Box,
  Typography,
  Paper,
  Rating,
  Button,
  Divider,
  List,
  ListItem,
  ListItemText,
  Chip,
} from '@mui/material';
import { useParams } from 'react-router-dom';
import { useQuery } from 'react-query';
import { format, parseISO } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import api from '../../services/api';
import { Filme, Sessao, Avaliacao } from '../../types';

// Função auxiliar para formatar datas de forma segura
const formatarData = (dataString: string | undefined, formato: string) => {
  if (!dataString) return 'Data indisponível';
  
  try {
    // Tenta converter a string para um objeto Date
    const data = parseISO(dataString);
    if (isNaN(data.getTime())) {
      throw new Error('Data inválida');
    }
    return format(data, formato, { locale: ptBR });
  } catch (error) {
    console.error('Erro ao formatar data:', error);
    return 'Data indisponível';
  }
};

// Função auxiliar para formatar preço
const formatarPreco = (preco: number | undefined) => {
  if (preco === undefined || preco === null) return 'Preço indisponível';
  return `R$ ${preco.toFixed(2)}`;
};

const FilmeDetalhes: React.FC = () => {
  const { id } = useParams<{ id: string }>();

  const { data: filme, isLoading: isLoadingFilme } = useQuery<Filme>(
    ['filme', id],
    async () => {
      const response = await api.get(`/filmes/${id}`);
      return response.data;
    }
  );

  const { data: sessoes, isLoading: isLoadingSessoes } = useQuery<Sessao[]>(
    ['sessoes', id],
    async () => {
      const response = await api.get(`/filmes/${id}/sessoes`);
      return response.data;
    }
  );

  const { data: avaliacoes, isLoading: isLoadingAvaliacoes } = useQuery<Avaliacao[]>(
    ['avaliacoes', id],
    async () => {
      const response = await api.get(`/filmes/${id}/avaliacoes`);
      return response.data;
    }
  );

  if (isLoadingFilme || isLoadingSessoes || isLoadingAvaliacoes) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <Typography>Carregando...</Typography>
      </Box>
    );
  }

  if (!filme) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <Typography>Filme não encontrado</Typography>
      </Box>
    );
  }

  return (
    <Box sx={{ display: 'grid', gridTemplateColumns: { xs: '1fr', md: '1fr 2fr' }, gap: 4 }}>
      <Box>
        <Paper
          sx={{
            p: 2,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Box
            component="img"
            src={`https://source.unsplash.com/random/300x450?movie,${filme.id}`}
            alt={filme.titulo}
            sx={{
              width: '100%',
              maxWidth: 300,
              height: 'auto',
              borderRadius: 1,
            }}
          />
          <Box sx={{ mt: 2, textAlign: 'center' }}>
            <Typography variant="h6" gutterBottom>
              {filme.titulo}
            </Typography>
            <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center', mb: 1 }}>
              <Rating value={filme.notaMediaAvaliacao || 0} precision={0.5} readOnly />
              <Typography variant="body2" color="text.secondary" sx={{ ml: 1 }}>
                ({(filme.notaMediaAvaliacao || 0).toFixed(1)})
              </Typography>
            </Box>
            <Chip
              label={filme.classificacaoEtaria || 'Não informado'}
              color="primary"
              size="small"
              sx={{ mr: 1 }}
            />
            <Chip
              label={`${filme.duracaoMinutos || 0} min`}
              color="secondary"
              size="small"
            />
          </Box>
        </Paper>
      </Box>

      <Box>
        <Paper sx={{ p: 3 }}>
          <Typography variant="h5" gutterBottom>
            Sinopse
          </Typography>
          <Typography paragraph>{filme.sinopse || 'Sinopse não disponível'}</Typography>

          <Divider sx={{ my: 3 }} />

          <Typography variant="h5" gutterBottom>
            Sessões Disponíveis
          </Typography>
          <List>
            {sessoes?.map((sessao) => (
              <ListItem
                key={sessao.id}
                secondaryAction={
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => {/* Implementar reserva */}}
                  >
                    Reservar
                  </Button>
                }
              >
                <ListItemText
                  primary={formatarData(sessao.dataHora, "EEEE, d 'de' MMMM 'às' HH:mm")}
                  secondary={`${formatarPreco(sessao.preco)} • ${sessao.assentosDisponiveis || 0} assentos disponíveis`}
                />
              </ListItem>
            ))}
          </List>

          <Divider sx={{ my: 3 }} />

          <Typography variant="h5" gutterBottom>
            Avaliações
          </Typography>
          <List>
            {avaliacoes?.map((avaliacao) => (
              <ListItem key={avaliacao.id}>
                <ListItemText
                  primary={
                    <Box sx={{ display: 'flex', alignItems: 'center' }}>
                      <Rating value={avaliacao.nota || 0} readOnly size="small" />
                      <Typography variant="body2" color="text.secondary" sx={{ ml: 1 }}>
                        {formatarData(avaliacao.dataAvaliacao, 'dd/MM/yyyy')}
                      </Typography>
                    </Box>
                  }
                  secondary={avaliacao.comentario || 'Sem comentário'}
                />
              </ListItem>
            ))}
          </List>
        </Paper>
      </Box>
    </Box>
  );
};

export default FilmeDetalhes; 