import React from 'react';
import '../assets/QuienesSomos.css';
import { FaFacebookF, FaInstagram, FaWhatsapp } from 'react-icons/fa';

const QuienesSomos = () => {
    return (
        <div className="qs-bg">
            <header className="qs-header">
                <nav className="qs-navbar">
                    <a href="/" className="qs-logo">ElectroElite</a>
                    <div className="qs-nav-links">
                        <a href="/LoginRegister" className="qs-btn qs-btn-outline">Iniciar Sesión</a>
                    </div>
                </nav>
            </header>

            <main>
                {/* HERO */}
                <section className="qs-hero">
                    <div className="qs-hero-content">
                        <h1>¿Quiénes Somos?</h1>
                        <p>
                            Somos <span className="qs-highlight">ElectroElite</span>, expertos en reparación y mantenimiento de lavadoras y neveras.<br />
                            Nuestro compromiso es tu tranquilidad y la vida útil de tus electrodomésticos.
                        </p>
                    </div>
                </section>

                {/* Misión, Visión, Compromiso */}
                <section className="qs-mvc-section">
                    <div className="qs-mvc-grid">
                        <div className="qs-mvc-card">
                            <h3>Misión</h3>
                            <p>
                                Brindar soluciones técnicas rápidas y confiables para el hogar, con atención personalizada y calidad garantizada.
                            </p>
                        </div>
                        <div className="qs-mvc-card qs-mvc-card-center">
                            <h3>Visión</h3>
                            <p>
                                Ser la empresa líder en servicios técnicos de electrodomésticos, reconocida por la excelencia y la confianza de nuestros clientes.
                            </p>
                        </div>
                        <div className="qs-mvc-card">
                            <h3>Compromiso</h3>
                            <p>
                                Técnicos certificados, atención 24/7 y transparencia en cada diagnóstico y presupuesto.
                            </p>
                        </div>
                    </div>
                </section>

                {/* Reseñas */}
                <section className="qs-reviews-section">
                    <h2>Lo que dicen nuestros clientes</h2>
                    <div className="qs-reviews-grid">
                        <div className="qs-review-card">
                            <div className="qs-review-stars">
                                {[...Array(5)].map((_, i) => (
                                    <span key={i}>★</span>
                                ))}
                            </div>
                            <p>"¡Excelente servicio! Mi nevera quedó como nueva en menos de un día."</p>
                            <span className="qs-review-author">Juan Pérez</span>
                        </div>
                        <div className="qs-review-card">
                            <div className="qs-review-stars">
                                {[...Array(5)].map((_, i) => (
                                    <span key={i}>★</span>
                                ))}
                            </div>
                            <p>"Muy profesionales y atentos. Recomiendo ElectroElite a todos."</p>
                            <span className="qs-review-author">María López</span>
                        </div>
                        <div className="qs-review-card">
                            <div className="qs-review-stars">
                                {[...Array(5)].map((_, i) => (
                                    <span key={i}>★</span>
                                ))}
                            </div>
                            <p>"Rápidos, honestos y con precios justos. ¡Volveré a contratarlos!"</p>
                            <span className="qs-review-author">Carlos Ramírez</span>
                        </div>
                    </div>
                </section>

                {/* Por qué elegirnos */}
                <section className="qs-why-section">
                    <h2>¿Por qué elegirnos?</h2>
                    <div className="qs-why-grid">
                        <div className="qs-why-card">
                            <i className="fas fa-bolt"></i>
                            <h4>Atención Rápida</h4>
                            <p>Soluciones en menos de 24 horas.</p>
                        </div>
                        <div className="qs-why-card">
                            <i className="fas fa-user-shield"></i>
                            <h4>Técnicos Certificados</h4>
                            <p>Personal calificado y de confianza.</p>
                        </div>
                        <div className="qs-why-card">
                            <i className="fas fa-thumbs-up"></i>
                            <h4>Garantía</h4>
                            <p>Respaldo en cada reparación.</p>
                        </div>
                        <div className="qs-why-card">
                            <i className="fas fa-hand-holding-usd"></i>
                            <h4>Precios Justos</h4>
                            <p>Transparencia y sin sorpresas.</p>
                        </div>
                    </div>
                </section>

                {/* Contacto */}
                <section className="qs-contact-section">
                    <h2>Contacto</h2>
                    <p>¿Tienes dudas o necesitas ayuda? ¡Estamos para servirte!</p>
                    <div className="qs-contact-info">
                        <a href="mailto:contacto@ElectroElite.com" className="qs-contact-link">contacto@ElectroElite.com</a>
                        <span> | </span>
                        <a href="tel:+573223253844" className="qs-contact-link">+57 322 325 3844</a>
                    </div>
                    <div className="qs-social-icons">
                        <a href="https://facebook.com/" target="_blank" rel="noopener noreferrer" className="qs-social-btn facebook" title="Facebook">
                            <FaFacebookF />
                        </a>
                        <a href="https://instagram.com/" target="_blank" rel="noopener noreferrer" className="qs-social-btn instagram" title="Instagram">
                            <FaInstagram />
                        </a>
                        <a href="https://wa.me/573223253844" target="_blank" rel="noopener noreferrer" className="qs-social-btn whatsapp" title="WhatsApp">
                            <FaWhatsapp />
                        </a>
                    </div>
                </section>
            </main>
            <footer className="qs-footer">
                <div>
                    <p>&copy; {new Date().getFullYear()} ElectroElite. Todos los derechos reservados.</p>
                </div>
            </footer>
        </div>
    );
};

export default QuienesSomos;