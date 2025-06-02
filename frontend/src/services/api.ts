import axios from 'axios';

interface IdObject {
  valor: string | number;
}

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para adicionar token de autenticação
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('@CineTech:token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Interceptor para transformar IDs em strings e formatar datas
api.interceptors.response.use((response) => {
  const transformData = (data: any): any => {
    if (Array.isArray(data)) {
      return data.map(transformData);
    }
    if (data && typeof data === 'object') {
      const transformed: any = {};
      for (const [key, value] of Object.entries(data)) {
        // Transforma IDs
        if (key === 'id' && value && typeof value === 'object' && 'valor' in value) {
          transformed[key] = (value as IdObject).valor.toString();
        }
        // Formata datas
        else if (
          (key === 'dataHora' || 
           key === 'dataAvaliacao' || 
           key === 'dataCompra' || 
           key === 'dataInicioExibicao' || 
           key === 'dataFimExibicao') && 
          typeof value === 'string'
        ) {
          // Converte a data para o formato ISO
          const date = new Date(value);
          if (!isNaN(date.getTime())) {
            transformed[key] = date.toISOString();
          } else {
            transformed[key] = value;
          }
        }
        // Recursivamente transforma outros objetos
        else {
          transformed[key] = transformData(value);
        }
      }
      return transformed;
    }
    return data;
  };

  response.data = transformData(response.data);
  return response;
});

export default api; 