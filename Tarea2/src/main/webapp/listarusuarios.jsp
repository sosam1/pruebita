<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Usuarios - Biblioteca Comunitaria</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .loading {
            display: none;
        }
        .error {
            display: none;
        }
        .user-card {
            transition: transform 0.2s;
        }
        .user-card:hover {
            transform: translateY(-2px);
        }
        .badge-lector {
            background-color: #28a745;
        }
        .badge-bibliotecario {
            background-color: #007bff;
        }
        .badge-activo {
            background-color: #28a745;
        }
        .badge-inactivo {
            background-color: #dc3545;
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
            <div class="navbar-nav ms-auto">
                <span class="navbar-text">
                    <i class="fas fa-users"></i> Lista de Usuarios
                </span>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <h1 class="mb-4">
                    <i class="fas fa-users"></i> Usuarios Registrados
                </h1>
                
                <!-- Controls -->
                <div class="card mb-4">
                    <div class="card-body">
                        <div class="row align-items-center">
                            <div class="col-md-6">
                                <button class="btn btn-primary" onclick="cargarUsuarios()">
                                    <i class="fas fa-sync-alt"></i> Actualizar Lista
                                </button>
                                <span class="ms-3 text-muted" id="totalUsuarios">0 usuarios registrados</span>
                            </div>
                            <div class="col-md-6 text-end">
                                <div class="btn-group" role="group">
                                    <input type="radio" class="btn-check" name="viewMode" id="cardView" autocomplete="off" checked>
                                    <label class="btn btn-outline-secondary" for="cardView">
                                        <i class="fas fa-th"></i> Tarjetas
                                    </label>
                                    
                                    <input type="radio" class="btn-check" name="viewMode" id="tableView" autocomplete="off">
                                    <label class="btn btn-outline-secondary" for="tableView">
                                        <i class="fas fa-table"></i> Tabla
                                    </label>
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
                    <p>Cargando usuarios...</p>
                </div>

                <!-- Error -->
                <div class="error alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-triangle"></i>
                    <span id="errorMessage"></span>
                </div>

                <!-- Users Cards View -->
                <div id="cardsView">
                    <div class="row" id="usuariosCards">
                        <!-- Cards will be loaded here -->
                    </div>
                </div>

                <!-- Users Table View -->
                <div id="tableView" style="display: none;">
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
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        let usuarios = [];
        
        // Load users on page load
        document.addEventListener('DOMContentLoaded', function() {
            cargarUsuarios();
            
            // View mode toggle
            document.getElementById('cardView').addEventListener('change', function() {
                if (this.checked) {
                    document.getElementById('cardsView').style.display = 'block';
                    document.getElementById('tableView').style.display = 'none';
                }
            });
            
            document.getElementById('tableView').addEventListener('change', function() {
                if (this.checked) {
                    document.getElementById('cardsView').style.display = 'none';
                    document.getElementById('tableView').style.display = 'block';
                }
            });
        });

        function cargarUsuarios() {
            showLoading(true);
            hideError();
            
            fetch('usuarios?action=list')
                .then(response => response.json())
                .then(data => {
                    showLoading(false);
                    usuarios = data;
                    mostrarUsuarios(data);
                    actualizarContador(data.length);
                })
                .catch(error => {
                    showLoading(false);
                    showError('Error al cargar usuarios: ' + error.message);
                });
        }

        function mostrarUsuarios(usuarios) {
            mostrarUsuariosCards(usuarios);
            mostrarUsuariosTabla(usuarios);
        }

        function mostrarUsuariosCards(usuarios) {
            const container = document.getElementById('usuariosCards');
            container.innerHTML = '';
            
            if (usuarios.length === 0) {
                container.innerHTML = `
                    <div class="col-12">
                        <div class="alert alert-info text-center">
                            <i class="fas fa-info-circle"></i>
                            No hay usuarios registrados en la base de datos
                        </div>
                    </div>
                `;
                return;
            }
            
            usuarios.forEach(usuario => {
                const card = crearTarjetaUsuario(usuario);
                container.appendChild(card);
            });
        }

        function crearTarjetaUsuario(usuario) {
            const col = document.createElement('div');
            col.className = 'col-md-6 col-lg-4 mb-3';
            
            let tipo = 'Usuario';
            let detalles = 'N/A';
            let estado = 'N/A';
            let tipoClass = 'badge-secondary';
            let estadoClass = 'badge-secondary';
            
            if (usuario.fechaIngreso) {
                // Es un Lector
                tipo = 'Lector';
                tipoClass = 'badge-lector';
                detalles = `Zona: ${usuario.zona || 'N/A'}<br>Dirección: ${usuario.direccion || 'N/A'}`;
                estado = usuario.estadoUsuario || 'N/A';
                estadoClass = estado === 'ACTIVO' ? 'badge-activo' : 'badge-inactivo';
            } else if (usuario.idEmp !== undefined) {
                // Es un Bibliotecario
                tipo = 'Bibliotecario';
                tipoClass = 'badge-bibliotecario';
                detalles = `ID Empleado: ${usuario.idEmp}`;
                estado = 'Activo';
                estadoClass = 'badge-activo';
            }
            
            col.innerHTML = `
                <div class="card user-card h-100">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-start mb-2">
                            <h5 class="card-title mb-0">${usuario.nombre}</h5>
                            <span class="badge ${tipoClass}">${tipo}</span>
                        </div>
                        <p class="card-text">
                            <i class="fas fa-envelope"></i> ${usuario.correo}
                        </p>
                        <div class="mb-2">
                            <small class="text-muted">${detalles}</small>
                        </div>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="badge ${estadoClass}">${estado}</span>
                            <small class="text-muted">
                                <i class="fas fa-calendar"></i> Registrado
                            </small>
                        </div>
                    </div>
                </div>
            `;
            
            return col;
        }

        function mostrarUsuariosTabla(usuarios) {
            const tbody = document.getElementById('usuariosTableBody');
            tbody.innerHTML = '';
            
            if (usuarios.length === 0) {
                tbody.innerHTML = '<tr><td colspan="5" class="text-center">No hay usuarios registrados en la base de datos</td></tr>';
                return;
            }
            
            usuarios.forEach(usuario => {
                const row = document.createElement('tr');
                
                let tipo = 'Usuario';
                let detalles = 'N/A';
                let estado = 'N/A';
                let tipoClass = 'bg-secondary';
                let estadoClass = 'bg-secondary';
                
                if (usuario.fechaIngreso) {
                    // Es un Lector
                    tipo = 'Lector';
                    tipoClass = 'bg-success';
                    detalles = `Zona: ${usuario.zona || 'N/A'}, Dirección: ${usuario.direccion || 'N/A'}`;
                    estado = usuario.estadoUsuario || 'N/A';
                    estadoClass = estado === 'ACTIVO' ? 'bg-success' : 'bg-danger';
                } else if (usuario.idEmp !== undefined) {
                    // Es un Bibliotecario
                    tipo = 'Bibliotecario';
                    tipoClass = 'bg-primary';
                    detalles = `ID Empleado: ${usuario.idEmp}`;
                    estado = 'Activo';
                    estadoClass = 'bg-success';
                }
                
                row.innerHTML = `
                    <td>${usuario.nombre}</td>
                    <td>${usuario.correo}</td>
                    <td><span class="badge ${tipoClass}">${tipo}</span></td>
                    <td>${detalles}</td>
                    <td><span class="badge ${estadoClass}">${estado}</span></td>
                `;
                
                tbody.appendChild(row);
            });
        }

        function actualizarContador(total) {
            document.getElementById('totalUsuarios').textContent = `${total} usuario${total !== 1 ? 's' : ''} registrado${total !== 1 ? 's' : ''}`;
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
