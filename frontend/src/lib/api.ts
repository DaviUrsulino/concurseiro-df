import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
  // Habilita envio de cookies/credenciais se o backend estiver rodando
  withCredentials: true,
});
// Request interceptor to attach JWT token
api.interceptors.request.use(
  (config) => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('@ConcurseiroDF:token');
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
// Interceptor genérico para tratar erros globais (opcional)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error.response?.data || error.message);
    if (error.response?.status === 401 || error.response?.status === 403) {
      if (typeof window !== 'undefined') {
        localStorage.removeItem('@ConcurseiroDF:token');
        localStorage.removeItem('@ConcurseiroDF:user');
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);
