<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca Comunitaria - Sistema Web</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 100px 0;
        }
        .card-hover {
            transition: transform 0.3s ease;
        }
        .card-hover:hover {
            transform: translateY(-5px);
        }
        .feature-icon {
            font-size: 3rem;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">
                <i class="fas fa-book"></i> Biblioteca Comunitaria
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="#materiales">Materiales</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#usuarios">Usuarios</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#prestamos">Préstamos</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero-section text-center">
        <div class="container">
            <h1 class="display-4 mb-4">
                <i class="fas fa-library"></i> Sistema de Gestión de Biblioteca
            </h1>
            <p class="lead mb-4">
                Gestiona materiales, usuarios y préstamos de manera eficiente
            </p>
            <a href="#materiales" class="btn btn-light btn-lg">
                <i class="fas fa-arrow-down"></i> Comenzar
            </a>
        </div>
    </section>

    <!-- Features Section -->
    <section class="py-5">
        <div class="container">
            <div class="row">
                <div class="col-md-4 mb-4">
                    <div class="card card-hover h-100 text-center">
                        <div class="card-body">
                            <i class="fas fa-book feature-icon text-primary"></i>
                            <h5 class="card-title">Gestión de Materiales</h5>
                            <p class="card-text">
                                Administra libros y artículos especiales de la biblioteca.
                            </p>
                            <a href="materiales.jsp" class="btn btn-primary">
                                <i class="fas fa-eye"></i> Ver Materiales
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="card card-hover h-100 text-center">
                        <div class="card-body">
                            <i class="fas fa-users feature-icon text-success"></i>
                            <h5 class="card-title">Gestión de Usuarios</h5>
                            <p class="card-text">
                                Gestiona lectores y bibliotecarios del sistema.
                            </p>
                            <a href="usuarios.jsp" class="btn btn-success">
                                <i class="fas fa-eye"></i> Ver Usuarios
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="card card-hover h-100 text-center">
                        <div class="card-body">
                            <i class="fas fa-exchange-alt feature-icon text-warning"></i>
                            <h5 class="card-title">Gestión de Préstamos</h5>
                            <p class="card-text">
                                Controla préstamos y devoluciones de materiales.
                            </p>
                            <a href="prestamos.jsp" class="btn btn-warning">
                                <i class="fas fa-eye"></i> Ver Préstamos
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Quick Actions -->
    <section class="py-5 bg-light">
        <div class="container">
            <h2 class="text-center mb-5">Acciones Rápidas</h2>
            <div class="row">
                <div class="col-md-3 mb-3">
                    <a href="agregar-material.jsp" class="btn btn-outline-primary w-100">
                        <i class="fas fa-plus"></i> Agregar Material
                    </a>
                </div>
                <div class="col-md-3 mb-3">
                    <a href="agregar-usuario.jsp" class="btn btn-outline-success w-100">
                        <i class="fas fa-user-plus"></i> Agregar Usuario
                    </a>
                </div>
                <div class="col-md-3 mb-3">
                    <a href="agregar-prestamo.jsp" class="btn btn-outline-warning w-100">
                        <i class="fas fa-hand-holding"></i> Nuevo Préstamo
                    </a>
                </div>
                <div class="col-md-3 mb-3">
                    <a href="reportes.jsp" class="btn btn-outline-info w-100">
                        <i class="fas fa-chart-bar"></i> Reportes
                    </a>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="bg-dark text-white py-4">
        <div class="container text-center">
            <p>&copy; 2024 Biblioteca Comunitaria. Sistema de Gestión Web.</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

