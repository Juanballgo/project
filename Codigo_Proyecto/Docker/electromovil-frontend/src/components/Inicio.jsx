import React from 'react';
import '../assets/Inicio.css';
import imagen1 from '../assets/img/imagen1.jpg';
import imagen2 from '../assets/img/imagen2.webp';
import imagen3 from '../assets/img/imagen3.jpg';
import logoImg from '../assets/img/Logo.png';

// Puedes usar react-icons para mejores íconos sociales
import { FaFacebookF, FaInstagram, FaWhatsapp } from 'react-icons/fa';

const Inicio = () => {
    return (
        <div>
            {/* Header */}
            <header>
                <nav>
                    <a href="/" className="logo">
                        <img src={logoImg} alt="Logo" className="logo-img" />
                        ElectroElite
                    </a>
                    <input type="checkbox" id="menu-toggle" className="menu-toggle" />
                    <label htmlFor="menu-toggle" className="menu-icon">&#9776;</label>
                    <div className="nav-links">
                        <a href="#servicios" className="login-btn">Servicios</a>
                        <a href="#porque-elegirnos" className="login-btn">Por qué Elegirnos</a>
                        <a href="/LoginRegister" className="login-btn">Iniciar Sesión</a>
                        <a href="/LoginRegister#register" className="login-btn">Registrarse</a>
                    </div>
                </nav>
            </header>

            {/* Main content */}
            <main>
                {/* Hero Section */}
                <section className="hero hero-updated">
                    <div className="hero-content">
                        <h1 className="display-4 fw-bold mb-4">
                            <span className="hero-highlight">Expertos</span> en Reparación de Lavadoras y Neveras
                        </h1>
                        <p>
                            Soluciones rápidas y confiables para tus electrodomésticos.<br />
                            <span className="hero-sub">Servicio profesional garantizado las 24 horas.</span>
                        </p>
                        <div className="hero-buttons">
                            <a href="/LoginRegister" className="btn">Solicitar Servicio</a>
                            <a href="/QuienesSomos" className="btn btn-outline">Más Información</a>
                        </div>
                    </div>
                </section>

                {/* Services Section */}
                <section id="servicios" className="servicios">
                    <h2>Nuestros Servicios</h2>
                    <div className="servicios-grid">
                        <div className="servicio-card servicio-card-updated">
                            <span className="servicio-icon"><i className="fas fa-tools"></i></span>
                            <h3>Reparación de Lavadoras</h3>
                            <img src={imagen1} alt="Reparación de Lavadoras" />
                            <p>Reparaciones rápidas y efectivas para todos los modelos de lavadoras.</p>
                        </div>
                        <div className="servicio-card servicio-card-updated">
                            <span className="servicio-icon"><i className="fas fa-snowflake"></i></span>
                            <h3>Reparación de Neveras</h3>
                            <img src={imagen2} alt="Reparación de Neveras" />
                            <p>Servicio especializado en la reparación de neveras y congeladores.</p>
                        </div>
                        <div className="servicio-card servicio-card-updated">
                            <span className="servicio-icon"><i className="fas fa-cogs"></i></span>
                            <h3>Mantenimiento Preventivo</h3>
                            <img src={imagen3} alt="Mantenimiento Preventivo" />
                            <p>Servicio de mantenimiento preventivo para asegurar el buen funcionamiento.</p>
                        </div>
                    </div>
                </section>

                {/* Why Choose Us Section */}
                <section id="porque-elegirnos" className="porque-elegirnos">
                    <h2>¿Por qué Elegirnos?</h2>
                    <div className="razones-grid">
                        <div className="razon-card razon-card-updated">
                            <i className="fas fa-clock"></i>
                            <h3>Servicio Rápido</h3>
                            <p>Atención inmediata y soluciones efectivas en el menor tiempo posible.</p>
                        </div>
                        <div className="razon-card razon-card-updated">
                            <i className="fas fa-thumbs-up"></i>
                            <h3>Calidad Garantizada</h3>
                            <p>Servicios realizados por técnicos capacitados y con experiencia.</p>
                        </div>
                        <div className="razon-card razon-card-updated">
                            <i className="fas fa-money-bill-wave"></i>
                            <h3>Precios Competitivos</h3>
                            <p>Ofrecemos precios justos y competitivos sin comprometer la calidad.</p>
                        </div>
                    </div>
                </section>

                {/* Contact Section */}
                <section id="contacto" className="contacto contacto-updated">
                    <h2>Contacto</h2>
                    <p>¿Tienes alguna pregunta o necesitas asistencia? Contáctanos y te ayudaremos con gusto.</p>
                    <p>Email: <a href="mailto:contacto@ElectroElite.com" className="contact-link">contacto@ElectroElite.com</a></p>
                    <p>Teléfono: <a href="tel:+573223253844" className="contact-link">+57 322 325 3844</a></p>
                    <div className="social-icons-contact">
                        <a href="https://facebook.com/" target="_blank" rel="noopener noreferrer" className="social-btn facebook" title="Facebook">
                            <FaFacebookF />
                        </a>
                        <a href="https://www.instagram.com/servitecnicoselectroelite/?utm_source=qr&igsh=YXZ4bnFkaWNmenNp#" target="_blank" rel="noopener noreferrer" className="social-btn instagram" title="Instagram">
                            <FaInstagram />
                        </a>
                        <a href="https://wa.me/573223253844" target="_blank" rel="noopener noreferrer" className="social-btn whatsapp" title="WhatsApp">
                            <FaWhatsapp />
                        </a>
                    </div>
                </section>
            </main>

            {/* Footer */}
            <footer className="app-footer footer-updated">
                <div className="footer-content">
                    <p>&copy; {new Date().getFullYear()} ElectroElite. Todos los derechos reservados.</p>
                    <div className="footer-links">
                        <a href="#">Términos y condiciones</a>
                        <a href="#">Política de privacidad</a>
                        <a href="https://wa.me/573223253844">Contacto</a>
                    </div>
                </div>
            </footer>
        </div>
    );
};

export default Inicio;