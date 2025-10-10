<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Préstamos - Biblioteca Comunitaria</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .loading {
            display: none;
        }
        .error {
            display: none;
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">
                <i class="fas fa-book"></i> Biblioteca Comunitaria
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="index.jsp">
                    <i class="fas fa-home"></i> Inicio
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <h1 class="mb-4">
                    <i class="fas fa-exchange-alt"></i> Gestión de Préstamos
                </h1>
                
                <!-- Controls -->
                <div class="card mb-4">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <button class="btn btn-primary" onclick="cargarPrestamos()">
                                    <i class="fas fa-refresh"></i> Actualizar Lista
                                </button>
                                <a href="agregar-prestamo.jsp" class="btn btn-success">
                                    <i class="fas fa-plus"></i> Nuevo Préstamo
                                </a>
                            </div>
                            <div class="col-md-6">
                                <div class="row">
                                    <div class="col-md-4">
                                        <button class="btn btn-warning" onclick="cargarPrestamosPendientes()">
                                            <i class="fas fa-clock"></i> Pendientes
                                        </button>
                                    </div>
                                    <div class="col-md-4">
                                        <select class="form-select" id="zonaFilter" onchange="filtrarPorZona()">
                                            <option value="">Filtrar por Zona</option>
                                            <option value="BIBLIOTECA_CENTRAL">Biblioteca Central</option>
                                            <option value="SUCURSAL_ESTE">Sucursal Este</option>
                                            <option value="SUCURSAL_OESTE">Sucursal Oeste</option>
                                            <option value="BIBLIOTECA_INFANTIL">Biblioteca Infantil</option>
                                            <option value="ARCHIVO_GENERAL">Archivo General</option>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <input type="email" class="form-control" id="correoLector" placeholder="Correo del lector">
                                        <button class="btn btn-info mt-2" onclick="filtrarPorLector()">
                                            <i class="fas fa-user"></i> Por Lector
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Loading -->
                <div class="loading text-center">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Cargando...</span>
                    </div>
                    <p>Cargando préstamos...</p>
                </div>

                <!-- Error -->
                <div class="error alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-triangle"></i>
                    <span id="errorMessage"></span>
                </div>

                <!-- Loans Table -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">
                            <i class="fas fa-list"></i> Lista de Préstamos
                        </h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover" id="prestamosTable">
                                <thead class="table-dark">
                                    <tr>
                                        <th>ID</th>
                                        <th>Fecha Solicitud</th>
                                        <th>Fecha Devolución</th>
                                        <th>Estado</th>
                                        <th>Lector</th>
                                        <th>Bibliotecario</th>
                                        <th>Material ID</th>
                                    </tr>
                                </thead>
                                <tbody id="prestamosTableBody">
                                    <!-- Data will be loaded here -->
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Load loans on page load
        document.addEventListener('DOMContentLoaded', function() {
            cargarPrestamos();
        });

        function cargarPrestamos() {
            showLoading(true);
            hideError();
            
            fetch('prestamos?action=list')
                .then(response => response.json())
                .then(data => {
                    showLoading(false);
                    mostrarPrestamos(data);
                })
                .catch(error => {
                    showLoading(false);
                    showError('Error al cargar préstamos: ' + error.message);
                });
        }

        function cargarPrestamosPendientes() {
            showLoading(true);
            hideError();
            
            fetch('prestamos?action=pendientes')
                .then(response => response.json())
                .then(data => {
                    showLoading(false);
                    mostrarPrestamos(data);
                })
                .catch(error => {
                    showLoading(false);
                    showError('Error al cargar préstamos pendientes: ' + error.message);
                });
        }

        function filtrarPorZona() {
            const zona = document.getElementById('zonaFilter').value;
            
            if (!zona) {
                cargarPrestamos();
                return;
            }
            
            showLoading(true);
            hideError();
            
            fetch(`prestamos?action=porZona&zona=${zona}`)
                .then(response => response.json())
                .then(data => {
                    showLoading(false);
                    mostrarPrestamos(data);
                })
                .catch(error => {
                    showLoading(false);
                    showError('Error al filtrar por zona: ' + error.message);
                });
        }

        function filtrarPorLector() {
            const correoLector = document.getElementById('correoLector').value;
            
            if (!correoLector) {
                showError('Por favor ingrese el correo del lector');
                return;
            }
            
            showLoading(true);
            hideError();
            
            fetch(`prestamos?action=activosLector&correoLector=${correoLector}`)
                .then(response => response.json())
                .then(data => {
                    showLoading(false);
                    mostrarPrestamos(data);
                })
                .catch(error => {
                    showLoading(false);
                    showError('Error al filtrar por lector: ' + error.message);
                });
        }

        function mostrarPrestamos(prestamos) {
            const tbody = document.getElementById('prestamosTableBody');
            tbody.innerHTML = '';
            
            if (prestamos.length === 0) {
                tbody.innerHTML = '<tr><td colspan="7" class="text-center">No hay préstamos disponibles</td></tr>';
                return;
            }
            
            prestamos.forEach(prestamo => {
                const row = document.createElement('tr');
                
                const estadoClass = getEstadoClass(prestamo.estadoPres);
                const fechaSoli = new Date(prestamo.fechaSoli).toLocaleDateString();
                const fechaDev = prestamo.fechaDev ? new Date(prestamo.fechaDev).toLocaleDateString() : 'N/A';
                
                row.innerHTML = `
                    <td>${prestamo.idPrestamo}</td>
                    <td>${fechaSoli}</td>
                    <td>${fechaDev}</td>
                    <td><span class="badge ${estadoClass}">${prestamo.estadoPres}</span></td>
                    <td>${prestamo.lector}</td>
                    <td>${prestamo.bibliotecario}</td>
                    <td>${prestamo.material}</td>
                `;
                
                tbody.appendChild(row);
            });
        }

        function getEstadoClass(estado) {
            switch(estado) {
                case 'PENDIENTE':
                    return 'bg-warning';
                case 'EN_CURSO':
                    return 'bg-primary';
                case 'DEVUELTO':
                    return 'bg-success';
                default:
                    return 'bg-secondary';
            }
        }

        function showLoading(show) {
            document.querySelector('.loading').style.display = show ? 'block' : 'none';
        }

        function showError(message) {
            document.getElementById('errorMessage').textContent = message;
            document.querySelector('.error').style.display = 'block';
        }

        function hideError() {
            document.querySelector('.error').style.display = 'none';
        }
    </script>
</body>
</html>

