<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Usuarios - Biblioteca Comunitaria</title>
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
                    <i class="fas fa-users"></i> Gestión de Usuarios
                </h1>
                
                <!-- Controls -->
                <div class="card mb-4">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <button class="btn btn-primary" onclick="cargarUsuarios()">
                                    <i class="fas fa-refresh"></i> Actualizar Lista
                                </button>
                                <a href="agregar-usuario.jsp" class="btn btn-success">
                                    <i class="fas fa-user-plus"></i> Agregar Usuario
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Loading -->
                <div class="loading text-center">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Cargando...</span>
                    </div>
                    <p>Cargando usuarios...</p>
                </div>

                <!-- Error -->
                <div class="error alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-triangle"></i>
                    <span id="errorMessage"></span>
                </div>

                <!-- Users Table -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">
                            <i class="fas fa-list"></i> Lista de Usuarios
                        </h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover" id="usuariosTable">
                                <thead class="table-dark">
                                    <tr>
                                        <th>Nombre</th>
                                        <th>Correo</th>
                                        <th>Tipo</th>
                                        <th>Detalles</th>
                                        <th>Estado</th>
                                    </tr>
                                </thead>
                                <tbody id="usuariosTableBody">
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
        // Load users on page load
        document.addEventListener('DOMContentLoaded', function() {
            cargarUsuarios();
        });

        function cargarUsuarios() {
            showLoading(true);
            hideError();
            
            fetch('usuarios?action=list')
                .then(response => response.json())
                .then(data => {
                    showLoading(false);
                    mostrarUsuarios(data);
                })
                .catch(error => {
                    showLoading(false);
                    showError('Error al cargar usuarios: ' + error.message);
                });
        }

        function mostrarUsuarios(usuarios) {
            const tbody = document.getElementById('usuariosTableBody');
            tbody.innerHTML = '';
            
            if (usuarios.length === 0) {
                tbody.innerHTML = '<tr><td colspan="5" class="text-center">No hay usuarios disponibles</td></tr>';
                return;
            }
            
            usuarios.forEach(usuario => {
                const row = document.createElement('tr');
                
                let tipo = 'Usuario';
                let detalles = 'N/A';
                let estado = 'N/A';
                
                if (usuario.fechaIngreso) {
                    // Es un Lector
                    tipo = 'Lector';
                    detalles = `Zona: ${usuario.zona}, Dirección: ${usuario.direccion}`;
                    estado = usuario.estadoUsuario;
                } else if (usuario.idEmp !== undefined) {
                    // Es un Bibliotecario
                    tipo = 'Bibliotecario';
                    detalles = `ID Empleado: ${usuario.idEmp}`;
                    estado = 'Activo';
                }
                
                const estadoClass = estado === 'ACTIVO' ? 'bg-success' : 'bg-warning';
                
                row.innerHTML = `
                    <td>${usuario.nombre}</td>
                    <td>${usuario.correo}</td>
                    <td><span class="badge bg-primary">${tipo}</span></td>
                    <td>${detalles}</td>
                    <td><span class="badge ${estadoClass}">${estado}</span></td>
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

