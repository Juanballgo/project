import React, { useState, useEffect } from 'react';
import { Table, Button, Form, Modal, Alert, Badge } from 'react-bootstrap';
import { getInvoices, generateInvoice, updateInvoice } from '../../services/invoiceService';
import { getServices } from '../../services/serviceService';

const InvoicesManagement = () => {
  const [invoices, setInvoices] = useState([]);
  const [services, setServices] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [currentInvoice, setCurrentInvoice] = useState(null);
  const [alert, setAlert] = useState({ show: false, message: '', variant: 'success' });

  useEffect(() => {
    fetchInvoices();
    fetchServices();
  }, []);

  const fetchInvoices = async () => {
    try {
      const response = await getInvoices();
      setInvoices(response.data);
    } catch (error) {
      showAlert('Error al cargar facturas', 'danger');
    }
  };

  const fetchServices = async () => {
    try {
      const response = await getServices({ status: 'completed', hasInvoice: false });
      setServices(response.data);
    } catch (error) {
      showAlert('Error al cargar servicios', 'danger');
    }
  };

  const handleGenerate = (service) => {
    setCurrentInvoice({
      serviceId: service.id,
      clientId: service.clientId,
      amount: service.estimatedCost || 0,
      details: `Factura por servicio técnico #${service.id}`,
      status: 'pending'
    });
    setShowModal(true);
  };

  const handlePrint = (invoiceId) => {
    // Implementar lógica de impresión
    window.print();
    showAlert('Factura enviada a impresión', 'info');
  };

  const handleStatusChange = async (invoiceId, newStatus) => {
    try {
      await updateInvoice(invoiceId, { status: newStatus });
      fetchInvoices();
      showAlert('Estado de la factura actualizado', 'success');
    } catch (error) {
      showAlert('Error al actualizar factura', 'danger');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await generateInvoice(currentInvoice);
      setShowModal(false);
      fetchInvoices();
      fetchServices(); // Actualizar lista de servicios
      showAlert('Factura generada correctamente', 'success');
    } catch (error) {
      showAlert('Error al generar factura', 'danger');
    }
  };

  const showAlert = (message, variant) => {
    setAlert({ show: true, message, variant });
    setTimeout(() => setAlert({ ...alert, show: false }), 3000);
  };

  const getStatusBadge = (status) => {
    const variants = {
      pending: 'warning',
      paid: 'success',
      cancelled: 'danger'
    };
    return <Badge bg={variants[status]}>{status}</Badge>;
  };

  return (
    <div className="p-4">
      {alert.show && <Alert variant={alert.variant}>{alert.message}</Alert>}
      
      <h2 className="mb-4">Gestión de Facturas</h2>

      <h4 className="mt-5 mb-3">Servicios completados sin factura</h4>
      <Table striped bordered hover responsive className="mb-5">
        <thead>
          <tr>
            <th>ID Servicio</th>
            <th>Cliente</th>
            <th>Descripción</th>
            <th>Costo Estimado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {services.map(service => (
            <tr key={service.id}>
              <td>{service.id}</td>
              <td>{service.client?.name}</td>
              <td>{service.description}</td>
              <td>${service.estimatedCost?.toFixed(2) || '0.00'}</td>
              <td>
                <Button variant="success" size="sm" onClick={() => handleGenerate(service)}>
                  Generar Factura
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      <h4 className="mt-5 mb-3">Facturas Generadas</h4>
      <Table striped bordered hover responsive>
        <thead>
          <tr>
            <th>N° Factura</th>
            <th>Fecha</th>
            <th>Cliente</th>
            <th>Servicio</th>
            <th>Monto</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {invoices.map(invoice => (
            <tr key={invoice.id}>
              <td>FAC-{invoice.id.toString().padStart(5, '0')}</td>
              <td>{new Date(invoice.createdAt).toLocaleDateString()}</td>
              <td>{invoice.client?.name}</td>
              <td>Servicio #{invoice.serviceId}</td>
              <td>${invoice.amount.toFixed(2)}</td>
              <td>{getStatusBadge(invoice.status)}</td>
              <td>
                <Button variant="info" size="sm" onClick={() => window.open(`/api/invoices/${invoice.id}/view`, '_blank')}>
                  Ver
                </Button>
                <Button variant="secondary" size="sm" onClick={() => handlePrint(invoice.id)} className="ms-2">
                  Imprimir
                </Button>
                <div className="btn-group ms-2">
                  <Button 
                    variant="outline-success" 
                    size="sm" 
                    onClick={() => handleStatusChange(invoice.id, 'paid')}
                    active={invoice.status === 'paid'}
                  >
                    Pagada
                  </Button>
                  <Button 
                    variant="outline-danger" 
                    size="sm" 
                    onClick={() => handleStatusChange(invoice.id, 'cancelled')}
                    active={invoice.status === 'cancelled'}
                  >
                    Cancelar
                  </Button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      {/* Modal para generar factura */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Generar Factura</Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            <Form.Group className="mb-3">
              <Form.Label>Servicio</Form.Label>
              <Form.Control
                type="text"
                value={`Servicio #${currentInvoice?.serviceId || ''}`}
                readOnly
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Monto</Form.Label>
              <Form.Control
                type="number"
                step="0.01"
                min="0"
                value={currentInvoice?.amount || 0}
                onChange={(e) => setCurrentInvoice({ ...currentInvoice, amount: parseFloat(e.target.value) })}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Detalles</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                value={currentInvoice?.details || ''}
                onChange={(e) => setCurrentInvoice({ ...currentInvoice, details: e.target.value })}
                required
              />
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={() => setShowModal(false)}>Cancelar</Button>
            <Button variant="primary" type="submit">Generar Factura</Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </div>
  );
};

export default InvoicesManagement;