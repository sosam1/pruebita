<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Materiales - Biblioteca Comunitaria</title>
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
                    <i class="fas fa-book"></i> Gestión de Materiales
                </h1>
                
                <!-- Controls -->
                <div class="card mb-4">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <button class="btn btn-primary" onclick="cargarMateriales()">
                                    <i class="fas fa-refresh"></i> Actualizar Lista
                                </button>
                                <a href="agregar-material.jsp" class="btn btn-success">
                                    <i class="fas fa-plus"></i> Agregar Material
                                </a>
                            </div>
                            <div class="col-md-6">
                                <div class="row">
                                    <div class="col-md-6">
                                        <input type="date" id="fechaInicio" class="form-control" placeholder="Fecha Inicio">
                                    </div>
                                    <div class="col-md-6">
                                        <input type="date" id="fechaFin" class="form-control" placeholder="Fecha Fin">
                                        <button class="btn btn-info mt-2" onclick="filtrarPorRango()">
                                            <i class="fas fa-filter"></i> Filtrar por Rango
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
                    <p>Cargando materiales...</p>
                </div>

                <!-- Error -->
                <div class="error alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-triangle"></i>
                    <span id="errorMessage"></span>
                </div>

                <!-- Materials Table -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">
                            <i class="fas fa-list"></i> Lista de Materiales
                        </h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover" id="materialesTable">
                                <thead class="table-dark">
                                    <tr>
                                        <th>ID</th>
                                        <th>Tipo</th>
                                        <th>Título/Descripción</th>
                                        <th>Detalles</th>
                                        <th>Fecha Registro</th>
                                    </tr>
                                </thead>
                                <tbody id="materialesTableBody">
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
        // Load materials on page load
        document.addEventListener('DOMContentLoaded', function() {
            cargarMateriales();
        });

        function cargarMateriales() {
            showLoading(true);
            hideError();
            
            fetch('materiales?action=list')
                .then(response => response.json())
                .then(data => {
                    showLoading(false);
                    mostrarMateriales(data);
                })
                .catch(error => {
                    showLoading(false);
                    showError('Error al cargar materiales: ' + error.message);
                });
        }

        function filtrarPorRango() {
            const fechaInicio = document.getElementById('fechaInicio').value;
            const fechaFin = document.getElementById('fechaFin').value;
            
            if (!fechaInicio || !fechaFin) {
                showError('Por favor seleccione ambas fechas');
                return;
            }
            
            showLoading(true);
            hideError();
            
            fetch(`materiales?action=listByRange&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`)
                .then(response => response.json())
                .then(data => {
                    showLoading(false);
                    mostrarMateriales(data);
                })
                .catch(error => {
                    showLoading(false);
                    showError('Error al filtrar materiales: ' + error.message);
                });
        }

        function mostrarMateriales(materiales) {
            const tbody = document.getElementById('materialesTableBody');
            tbody.innerHTML = '';
            
            if (materiales.length === 0) {
                tbody.innerHTML = '<tr><td colspan="5" class="text-center">No hay materiales disponibles</td></tr>';
                return;
            }
            
            materiales.forEach(material => {
                const row = document.createElement('tr');
                
                let tipo = 'Material';
                let tituloDescripcion = 'N/A';
                let detalles = 'N/A';
                
                if (material.titulo) {
                    tipo = 'Libro';
                    tituloDescripcion = material.titulo;
                    detalles = `${material.cantPag} páginas`;
                } else if (material.descripcion) {
                    tipo = 'Artículo Especial';
                    tituloDescripcion = material.descripcion;
                    detalles = `Peso: ${material.peso}kg, Dimensión: ${material.dimFisica}cm`;
                }
                
                row.innerHTML = `
                    <td>${material.idMaterial}</td>
                    <td><span class="badge bg-primary">${tipo}</span></td>
                    <td>${tituloDescripcion}</td>
                    <td>${detalles}</td>
                    <td>${new Date(material.fechaRegistro).toLocaleDateString()}</td>
                `;
                
                tbody.appendChild(row);
            });
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

