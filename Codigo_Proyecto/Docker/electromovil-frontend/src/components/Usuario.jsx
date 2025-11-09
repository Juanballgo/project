import React, { useState, useEffect, useContext, useRef } from 'react';
import { FaUser, FaPlus, FaEdit, FaSnowflake, FaTshirt, FaTrash, FaTools, FaCalendarAlt, FaPhone, FaMapMarkerAlt } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import '../assets/Usuario.css';
import Calidad from '../assets/img/calidad.avif';
import Rapidez from '../assets/img/Rapidez.jpg';
import Soporte from '../assets/img/soporte.jpg';
import Garantia from '../assets/img/Garantia.webp';
import Tecnico from '../assets/img/Tecnico.jpg';
import Descuento from '../assets/img/Descuento.webp';
import logoImg from '../assets/img/Logo.png';

const UserContext = React.createContext();
const INACTIVITY_TIMEOUT = 30 * 60 * 1000;

const Usuario = () => {
  const navigate = useNavigate();
  const [showProfileModal, setShowProfileModal] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [userData, setUserData] = useState({
    name: '',
    email: '',
    phone: '',
    address: '',
    current_password: '',
    password: '',
    password_confirmation: ''
  });
  const [errors, setErrors] = useState({});
  const [successMessage, setSuccessMessage] = useState('');
  const [serviceData, setServiceData] = useState({
    tipo_equipo: 'lavadora',
    marca: '',
    modelo: '',
    descripcion_problema: '',
    fecha_solicitud: new Date().toISOString().split('T')[0]
  });
  const [servicios, setServicios] = useState([]);
  const [appliances, setAppliances] = useState([]);
  const [editId, setEditId] = useState(null);
  const [editData, setEditData] = useState({});
  const [newAppliance, setNewAppliance] = useState({
    type: 'nevera',
    brand: '',
    model: '',
    purchaseDate: '',
    image: null
  });
  const [showApplianceForm, setShowApplianceForm] = useState(false);
  const [inactivityTimer, setInactivityTimer] = useState(null);
  const [showServiceModal, setShowServiceModal] = useState(false);
  const [selectedAppliance, setSelectedAppliance] = useState(null);
  const [serviceDescription, setServiceDescription] = useState('');
  const inactivityTimerRef = useRef(null);
  const [originalProfileData, setOriginalProfileData] = useState({});

  const checkAuthError = (error) => {
    if (error.response && (error.response.status === 401 || error.response.status === 419)) {
      handleLogout();
      return true;
    }
    return false;
  };

  const [userIsLoggedIn, setUserIsLoggedIn] = useState(() => {
    // Al cargar el componente, verificamos si hay token (o lo que uses)
    return Boolean(localStorage.getItem('auth_token'));
  });

  const resetInactivityTimer = () => {
    if (inactivityTimerRef.current) clearTimeout(inactivityTimerRef.current);

    if (userIsLoggedIn) {
      inactivityTimerRef.current = setTimeout(() => {
        if (userIsLoggedIn) { // Solo si sigue logueado
          handleLogout();
          alert('Has sido desconectado por inactividad');
        }
      }, INACTIVITY_TIMEOUT);
    }
  };


  const handleUserActivity = () => resetInactivityTimer();
  const [originalEmail, setOriginalEmail] = useState('');

  const fetchUserData = async () => {
    try {
      const response = await api.get('/me');
      if (!response.data) throw new Error('Datos de usuario no recibidos');

      setUserData({
        name: response.data.name || '',
        email: response.data.email || '',
        phone: response.data.phone || '',
        address: response.data.address || '',
        current_password: '',
        password: '',
        password_confirmation: ''
      });
      setOriginalEmail(response.data.email || '');
    } catch (error) {
      console.error('Error al cargar datos del usuario:', error);
      if (!checkAuthError(error)) {
        handleLogout();
      }
    }
  };

  const handleLogout = async () => {
    try {
      if (inactivityTimerRef.current) clearTimeout(inactivityTimerRef.current);

      // Limpia listeners de actividad
      const events = ['mousemove', 'keydown', 'click', 'scroll'];
      events.forEach(e => window.removeEventListener(e, handleUserActivity));
      await api.post('/logout');
      localStorage.removeItem('auth_token');
      navigate('/LoginRegister', { state: { logoutSuccess: true } });
    } catch (error) {
      localStorage.removeItem('auth_token');
      navigate('/LoginRegister');
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true);

        const [userResponse, serviciosResponse, appliancesResponse] = await Promise.all([
          api.get('/me'),
          api.get('/servicios'),
          api.get('/appliances')
        ]);

        setUserData(prev => ({
          ...prev,
          id: userResponse.data.id || '',
          name: userResponse.data.name || '',
          email: userResponse.data.email || '',
          phone: userResponse.data.phone || '',
          address: userResponse.data.address || ''
        }));

        setServicios(serviciosResponse.data);
        setAppliances(appliancesResponse.data);
      } catch (error) {
        console.error('Error al cargar datos:', error);
        if (error.response?.status === 401) handleLogout();
      } finally {
        setIsLoading(false);
      }
    };

    const events = ['mousemove', 'keydown', 'click', 'scroll'];
    events.forEach(e => window.addEventListener(e, handleUserActivity));
    resetInactivityTimer();
    fetchData();
    return () => {
      if (inactivityTimerRef.current) clearTimeout(inactivityTimerRef.current);

    };
  }, []);

  const validateProfileField = (name, value, allValues = {}) => {
    let error = '';

    // Validar si el campo está vacío
    if (!value || value.trim() === '') {
      return 'Este campo es obligatorio';
    }

    // Validaciones específicas por campo
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

    if (name === 'phone') {
      if (!/^\d{7,10}$/.test(value)) {
        error = 'El teléfono debe tener entre 7 y 10 dígitos';
      }
    }

    if (name === 'address') {
      if (value.trim().length < 5) {
        error = 'La dirección debe tener al menos 5 caracteres';
      }
    }

    if (name === 'password') {
      if (value.length < 8) {
        error = 'La contraseña debe tener al menos 8 caracteres';
      }
    }

    if (name === 'password_confirmation') {
      if (value !== allValues.password) {
        error = 'Las contraseñas no coinciden';
      }
    }

    return error;
  };


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUserData(prev => {
      const updated = { ...prev, [name]: value };
      // Validación en tiempo real
      const error = validateProfileField(name, value, updated);
      setErrors(prevErr => ({ ...prevErr, [name]: error }));
      return updated;
    });
  };

  const handleServiceInputChange = (e) => {
    const { name, value } = e.target;
    setServiceData(prev => ({ ...prev, [name]: value }));
  };
  const buildUpdatePayload = (data) => {
    const payload = {};
    for (const [key, value] of Object.entries(data)) {
      // Ignora 'id', 'current_password' o campos vacíos
      if (key !== 'id' && key !== 'current_password' && value !== '') {
        payload[key] = value;
      }
    }
    return payload;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    setSuccessMessage('');
    setIsLoading(true);

    try {
      const originalEmail = userData.email; // Guarda el email original antes de la actualización
      const dataToUpdate = buildUpdatePayload(userData);

      const response = await api.put(`/users/${userData.id}`, dataToUpdate);

      setSuccessMessage('Perfil actualizado correctamente');
      setUserData(prev => ({
        ...prev,
        current_password: '',
        password: '',
        password_confirmation: ''
      }));

      // Si cambió el email, cerrar sesión
      if (dataToUpdate.email && dataToUpdate.email !== originalEmail) {
        alert('Has cambiado tu correo, por seguridad debes iniciar sesión de nuevo.');
        await handleLogout();
        return; // Salir para que no vuelva a fetchUserData
      }

      await fetchUserData();
    } catch (error) {
      if (!checkAuthError(error)) {
        console.log('Errores desde Laravel:', error.response?.data);
        setErrors(error.response?.data?.errors || { general: 'Error al actualizar perfil' });
      }
    } finally {
      setIsLoading(false);
    }
  };

  const handleApplianceChange = (e) => {
    const { name, value } = e.target;
    setNewAppliance(prev => ({ ...prev, [name]: value }));
  };

  const handleImageUpload = (e) => {
    setNewAppliance(prev => ({ ...prev, image: e.target.files[0] }));
  };

  const saveAppliance = async (e) => {
    e.preventDefault();
    try {
      const formData = new FormData();

      Object.entries(newAppliance).forEach(([key, value]) => {
        if (value) {
          const backendKey = key === 'purchaseDate' ? 'purchase_date' : key;
          formData.append(backendKey, value);
        }
      });

      const response = await api.post('/appliances', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });

      setAppliances(prev => [...prev, response.data]);
      setNewAppliance({
        type: 'nevera',
        brand: '',
        model: '',
        purchaseDate: '',
        image: null
      });
      setShowApplianceForm(false);
    } catch (error) {
      if (!checkAuthError(error)) {
        alert('Error al guardar electrodoméstico');
      }
    }
  };

  const deleteAppliance = async (id) => {
    try {
      await api.delete(`/appliances/${id}`);
      setAppliances(prev => prev.filter(app => app.id !== id));
    } catch (error) {
      if (!checkAuthError(error)) {
        alert('Error al eliminar electrodoméstico');
      }
    }
  };

  const updateAppliance = async (id, updatedData) => {
    try {
      const response = await api.put(`/appliances/${id}`, updatedData);
      setAppliances(prev => prev.map(app => app.id === id ? response.data : app));
    } catch (error) {
      if (!checkAuthError(error)) {
        alert('Error al actualizar electrodoméstico');
      }
    }
  };

  const crearServicioTecnico = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post('/servicios', serviceData);
      setServicios(prev => [...prev, response.data]);
      alert("Servicio técnico creado con éxito!");
      setServiceData({
        tipo_equipo: '',
        marca: '',
        modelo: '',
        descripcion_problema: '',
        fecha_solicitud: new Date().toISOString().split('T')[0]
      });
    } catch (error) {
      if (!checkAuthError(error)) {
        alert("Error al crear servicio técnico");
      }
    }
  };

  if (isLoading) {
    return (
      <div className="loading-container">
        <div className="spinner"></div>
        <span>Cargando...</span>
      </div>
    );
  }

  return (
    <UserContext.Provider value={{ userData, fetchUserData }}>
      <div className="usuario-container">
        <header className="compact-header">
          <div className="logo-area">
            <img src={logoImg} alt="Logo ElectroElite" className="logo-img" />
            <span className="logo-text">ElectroElite</span>
          </div>
          <div className="header-controls">
            <div className="controls-spacer"></div>
            <button className="profile-btn" onClick={() => {
              setOriginalProfileData(userData);
              setShowProfileModal(true);
            }}>
              <FaUser className="icon" />
              <span>Perfil</span>
            </button>
            <button className="logout-btn" onClick={handleLogout}>Salir</button>
          </div>
        </header>

        <section className="servicios-section">
          <div className="section-header">
            <h2>Mis Servicios Técnicos</h2>
          </div>
          <div className="servicios-list">
            {servicios.length > 0 ? (
              servicios.map(servicio => (
                <div key={servicio.id} className="servicio-card">
                  <h3>{servicio.tipo_equipo} {servicio.marca} {servicio.modelo}</h3>

                  <p>
                    <strong>Estado:</strong>
                    <span className={`status-badge ${servicio.estado || ''}`}>
                      {servicio.estado ? servicio.estado.replace('_', ' ') : 'Sin estado'}
                    </span>
                  </p>

                  <p><strong>Problema:</strong> {servicio.descripcion_problema}</p>
                  <p><strong>Solicitado:</strong> {new Date(servicio.fecha_solicitud).toLocaleDateString()}</p>

                  {servicio.fecha_atendido && (
                    <p><strong>Atendido:</strong> {new Date(servicio.fecha_atendido).toLocaleDateString()}</p>
                  )}

                  {servicio.costo && (
                    <p><strong>Costo:</strong> ${Number(servicio.costo).toLocaleString('es-CO', { maximumFractionDigits: 0 })}</p>
                  )}
                </div>
              ))
            ) : (
              <div className="no-servicios">
                <p>No tienes servicios técnicos registrados</p>
              </div>
            )}
          </div>
        </section>

        <section className="service-request-section">
          <div className="section-header">
            <h2>Solicitar Servicio Técnico</h2>
            <p>Agenda una visita de nuestros técnicos especializados</p>
          </div>
          <form className="service-form" onSubmit={crearServicioTecnico}>
            <div className="form-grid">
              <div className="form-group">
                <label>Tipo de Equipo</label>
                <select name="tipo_equipo" value={serviceData.tipo_equipo} onChange={handleServiceInputChange} required>
                  <option value="lavadora">Lavadora</option>
                  <option value="nevera">Nevera</option>
                </select>
              </div>
              <div className="form-group">
                <label>Marca</label>
                <input type="text" name="marca" value={serviceData.marca} onChange={handleServiceInputChange} required placeholder="Ej: Samsung, LG, etc." />
              </div>
              <div className="form-group">
                <label>Modelo</label>
                <input type="text" name="modelo" value={serviceData.modelo} onChange={handleServiceInputChange} required placeholder="Modelo del equipo" />
              </div>
              <div className="form-group full-width">
                <label>Descripción del Problema</label>
                <textarea name="descripcion_problema" value={serviceData.descripcion_problema} onChange={handleServiceInputChange} required placeholder="Describa el problema con detalle" rows="4" />
              </div>
              <div className="form-group">
                <label>Fecha de Solicitud</label>
                <input type="date" name="fecha_solicitud" value={serviceData.fecha_solicitud} onChange={handleServiceInputChange} required min={new Date().toISOString().split("T")[0]} />
              </div>
            </div>
            <div className="form-submit">
              <button type="submit" className="submit-btn">Crear Servicio Técnico</button>
            </div>
          </form>
        </section>

        <section className="appliances-section">
          <div className="section-header">
            <h2>Mis Electrodomésticos</h2>
            <button className="add-appliance-btn" onClick={() => setShowApplianceForm(true)}>
              <FaPlus /> Añadir Electrodoméstico
            </button>
          </div>

          {showApplianceForm && (
            <div className="appliance-form-container">
              <form className="appliance-form" onSubmit={saveAppliance}>
                <h3 className="form-title">Agregar Nuevo Electrodoméstico</h3>
                <div className="form-grid">
                  <div className="form-group">
                    <label>Tipo</label>
                    <select name="type" value={newAppliance.type} onChange={handleApplianceChange} required className="form-control">
                      <option value="nevera">Nevera</option>
                      <option value="lavadora">Lavadora</option>
                    </select>
                  </div>
                  <div className="form-group">
                    <label>Marca</label>
                    <input type="text" name="brand" value={newAppliance.brand} onChange={handleApplianceChange} required className="form-control" placeholder="Ej: Samsung, LG, etc." />
                  </div>
                  <div className="form-group">
                    <label>Modelo</label>
                    <input type="text" name="model" value={newAppliance.model} onChange={handleApplianceChange} required className="form-control" placeholder="Ej: XT-2000, 4K-UHD, etc." />
                  </div>
                  <div className="form-group">
                    <label>Fecha de compra</label>
                    <input type="date" name="purchaseDate" value={newAppliance.purchaseDate} onChange={handleApplianceChange} required className="form-control" />
                  </div>
                  <div className="form-group full-width">
                    <label>Imagen (opcional)</label>
                    <div className="file-upload-wrapper">
                      <input type="file" accept="image/*" onChange={handleImageUpload} className="file-input" id="appliance-image" />
                      <label htmlFor="appliance-image" className="file-upload-label">
                        {newAppliance.image ? newAppliance.image.name : 'Seleccionar imagen...'}
                      </label>
                    </div>
                    {newAppliance.image && (
                      <div className="image-preview">
                        <span>Vista previa:</span>
                        <img src={URL.createObjectURL(newAppliance.image)} alt="Vista previa" className="preview-image" />
                      </div>
                    )}
                  </div>
                </div>
                <div className="form-actions">
                  <button type="button" className="cancel-btn" onClick={() => setShowApplianceForm(false)}>Cancelar</button>
                  <button type="submit" className="save-btn">Guardar Electrodoméstico</button>
                </div>
              </form>
            </div>
          )}
          <div className="appliances-grid">
            {appliances.length > 0 ? (
              appliances.map(appliance => (
                <div key={appliance.id} className="appliance-card">
                  <div className="appliance-image">
                    {appliance.image_url ? (
                      <img src={appliance.image_url} alt={appliance.type} />
                    ) : (
                      <div className="default-image">
                        {appliance.image ? (
                          <img
                            src={appliance.image}
                            alt={appliance.type}
                            className="w-20 h-20 object-cover rounded-lg"
                          />
                        ) : (
                          <span className="text-4xl">
                            {appliance.type === 'nevera' ? <FaSnowflake /> : <FaTshirt />}
                          </span>
                        )}
                      </div>
                    )}
                  </div>

                  <div className="appliance-info">
                    {editId === appliance.id ? (
                      <>
                        <div className="edit-form">
                          <div className="form-group">
                            <label>Tipo:</label>
                            <input
                              type="text"
                              name="type"
                              value={editData.type}
                              onChange={(e) => setEditData(prev => ({ ...prev, type: e.target.value }))}
                            />
                          </div>

                          <div className="form-group">
                            <label>Marca:</label>
                            <input
                              type="text"
                              name="brand"
                              value={editData.brand}
                              onChange={(e) => setEditData(prev => ({ ...prev, brand: e.target.value }))}
                            />
                          </div>

                          <div className="form-group">
                            <label>Modelo:</label>
                            <input
                              type="text"
                              name="model"
                              value={editData.model}
                              onChange={(e) => setEditData(prev => ({ ...prev, model: e.target.value }))}
                            />
                          </div>

                          <div className="form-group">
                            <label>Comprado:</label>
                            <input
                              type="date"
                              name="purchase_date"
                              value={editData.purchase_date}
                              onChange={(e) => setEditData(prev => ({ ...prev, purchase_date: e.target.value }))}
                            />
                          </div>

                          <div className="button-group">
                            <button onClick={async () => { await updateAppliance(appliance.id, editData); setEditId(null); }} className="btn-save">
                              Guardar
                            </button>

                            <button onClick={() => setEditId(null)} className="btn-cancel">
                              Cancelar
                            </button>
                          </div>
                        </div>
                      </>) : (
                      <>
                        <h3>{appliance.type.charAt(0).toUpperCase() + appliance.type.slice(1)}</h3>
                        <p><strong>Marca:</strong> {appliance.brand}</p>
                        <p><strong>Modelo:</strong> {appliance.model}</p>
                        <p><strong>Comprado:</strong> {appliance.purchase_date ? new Date(appliance.purchase_date).toLocaleDateString() : 'No especificado'}</p>

                        <button className="edit-btn" onClick={() => {
                          setEditId(appliance.id);
                          setEditData({
                            type: appliance.type,
                            brand: appliance.brand,
                            model: appliance.model,
                            purchase_date: appliance.purchase_date ? appliance.purchase_date.split('T')[0] : ''
                          });
                        }}>
                          <FaEdit />
                        </button>
                        <button className="delete-btn" onClick={() => deleteAppliance(appliance.id)}>
                          <FaTrash />
                        </button>
                      </>
                    )}
                  </div>
                  <button
                    className="solicitar-servicio-btn"
                    onClick={() => {
                      setSelectedAppliance(appliance);
                      setShowServiceModal(true);
                      setServiceDescription('');
                    }}
                  >
                    Solicitar servicio
                  </button>
                </div>
              ))
            ) : (
              <div className="no-appliances">
                <p>No tienes electrodomésticos registrados</p>
              </div>
            )}
          </div>
        </section>

        <section className="benefits-section">
          <div className="section-header">
            <h2>Nuestros Beneficios</h2>
            <p>Con ElectroElite, tienes la seguridad de un servicio confiable y garantizado</p>
          </div>
          <div className="benefits-grid">
            {[
              { icon: Calidad, title: 'Calidad', text: 'Servicios garantizados con los mejores técnicos.' },
              { icon: Rapidez, title: 'Rapidez', text: 'Atención inmediata y eficiente a domicilio.' },
              { icon: Soporte, title: 'Soporte', text: 'Soporte continuo para cualquier consulta.' },
              { icon: Garantia, title: 'Garantía', text: 'Obtén el beneficio de garantía extendida por hasta dos años.' },
              { icon: Tecnico, title: 'Técnicos Certificados', text: 'Nuestros técnicos están certificados y capacitados.' },
              { icon: Descuento, title: 'Descuentos Exclusivos', text: 'Accede a promociones especiales solo disponibles aquí.' }
            ].map((benefit, index) => (
              <div key={index} className="benefit-card">
                <div className="benefit-icon">
                  <img src={benefit.icon} alt={benefit.title} />
                </div>
                <h3>{benefit.title}</h3>
                <p>{benefit.text}</p>
              </div>
            ))}
          </div>
        </section>

        {/*Modal perfil*/}
        <div className={`modal-overlay ${showProfileModal ? 'show' : ''}`}>
          {showProfileModal && (
            <div className="modal-content">
              <div className="modal-header">
                <h3>Perfil de {userData.name || 'Usuario'}</h3>
                <button onClick={() => setShowProfileModal(false)}>×</button>
              </div>
              <form className="profile-form" onSubmit={handleSubmit}>
                {successMessage && (
                  <div className="alert alert-success">
                    {successMessage}
                    <button onClick={() => setSuccessMessage('')}>×</button>
                  </div>
                )}
                {errors.general && (
                  <div className="alert alert-danger">
                    {errors.general}
                    <button
                      type="button"
                      onClick={() => {
                        setUserData(originalProfileData);
                        setShowProfileModal(false);
                        setErrors(prev => ({ ...prev, general: '' }));
                      }}
                    >
                      ×
                    </button>
                  </div>
                )}
                {['name', 'email', 'phone', 'address'].map(field => (
                  <div key={field} className="form-group">
                    <label>{{
                      name: 'Nombre completo',
                      email: 'Correo electrónico',
                      phone: 'Teléfono',
                      address: 'Dirección'
                    }[field]}</label>
                    <input
                      type={{
                        name: 'text',
                        email: 'email',
                        phone: 'tel',
                        address: 'text'
                      }[field]}
                      name={field}
                      value={userData[field]}
                      onChange={handleInputChange}
                      className={errors[field] ? 'is-invalid' : ''}
                    />
                    {errors[field] && (
                      <div className="invalid-feedback">
                        {Array.isArray(errors[field]) ? errors[field][0] : errors[field]}
                      </div>
                    )}
                  </div>
                ))}

                <div className="password-section">
                  <h4>Cambiar contraseña</h4>
                  <p className="text-muted">Deja estos campos vacíos si no deseas cambiar la contraseña</p>

                  {['current_password', 'password', 'password_confirmation'].map(field => (
                    <div key={field} className="form-group">
                      <label>{{
                        current_password: 'Contraseña actual',
                        password: 'Nueva contraseña',
                        password_confirmation: 'Confirmar nueva contraseña'
                      }[field]}</label>
                      <input
                        type="password"
                        name={field}
                        value={userData[field]}
                        onChange={handleInputChange}
                        placeholder={{
                          current_password: 'Ingresa tu contraseña actual',
                          password: 'Mínimo 8 caracteres',
                          password_confirmation: 'Repite tu nueva contraseña'
                        }[field]}
                        className={errors[field] ? 'is-invalid' : ''}
                      />
                      {errors[field] && <div className="invalid-feedback">{errors[field][0]}</div>}
                    </div>
                  ))}
                </div>

                <div className="form-actions">
                  <button
                    type="button"
                    className="cancel-btn"
                    onClick={() => {
                      setUserData(originalProfileData);
                      setShowProfileModal(false);
                      setErrors({});
                    }}
                    disabled={isLoading}
                  >
                    Cancelar
                  </button>
                  <button type="submit" className="save-btn" disabled={isLoading}>
                    {isLoading ? 'Guardando...' : 'Guardar cambios'}
                  </button>
                </div>
              </form>
            </div>
          )}
        </div>

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
      {showServiceModal && selectedAppliance && (
        <div className="modal-overlay show">
          <div className="modal-content">
            <div className="modal-header">
              <h3>Solicitar Servicio para {selectedAppliance.type} {selectedAppliance.brand}</h3>
              <button onClick={() => setShowServiceModal(false)}>×</button>
            </div>
            <form
              onSubmit={async (e) => {
                e.preventDefault();
                try {
                  await api.post('/servicios', {
                    tipo_equipo: selectedAppliance.type,
                    marca: selectedAppliance.brand,
                    modelo: selectedAppliance.model,
                    descripcion_problema: serviceDescription,
                    fecha_solicitud: new Date().toISOString().split('T')[0]
                  });
                  alert('Servicio técnico solicitado con éxito');
                  setShowServiceModal(false);
                  setServiceDescription('');
                } catch (error) {
                  alert('Error al solicitar servicio');
                }
              }}
            >
              <div className="form-group">
                <label>Descripción del problema</label>
                <textarea
                  value={serviceDescription}
                  onChange={e => setServiceDescription(e.target.value)}
                  required
                  placeholder="Describe el problema o lo que necesitas"
                  rows={4}
                />
              </div>
              <div className="form-actions">
                <button type="button" className="cancel-btn" onClick={() => setShowServiceModal(false)}>
                  Cancelar
                </button>
                <button type="submit" className="save-btn">
                  Solicitar Servicio
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </UserContext.Provider>
  );
};

export const useUser = () => {
  const context = useContext(UserContext);
  if (!context) throw new Error('useUser debe usarse dentro de UserProvider');
  return context;
};

export default Usuario;