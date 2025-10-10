<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Material - Biblioteca Comunitaria</title>
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
                <a class="nav-link" href="materiales.jsp">
                    <i class="fas fa-arrow-left"></i> Volver a Materiales
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
                            <i class="fas fa-plus"></i> Agregar Nuevo Material
                        </h3>
                    </div>
                    <div class="card-body">
                        <form id="materialForm">
                            <!-- Tipo de Material -->
                            <div class="mb-3">
                                <label for="tipo" class="form-label">Tipo de Material *</label>
                                <select class="form-select" id="tipo" name="tipo" onchange="toggleFormSections()" required>
                                    <option value="">Seleccione un tipo</option>
                                    <option value="libro">Libro</option>
                                    <option value="articulo">Artículo Especial</option>
                                </select>
                            </div>

                            <!-- Fecha de Registro -->
                            <div class="mb-3">
                                <label for="fechaRegistro" class="form-label">Fecha de Registro *</label>
                                <input type="date" class="form-control" id="fechaRegistro" name="fechaRegistro" required>
                            </div>

                            <!-- Formulario para Libro -->
                            <div id="libroSection" class="form-section">
                                <h5 class="mb-3">Información del Libro</h5>
                                <div class="mb-3">
                                    <label for="titulo" class="form-label">Título *</label>
                                    <input type="text" class="form-control" id="titulo" name="titulo" placeholder="Ingrese el título del libro">
                                </div>
                                <div class="mb-3">
                                    <label for="cantPag" class="form-label">Cantidad de Páginas *</label>
                                    <input type="number" class="form-control" id="cantPag" name="cantPag" min="1" placeholder="Ingrese la cantidad de páginas">
                                </div>
                            </div>

                            <!-- Formulario para Artículo Especial -->
                            <div id="articuloSection" class="form-section">
                                <h5 class="mb-3">Información del Artículo Especial</h5>
                                <div class="mb-3">
                                    <label for="descripcion" class="form-label">Descripción *</label>
                                    <textarea class="form-control" id="descripcion" name="descripcion" rows="3" placeholder="Ingrese una descripción del artículo"></textarea>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="peso" class="form-label">Peso (kg) *</label>
                                            <input type="number" class="form-control" id="peso" name="peso" step="0.01" min="0" placeholder="0.00">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="dimFisica" class="form-label">Dimensión Física (cm) *</label>
                                            <input type="number" class="form-control" id="dimFisica" name="dimFisica" step="0.01" min="0" placeholder="0.00">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Botones -->
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="materiales.jsp" class="btn btn-secondary me-md-2">
                                    <i class="fas fa-times"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Guardar Material
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
        // Set today's date as default
        document.getElementById('fechaRegistro').valueAsDate = new Date();

        function toggleFormSections() {
            const tipo = document.getElementById('tipo').value;
            const libroSection = document.getElementById('libroSection');
            const articuloSection = document.getElementById('articuloSection');
            
            // Hide all sections
            libroSection.classList.remove('active');
            articuloSection.classList.remove('active');
            
            // Show selected section
            if (tipo === 'libro') {
                libroSection.classList.add('active');
            } else if (tipo === 'articulo') {
                articuloSection.classList.add('active');
            }
        }

        document.getElementById('materialForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const formData = new FormData(this);
            formData.append('action', 'add');
            
            // Show loading
            const submitBtn = this.querySelector('button[type="submit"]');
            const originalText = submitBtn.innerHTML;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Guardando...';
            submitBtn.disabled = true;
            
            fetch('materiales', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    showMessage('error', data.error);
                } else {
                    showMessage('success', data.message || 'Material agregado exitosamente');
                    this.reset();
                    document.getElementById('fechaRegistro').valueAsDate = new Date();
                    toggleFormSections();
                }
            })
            .catch(error => {
                showMessage('error', 'Error al agregar material: ' + error.message);
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
