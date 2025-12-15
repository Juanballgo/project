import React, { useState, useEffect, useCallback } from 'react';
import { Table, Button, Form, Modal, Alert, Badge, Dropdown } from 'react-bootstrap';
import apiService, { api } from '../../services/api.js';
import { FaEdit, FaExchangeAlt } from "react-icons/fa";
import '../../assets/admin.css';

const ServicesManagement = () => {
  const [services, setServices] = useState([]);
  const [filteredServices, setFilteredServices] = useState([]);
  const [technicians, setTechnicians] = useState([]);
  const [cient, setcient] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [currentService, setCurrentService] = useState(null);
  const [alert, setAlert] = useState({ show: false, message: '', variant: 'success' });
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 10;

  const authConfig = () => {
    const token = localStorage.getItem('auth_token');
    return token
      ? { headers: { Authorization: `Bearer ${token}` } }
      : {};
  };

  const showAlert = (message, variant) => {
    setAlert({ show: true, message, variant });
    setTimeout(() => setAlert(prev => ({ ...prev, show: false })), 3000);
  };

  const fetchServices = async () => {
    setLoading(true);
    try {
      const response = await apiService.getServicios();
      setServices(response.data);
      setFilteredServices(response.data);
    } catch (error) {
      console.error(error);
      showAlert('Error al cargar servicios', 'danger');
    } finally {
      setLoading(false);
    }
  };

  const fetchTechnicians = async () => {
    try {
      const response = await api.get('/users/by-role?role=tecnico', authConfig());
      setTechnicians(response.data);
    } catch (error) {
      console.error(error);
      showAlert('Error al cargar técnicos', 'danger');
    }
  };

  const fetchcient = async () => {
    try {
      const response = await api.get('/users/by-role?role=cliente', authConfig());
      setcient(response.data);
    } catch (error) {
      console.error(error);
      showAlert('Error al cargar clientes', 'danger');
    }
  };

  useEffect(() => {
    fetchServices();
    fetchTechnicians();
    fetchcient();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const filterServices = useCallback((term) => {
    const lowerTerm = (term || '').toLowerCase().trim();

    const filtered = services.filter(service => {
      const tecnicoName = service.tecnico?.name?.toLowerCase() || '';
      const tieneTecnico = tecnicoName.length > 0;

      const matchesTecnicoNoAsignado = lowerTerm === 'no asignado' && !tieneTecnico;

      return (
        service.tipo_equipo?.toLowerCase().includes(lowerTerm) ||
        service.marca?.toLowerCase().includes(lowerTerm) ||
        service.modelo?.toLowerCase().includes(lowerTerm) ||
        service.descripcion_problema?.toLowerCase().includes(lowerTerm) ||
        service.estado?.toLowerCase().includes(lowerTerm) ||
        matchesTecnicoNoAsignado ||
        tecnicoName.includes(lowerTerm)
      );
    });

    setFilteredServices(filtered);
  }, [services]);

  useEffect(() => {
    filterServices(searchTerm);
    setCurrentPage(1);
  }, [searchTerm, filterServices]);

  const handleEdit = (service) => {
    setCurrentService({
      ...service,
      cientid: service.cliente_id || '',
      technicianId: service.tecnico_id || '',
      description: service.descripcion_problema,
      status: service.estado,
      notes: service.notas || '',
      tipo_equipo: service.tipo_equipo,
      marca: service.marca,
      modelo: service.modelo,
      diagnostico: service.diagnostico,
      solucion: service.solucion,
      costo: service.costo,
      fecha_solicitud: service.fecha_solicitud,
      fecha_atendido: service.fecha_atendido,
      fecha_completado: service.fecha_completado,
    });
    setShowModal(true);
  };

  const handleStatusChange = async (serviceId, newStatus) => {
    try {
      await api.put(
        `/servicios/${serviceId}`,
        { estado: newStatus },
        authConfig()
      );
      await fetchServices();
      showAlert('Estado del servicio actualizado', 'success');
    } catch (error) {
      console.error(error);
      showAlert('Error al actualizar servicio', 'danger');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const payload = {
        tipo_equipo: currentService.tipo_equipo,
        marca: currentService.marca,
        modelo: currentService.modelo,
        descripcion_problema: currentService.description,
        estado: currentService.status,
        diagnostico: currentService.diagnostico,
        solucion: currentService.solucion,
        costo: currentService.costo,
        fecha_solicitud: currentService.fecha_solicitud,
        fecha_atendido: currentService.fecha_atendido,
        fecha_completado: currentService.fecha_completado,
        tecnico_id: currentService.technicianId || null,
        cliente_id: currentService.cientid || null,
      };

      if (currentService.id) {
        await api.put(`/servicios/${currentService.id}`, payload, authConfig());
      } else {
        await api.post('/servicios', payload, authConfig());
      }

      setShowModal(false);
      await fetchServices();
      showAlert(
        `Servicio ${currentService.id ? 'actualizado' : 'creado'} correctamente`,
        'success'
      );
    } catch (error) {
      console.error(error);
      showAlert('Error al guardar servicio', 'danger');
    }
  };

  const getStatusBadge = (status) => {
    const variants = {
      pendiente: 'warning',
      en_proceso: 'primary',
      completado: 'success',
      cancelado: 'danger'
    };
    return <Badge bg={variants[status]}>{String(status).replace('_', ' ')}</Badge>;
  };

  if (loading) {
    return (
      <div className="custom-spinner-container">
        <div className="custom-spinner"></div>
        <div className="spinner-text">Cargando servicios...</div>
      </div>
    );
  }

  const totalPages = Math.ceil(filteredServices.length / pageSize);
  const paginatedServices = filteredServices.slice(
    (currentPage - 1) * pageSize,
    currentPage * pageSize
  );

  return (
    <div className="p-4">
      {alert.show && <Alert variant={alert.variant}>{alert.message}</Alert>}

      <div className="d-flex justify-content-between mb-4 align-items-center flex-wrap gap-2">
        <h2 className="m-0">Gestión de Servicios</h2>
        <Button
          variant="primary"
          onClick={() => {
            setCurrentService({ status: 'pendiente' });
            setShowModal(true);
          }}
          className="service-action-btn"
        >
          Crear Servicio
        </Button>
      </div>

      <Form.Control
        type="text"
        placeholder="Buscar por equipo, marca, tecnico, descripción, estado..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="mb-4"
      />

      <Table striped bordered hover responsive>
        <thead>
          <tr>
            <th>ID</th>
            <th>Equipo</th>
            <th>Marca</th>
            <th>Modelo</th>
            <th>Descripción problema</th>
            <th>Técnico</th>
            <th>Estado</th>
            <th>Fecha Solicitud</th>
            <th>Acciones</th>
          </tr>
        </thead>

        <tbody>
          {paginatedServices.length === 0 ? (
            <tr>
              <td colSpan="9" className="text-center">
                No se encontraron servicios.
              </td>
            </tr>
          ) : (
            paginatedServices.map(service => (
              <tr key={service.id}>
                <td>{service.id}</td>
                <td>{service.tipo_equipo}</td>
                <td>{service.marca}</td>
                <td>{service.modelo}</td>
                <td>{service.descripcion_problema}</td>
                <td>{service.tecnico?.name || 'No asignado'}</td>
                <td>{getStatusBadge(service.estado)}</td>
                <td>{service.fecha_solicitud}</td>

                <td>
                  <div className="services-actions">
                    <Button
                      variant="info"
                      size="sm"
                      className="service-action-btn"
                      onClick={() => handleEdit(service)}
                    >
                      <FaEdit className="me-1" /> Editar
                    </Button>

                    <Dropdown align="end">
                      <Dropdown.Toggle
                        variant="outline-primary"
                        size="sm"
                        className="service-action-btn service-dropdown-toggle"
                        id={`dropdown-status-${service.id}`}
                      >
                        <FaExchangeAlt className="me-1" />
                        Cambiar estado
                      </Dropdown.Toggle>

                      <Dropdown.Menu>
                        <Dropdown.Item
                          active={service.estado === 'pendiente'}
                          onClick={() => handleStatusChange(service.id, 'pendiente')}
                        >
                          Pendiente
                        </Dropdown.Item>
                        <Dropdown.Item
                          active={service.estado === 'en_proceso'}
                          onClick={() => handleStatusChange(service.id, 'en_proceso')}
                        >
                          En Proceso
                        </Dropdown.Item>
                        <Dropdown.Item
                          active={service.estado === 'completado'}
                          onClick={() => handleStatusChange(service.id, 'completado')}
                        >
                          Completado
                        </Dropdown.Item>
                        <Dropdown.Item
                          active={service.estado === 'cancelado'}
                          onClick={() => handleStatusChange(service.id, 'cancelado')}
                        >
                          Cancelado
                        </Dropdown.Item>
                      </Dropdown.Menu>
                    </Dropdown>
                  </div>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </Table>

      <div className="pagination-controls d-flex justify-content-between align-items-center my-3">
        <Button
          variant="secondary"
          size="sm"
          disabled={currentPage === 1}
          onClick={() => setCurrentPage(currentPage - 1)}
        >
          Anterior
        </Button>

        <span className="pagination-text">
          Página {currentPage} de {totalPages}
        </span>

        <Button
          variant="secondary"
          size="sm"
          disabled={currentPage === totalPages || totalPages === 0}
          onClick={() => setCurrentPage(currentPage + 1)}
        >
          Siguiente
        </Button>
      </div>

      <Modal
        show={showModal}
        onHide={() => setShowModal(false)}
        size="lg"
        centered
        dialogClassName="ee-modal"
        contentClassName="ee-modal-content"
      >
        <Modal.Header closeButton className="ee-modal-header">
          <Modal.Title className="ee-modal-title">
            {currentService?.id ? 'Editar Servicio' : 'Crear Servicio'}
          </Modal.Title>
        </Modal.Header>

        <Form onSubmit={handleSubmit}>
          {/* ✅ FORZADO DEL SCROLL (esto es lo que lo arregla sí o sí) */}
          <Modal.Body
            className="ee-modal-body"
            style={{ maxHeight: '70vh', overflowY: 'auto', WebkitOverflowScrolling: 'touch' }}
          >
            {/* ... TU FORM COMPLETO (igual que lo tienes) ... */}
            <Form.Group className="mb-3">
              <Form.Label>Tipo de Equipo</Form.Label>
              <Form.Select
                value={currentService?.tipo_equipo || ''}
                onChange={(e) =>
                  setCurrentService({ ...currentService, tipo_equipo: e.target.value })
                }
                required
              >
                <option value="" disabled hidden>Seleccione tipo de equipo</option>
                <option value="lavadora">Lavadora</option>
                <option value="nevera">Nevera</option>
              </Form.Select>
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Marca</Form.Label>
              <Form.Control
                type="text"
                value={currentService?.marca || ''}
                onChange={(e) =>
                  setCurrentService({ ...currentService, marca: e.target.value })
                }
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Modelo</Form.Label>
              <Form.Control
                type="text"
                value={currentService?.modelo || ''}
                onChange={(e) =>
                  setCurrentService({ ...currentService, modelo: e.target.value })
                }
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Descripción del Problema</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                value={currentService?.description || ''}
                onChange={(e) =>
                  setCurrentService({ ...currentService, description: e.target.value })
                }
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Estado</Form.Label>
              <Form.Select
                value={currentService?.status || 'pendiente'}
                onChange={(e) =>
                  setCurrentService({ ...currentService, status: e.target.value })
                }
                required
              >
                <option value="pendiente">Pendiente</option>
                <option value="en_proceso">En Proceso</option>
                <option value="completado">Completado</option>
                <option value="cancelado">Cancelado</option>
              </Form.Select>
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Asignar Técnico</Form.Label>
              <Form.Select
                value={currentService?.technicianId || ''}
                onChange={(e) =>
                  setCurrentService({ ...currentService, technicianId: e.target.value })
                }
              >
                <option value="">No asignado</option>
                {technicians.map(tech => (
                  <option key={tech.id} value={tech.id}>{tech.name}</option>
                ))}
              </Form.Select>

              <Form.Label className="mt-2">Asignar un cliente</Form.Label>
              <Form.Select
                value={currentService?.cientid || ''}
                onChange={(e) =>
                  setCurrentService({ ...currentService, cientid: e.target.value })
                }
              >
                <option value="">Seleccione un cliente</option>
                {cient.filter(client => client?.id).map(client => (
                  <option key={client.id} value={client.id}>{client.name}</option>
                ))}
              </Form.Select>
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Diagnóstico</Form.Label>
              <Form.Control
                as="textarea"
                rows={2}
                value={currentService?.diagnostico || ''}
                onChange={(e) =>
                  setCurrentService({ ...currentService, diagnostico: e.target.value })
                }
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Solución</Form.Label>
              <Form.Control
                as="textarea"
                rows={2}
                value={currentService?.solucion || ''}
                onChange={(e) =>
                  setCurrentService({ ...currentService, solucion: e.target.value })
                }
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Costo</Form.Label>
              <Form.Control
                type="number"
                min="0"
                step="0.01"
                value={currentService?.costo || ''}
                onChange={(e) =>
                  setCurrentService({ ...currentService, costo: e.target.value })
                }
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Fecha Solicitud</Form.Label>
              <Form.Control
                type="date"
                value={currentService?.fecha_solicitud || ''}
                onChange={(e) =>
                  setCurrentService({ ...currentService, fecha_solicitud: e.target.value })
                }
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Fecha Atendido</Form.Label>
              <Form.Control
                type="date"
                value={currentService?.fecha_atendido || ''}
                onChange={(e) =>
                  setCurrentService({ ...currentService, fecha_atendido: e.target.value })
                }
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Fecha Completado</Form.Label>
              <Form.Control
                type="date"
                value={currentService?.fecha_completado || ''}
                onChange={(e) =>
                  setCurrentService({ ...currentService, fecha_completado: e.target.value })
                }
              />
            </Form.Group>
          </Modal.Body>

          <Modal.Footer className="ee-modal-footer">
            <Button variant="secondary" onClick={() => setShowModal(false)}>
              Cancelar
            </Button>
            <Button variant="primary" type="submit">
              {currentService?.id ? 'Actualizar' : 'Crear'}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </div>
  );
};

export default ServicesManagement;
