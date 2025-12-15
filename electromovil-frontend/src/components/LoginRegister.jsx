import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../assets/LoginRegister.css';
import 'boxicons/css/boxicons.min.css';
import api from '../services/api';

const LoginRegister = ({ onLoginSuccess }) => {
  const navigate = useNavigate();

  const [loginData, setLoginData] = useState({ email: '', password: '' });
  const [registerData, setRegisterData] = useState({
    name: '',
    email: '',
    password: '',
    password_confirmation: ''
  });
  const [showForgotForm, setShowForgotForm] = useState(false);
  const [forgotEmail, setForgotEmail] = useState('');
  const [forgotSuccess, setForgotSuccess] = useState('');
  const [forgotError, setForgotError] = useState('');
  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [registrationSuccess, setRegistrationSuccess] = useState(false);

  useEffect(() => {
    const container = document.querySelector('.container');
    const registerBtn = document.querySelector('.register-btn');
    const loginBtn = document.querySelector('.login-btn');

    if (window.location.hash === '#register') {
      container?.classList.add('active');
    }

    const onRegister = () => container?.classList.add('active');
    const onLogin = () => container?.classList.remove('active');

    registerBtn?.addEventListener('click', onRegister);
    loginBtn?.addEventListener('click', onLogin);

    return () => {
      registerBtn?.removeEventListener('click', onRegister);
      loginBtn?.removeEventListener('click', onLogin);
    };
  }, []);

  const validateField = (name, value) => {
    let error = '';

    if (name === 'name') {
      if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(value)) {
        error = 'El nombre solo debe contener letras';
      }
    }

    if (name === 'email') {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(value)) {
        error = 'Correo electrónico no válido';
      }
    }

    if (name === 'password') {
      if (value.length < 6) {
        error = 'La contraseña debe tener al menos 6 caracteres';
      }
    }

    if (name === 'password_confirmation') {
      if (value !== registerData.password) {
        error = 'Las contraseñas no coinciden';
      }
    }

    setErrors(prev => ({ ...prev, [name]: error }));
  };

  // formulario de olvido de contraseña
  const handleForgotSubmit = async (e) => {
    e.preventDefault();
    setForgotSuccess('');
    setForgotError('');
    try {
      await api.forgotPassword({ email: forgotEmail });
      setForgotSuccess('¡Te hemos enviado un correo para restablecer tu contraseña!');
      setForgotEmail('');
    } catch (error) {
      setForgotError('No pudimos enviar el correo. Verifica tu email.');
    }
  };

  const handleRegisterChange = (e) => {
    const { name, value } = e.target;
    setRegisterData(prev => ({ ...prev, [name]: value }));
    validateField(name, value);
  };

  const isRegisterValid = () => {
    const localErrors = {};
    if (!registerData.name || !/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(registerData.name)) {
      localErrors.name = 'El nombre solo debe contener letras';
    }
    if (!registerData.email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(registerData.email)) {
      localErrors.email = 'Correo electrónico no válido';
    }
    if (!registerData.password || registerData.password.length < 6) {
      localErrors.password = 'La contraseña debe tener al menos 6 caracteres';
    }
    if (registerData.password_confirmation !== registerData.password) {
      localErrors.password_confirmation = 'Las contraseñas no coinciden';
    }

    setErrors(localErrors);
    return Object.keys(localErrors).length === 0;
  };

  const handleRegisterSubmit = async (e) => {
    e.preventDefault();
    if (!isRegisterValid()) return;
    setIsLoading(true);
    setErrors({});

    try {
      await api.register(registerData);

      const loginResponse = await api.login({
        email: registerData.email,
        password: registerData.password
      });

      const token = loginResponse?.data?.token || loginResponse?.data?.access_token;
      if (token) localStorage.setItem('auth_token', token);

      const meResp = await api.getCurrentUser();
      const user = meResp?.data;

      setRegistrationSuccess(true);

      setTimeout(() => {
        // fallback duro por si el router en hosting falla
        navigate('/usuario');
        setTimeout(() => {
          if (window.location.pathname !== '/usuario') {
            window.location.assign('/usuario');
          }
        }, 200);

        onLoginSuccess?.(user);
      }, 900);
    } catch (error) {
      console.error('Error en registro:', error);
      setErrors(error?.response?.data?.errors || {
        general: error?.response?.data?.message || 'Error en el registro'
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleLoginChange = (e) => {
    const { name, value } = e.target;
    setLoginData(prev => ({ ...prev, [name]: value }));
  };

  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setErrors({});

    try {
      const response = await api.login(loginData);

      const token = response?.data?.token || response?.data?.access_token;
      if (token) localStorage.setItem('auth_token', token);

      const meResp = await api.getCurrentUser();
      const user = meResp?.data;

      if (!user) {
        setErrors({ general: 'Login OK, pero /me no devolvió usuario. Revisa backend.' });
        setIsLoading(false);
        return;
      }

      // Normaliza role (por si viene en mayúsculas o con espacios)
      const role = String(user.role || '').trim().toLowerCase();

      const redirectPath = {
        admin: '/admin',
        tecnico: '/tecnico',
        cliente: '/usuario'
      }[role] || '/usuario';

      // Intento 1: React Router
      const before = window.location.pathname;
      navigate(redirectPath);

      // Fallback: si el hosting no resuelve rutas SPA, fuerza navegación
      setTimeout(() => {
        // si no cambió, forzamos
        if (window.location.pathname === before) {
          window.location.assign(redirectPath);
        }
      }, 200);

      onLoginSuccess?.(user);
    } catch (error) {
<<<<<<< Updated upstream
      setErrors({ general: error.response?.data?.message || 'Error al iniciar sesión' });
=======
      console.error('Error en login:', error);

      let friendly = 'Error al iniciar sesión';
      let fieldErrors = {};

      // Si tu api.js está rechazando con `error.response?.data`, aquí puede venir “plano”
      const status = error?.status || error?.response?.status;
      const data = error?.response?.data || error;
      const serverMsg = String(data?.message || '').toLowerCase();

      if (status >= 500 || serverMsg.includes('connection') || serverMsg.includes('conex') || serverMsg.includes('database') || serverMsg.includes('db')) {
        friendly = 'Error de conexión';
      } else if (status === 401) {
        if (serverMsg.includes('password') || serverMsg.includes('contrase') || serverMsg.includes('invalid credentials') || serverMsg.includes('credenciales')) {
          friendly = 'Contraseña incorrecta';
          fieldErrors.password = 'Contraseña incorrecta';
        } else if (serverMsg.includes('user') || serverMsg.includes('email') || serverMsg.includes('no encontrado') || serverMsg.includes('not found')) {
          friendly = 'Email no registrado';
          fieldErrors.email = 'Email no registrado';
        } else {
          friendly = data?.message || 'Credenciales inválidas';
        }
      } else if (data?.errors) {
        if (data.errors.password) {
          fieldErrors.password = Array.isArray(data.errors.password) ? data.errors.password.join(' ') : data.errors.password;
          friendly = fieldErrors.password;
        } else if (data.errors.email) {
          fieldErrors.email = Array.isArray(data.errors.email) ? data.errors.email.join(' ') : data.errors.email;
          friendly = fieldErrors.email;
        } else if (data.message) {
          friendly = data.message;
        }
      } else if (data?.message) {
        if (serverMsg.includes('connection') || serverMsg.includes('conex')) {
          friendly = 'Error de conexión';
        } else {
          friendly = data.message;
        }
      } else if (error?.request) {
        friendly = 'Error de conexión';
      } else {
        friendly = error?.message || friendly;
      }

      setErrors({ ...fieldErrors, general: friendly });
>>>>>>> Stashed changes
    } finally {
      setIsLoading(false);
    }
  };

  if (registrationSuccess) {
    return (
      <div className="registration-success">
        <div className="success-icon">&#10003;</div>
        <h2 className="success-title">¡Registro exitoso!</h2>
        <p className="success-message">Serás redirigido a tu cuenta...</p>
      </div>
    );
  }

  return (
    <>
      {showForgotForm && (
        <div className="forgot-modal-overlay">
          <div className="forgot-modal">
            <button
              className="close-btn"
              onClick={() => {
                setShowForgotForm(false);
                setForgotSuccess('');
                setForgotError('');
                setForgotEmail('');
              }}
            >
              &times;
            </button>
            <h2>¿Olvidaste tu contraseña?</h2>
            <p>Ingresa tu correo electrónico y te enviaremos un enlace para restablecer tu contraseña.</p>
            <form onSubmit={handleForgotSubmit}>
              <input
                type="email"
                placeholder="Correo electrónico"
                value={forgotEmail}
                onChange={e => setForgotEmail(e.target.value)}
                required
                autoFocus
              />
              <button type="submit" className="btn" style={{ marginTop: 12 }}>
                Enviar enlace
              </button>
            </form>
            {forgotSuccess && <div className="success-message">{forgotSuccess}</div>}
            {forgotError && <div id="error" className="error-message">{forgotError}</div>}
          </div>
        </div>
      )}

      <div className="login-register">
        <div className="container">
          <div className="form-box login">
            <form onSubmit={handleLoginSubmit}>
              <h1>Login</h1>
              {errors.general && <div className="error-message">{errors.general}</div>}
              <div className="input-box">
                <input
                  id="email"
                  type="email"
                  name="email"
                  placeholder="Correo electrónico"
                  required
                  value={loginData.email}
                  onChange={handleLoginChange}
                  autoComplete="off"
                />
                <i className="bx bxs-envelope"></i>
              </div>
              <div className="input-box">
                <input
                  id="password"
                  type="password"
                  name="password"
                  placeholder="Contraseña"
                  required
                  value={loginData.password}
                  onChange={handleLoginChange}
                />
                <i className="bx bxs-lock-alt"></i>
              </div>
              <button id="btn" type="submit" className="btn" disabled={isLoading}>
                {isLoading ? 'Procesando...' : 'Iniciar sesión'}
              </button>

              <div className="forgot-link">
                <button
                  type="button"
                  onClick={() => setShowForgotForm(true)}
                  style={{
                    background: 'none',
                    border: 'none',
                    padding: 0,
                    margin: 0,
                    color: 'inherit',
                    textDecoration: 'underline',
                    cursor: 'pointer',
                    font: 'inherit'
                  }}
                >
                  ¿Olvidaste tu contraseña?
                </button>
              </div>
            </form>
          </div>

          <div className="form-box register">
            <form onSubmit={handleRegisterSubmit}>
              <h1>Registro</h1>
              {errors.general && <div className="error-message">{errors.general}</div>}
              <div className="input-box">
                <input
                  type="text"
                  name="name"
                  placeholder="Nombre completo"
                  required
                  value={registerData.name}
                  onChange={handleRegisterChange}
                  className={errors.name ? 'error-field' : ''}
                  autoComplete="off"
                />
                <i className="bx bxs-user"></i>
                {errors.name && <span className="input-error">{errors.name}</span>}
              </div>
              <div className="input-box">
                <input
                  type="email"
                  name="email"
                  placeholder="Correo electrónico"
                  required
                  value={registerData.email}
                  onChange={handleRegisterChange}
                  className={errors.email ? 'error-field' : ''}
                  autoComplete="off"
                />
                <i className="bx bxs-envelope"></i>
                {errors.email && <span className="input-error">{errors.email}</span>}
              </div>
              <div className="input-box">
                <input
                  type="password"
                  name="password"
                  placeholder="Contraseña"
                  required
                  value={registerData.password}
                  onChange={handleRegisterChange}
                  className={errors.password ? 'error-field' : ''}
                  autoComplete="off"
                />
                <i className="bx bxs-lock-alt"></i>
                {errors.password && <span className="input-error">{errors.password}</span>}
              </div>
              <div className="input-box">
                <input
                  type="password"
                  name="password_confirmation"
                  placeholder="Confirmar contraseña"
                  required
                  value={registerData.password_confirmation}
                  onChange={handleRegisterChange}
                  className={errors.password_confirmation ? 'error-field' : ''}
                  autoComplete="off"
                />
                <i className="bx bxs-lock-alt"></i>
                {errors.password_confirmation && <span className="input-error">{errors.password_confirmation}</span>}
              </div>
              <button type="submit" className="btn" disabled={isLoading}>
                {isLoading ? 'Procesando...' : 'Registrarse'}
              </button>
            </form>
          </div>

          <div className="toggle-box">
            <div className="toggle-panel toggle-left">
              <h1>¡Bienvenido!</h1>
              <p>Inicia sesión con:</p>
              <div className="social-icons">
                <a href="https://workspace.google.com/intl/es-419/gmail/"><i className="bx bxl-google"></i></a>
                <a href="https://www.facebook.com/"><i className="bx bxl-facebook"></i></a>
                <a href="https://github.com/login"><i className="bx bxl-github"></i></a>
              </div>
              <p>¿No tienes una cuenta?</p>
              <button className="btn register-btn" type="button">Registrarse</button>
            </div>
            <div className="toggle-panel toggle-right">
              <h1>¡Hola de nuevo!</h1>
              <p>¿Ya tienes una cuenta?</p>
              <button className="btn login-btn" type="button">Iniciar sesión</button>
              <p>O regístrate con:</p>
              <div className="social-icons">
                <a href="https://workspace.google.com/intl/es-419/gmail/"><i className="bx bxl-google"></i></a>
                <a href="https://www.facebook.com/"><i className="bx bxl-facebook"></i></a>
                <a href="https://github.com/login"><i className="bx bxl-github"></i></a>
              </div>
            </div>
          </div>

        </div>
      </div>
    </>
  );
};

export default LoginRegister;
