import React from 'react';
import {
  Box,
  Typography,
  Card,
  CardContent,
  CardMedia,
  Button,
  Rating,
} from '@mui/material';
import type { GridProps } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useQuery } from 'react-query';
import api from '../../services/api';
import { Filme } from '../../types';

const Home: React.FC = () => {
  const navigate = useNavigate();

  const { data: filmes, isLoading } = useQuery<Filme[]>(
    'filmes',
    async () => {
      const response = await api.get('/filmes');
      return response.data;
    },
    {
      staleTime: 1000 * 60 * 5, // 5 minutos
    }
  );

  if (isLoading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <Typography>Carregando filmes...</Typography>
      </Box>
    );
  }

  return (
    <Box>
      <Box sx={{ mb: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Filmes em Cartaz
        </Typography>
        <Typography variant="subtitle1" color="text.secondary">
          Confira os filmes que estão em exibição
        </Typography>
      </Box>

      <Box sx={{ display: 'grid', gridTemplateColumns: { xs: '1fr', sm: '1fr 1fr', md: '1fr 1fr 1fr', lg: '1fr 1fr 1fr 1fr' }, gap: 3 }}>
        {filmes?.map((filme) => (
          <Box key={filme.id}>
            <Card
              sx={{
                height: '100%',
                display: 'flex',
                flexDirection: 'column',
                '&:hover': {
                  transform: 'scale(1.02)',
                  transition: 'transform 0.2s ease-in-out',
                },
              }}
            >
              <CardMedia
                component="img"
                height="300"
                image={`https://source.unsplash.com/random/300x300?movie,${filme.id}`}
                alt={filme.titulo}
              />
              <CardContent sx={{ flexGrow: 1 }}>
                <Typography gutterBottom variant="h6" component="h2">
                  {filme.titulo}
                </Typography>
                <Typography variant="body2" color="text.secondary" gutterBottom>
                  {filme.genero} • {filme.duracaoMinutos} min • {filme.classificacaoEtaria}
                </Typography>
                <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                  <Rating
                    value={filme.notaMediaAvaliacao}
                    precision={0.5}
                    readOnly
                    size="small"
                  />
                  <Typography variant="body2" color="text.secondary" sx={{ ml: 1 }}>
                    ({filme.notaMediaAvaliacao.toFixed(1)})
                  </Typography>
                </Box>
                <Button
                  variant="contained"
                  fullWidth
                  onClick={() => navigate(`/filmes/${filme.id}`)}
                >
                  Ver Detalhes
                </Button>
              </CardContent>
            </Card>
          </Box>
        ))}
      </Box>
    </Box>
  );
};

export default Home; 