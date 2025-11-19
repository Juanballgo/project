import axios from 'axios';

const api = axios.create({
  baseURL: 'http://127.0.0.1:8000/api',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
});

// Interceptor para autenticación y CSRF
api.interceptors.request.use(async (config) => {
  const token = localStorage.getItem('auth_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  // Obtener CSRF token para métodos que modifican datos
  if (['post', 'put', 'patch', 'delete'].includes(config.method.toLowerCase())) {
    await axios.get('http://127.0.0.1:8000/sanctum/csrf-cookie', {
      withCredentials: true
    });
  }

  return config;
});

// Interceptor de respuestas
api.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 401) {
      // Solo elimina el token si existe
      localStorage.removeItem('auth_token');

      // Verifica si el error viene del endpoint de login
      const isLoginRequest = error.config.url.includes('/login');

      if (!isLoginRequest) {
        // Redirige solo si no es una petición de login
        window.location.href = '/login';
      }
    }
    return Promise.reject(error.response?.data || error);
  }
);
const forgotPassword = (data) => {
  return api.post('/forgot-password', data);
};

const apiService = {
  forgotPassword,
  async register(userData) {
    const registrationData = {
      ...userData,
      role: 'cliente',
      phone: '0',
      address: '0'
    };
    return api.post('/register', registrationData);
  },

  async login(credentials) {
    // Obtener CSRF token primero
    await axios.get('http://127.0.0.1:8000/sanctum/csrf-cookie', {
      withCredentials: true
    });

    const response = await api.post('/login', credentials);

    // Guardar token (ajusta según la estructura de tu API)
    if (response.data.token) {
      localStorage.setItem('auth_token', response.data.token);
    }
    return response;
  },

  async logout() {
    const response = await api.post('/logout');
    localStorage.removeItem('auth_token');
    return response;
  },

  // Cambiado de /user a /me para coincidir con tu backend
  async getCurrentUser() {
    return api.get('/me');
  },

  // Nuevo método para verificar autenticación
  async checkAuth() {
    try {
      const token = localStorage.getItem('auth_token');
      if (!token) return false;

      // Usar el endpoint /check-auth que ya tienes
      const response = await api.get('/check-auth');
      return response.data.authenticated;
    } catch (error) {
      return false;
    }
  },
  async getUserById(id) {
    return api.get(`/users/${id}`);
  },

  async getServicios() {
    return api.get('/servicios');
  },

  async getAppliances() {
    return api.get('/appliances'); // electrodomesticos
  },
   async getTechnicians() {
    return api.get('/users/by-role?role=tecnico');
  },

  // Nuevo: editar servicio
  async updateServicio(id, data) {
    return api.put(`/servicios/${id}`, data);
  },

  // Nuevo: asignar técnico a servicio
  async asignarTecnico(servicioId, tecnicoId) {
    return api.post(`/servicios/${servicioId}/asignar-tecnico`, {
      tecnico_id: tecnicoId,
    });
  },
};

export { api };
export default apiService;