import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8000/api',
  withCredentials: true,
});

export const getUsersByRole = (role) =>
  api.get(`/users/by-role?role=${role}`);

export const getTechnicians = () => getUsersByRole('technician');
