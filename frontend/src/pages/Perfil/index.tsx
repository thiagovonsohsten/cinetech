import React from 'react';
import {
  Box,
  Typography,
  Paper,
  TextField,
  Button,
  Divider,
  List,
  ListItem,
  ListItemText,
  Alert,
  Chip,
} from '@mui/material';
import { useFormik } from 'formik';
import * as yup from 'yup';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import api from '../../services/api';
import { Cliente, Ingresso } from '../../types';

const validationSchema = yup.object({
  nome: yup.string().required('Nome é obrigatório'),
  email: yup
    .string()
    .email('Digite um e-mail válido')
    .required('E-mail é obrigatório'),
  senhaAtual: yup.string().when('novaSenha', {
    is: (val: string) => val?.length > 0,
    then: (schema) => schema.required('Senha atual é obrigatória'),
  }),
  novaSenha: yup.string().min(6, 'A senha deve ter no mínimo 6 caracteres'),
  confirmarNovaSenha: yup.string().oneOf(
    [yup.ref('novaSenha')],
    'As senhas não conferem'
  ),
});

const Perfil: React.FC = () => {
  const queryClient = useQueryClient();
  const [error, setError] = React.useState<string | null>(null);
  const [success, setSuccess] = React.useState<string | null>(null);

  const { data: cliente, isLoading } = useQuery<Cliente>(
    'cliente',
    async () => {
      const response = await api.get('/clientes/me');
      return response.data;
    }
  );

  const { data: ingressos } = useQuery<Ingresso[]>(
    'ingressos',
    async () => {
      const response = await api.get('/clientes/me/ingressos');
      return response.data;
    }
  );

  const updateClienteMutation = useMutation(
    async (values: any) => {
      const response = await api.put('/clientes/me', values);
      return response.data;
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries('cliente');
        setSuccess('Perfil atualizado com sucesso!');
        setError(null);
      },
      onError: () => {
        setError('Erro ao atualizar perfil. Tente novamente.');
        setSuccess(null);
      },
    }
  );

  const formik = useFormik({
    initialValues: {
      nome: cliente?.nome || '',
      email: cliente?.email || '',
      senhaAtual: '',
      novaSenha: '',
      confirmarNovaSenha: '',
    },
    validationSchema: validationSchema,
    enableReinitialize: true,
    onSubmit: async (values) => {
      const { senhaAtual, novaSenha, confirmarNovaSenha, ...dadosBasicos } = values;
      
      if (novaSenha) {
        updateClienteMutation.mutate({
          ...dadosBasicos,
          senhaAtual,
          novaSenha,
        });
      } else {
        updateClienteMutation.mutate(dadosBasicos);
      }
    },
  });

  if (isLoading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <Typography>Carregando...</Typography>
      </Box>
    );
  }

  return (
    <Box sx={{ display: 'grid', gridTemplateColumns: { xs: '1fr', md: '1fr 1fr' }, gap: 4 }}>
      <Box>
        <Paper sx={{ p: 3 }}>
          <Typography variant="h5" gutterBottom>
            Meus Dados
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

          <form onSubmit={formik.handleSubmit}>
            <TextField
              fullWidth
              id="nome"
              name="nome"
              label="Nome completo"
              value={formik.values.nome}
              onChange={formik.handleChange}
              error={formik.touched.nome && Boolean(formik.errors.nome)}
              helperText={formik.touched.nome && formik.errors.nome}
              margin="normal"
            />

            <TextField
              fullWidth
              id="email"
              name="email"
              label="E-mail"
              value={formik.values.email}
              onChange={formik.handleChange}
              error={formik.touched.email && Boolean(formik.errors.email)}
              helperText={formik.touched.email && formik.errors.email}
              margin="normal"
            />

            <Divider sx={{ my: 3 }}>
              <Typography variant="body2" color="text.secondary">
                Alterar Senha
              </Typography>
            </Divider>

            <TextField
              fullWidth
              id="senhaAtual"
              name="senhaAtual"
              label="Senha Atual"
              type="password"
              value={formik.values.senhaAtual}
              onChange={formik.handleChange}
              error={formik.touched.senhaAtual && Boolean(formik.errors.senhaAtual)}
              helperText={formik.touched.senhaAtual && formik.errors.senhaAtual}
              margin="normal"
            />

            <TextField
              fullWidth
              id="novaSenha"
              name="novaSenha"
              label="Nova Senha"
              type="password"
              value={formik.values.novaSenha}
              onChange={formik.handleChange}
              error={formik.touched.novaSenha && Boolean(formik.errors.novaSenha)}
              helperText={formik.touched.novaSenha && formik.errors.novaSenha}
              margin="normal"
            />

            <TextField
              fullWidth
              id="confirmarNovaSenha"
              name="confirmarNovaSenha"
              label="Confirmar Nova Senha"
              type="password"
              value={formik.values.confirmarNovaSenha}
              onChange={formik.handleChange}
              error={formik.touched.confirmarNovaSenha && Boolean(formik.errors.confirmarNovaSenha)}
              helperText={formik.touched.confirmarNovaSenha && formik.errors.confirmarNovaSenha}
              margin="normal"
            />

            <Button
              color="primary"
              variant="contained"
              fullWidth
              type="submit"
              sx={{ mt: 3 }}
              disabled={updateClienteMutation.isLoading}
            >
              {updateClienteMutation.isLoading ? 'Salvando...' : 'Salvar Alterações'}
            </Button>
          </form>
        </Paper>
      </Box>

      <Box>
        <Paper sx={{ p: 3 }}>
          <Typography variant="h5" gutterBottom>
            Meus Ingressos
          </Typography>

          <List>
            {ingressos?.map((ingresso) => (
              <ListItem key={ingresso.id}>
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
                />
              </ListItem>
            ))}
          </List>
        </Paper>

        <Paper sx={{ p: 3, mt: 3 }}>
          <Typography variant="h5" gutterBottom>
            Créditos Disponíveis
          </Typography>
          <Typography variant="h4" color="primary">
            R$ {cliente?.creditos.toFixed(2)}
          </Typography>
        </Paper>
      </Box>
    </Box>
  );
};

export default Perfil; 