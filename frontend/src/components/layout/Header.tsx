import React from 'react';
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  IconButton,
  Box,
  useTheme,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import { Movie as MovieIcon, Person as PersonIcon } from '@mui/icons-material';

const Header: React.FC = () => {
  const theme = useTheme();
  const navigate = useNavigate();
  const { isAuthenticated, signOut, cliente } = useAuth();

  return (
    <AppBar position="static">
      <Toolbar>
        <IconButton
          edge="start"
          color="inherit"
          aria-label="menu"
          onClick={() => navigate('/')}
          sx={{ mr: 2 }}
        >
          <MovieIcon />
        </IconButton>

        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          CineTech
        </Typography>

        <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
          <Button color="inherit" onClick={() => navigate('/filmes')}>
            Filmes
          </Button>
          <Button color="inherit" onClick={() => navigate('/sessoes')}>
            Sessões
          </Button>

          {isAuthenticated ? (
            <>
              <Button color="inherit" onClick={() => navigate('/meus-ingressos')}>
                Meus Ingressos
              </Button>
              <Button color="inherit" onClick={() => navigate('/perfil')}>
                <PersonIcon sx={{ mr: 1 }} />
                {cliente?.nome}
              </Button>
              <Button color="inherit" onClick={signOut}>
                Sair
              </Button>
            </>
          ) : (
            <>
              <Button color="inherit" onClick={() => navigate('/login')}>
                Entrar
              </Button>
              <Button
                color="inherit"
                variant="outlined"
                onClick={() => navigate('/cadastro')}
              >
                Cadastrar
              </Button>
            </>
          )}
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Header; 