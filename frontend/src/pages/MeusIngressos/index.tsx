import React from 'react';
import {
  Box,
  Typography,
  Paper,
  List,
  ListItem,
  ListItemText,
  Chip,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Alert,
} from '@mui/material';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import api from '../../services/api';
import { Ingresso } from '../../types';

const MeusIngressos: React.FC = () => {
  const queryClient = useQueryClient();
  const [selectedIngresso, setSelectedIngresso] = React.useState<Ingresso | null>(null);
  const [error, setError] = React.useState<string | null>(null);
  const [success, setSuccess] = React.useState<string | null>(null);

  const { data: ingressos, isLoading } = useQuery<Ingresso[]>(
    'ingressos',
    async () => {
      const response = await api.get('/clientes/me/ingressos');
      return response.data;
    }
  );

  const cancelarIngressoMutation = useMutation(
    async (ingressoId: string) => {
      const response = await api.post(`/ingressos/${ingressoId}/cancelar`);
      return response.data;
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries('ingressos');
        setSuccess('Ingresso cancelado com sucesso!');
        setError(null);
        setSelectedIngresso(null);
      },
      onError: () => {
        setError('Erro ao cancelar ingresso. Tente novamente.');
        setSuccess(null);
      },
    }
  );

  const handleCancelarIngresso = () => {
    if (selectedIngresso) {
      cancelarIngressoMutation.mutate(selectedIngresso.id);
    }
  };

  if (isLoading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <Typography>Carregando ingressos...</Typography>
      </Box>
    );
  }

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Meus Ingressos
      </Typography>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}

      {success && (
        <Alert severity="success" sx={{ mb: 2 }}>
          {success}
        </Alert>
      )}

      <Paper>
        <List>
          {ingressos?.map((ingresso) => (
            <ListItem
              key={ingresso.id}
              secondaryAction={
                ingresso.status === 'CONFIRMADO' && (
                  <Button
                    variant="outlined"
                    color="error"
                    onClick={() => setSelectedIngresso(ingresso)}
                  >
                    Cancelar
                  </Button>
                )
              }
            >
              <ListItemText
                primary={`Assento ${ingresso.assento}`}
                secondary={
                  <>
                    <Typography component="span" variant="body2" color="text.primary">
                      {format(new Date(ingresso.dataCompra), 'dd/MM/yyyy HH:mm')}
                    </Typography>
                    {` — R$ ${ingresso.valor.toFixed(2)}`}
                  </>
                }
              />
              <Chip
                label={ingresso.status}
                color={
                  ingresso.status === 'CONFIRMADO'
                    ? 'success'
                    : ingresso.status === 'RESERVADO'
                    ? 'warning'
                    : 'error'
                }
                size="small"
                sx={{ ml: 2 }}
              />
            </ListItem>
          ))}
        </List>
      </Paper>

      <Dialog
        open={!!selectedIngresso}
        onClose={() => setSelectedIngresso(null)}
      >
        <DialogTitle>Cancelar Ingresso</DialogTitle>
        <DialogContent>
          <Typography>
            Tem certeza que deseja cancelar este ingresso? O valor será convertido em créditos para uso futuro.
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setSelectedIngresso(null)}>Não</Button>
          <Button
            onClick={handleCancelarIngresso}
            color="error"
            variant="contained"
            disabled={cancelarIngressoMutation.isLoading}
          >
            {cancelarIngressoMutation.isLoading ? 'Cancelando...' : 'Sim, Cancelar'}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default MeusIngressos; 