import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Usuario from './components/Usuario';
import Inicio from './components/Inicio';
import LoginRegister from './components/LoginRegister';
import QuienesSomos from './components/QuienesSomos';
import Tecnico from './components/Tecnico';
import AdminDashboard from './components/admin/AdminDashboard';
import UsersManagement from './components/admin/UsersManagement';
import ServicesManagement from './components/admin/ServicesManagement';
import InvoicesManagement from './components/admin/InvoicesManagement';
import ReportsManagement from './components/admin/ReportsManagement';
import ProtectedRoute from './services/ProtectedRoute';

// Componente principal de la aplicación
// Aquí se definen las rutas de la aplicación
function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route
            path="/Usuario"
            element={
              <ProtectedRoute>
                <Usuario />
              </ProtectedRoute>
            }
          />
          <Route path="/" element={<Inicio />} />
          <Route path="/LoginRegister" element={<LoginRegister />} />
          <Route path="/QuienesSomos" element={<QuienesSomos />} />
          <Route
            path="/Tecnico"
            element={
              <ProtectedRoute>
                <Tecnico />
              </ProtectedRoute>
            }
          />

          {/* Rutas del panel de administración */}
          <Route
            path="/admin/*"
            element={
              <ProtectedRoute>
                <AdminDashboard />
              </ProtectedRoute>
            }
          >
            <Route index element={<Navigate to="users" replace />} />
            <Route path="users" element={<UsersManagement />} />
            <Route path="services" element={<ServicesManagement />} />
            <Route path="invoices" element={<InvoicesManagement />} />
            <Route path="reports" element={<ReportsManagement />} />
          </Route>

          {/* Ruta para páginas no encontradas */}
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;