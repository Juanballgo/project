import React from "react";
import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ children }) => {
  // Solo verifica si hay un token en localStorage (puedes cambiar la clave si usas otra)
  const token = localStorage.getItem("auth_token");
  if (!token) {
    return <Navigate to="/LoginRegister" replace />;
  }
  return children;
};

export default ProtectedRoute;