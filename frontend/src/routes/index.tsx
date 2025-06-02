import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import Layout from '../components/layout/Layout';
import Home from '../pages/Home';
import Login from '../pages/Login';
import Cadastro from '../pages/Cadastro';
import FilmeDetalhes from '../pages/FilmeDetalhes';
import MeusIngressos from '../pages/MeusIngressos';
import Perfil from '../pages/Perfil';

interface PrivateRouteProps {
  children: React.ReactNode;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ children }) => {
  const { isAuthenticated } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" />;
  }

  return <>{children}</>;
};

const AppRoutes: React.FC = () => {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/cadastro" element={<Cadastro />} />
          <Route path="/filmes/:id" element={<FilmeDetalhes />} />
          
          <Route
            path="/meus-ingressos"
            element={
              <PrivateRoute>
                <MeusIngressos />
              </PrivateRoute>
            }
          />
          
          <Route
            path="/perfil"
            element={
              <PrivateRoute>
                <Perfil />
              </PrivateRoute>
            }
          />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
};

export default AppRoutes; 