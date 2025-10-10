<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Usuario - Biblioteca Comunitaria</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .form-section {
            display: none;
        }
        .form-section.active {
            display: block;
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
                <a class="nav-link" href="usuarios.jsp">
                    <i class="fas fa-arrow-left"></i> Volver a Usuarios
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h3 class="mb-0">
                            <i class="fas fa-user-plus"></i> Agregar Nuevo Usuario
                        </h3>
                    </div>
                    <div class="card-body">
                        <form id="usuarioForm">
                            <!-- Tipo de Usuario -->
                            <div class="mb-3">
                                <label for="tipo" class="form-label">Tipo de Usuario *</label>
                                <select class="form-select" id="tipo" name="tipo" onchange="toggleFormSections()" required>
                                    <option value="">Seleccione un tipo</option>
                                    <option value="lector">Lector</option>
                                    <option value="bibliotecario">Bibliotecario</option>
                                </select>
                            </div>

                            <!-- Información Básica -->
                            <h5 class="mb-3">Información Básica</h5>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="nombre" class="form-label">Nombre *</label>
                                        <input type="text" class="form-control" id="nombre" name="nombre" required placeholder="Ingrese el nombre completo">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="correo" class="form-label">Correo Electrónico *</label>
                                        <input type="email" class="form-control" id="correo" name="correo" required placeholder="usuario@ejemplo.com">
                                    </div>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Contraseña *</label>
                                <input type="password" class="form-control" id="password" name="password" required placeholder="Ingrese una contraseña">
                            </div>

                            <!-- Formulario para Lector -->
                            <div id="lectorSection" class="form-section">
                                <h5 class="mb-3">Información del Lector</h5>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="fechaIngreso" class="form-label">Fecha de Ingreso *</label>
                                            <input type="date" class="form-control" id="fechaIngreso" name="fechaIngreso">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="estado" class="form-label">Estado *</label>
                                            <select class="form-select" id="estado" name="estado">
                                                <option value="ACTIVO">Activo</option>
                                                <option value="SUSPENDIDO">Suspendido</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="zona" class="form-label">Zona *</label>
                                            <select class="form-select" id="zona" name="zona">
                                                <option value="BIBLIOTECA_CENTRAL">Biblioteca Central</option>
                                                <option value="SUCURSAL_ESTE">Sucursal Este</option>
                                                <option value="SUCURSAL_OESTE">Sucursal Oeste</option>
                                                <option value="BIBLIOTECA_INFANTIL">Biblioteca Infantil</option>
                                                <option value="ARCHIVO_GENERAL">Archivo General</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="direccion" class="form-label">Dirección *</label>
                                            <input type="text" class="form-control" id="direccion" name="direccion" placeholder="Ingrese la dirección">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Formulario para Bibliotecario -->
                            <div id="bibliotecarioSection" class="form-section">
                                <h5 class="mb-3">Información del Bibliotecario</h5>
                                <div class="mb-3">
                                    <label for="idEmp" class="form-label">ID Empleado (Opcional)</label>
                                    <input type="number" class="form-control" id="idEmp" name="idEmp" placeholder="Ingrese el ID del empleado">
                                </div>
                            </div>

                            <!-- Botones -->
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="usuarios.jsp" class="btn btn-secondary me-md-2">
                                    <i class="fas fa-times"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Guardar Usuario
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Success/Error Messages -->
                <div id="messageContainer" class="mt-3"></div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Set today's date as default for fechaIngreso
        document.getElementById('fechaIngreso').valueAsDate = new Date();

        function toggleFormSections() {
            const tipo = document.getElementById('tipo').value;
            const lectorSection = document.getElementById('lectorSection');
            const bibliotecarioSection = document.getElementById('bibliotecarioSection');
            
            // Hide all sections
            lectorSection.classList.remove('active');
            bibliotecarioSection.classList.remove('active');
            
            // Show selected section
            if (tipo === 'lector') {
                lectorSection.classList.add('active');
            } else if (tipo === 'bibliotecario') {
                bibliotecarioSection.classList.add('active');
            }
        }

        document.getElementById('usuarioForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const formData = new FormData(this);
            formData.append('action', 'add');
            
            // Show loading
            const submitBtn = this.querySelector('button[type="submit"]');
            const originalText = submitBtn.innerHTML;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Guardando...';
            submitBtn.disabled = true;
            
            fetch('usuarios', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    showMessage('error', data.error);
                } else {
                    showMessage('success', data.message || 'Usuario agregado exitosamente');
                    this.reset();
                    document.getElementById('fechaIngreso').valueAsDate = new Date();
                    toggleFormSections();
                }
            })
            .catch(error => {
                showMessage('error', 'Error al agregar usuario: ' + error.message);
            })
            .finally(() => {
                submitBtn.innerHTML = originalText;
                submitBtn.disabled = false;
            });
        });

        function showMessage(type, message) {
            const container = document.getElementById('messageContainer');
            const alertClass = type === 'success' ? 'alert-success' : 'alert-danger';
            const icon = type === 'success' ? 'fas fa-check-circle' : 'fas fa-exclamation-triangle';
            
            container.innerHTML = `
                <div class="alert ${alertClass} alert-dismissible fade show" role="alert">
                    <i class="${icon}"></i> ${message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            `;
            
            // Auto-hide success messages after 5 seconds
            if (type === 'success') {
                setTimeout(() => {
                    const alert = container.querySelector('.alert');
                    if (alert) {
                        alert.remove();
                    }
                }, 5000);
            }
        }
    </script>
</body>
</html>

