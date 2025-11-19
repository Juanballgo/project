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

    registerBtn?.addEventListener('click', () => container?.classList.add('active'));
    loginBtn?.addEventListener('click', () => container?.classList.remove('active'));

    return () => {
      registerBtn?.removeEventListener('click', () => { });
      loginBtn?.removeEventListener('click', () => { });
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
      // Aquí deberías llamar a tu endpoint real de recuperación
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

      const token = loginResponse.data.token || loginResponse.data.access_token;
      if (token) localStorage.setItem('auth_token', token);

      const user = (await api.getCurrentUser()).data;
      setRegistrationSuccess(true);

      setTimeout(() => {
        navigate('/usuario');
        onLoginSuccess?.(user);
      }, 2000);
    } catch (error) {
      console.error('Error en registro:', error);
      setErrors(error.response?.data?.errors || {
        general: error.response?.data?.message || 'Error en el registro'
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
      const token = response.data.token || response.data.access_token;
      if (token) localStorage.setItem('auth_token', token);

      const user = (await api.getCurrentUser()).data;

      const redirectPath = {
        'admin': '/admin',
        'tecnico': '/tecnico',
        'cliente': '/usuario'
      }[user.role] || '/usuario';

      navigate(redirectPath);
      onLoginSuccess?.(user);
    } catch (error) {
      setErrors({ general: error.response?.data?.message || 'Error al iniciar sesión' });
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
          <button className="close-btn" onClick={() => {
            setShowForgotForm(false);
            setForgotSuccess('');
            setForgotError('');
            setForgotEmail('');
          }}>&times;</button>
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
            <button type="submit" className="btn" style={{ marginTop: 12 }}>Enviar enlace</button>
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
                autoComplete='off'
              />
              <i className='bx bxs-envelope'></i>
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
              <i className='bx bxs-lock-alt'></i>
            </div>
            <button  id= "btn" type="submit" className="btn" disabled={isLoading}>
              {isLoading ? 'Procesando...' : 'Iniciar sesión'}
            </button>
            <div className="forgot-link">
              <a href="#" onClick={e => { e.preventDefault(); setShowForgotForm(true); }}>
                ¿Olvidaste tu contraseña?
              </a>
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
                autoComplete='off'
              />
              <i className='bx bxs-user'></i>
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
                autoComplete='off'
              />
              <i className='bx bxs-envelope'></i>
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
                autoComplete='off'
              />
              <i className='bx bxs-lock-alt'></i>
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
                autoComplete='off'
              />
              <i className='bx bxs-lock-alt'></i>
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
              <a href="https://workspace.google.com/intl/es-419/gmail/"><i className='bx bxl-google'></i></a>
              <a href="https://www.facebook.com/"><i className='bx bxl-facebook'></i></a>
              <a href="https://github.com/login"><i className='bx bxl-github'></i></a>
            </div>
            <p>¿No tienes una cuenta?</p>
            <button className="btn register-btn">Registrarse</button>
          </div>
          <div className="toggle-panel toggle-right">
            <h1>¡Hola de nuevo!</h1>
            <p>¿Ya tienes una cuenta?</p>
            <button className="btn login-btn">Iniciar sesión</button>
            <p>O regístrate con:</p>
            <div className="social-icons">
              <a href="https://workspace.google.com/intl/es-419/gmail/"><i className='bx bxl-google'></i></a>
              <a href="https://www.facebook.com/"><i className='bx bxl-facebook'></i></a>
              <a href="https://github.com/login"><i className='bx bxl-github'></i></a>
            </div>
          </div>
        </div>
      </div>
    </div>
     </>
  );
};

export default LoginRegister;
