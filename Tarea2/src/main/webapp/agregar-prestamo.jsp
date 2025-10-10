<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Préstamo - Biblioteca Comunitaria</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">
                <i class="fas fa-book"></i> Biblioteca Comunitaria
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="prestamos.jsp">
                    <i class="fas fa-arrow-left"></i> Volver a Préstamos
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
                            <i class="fas fa-hand-holding"></i> Registrar Nuevo Préstamo
                        </h3>
                    </div>
                    <div class="card-body">
                        <form id="prestamoForm">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="fechaSoli" class="form-label">Fecha de Solicitud *</label>
                                        <input type="date" class="form-control" id="fechaSoli" name="fechaSoli" required>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="fechaDev" class="form-label">Fecha de Devolución *</label>
                                        <input type="date" class="form-control" id="fechaDev" name="fechaDev" required>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="estado" class="form-label">Estado *</label>
                                        <select class="form-select" id="estado" name="estado" required>
                                            <option value="">Seleccione un estado</option>
                                            <option value="PENDIENTE">Pendiente</option>
                                            <option value="EN_CURSO">En Curso</option>
                                            <option value="DEVUELTO">Devuelto</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="idMaterial" class="form-label">ID del Material *</label>
                                        <input type="number" class="form-control" id="idMaterial" name="idMaterial" required placeholder="Ingrese el ID del material">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="correoLector" class="form-label">Correo del Lector *</label>
                                        <input type="email" class="form-control" id="correoLector" name="correoLector" required placeholder="lector@ejemplo.com">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="correoBiblio" class="form-label">Correo del Bibliotecario *</label>
                                        <input type="email" class="form-control" id="correoBiblio" name="correoBiblio" required placeholder="bibliotecario@ejemplo.com">
                                    </div>
                                </div>
                            </div>

                            <!-- Botones -->
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="prestamos.jsp" class="btn btn-secondary me-md-2">
                                    <i class="fas fa-times"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Registrar Préstamo
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
        // Set today's date as default for fechaSoli
        document.getElementById('fechaSoli').valueAsDate = new Date();
        
        // Set default fechaDev to 7 days from today
        const today = new Date();
        const nextWeek = new Date(today.getTime() + 7 * 24 * 60 * 60 * 1000);
        document.getElementById('fechaDev').valueAsDate = nextWeek;

        document.getElementById('prestamoForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const formData = new FormData(this);
            formData.append('action', 'add');
            
            // Show loading
            const submitBtn = this.querySelector('button[type="submit"]');
            const originalText = submitBtn.innerHTML;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Registrando...';
            submitBtn.disabled = true;
            
            fetch('prestamos', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    showMessage('error', data.error);
                } else {
                    showMessage('success', data.message || 'Préstamo registrado exitosamente');
                    this.reset();
                    document.getElementById('fechaSoli').valueAsDate = new Date();
                    const today = new Date();
                    const nextWeek = new Date(today.getTime() + 7 * 24 * 60 * 60 * 1000);
                    document.getElementById('fechaDev').valueAsDate = nextWeek;
                }
            })
            .catch(error => {
                showMessage('error', 'Error al registrar préstamo: ' + error.message);
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

