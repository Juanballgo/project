import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Outlet, Link, useNavigate, useLocation } from 'react-router-dom';
import { faMapMarkerAlt, faTasks, faFileAlt, faUser, faPhone, faEnvelope, faHome, faLock } from '@fortawesome/free-solid-svg-icons';
import '../../assets/admin.css';
import { api } from '../../services/api';
import { BiGroup, BiWrench, BiReceipt, BiBarChartAlt2 } from "react-icons/bi";
import logoImg from '../../assets/img/Logo.png';


const AdminDashboard = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [showProfileModal, setShowProfileModal] = useState(false);
  const userData = JSON.parse(localStorage.getItem('userData'));
  const [loading, setLoading] = useState(true);
  const [profile, setProfile] = useState({
    name: '',
    email: '',
    phone: '',
    address: '',
    current_password: '',
    password: '',
    password_confirmation: ''
  });
  const [errors, setErrors,] = useState({});
  const [successMessage, setSuccessMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  // Cargar datos guardados en localStorage si existen
  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const res = await api.get('/me');
        setProfile(prev => ({
          ...prev,
          id: res.data.id, // Guarda el id aquí
          name: res.data.name || '',
          email: res.data.email || '',
          phone: res.data.phone || '',
          address: res.data.address || ''
        }));
      } catch (error) {
        console.error('Error al cargar el perfil:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchProfile();
  }, []);

  const handleProfileChange = (e) => {
    const { name, value } = e.target;
    setProfile(prev => ({ ...prev, [name]: value }));
  };

  const checkAuthError = (error) => {
    if (error.response && (error.response.status === 401 || error.response.status === 419)) {
      handleLogout();
      return true;
    }
    return false;
  };

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const res = await api.get('/me');
        setProfile(prev => ({
          ...prev,
          id: res.data.id, // Guarda el id aquí
          name: res.data.name || '',
          email: res.data.email || '',
          phone: res.data.phone || '',
          address: res.data.address || ''
        }));
      } catch (error) {
        console.error('Error al cargar el perfil:', error);
      }
    };
    fetchProfile();
  }, []);
  const actualizarPerfil = async (e) => {
    e.preventDefault();
    if (!profile.id) {
      alert('No se encontró información del usuario. Por favor, vuelve a iniciar sesión.');
      return;
    }
    // Construye el objeto solo con los campos necesarios
    const data = {
      name: profile.name,
      email: profile.email,
      phone: profile.phone,
      address: profile.address
    };
    // Solo agrega los campos de contraseña si el usuario los llenó
    if (profile.password) {
      data.password = profile.password;
      data.password_confirmation = profile.password_confirmation;
      data.current_password = profile.current_password;
    }
    try {
      await api.put(`/users/${profile.id}`, data);
      alert('Perfil actualizado correctamente');
      setShowProfileModal(false);
      setProfile(prev => ({
        ...prev,
        current_password: '',
        password: '',
        password_confirmation: ''
      }));
    } catch (error) {
      // Muestra los errores del backend si existen
      if (error.response && error.response.data && error.response.data.errors) {
        const errores = error.response.data.errors;
        alert(Object.values(errores).flat().join('\n'));
      } else {
        alert('Error al actualizar el perfil');
      }
      console.error(error);
    }
  };

  const handleLogout = async () => {
    try {
      await api.post('/logout');
    } catch (error) {
      console.error('Logout error:', error);
    } finally {
      localStorage.removeItem('auth_token');
      localStorage.removeItem('userData');
      navigate('/login-register', { replace: true });
    }
  };
  const handleElectroMovilClick = () => {
    navigate('/admin');
  };
  const isBaseRoute = location.pathname === '/admin' || location.pathname === '/admin/';

  const dashboardOptions = [
    {
      path: 'users',
      icon: <BiGroup />,
      title: 'Gestión de Usuarios',
      description: 'Administra los usuarios del sistema, roles y permisos.'
    },
    {
      path: 'services',
      icon: <BiWrench />,
      title: 'Gestión de Servicios',
      description: 'Gestiona los servicios ofrecidos por la empresa.'
    },
    //{
    // path: 'invoices',
    //icon: <BiReceipt />,
    //title: 'Gestión de Facturas',
    //description: 'Administra las facturas y transacciones de los clientes.'
    //},
    //{
    //path: 'reports',
    //icon: <BiBarChartAlt2 />,
    //title: 'Reportes',
    //description: 'Genera reportes y estadísticas del sistema.'
    //}
  ];
  if (loading) {
    return (
      <div className="loading-container">
        <div className="spinner"></div>
        <span>Cargando...</span>
      </div>
    );
  }
  return (
    <div className="usuario-container">
      <header className="compact-header">
        <h1 className="admin-logo" onClick={handleElectroMovilClick} style={{ cursor: 'pointer' }}>
          <img src={logoImg} alt="Logo ElectroElite" className="logo-img" />
          <span className="logo-text">ElectroElite</span>
        </h1>
        <div className="header-controls">
          <div className="controls-spacer"></div>
          <button
            className="profile-btn"
            onClick={() => setShowProfileModal(true)}>
            <FontAwesomeIcon icon={faUser} />
          </button>
          <button className="logout-btn" onClick={handleLogout}>Salir</button>
        </div>
      </header>

      <div className="container-fluid admin-content">
        {isBaseRoute ? (
          <div className="row dashboard-container">
            <h2 className="dashboard-title">Panel de Administración</h2>
            <p className="dashboard-subtitle">Selecciona una opción para gestionar</p>
            <div className="dashboard-options">
              {dashboardOptions.map(option => (
                <Link to={option.path} key={option.path} className="dashboard-card">
                  <div className="card-icon">
                    {option.icon}
                  </div>
                  <h3>{option.title}</h3>
                  <p>{option.description}</p>
                </Link>
              ))}
            </div>
          </div>
        ) : (
          <div className="admin-content">
            <div className="content-container" style={{ width: "100%", maxWidth: "100%", padding: 0, boxShadow: "none", background: "transparent" }}>
              <Outlet />
            </div>
          </div>
        )}
      </div>

      {/* MODAL PERFIL */}


      {/* Modal de perfil */}
      {showProfileModal && (
        <div className={`modal-overlay ${showProfileModal ? 'show' : ''}`}>
          <div className="modal-content">
            <div className="modal-header">
              <h3>Perfil del Administrador</h3>
              <button onClick={() => setShowProfileModal(false)}>×</button>
            </div>
            <form className="profile-form" onSubmit={actualizarPerfil}>
              <div className="form-group">
                <label>Nombre completo</label>
                <input
                  type="text"
                  name="name"
                  value={profile.name}
                  onChange={handleProfileChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Correo electrónico</label>
                <input
                  type="email"
                  name="email"
                  value={profile.email}
                  onChange={handleProfileChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Teléfono</label>
                <input
                  type="tel"
                  name="phone"
                  value={profile.phone}
                  onChange={handleProfileChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Dirección</label>
                <input
                  type="text"
                  name="address"
                  value={profile.address}
                  onChange={handleProfileChange}
                  required
                />
              </div>

              <div className="password-section">
                <h4>Cambiar contraseña</h4>

                <div className="form-group">
                  <label>Contraseña actual</label>
                  <input
                    type="password"
                    name="current_password"
                    value={profile.current_password}
                    onChange={handleProfileChange}
                  />
                </div>

                <div className="form-group">
                  <label>Nueva contraseña</label>
                  <input
                    type="password"
                    name="password"
                    value={profile.password}
                    onChange={handleProfileChange}
                  />
                </div>

                <div className="form-group">
                  <label>Confirmar nueva contraseña</label>
                  <input
                    type="password"
                    name="password_confirmation"
                    value={profile.password_confirmation}
                    onChange={handleProfileChange}
                  />
                </div>
              </div>

              <div className="form-actions">
                <button type="button" className="cancel-btn" onClick={() => setShowProfileModal(false)}>
                  Cancelar
                </button>
                <button type="submit" className="save-btn">
                  Guardar cambios
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      <footer className="app-footer">
        <div className="footer-content">
          <p>&copy; {new Date().getFullYear()} ElectroElite. Todos los derechos reservados.</p>
          <div className="footer-links">
            <a href="#">Términos y condiciones</a>
            <a href="#">Política de privacidad</a>
            <a href="#">Contacto</a>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default AdminDashboard;