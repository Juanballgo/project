import React, { useState } from "react";
import { Menu, X } from "lucide-react"; // íconos de menú
import { Link } from "react-router-dom"; // si estás usando React Router

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className="bg-white shadow-md px-4 py-3 md:flex md:items-center md:justify-between">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold text-blue-600">ElectroMovil</h1>
        <button className="md:hidden" onClick={() => setIsOpen(!isOpen)}>
          {isOpen ? <X size={24} /> : <Menu size={24} />}
        </button>
      </div>

      <div className={`${isOpen ? "block" : "hidden"} md:flex md:items-center`}>
        <ul className="md:flex space-y-4 md:space-y-0 md:space-x-6 mt-4 md:mt-0">
          <li>
            <a href="#servicios" className="block bg-blue-500 text-white px-4 py-2 rounded-full text-center">Servicios</a>
          </li>
          <li>
            <a href="#porque" className="block bg-blue-500 text-white px-4 py-2 rounded-full text-center">Por qué Elegirnos</a>
          </li>
          <li>
            <a href="#login" className="block bg-blue-500 text-white px-4 py-2 rounded-full text-center">Iniciar Sesión</a>
          </li>
          <li>
            <a href="#register" className="block bg-blue-500 text-white px-4 py-2 rounded-full text-center">Registrarse</a>
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
