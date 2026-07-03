import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
  // Habilita envio de cookies/credenciais se o backend estiver rodando
  withCredentials: true,
});

// Interceptor genérico para tratar erros globais (opcional)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error.response?.data || error.message);
    return Promise.reject(error);
  }
);
