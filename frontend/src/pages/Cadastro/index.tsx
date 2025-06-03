import React from 'react';
import {
  Box,
  Typography,
  TextField,
  Button,
  Paper,
  Link,
  Alert,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from '@mui/material';
import { useFormik } from 'formik';
import * as yup from 'yup';
import { useNavigate } from 'react-router-dom';
import api from '../../services/api';

const validationSchema = yup.object({
  nome: yup.string().required('Nome é obrigatório'),
  email: yup
    .string()
    .email('Digite um e-mail válido')
    .required('E-mail é obrigatório'),
  cpf: yup
    .string()
    .matches(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/, 'CPF inválido')
    .required('CPF é obrigatório'),
  dataNascimento: yup
    .string()
    .matches(
      /^\d{2}\/\d{2}\/\d{4}$/,
      'Data de nascimento deve estar no formato DD/MM/AAAA'
    )
    .required('Data de nascimento é obrigatória'),
  perfil: yup
    .string()
    .required('Perfil é obrigatório'),
  senha: yup
    .string()
    .min(6, 'A senha deve ter no mínimo 6 caracteres')
    .required('Senha é obrigatória'),
  confirmarSenha: yup
    .string()
    .oneOf([yup.ref('senha')], 'As senhas não conferem')
    .required('Confirmação de senha é obrigatória'),
});

const Cadastro: React.FC = () => {
  const navigate = useNavigate();
  const [error, setError] = React.useState<string | null>(null);

  const formik = useFormik({
    initialValues: {
      nome: '',
      email: '',
      cpf: '',
      dataNascimento: '',
      perfil: '',
      senha: '',
      confirmarSenha: '',
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      try {
        await api.post('/clientes', {
          nome: values.nome,
          email: values.email,
          cpf: values.cpf,
          dataNascimento: values.dataNascimento,
          perfil: values.perfil,
          senha: values.senha,
        });
        navigate('/login');
      } catch (err) {
        setError('Erro ao realizar cadastro. Tente novamente.');
      }
    },
  });

  return (
    <Box
      sx={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        minHeight: '80vh',
      }}
    >
      <Paper
        elevation={3}
        sx={{
          p: 4,
          width: '100%',
          maxWidth: 500,
        }}
      >
        <Typography variant="h5" component="h1" gutterBottom align="center">
          Criar Conta
        </Typography>

        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
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

          <TextField
            fullWidth
            id="cpf"
            name="cpf"
            label="CPF"
            value={formik.values.cpf}
            onChange={formik.handleChange}
            error={formik.touched.cpf && Boolean(formik.errors.cpf)}
            helperText={formik.touched.cpf && formik.errors.cpf}
            margin="normal"
            placeholder="000.000.000-00"
          />

          <TextField
            fullWidth
            id="dataNascimento"
            name="dataNascimento"
            label="Data de Nascimento"
            value={formik.values.dataNascimento}
            onChange={formik.handleChange}
            error={formik.touched.dataNascimento && Boolean(formik.errors.dataNascimento)}
            helperText={formik.touched.dataNascimento && formik.errors.dataNascimento}
            margin="normal"
            placeholder="DD/MM/AAAA"
          />

          <FormControl fullWidth margin="normal">
            <InputLabel id="perfil-label">Perfil</InputLabel>
            <Select
              labelId="perfil-label"
              id="perfil"
              name="perfil"
              value={formik.values.perfil}
              onChange={formik.handleChange}
              error={formik.touched.perfil && Boolean(formik.errors.perfil)}
              label="Perfil"
            >
              <MenuItem value="COMUM">Comum</MenuItem>
              <MenuItem value="ESTUDANTE">Estudante</MenuItem>
              <MenuItem value="IDOSO">Idoso</MenuItem>
              <MenuItem value="PCD">PCD</MenuItem>
            </Select>
          </FormControl>

          <TextField
            fullWidth
            id="senha"
            name="senha"
            label="Senha"
            type="password"
            value={formik.values.senha}
            onChange={formik.handleChange}
            error={formik.touched.senha && Boolean(formik.errors.senha)}
            helperText={formik.touched.senha && formik.errors.senha}
            margin="normal"
          />

          <TextField
            fullWidth
            id="confirmarSenha"
            name="confirmarSenha"
            label="Confirmar Senha"
            type="password"
            value={formik.values.confirmarSenha}
            onChange={formik.handleChange}
            error={formik.touched.confirmarSenha && Boolean(formik.errors.confirmarSenha)}
            helperText={formik.touched.confirmarSenha && formik.errors.confirmarSenha}
            margin="normal"
          />

          <Button
            color="primary"
            variant="contained"
            fullWidth
            type="submit"
            sx={{ mt: 3, mb: 2 }}
          >
            Cadastrar
          </Button>

          <Box sx={{ textAlign: 'center' }}>
            <Typography variant="body2">
              Já tem uma conta?{' '}
              <Link
                component="button"
                variant="body2"
                onClick={() => navigate('/login')}
              >
                Faça login
              </Link>
            </Typography>
          </Box>
        </form>
      </Paper>
    </Box>
  );
};

export default Cadastro; 