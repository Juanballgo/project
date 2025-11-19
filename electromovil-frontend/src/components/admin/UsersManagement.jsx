import React, { useEffect, useState } from 'react';
import { api } from '../../services/api';
import { Modal, Button } from 'react-bootstrap';

const UsersManagement = () => {
  const [users, setUsers] = useState([]);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    role: 'cliente',
    phone: '',
    address: ''
  });
  const [addUserData, setAddUserData] = useState({
    name: '',
    email: '',
    role: '',
    phone: '',
    address: '',
    password: '',
    password_confirmation: ''
  });

  const [addErrors, setAddErrors] = useState({});
  const [editErrors, setEditErrors] = useState({});
  const [showEditModal, setShowEditModal] = useState(false);
  const [showAddModal, setShowAddModal] = useState(false);
  const [editingUser, setEditingUser] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 10; //cantidad de usuarios por página


  const fetchUsers = async () => {
    setLoading(true);
    try {
      const response = await api.get('/users');
      setUsers(response.data);
    } catch (error) {
      console.error('Error al obtener usuarios:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    validateEditField(name, value);
  };

  const handleAddChange = (e) => {
    const { name, value } = e.target;
    setAddUserData(prev => ({ ...prev, [name]: value }));
    validateAddField(name, value);
  };

  {/*validación de campos*/ }
  const validateEditField = (name, value) => {
    let error = '';

    switch (name) {
      case 'name':
        if (!value.trim()) error = 'El nombre es obligatorio';
        else if (!/^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$/.test(value)) error = 'Nombre inválido';
        break;
      case 'email':
        if (!value.trim()) error = 'El correo es obligatorio';
        else if (!/\S+@\S+\.\S+/.test(value)) error = 'Correo inválido';
        break;
      case 'role':
        if (!value) error = 'El rol es obligatorio';
        break;
      case 'phone':
        if (!value.trim()) {
          error = 'El teléfono es obligatorio';
        } else if (!/^\d+$/.test(value)) {
          error = 'El teléfono solo puede contener números';
        } else if (value.length < 10) {
          error = 'El teléfono debe tener al menos 10 dígitos';
        } else if (value.length > 10) {
          error = 'El teléfono no puede contener más de 10 dígitos';
        }
        break;
      case 'address':
        if (!value.trim()) error = 'La dirección es obligatoria';
        break;
      default:
        break;
    }

    setEditErrors(prev => ({ ...prev, [name]: error }));
  };
  const validateEditForm = () => {
    const errors = {};

    for (const [name, value] of Object.entries(formData)) {
      validateEditField(name, value);
      let error = '';

      switch (name) {
        case 'name':
          if (!value.trim()) error = 'El nombre es obligatorio';
          else if (!/^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$/.test(value)) error = 'Nombre inválido';
          break;
        case 'email':
          if (!value.trim()) error = 'El correo es obligatorio';
          else if (!/\S+@\S+\.\S+/.test(value)) error = 'Correo inválido';
          break;
        case 'role':
          if (!value) error = 'El rol es obligatorio';
          break;
        case 'phone':
          if (!value.trim()) error = 'El teléfono es obligatorio';
          else if (!/^\d{7,15}$/.test(value)) error = 'Teléfono inválido';
          break;
        case 'address':
          if (!value.trim()) error = 'La dirección es obligatoria';
          break;
        default:
          break;
      }

      if (error) errors[name] = error;
    }

    setEditErrors(errors);

    return Object.keys(errors).length === 0;
  };

  const validateAddField = (name, value) => {
    let error = '';
    switch (name) {
      case 'name':
        if (!value.trim()) {
          error = 'El nombre es obligatorio';
        } else if (!/^[a-zA-Z\s]+$/.test(value)) {
          error = 'El nombre solo puede contener letras';
        }
        break;
      case 'email':
        if (!value.trim()) error = 'El correo es obligatorio';
        else if (!/\S+@\S+\.\S+/.test(value)) error = 'Correo inválido';
        break;
      case 'role':
        if (!value) error = 'El rol es obligatorio';
        break;
      case 'password':
        if (!value) error = 'La contraseña es obligatoria';
        break;
      case 'password_confirmation':
        if (value !== addUserData.password) error = 'Las contraseñas no coinciden';
        break;
      case 'phone':
        if (!value.trim()) {
          error = 'El teléfono es obligatorio';
        } else if (!/^\d+$/.test(value)) {
          error = 'El teléfono solo puede contener números';
        } else if (value.length < 10) {
          error = 'El teléfono debe tener al menos 10 dígitos';
        } else if (value.length > 10) {
          error = 'El teléfono no puede contener mas de 10 dígitos';
        }
        break;
      case 'address':
        if (!value.trim()) {
          error = 'La dirección es obligatoria';
        } else if (value.length < 5) {
          error = 'La dirección debe tener al menos 5 caracteres';
        }
        break;

      default:
        break;
    }
    setAddErrors(prevErrors => ({ ...prevErrors, [name]: error }));
  };

  const validateAddForm = () => {
    const fields = [
      { name: 'name', value: addUserData.name },
      { name: 'email', value: addUserData.email },
      { name: 'role', value: addUserData.role },
      { name: 'phone', value: addUserData.phone },
      { name: 'address', value: addUserData.address },
      { name: 'password', value: addUserData.password },
      { name: 'password_confirmation', value: addUserData.password_confirmation },
    ];

    let isValid = true;
    const newErrors = {};

    fields.forEach(field => {
      let error = '';

      switch (field.name) {
        case 'name':
          if (!field.value.trim()) {
            error = 'El nombre es obligatorio';
          } else if (!/^[a-zA-ZÀ-ÿ\s]+$/.test(field.value)) {
            error = 'El nombre solo puede contener letras y espacios';
          }
          break;
        case 'email':
          if (!field.value.trim()) {
            error = 'El correo es obligatorio';
          } else if (!/\S+@\S+\.\S+/.test(field.value)) {
            error = 'Correo inválido';
          }
          break;
        case 'role':
          if (!field.value) {
            error = 'El rol es obligatorio';
          }
          break;
        case 'phone':
          if (!field.value.trim()) {
            error = 'El teléfono es obligatorio';
          } else if (!/^\d+$/.test(field.value)) {
            error = 'El teléfono solo puede contener números';
          } else if (field.value.length < 8) {
            error = 'El teléfono debe tener al menos 8 dígitos';
          }
          break;
        case 'address':
          if (!field.value.trim()) {
            error = 'La dirección es obligatoria';
          } else if (field.value.length < 5) {
            error = 'La dirección debe tener al menos 5 caracteres';
          }
          break;
        case 'password':
          if (!field.value.trim()) {
            error = 'La contraseña es obligatoria';
          } else if (field.value.length < 6) {
            error = 'La contraseña debe tener al menos 6 caracteres';
          }
          break;
        case 'password_confirmation':
          if (!field.value.trim()) {
            error = 'La confirmación de contraseña es obligatoria';
          } else if (field.value !== addUserData.password) {
            error = 'Las contraseñas no coinciden';
          }
          break;
        default:
          break;
      }

      if (error) {
        isValid = false;
        newErrors[field.name] = error;
      }
    });

    setAddErrors(newErrors);
    return isValid;
  };
  const handleCloseAddModal = () => {
    setShowAddModal(false);
    setAddUserData({
      name: '',
      email: '',
      role: '',
      phone: '',
      address: '',
      password: '',
      password_confirmation: ''
    });
    setAddErrors({});
  };

  const handleAddUser = async () => {
    if (!validateAddForm()) {
      return; // No enviamos formulario si hay errores
    }
    try {
      await api.post('/register', addUserData); // Ajusta según tu backend
      fetchUsers();
      setAddUserData({ name: '', email: '', password: '', password_confirmation: '' });
      setShowAddModal(false);
    } catch (error) {
      console.error('Error al agregar usuario:', error);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('¿Estás seguro de que deseas eliminar este usuario?')) return;
    try {
      await api.delete(`/users/${id}`);
      fetchUsers();
    } catch (error) {
      console.error('Error al eliminar usuario:', error);
    }
  };

  const handleEdit = (user) => {
    setEditingUser(user);
    setFormData(user); // Cargar los datos del usuario en el formulario
    setShowEditModal(true); // Mostrar el modal
  };

  const handleUpdate = async () => {
    if (!validateEditForm()) {
      return;
    }
    try {
      await api.put(`/users/${editingUser.id}`, formData);
      fetchUsers();
      setShowEditModal(false); // Cerrar el modal
      setFormData({ name: '', email: '', role: 'cliente', phone: '', address: '' }); // Resetear el formulario
    } catch (error) {
      console.error('Error al editar usuario:', error);
    }
  };

  const filteredUsers = users.filter(user =>
    user.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    user.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
    user.role.toLowerCase().includes(searchTerm.toLowerCase())
  );
  if (loading) {
    return (
      <div className="custom-spinner-container">
        <div className="custom-spinner"></div>
        <div className="spinner-text">Cargando usuarios...</div>
      </div>
    );
  }
  const totalPages = Math.ceil(filteredUsers.length / pageSize);
  const paginatedUsers = filteredUsers.slice(
    (currentPage - 1) * pageSize,
    currentPage * pageSize
  );
  return (
    <div className="card p-4">
      <h2>Gestión de Usuarios</h2>

      <div className="mb-3">
        <input
          type="text"
          className={`form-control ${addErrors.name ? 'is-invalid' : ''}`}
          placeholder="Buscar por nombre, correo o rol"
          value={searchTerm}
          onChange={(e) => { setSearchTerm(e.target.value); setCurrentPage(1); }}
        />
      </div>

      <div className="mb-3">
        <Button variant="primary" onClick={() => setShowAddModal(true)}>
          Añadir Usuario
        </Button>
      </div>

      <div className="table-responsive">
        <table className="table table-bordered table-hover">
          <thead className="table-dark">
            <tr>
              <th>#</th>
              <th>Nombre</th>
              <th>Correo</th>
              <th>Rol</th>
              <th>Teléfono</th>
              <th>Dirección</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {paginatedUsers.length === 0 ? (
              <tr>
                <td colSpan="7" className="text-center">No hay usuarios registrados.</td>
              </tr>
            ) : (
              paginatedUsers.map((user, index) => (
                <tr key={user.id}>
                  <td>{index + 1}</td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>{user.role}</td>
                  <td>{user.phone || '-'}</td>
                  <td>{user.address || '-'}</td>
                  <td>
                    <div className="d-flex flex-column gap-1">
                      <button className="btn btn-sm btn-info" onClick={() => handleEdit(user)}>Editar</button>
                      <button className="btn btn-sm btn-danger" onClick={() => handleDelete(user.id)}>Eliminar</button>
                    </div>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
      
      {/* Paginación */}
      <div className="pagination-controls d-flex justify-content-center align-items-center my-3">
        <Button
          variant="secondary"
          size="sm"
          disabled={currentPage === 1}
          onClick={() => setCurrentPage(currentPage - 1)}
          className="me-2"
        >
          Anterior
        </Button>
        <span>Página {currentPage} de {totalPages}</span>
        <Button
          variant="secondary"
          size="sm"
          disabled={currentPage === totalPages || totalPages === 0}
          onClick={() => setCurrentPage(currentPage + 1)}
          className="ms-2"
        >
          Siguiente
        </Button>
      </div>

      {/* Modal para añadir usuario */}
      <Modal show={showAddModal} onHide={() => setShowAddModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Crear Nuevo Usuario</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div className="mb-3">
            <input
              name="name"
              className={`form-control ${addErrors.name ? 'is-invalid' : ''}`}
              placeholder="Nombre"
              value={addUserData.name}
              onChange={handleAddChange}
            />
            {addErrors.name && <div className="invalid-feedback">{addErrors.name}</div>}
          </div>
          <div className="mb-3">
            <input
              name="email"
              className={`form-control ${addErrors.email ? 'is-invalid' : ''}`}
              placeholder="Correo electrónico"
              value={addUserData.email}
              onChange={handleAddChange}
            />
            {addErrors.email && <div className="invalid-feedback">{addErrors.email}</div>}
          </div>
          <div className="mb-3">
            <select
              name="role"
              className={`form-select ${addErrors.role ? 'is-invalid' : ''}`}
              value={addUserData.role}
              onChange={handleAddChange}
            >
              <option value="">Seleccione un rol</option>
              <option value="cliente">Cliente</option>
              <option value="tecnico">Técnico</option>
              <option value="admin">Admin</option>
            </select>
            {addErrors.role && <div className="invalid-feedback">{addErrors.role}</div>}
          </div>
          <div className="mb-3">
            <input
              name="phone"
              type="text"
              autoComplete='off'
              className={`form-control ${addErrors.phone ? 'is-invalid' : ''}`}
              placeholder="Celular"
              value={addUserData.phone}
              onChange={handleAddChange}
            />
            {addErrors.phone && <div className="invalid-feedback">{addErrors.phone}</div>}
          </div>
          <div className="mb-3">
            <input
              name="address"
              autoComplete='off'
              className={`form-control ${addErrors.address ? 'is-invalid' : ''}`}
              placeholder="Dirección"
              value={addUserData.address}
              onChange={handleAddChange}
            />
            {addErrors.address && <div className="invalid-feedback">{addErrors.address}</div>}
          </div>
          <div className="mb-3">
            <input
              name="password"
              type="password"
              className={`form-control ${addErrors.password ? 'is-invalid' : ''}`}
              placeholder="Contraseña"
              value={addUserData.password}
              onChange={handleAddChange}
            />
          </div>
          {addErrors.password && <div className="invalid-feedback">{addErrors.password}</div>}
          <div className="mb-3">
            <input
              name="password_confirmation"
              className={`form-control ${addErrors.password_confirmation ? 'is-invalid' : ''}`}
              placeholder="Confirmar Contraseña"
              value={addUserData.password_confirmation}
              onChange={handleAddChange}
            />
            {addErrors.password_confirmation && <div className="invalid-feedback">{addErrors.password_confirmation}</div>}
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseAddModal}>
            Cerrar
          </Button>
          <Button variant="primary" onClick={handleAddUser}>
            Crear Usuario
          </Button>
        </Modal.Footer>
      </Modal>

      {/* Modal para editar usuario */}
      <Modal show={showEditModal} onHide={() => setShowEditModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Editar Usuario</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {['name', 'email', 'role', 'phone', 'address'].map(field => (
            <div key={field} className="mb-3">
              {field !== 'role' ? (
                <>
                  <label>{{
                    name: 'Nombre completo',
                    email: 'Correo electrónico',
                    phone: 'Teléfono',
                    address: 'Dirección'
                  }[field]}</label>
                  <input
                    name={field}
                    type={{
                      name: 'text',
                      email: 'email',
                      phone: 'tel',
                      address: 'text'
                    }[field] || 'text'}
                    className={`form-control ${editErrors[field] ? 'is-invalid' : ''}`}
                    placeholder={{
                      name: 'Ingrese el nombre completo',
                      email: 'Ingrese el correo electrónico',
                      phone: 'Ingrese el teléfono',
                      address: 'Ingrese la dirección'
                    }[field]}
                    value={formData[field]}
                    onChange={handleChange}
                  />
                  {editErrors[field] && <div className="invalid-feedback">{editErrors[field]}</div>}
                </>
              ) : (
                <>
                  <label>Rol</label>
                  <select
                    name="role"
                    className={`form-select ${editErrors.role ? 'is-invalid' : ''}`}
                    value={formData.role}
                    onChange={handleChange}
                  >
                    <option value="">Seleccione un rol</option>
                    <option value="cliente">Cliente</option>
                    <option value="tecnico">Técnico</option>
                    <option value="admin">Admin</option>
                  </select>
                  {editErrors.role && <div className="invalid-feedback">{editErrors.role}</div>}
                </>
              )}
            </div>
          ))}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowEditModal(false)}>
            Cerrar
          </Button>
          <Button variant="primary" onClick={handleUpdate}>
            Guardar Cambios
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default UsersManagement;
