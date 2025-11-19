import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8000/api', // <- Aquí va la URL de tu backend Laravel
  withCredentials: true, // si usas cookies de sesión / sanctum
});

export const getServices = () => api.get('/servicios');

export const updateService = (id, data) => api.put(`/servicios/${id}`, data);

export const createService = (data) => api.post('/servicios', data);

export const asignarTecnico = (serviceId, tecnicoId) =>
  api.post(`/servicios/${serviceId}/asignar-tecnico`, { tecnico_id: tecnicoId });
