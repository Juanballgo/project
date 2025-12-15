import axios from "axios";

/**
 * HostGator (sin dominio):
 * Front:  https://jonathandavidseguraa1765550332496.0410233.misitiohostgator.com/
 * Backend: /backend   (public de Laravel expuesto allí)
 *
 * API:    /backend/api/...
 * CSRF:   /backend/sanctum/csrf-cookie
 */

// Si defines variables en .env del FRONT (React), úsalas.
// Ejemplo .env (en tu proyecto React):
// REACT_APP_SITE_URL=https://jonathandavidseguraa1765550332496.0410233.misitiohostgator.com
// REACT_APP_BACKEND_PATH=/backend
const ORIGIN =
  (process.env.REACT_APP_SITE_URL || "").trim() ||
  "https://jonathandavidseguraa1765550332496.0410233.misitiohostgator.com";

const BACKEND_PATH = (process.env.REACT_APP_BACKEND_PATH || "/backend").trim();
const BACKEND_BASE = `${ORIGIN}${BACKEND_PATH}`;
const API_BASE = `${BACKEND_BASE}/api`;
const CSRF_URL = `${BACKEND_BASE}/sanctum/csrf-cookie`;

// Cache simple para no pedir CSRF en cada request
let csrfReady = false;

const api = axios.create({
  baseURL: API_BASE,
  withCredentials: true, // NECESARIO para Sanctum cookie auth
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
    "X-Requested-With": "XMLHttpRequest",
  },
  // Axios por defecto usa XSRF-TOKEN y envía X-XSRF-TOKEN.
  // Lo dejamos explícito para evitar comportamientos raros en prod:
  xsrfCookieName: "XSRF-TOKEN",
  xsrfHeaderName: "X-XSRF-TOKEN",
});

async function ensureCsrfCookie() {
  if (csrfReady) return;
  await axios.get(CSRF_URL, { withCredentials: true });
  csrfReady = true;
}

// ================================
// ✅ Interceptor de REQUEST
// - Si hay token en localStorage lo usa (Bearer)
// - Si NO hay token, usa Sanctum cookies + CSRF para métodos mutables
// ================================
api.interceptors.request.use(async (config) => {
  const token = localStorage.getItem("auth_token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  const method = (config.method || "get").toLowerCase();

  // Solo pedir CSRF para mutaciones SI NO estamos usando Bearer
  if (!token && ["post", "put", "patch", "delete"].includes(method)) {
    await ensureCsrfCookie();
  }

  return config;
});

// ================================
// ✅ Interceptor de RESPUESTA
// ================================
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Si el backend responde 419 (CSRF mismatch), reintenta 1 vez refrescando CSRF
    if (error?.response?.status === 419) {
      csrfReady = false;
    }

    if (error?.response?.status === 401) {
      // Solo borrar token si lo estabas usando
      localStorage.removeItem("auth_token");

      const url = String(error.config?.url || "");
      const isLoginRequest = url.includes("/login");

      if (!isLoginRequest) {
        window.location.href = "/LoginRegister";
      }
    }

    return Promise.reject(error);
  }
);

// ================================
// ✅ Servicios
// ================================
const apiService = {
  async csrf() {
    await ensureCsrfCookie();
    return true;
  },

  async register(userData) {
    // Si estás con Sanctum cookies, necesitas CSRF antes
    await ensureCsrfCookie();

    const registrationData = {
      ...userData,
      role: "cliente",
      phone: "0",
      address: "0",
    };

    return api.post("/register", registrationData);
  },

  async login(credentials) {
    // Para Sanctum cookies, CSRF antes del login
    await ensureCsrfCookie();

    const response = await api.post("/login", credentials);

    // Si tu backend devuelve token, lo guardamos.
    // Si NO devuelve token (Sanctum cookies), esto no hace falta y no rompe.
    const token = response.data?.token || response.data?.access_token;
    if (token) {
      localStorage.setItem("auth_token", token);
    }

    return response;
  },

  async logout() {
    // Para Sanctum cookies, POST requiere CSRF
    // Si estás con Bearer, no es obligatorio, pero tampoco molesta
    await ensureCsrfCookie();

    const response = await api.post("/logout");
    localStorage.removeItem("auth_token");
    return response;
  },

  async getCurrentUser() {
    // Para Sanctum cookies, esto funciona si la sesión quedó iniciada
    // Para Bearer, funciona si el token está en Authorization
    return api.get("/me");
  },

  async checkAuth() {
    try {
      const response = await api.get("/check-auth");
      return !!response.data?.authenticated;
    } catch (e) {
      return false;
    }
  },

  async forgotPassword(data) {
    await ensureCsrfCookie();
    return api.post("/forgot-password", data);
  },

  async getUserById(id) {
    return api.get(`/users/${id}`);
  },

  async getServicios() {
    return api.get("/servicios");
  },

  async getAppliances() {
    return api.get("/appliances");
  },

  async getTechnicians() {
    return api.get("/users/by-role?role=tecnico");
  },

  async updateServicio(id, data) {
    return api.put(`/servicios/${id}`, data);
  },

  async asignarTecnico(servicioId, tecnicoId) {
    return api.post(`/servicios/${servicioId}/asignar-tecnico`, {
      tecnico_id: tecnicoId,
    });
  },

  // Útil por si necesitas resetear CSRF manualmente
  _resetCsrfCache() {
    csrfReady = false;
  },
};

export { api, ORIGIN, BACKEND_BASE, API_BASE, CSRF_URL };
export default apiService;
