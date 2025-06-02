import React from 'react';
import {
  Box,
  Typography,
  TextField,
  Button,
  Paper,
  Link,
  Alert,
} from '@mui/material';
import { useFormik } from 'formik';
import * as yup from 'yup';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';

const validationSchema = yup.object({
  email: yup
    .string()
    .email('Digite um e-mail válido')
    .required('E-mail é obrigatório'),
  senha: yup
    .string()
    .min(6, 'A senha deve ter no mínimo 6 caracteres')
    .required('Senha é obrigatória'),
});

const Login: React.FC = () => {
  const navigate = useNavigate();
  const { signIn } = useAuth();
  const [error, setError] = React.useState<string | null>(null);

  const formik = useFormik({
    initialValues: {
      email: '',
      senha: '',
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      try {
        await signIn(values);
        navigate('/');
      } catch (err) {
        setError('E-mail ou senha inválidos');
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
          maxWidth: 400,
        }}
      >
        <Typography variant="h5" component="h1" gutterBottom align="center">
          Entrar no CineTech
        </Typography>

        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        <form onSubmit={formik.handleSubmit}>
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

          <Button
            color="primary"
            variant="contained"
            fullWidth
            type="submit"
            sx={{ mt: 3, mb: 2 }}
          >
            Entrar
          </Button>

          <Box sx={{ textAlign: 'center' }}>
            <Typography variant="body2">
              Não tem uma conta?{' '}
              <Link
                component="button"
                variant="body2"
                onClick={() => navigate('/cadastro')}
              >
                Cadastre-se
              </Link>
            </Typography>
          </Box>
        </form>
      </Paper>
    </Box>
  );
};

export default Login; 